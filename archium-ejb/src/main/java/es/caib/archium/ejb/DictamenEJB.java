package es.caib.archium.ejb;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.Query;

import es.caib.archium.commons.i18n.I18NException;
import es.caib.archium.commons.model.Estat;
import es.caib.archium.ejb.service.DictamenService;
import es.caib.archium.ejb.service.FirmaServidorService;
import es.caib.archium.ejb.service.SerieService;
import es.caib.archium.ejb.service.TaulaValoracioService;
import es.caib.archium.persistence.dao.AbstractDAO;
import es.caib.archium.persistence.model.Dictamen;
import es.caib.archium.persistence.model.Normativa;

/*import org.apache.commons.io.IOUtils;
import org.fundaciobit.pluginsib.core.utils.MetadataConstants;*/

@Stateless
@RolesAllowed({"ACH_GESTOR"})
public class DictamenEJB extends AbstractDAO<Dictamen, Long> implements DictamenService {

    @Inject
    SerieService serieEJB;

    // FIXME: Problema potencial: dictamenEJB té com a dependència taulaValoracioEJB. taulaValoracioEJB té com a dependència dictamenEJB (recíproc)
    @Inject
    TaulaValoracioService taulaValoracioEJB;

//    @Inject
//    FirmaServidorService firmaServidorEJB;

    private String queryTaulaValoracio;


    @PostConstruct
    public void init() {
        InputStream inputStream = DictamenEJB.class.getResourceAsStream("/queries/taulaValoracio.sql");
        queryTaulaValoracio = new BufferedReader(
                new InputStreamReader(inputStream, StandardCharsets.UTF_8))
                .lines()
                .collect(Collectors.joining("\n"));
        //log.debug("Query taula valoració = " + queryTaulaValoracio);
    }

    @Override
    public List<Dictamen> getBySerieId(Long serieId) throws I18NException {
        Map<String, Object> filters = new HashMap<>();
        if (serieId != null) {
            filters.put("achSeriedocumental", serieEJB.getReference(serieId));
            return this.findFiltered(filters);
        } else {
            throw new I18NException("dictamen.getBySerieId.id.null", this.getClass().getSimpleName(), "getBySerieId");
        }
    }

    @Override
    public List<Object[]> getDadesTaulaValoracio(Long dictamenId) {
        log.debug("Obtenint les dades per a generar la taula de valoració del dictamen " + dictamenId);
        Query queryQdC = entityManager.createNativeQuery(this.queryTaulaValoracio);
        String paramCodiDictamen = dictamenId != null ? dictamenId.toString() : null;
        queryQdC.setParameter("idDictamen", paramCodiDictamen);

        List<Object[]> taulesValoracioSenseClob = new ArrayList<>();

        for (Object[] row : (List<Object[]>) queryQdC.getResultList()) {
            for (int i = 0; i < row.length; i++) {
                if (row[i] instanceof Clob) {
                    Clob clob = (Clob) row[i];
                    try {
                        row[i] = clob.getSubString(1, (int) clob.length());
                    } catch (SQLException e) {
                        log.error("Error accediendo a Clob del dictamen " + dictamenId, e);
                    }
                }
            }
            taulesValoracioSenseClob.add(row);
        }
        log.debug("Dades per a generar la taula de valoració del dictamen " + dictamenId + " obtingudes amb èxit.");
        return taulesValoracioSenseClob;
    }

    @Override
    public boolean isPublicable(Dictamen dictamen) {
        return (dictamen != null && Estat.PUBLICABLE.getValue().equalsIgnoreCase(dictamen.getEstat()));
    }

    @Override
    public void publicarDictamen(Dictamen dictamen) {
        // El dictamen s'ha de trobar en estat "publicable" per tal de poder publicar (passar a vigent)
        if (isPublicable(dictamen)) {
            // 1. generar taula de valoració
            InputStream pdfTaulaValoracio = taulaValoracioEJB.getTaulaValoracioPdf(dictamen.getId());

            // 2. Firma en servidor de la taula de valoració
            byte[] targetArray;
            try {
                targetArray = new byte[pdfTaulaValoracio.available()];
                pdfTaulaValoracio.read(targetArray);
                pdfTaulaValoracio.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
//            FirmaSimpleFile fileToSign = new FirmaSimpleFile("Taula de valoració " + dictamen.getCodi(), RestConstants.APPLICATION_PDF, targetArray);
//            InputStream pdfTaulaValoracioSignada = firmaServidorEJB.signar(fileToSign, "AAA - taula valoració aprovada");

            // 3. Guardar la taula de valoració a Alfresco
            // guardarAlfrescoTaulaValoracio(pdfTaulaValoracio, dictamen);

            // 4. Les dades per a generar la taula de valoració s'haurien de guardar a base de dades per informar als arxivers de canvis ulteriors (procediments, etc)
            // TODO. En principi Mateu no troba necessari aquest control, ja que diu que duran molta cura a l'hora d'afegir nous procediments a sèries amb dictamen vigent

            // 4. Modificar el dictamen a estat vigent
            dictamen.setEstat(Estat.VIGENT.getValue());
        } else {
            log.error("No es pot publicar un dictamen en estat diferent a 'Publicable'");
            throw new RuntimeException("No es pot publicar un dictamen en estat diferent a 'Publicable'");
        }
    }

    // Error ~redmine:#6298 Al eliminar un dictamen també s'ha de desvincular la seva normativa associada
    @Override
    public void delete(Long id) throws I18NException {
        Dictamen d = this.entityManager.find(Dictamen.class, id);

        List<Normativa> normativesDictamen = d.getAchNormativas();
        if (normativesDictamen != null && normativesDictamen.size() > 0) {
            log.debug("Del dictamen amb id = " + d.getId() + " llevarem la normativa associada...");
            for (Normativa n : d.getAchNormativas()) {
                log.debug("Llevam el dictamen associat a la normativa (relació dictamen - normativa)... id Normativa = " + n.getId());
                n.getAchDictamens2().remove(d);
            }
        }
        super.delete(id);
    }

	/*
	IArxiuPlugin pluginArxiu;

	protected void setPluginArxiu(IArxiuPlugin plugin) {
		this.pluginArxiu = plugin;
	}

	public IArxiuPlugin getPluginArxiu() {
		if (pluginArxiu == null) {
			Properties properties = new Properties();
			try {
				properties.load(this.getClass().getClassLoader().getResourceAsStream(
						"archium.properties"));
				pluginArxiu = new ArxiuPluginCaib(
						"",
						properties);
			} catch (IOException e) {
				log.error("No ha estat possible configurar el plugin d'arxiu. Properties de configuració no carregades", e);
			}
		}
		return pluginArxiu;
	}
	private void guardarAlfrescoTaulaValoracio(InputStream pdfTaulaValoracio, Dictamen dictamen) {
		IArxiuPlugin pluginArxiu = getPluginArxiu();

		Expedient exp = new Expedient();
		exp.setNom("Expedient amb doc definitiu (" + System.currentTimeMillis() + ")");
		exp.setDescripcio("");

		ExpedientMetadades metadades = new ExpedientMetadades();
		metadades.setSerieDocumental("SARCHIUM");
		metadades.setOrgans(Collections.singletonList("A04019898"));
		metadades.setClassificacio("SIA Inventat");


		Map<String, Object> metadadesAddicionals
				= ImmutableMap.of(MetadatosExpediente.IDENTIFICADOR_PROCEDIMIENTO, "A04019898_PRO_test00000000000000000000000001");
		metadades.setMetadadesAddicionals(metadadesAddicionals);
		exp.setExpedientMetadades(metadades);

		ContingutArxiu expedientCreat = pluginArxiu.expedientCrear(exp);
		if (expedientCreat.getIdentificador() == null) {
			throw new RuntimeException("Impossible crear expedient per guardar la taula de valoració");
		}

		Document d = new Document();
		d.setNom(dictamen.getCodi());
		d.setEstat(DocumentEstat.DEFINITIU);
		d.setDescripcio("");

		Firma f = new Firma();
		f.setPerfil(FirmaPerfil.BES);
		f.setTipus(FirmaTipus.PADES);
		// TODO Verificar que hi ha contingut...
		try {
			f.setContingut(IOUtils.toByteArray(pdfTaulaValoracio));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		f.setTipusMime("application/pdf");
		d.setFirmes(Collections.singletonList(f));

		DocumentMetadades metadadesDoc = new DocumentMetadades();
		metadadesDoc.setSerieDocumental("SDxxx");
		metadadesDoc.setOrgans(Collections.singletonList("A04019898"));
		metadadesDoc.setOrigen(ContingutOrigen.ADMINISTRACIO);
		metadadesDoc.setEstatElaboracio(DocumentEstatElaboracio.ORIGINAL);
		metadadesDoc.setExtensio(DocumentExtensio.PDF);
		metadadesDoc.setFormat(DocumentFormat.PDF);
		metadadesDoc.setTipusDocumental(DocumentTipus.ACORD);

		d.setDocumentMetadades(metadadesDoc);
		ContingutArxiu documentCreat = pluginArxiu.documentCrear(d, expedientCreat.getIdentificador());

		if (documentCreat.getIdentificador() == null) {
			log.error("Error creant el document de taula de valoració. Codi del dictamen: " + dictamen.getCodi());
			throw new RuntimeException("Error creant el document de taula de valoració. Codi del dictamen: " + dictamen.getCodi());

		}
	}

	private InputStream getTaulaValoracioAlfresco(Dictamen dictamen) {
		List<ConsultaFiltre> filtres = new ArrayList<>();
		ConsultaFiltre filtreSerie = new ConsultaFiltre();
		filtreSerie.setMetadada(MetadataConstants.ENI_COD_CLASIFICACION);
		filtreSerie.setOperacio(ConsultaOperacio.IGUAL);
		filtreSerie.setValorOperacio1(dictamen.getAchSeriedocumental().getCodi());
		filtres.add(filtreSerie);

		ConsultaFiltre filtreCodiDictamen = new ConsultaFiltre();
		filtreCodiDictamen.setMetadada("cm:name");
		filtreCodiDictamen.setOperacio(ConsultaOperacio.IGUAL);
		filtreCodiDictamen.setValorOperacio1(dictamen.getCodi());
		filtres.add(filtreCodiDictamen);

		int pagina = 0;
		int itemsPerPagina = 10;

		ConsultaResultat resultat = pluginArxiu.documentConsulta(filtres, pagina, itemsPerPagina, DocumentRepositori.ENI_DOCUMENTO);

		if (resultat.getNumRetornat() != 1) {
			String missatge = "S'esperava trobar un dictamen al gestor documental per al dictamen de codi " + dictamen.getCodi() + ", però s'han trobat " + resultat.getNumRetornat();
			log.error(missatge);
			throw new RuntimeException(missatge);
		}
		ContingutArxiu contingut = resultat.getResultats().get(0);
		Document taulaValoracio = pluginArxiu.documentDetalls(contingut.getIdentificador(), null, true);
		byte[] contingutBytes = taulaValoracio.getFirmes().get(0).getContingut();

		return new ByteArrayInputStream(contingutBytes);
	}*/
    
}
