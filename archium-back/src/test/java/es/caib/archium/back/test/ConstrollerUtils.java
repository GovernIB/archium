package es.caib.archium.back.test;

import es.caib.archium.commons.utils.Constants;
import es.caib.archium.csgd.apirest.constantes.*;
import es.caib.archium.csgd.apirest.facade.pojos.CuadroClasificacion;
import es.caib.archium.csgd.apirest.facade.pojos.Serie;
import es.caib.archium.objects.*;
import es.caib.archium.persistence.model.LimitacioNormativaSerie;
import es.caib.archium.persistence.model.Normativa;
import es.caib.archium.persistence.model.Seriedocumental;
import es.caib.archium.services.FuncioFrontService;
import es.caib.archium.services.QuadreFrontService;
import es.caib.archium.services.SerieFrontService;
import org.primefaces.model.DualListModel;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class ConstrollerUtils {
    @Inject
    private QuadreFrontService cuadroService;
    @Inject
    private FuncioFrontService funcionService;
    @Inject
    private SerieFrontService serieService;


    /**
     * Genera dto de un cuadro de clasificacion
     *
     * @return
     */
    protected QuadreObject generarDTOCuadro() {
        QuadreObject dto = new QuadreObject();
        dto.setEstat("Vigent");
        dto.setInici(new Date());
        dto.setNom("CuadroTest");
        dto.setNomCas("CuadroTestCas");
        dto.setSynchronized(false);
        return dto;
    }

    /**
     * Genera el DTO de la funcion con los datos minimos
     * No se informa:
     * -tipoSerie
     * -funcionPadre
     *
     * @return
     */
    protected FuncioObject generarDTOFuncion() {
        FuncioObject dto = new FuncioObject();
        dto.setSynchronized(false);
        dto.setCodi("FunTest01");
        dto.setEstat("Vigent");
        dto.setInici(new Date());
        dto.setModificacio(new Date());
        dto.setOrdre(BigDecimal.ONE);
        dto.setNom("FuncionTest");
        dto.setNomcas("FuncionTestCas");
        return dto;
    }

    /**
     * Genera el dto de serie
     *
     * @return
     */
    protected SerieDocumentalObject crearSerieDTO(Long idFuncio){
        String codi = "serieTest1";
        String nom = "serieTest";
        String nomCas = "serieTestCas";
        Long catalegSeriId = Long.valueOf(1);
        String descripcio = "descripcion";
        String descripcioCas = "";
        String resumMigracio = "";
        String dir3Promotor = null;
        String estat = "Esborrany";
        Long tipusSerieId = null;
        String codiIecisa = "";

        List<AplicacionObject> listaApps = new ArrayList<>();
//        listaApps.add(new AplicacionObject(1,"RIPEA"));
//        listaApps.add(new AplicacionObject(2,"Helium"));

        List<SerieDocumentalObject> relateds = new ArrayList<>();
//        Seriedocumental entity = new Seriedocumental();
//        entity.setId(Long.valueOf(9999));
//        entity.setCodi("SD0999");
//        entity.setNom("Sèrie test 9999");
//        relateds.add(new SerieDocumentalObject(entity));

        List<SerieArgenObject> argensList = new ArrayList<>();
//        argensList.add(new SerieArgenObject(1,"A01","MODIFICACIÓ DE CRÈDIT"));
//        argensList.add(new SerieArgenObject(2,"A02","BORSINS DE PERSONAL"));

        List<LimitacioNormativaSerieObject> listaLNS = new ArrayList<>();
//        NormativaAprobacioObject normativa = new NormativaAprobacioObject();
//        normativa.setId(Long.valueOf(523));
//        normativa.setNom("Ordre TIN/2504/2010, de 20 de setembre, que desenvolupa el RD 39/1997, que aprova el Reglament dels Serveis de Prevenció, en el que es refereix a l'acreditació d'entitats especialitzades com a serveis de prevenció");
//        DualListModel<CausaLimitacioObject> dualListCausas = new DualListModel<>();
//        dualListCausas.setTarget(new ArrayList<>());
//        dualListCausas.getTarget().add(new CausaLimitacioObject(Long.valueOf(1),null,"Seguretat nacional",null));
//        dualListCausas.getTarget().add(new CausaLimitacioObject(Long.valueOf(2),null,"Defensa",null));
//        LimitacioNormativaSerieObject lns = new LimitacioNormativaSerieObject();
//        lns.setNormativa(normativa);
//        lns.setDualListCausas(dualListCausas);
//        listaLNS.add(lns);

        List<NormativaAprobacioObject> normativasList = new ArrayList<>();
//        Normativa n = new Normativa();
//        n.setId(Long.valueOf(523));
//        n.setNom("Ordre TIN/2504/2010, de 20 de setembre, que desenvolupa el RD 39/1997, que aprova el Reglament dels Serveis de Prevenció, en el que es refereix a l'acreditació d'entitats especialitzades com a serveis de prevenció");
//        normativasList.add(new NormativaAprobacioObject(n));
//        n = new Normativa();
//        n.setId(Long.valueOf(524));
//        n.setNom("Real Decreto 459/2010,de 16 de abril, por el que se regulan las condiciones para el reconocimiento de efectos profesionales a títulos extranjeros de especialista en Ciencias de la Salud, obtenidos en Estados no miembros de la UE");
//        normativasList.add(new NormativaAprobacioObject(n));

        ValoracioObject valoracio = new ValoracioObject();
//        valoracio.setAchValorprimaris(new ArrayList<>());
//        valoracio.getAchValorprimaris().add(new ValorPrimariObject(null,null,new TipuValorObject(Long.valueOf(1),"Administratiu","Administrativo","Valor que presenten els documents que es troben en plena vigència administrativa",null),1,"Anys"));
//        valoracio.getAchValorprimaris().add(new ValorPrimariObject(null,null,new TipuValorObject(Long.valueOf(2),"Fiscal","Fiscal","Valor que presenten els documents que tracten d'entrades, sortides i controls dels diners gestionats a una institució",null),2,"Mesos"));
//        valoracio.getAchValorprimaris().add(new ValorPrimariObject(null,null,new TipuValorObject(Long.valueOf(3),"Jurídic","Jurídico","Valor que presenten els documents probatoris que salvaguarden els drets dels governs, institucions i dels individus en general",null),3,"Dies"));
//        valoracio.setAchValorsecundari(new ValorSecundariObject(Long.valueOf(3),"Sense cobertura de qualificació","Sin cobertura de calificación","Ha d'emprar-se aquest valor fins que la sèrie pertinent estigui coberta per una norma de conservació",null));


        return crearSerie(codi, nom, nomCas, catalegSeriId, idFuncio,
                descripcio, descripcioCas, resumMigracio, dir3Promotor, estat, tipusSerieId,
                codiIecisa, listaApps, relateds, argensList, listaLNS, normativasList, valoracio);
    }

    public DictamenObject crearDictamenObject(){
        DictamenObject dto = new DictamenObject();
        dto.setCodi("DicTest01");
//        dto.set
        return dto;
    }


    protected SerieDocumentalObject crearSerie(String codi, String nom, String nomCas, Long catalegSeriId, Long idFuncio,
                         String descripcio, String descripcioCas, String resumMigracio, String dir3Promotor, String estat, Long tipusSerieId,
                         String codiIecisa, List<AplicacionObject> listaApps, List<SerieDocumentalObject> relateds, List<SerieArgenObject> argensList, List<LimitacioNormativaSerieObject> listaLNS
            , List<NormativaAprobacioObject> normativasList, ValoracioObject valoracio){
        // TODO
        return null;
    }

//
//    /**
//     * Llama a sincronizar cuadro de clasaificacion
//     *
//     * @param dto
//     * @return
//     */
//    protected String sincronizarCuadro(CuadroClasificacion dto) {
//        try {
//            return cuadroService.synchronizeNode(dto);
//        } catch (Exception e) {
//            // Devuelve error si no se crea correctamente
//            Assert.fail("Se ha producido un error sincronizando el cuadro " + e);
//        }
//        return null;
//    }
//
//    /**
//     * Llama a sincronizar la funcion, devuelve la excepcion
//     *
//     * @param dto
//     * @return
//     */
//    protected String sincronizarFuncionSinCatch(Funcion dto, String parentId) throws CSGDException {
//        return funcionService.synchronizeNode(dto,parentId);
//    }
//
//    /**
//     * Llama al sincronizar funcion controlando la excepcion
//     *
//     * @param dto
//     * @return
//     */
//    protected String sincronizarFuncion(Funcion dto, String parentId) {
//        try {
//            return this.sincronizarFuncionSinCatch(dto,parentId);
//        } catch (Exception e) {
//            // Devuelve error si no se crea correctamente
//            Assert.fail("Se ha producido un error sincronizando la funcion " + e);
//        }
//        return null;
//    }
//
//    /**
//     * Llama a sincronizar la funcion, devuelve la excepcion
//     *
//     * @param dto
//     * @return
//     */
//    protected String sincronizarSerieSinCatch(Serie dto, String parentId) throws CSGDException {
//        return serieService.synchronizeNode(dto,parentId);
//    }
//
//    /**
//     * Llama al sincronizar serie controlando la excepcion
//     *
//     * @param dto
//     * @return
//     */
//    protected String sincronizarSerie(Serie dto, String parentId) {
//        try {
//           return this.sincronizarSerieSinCatch(dto,parentId);
//        } catch (Exception e) {
//            // Devuelve error si no se crea correctamente
//            Assert.fail("Se ha producido un error sincronizando la serie " + e);
//        }
//        return null;
//    }
//
//    /**
//     * Borra el cuadro indicado
//     *
//     * @param nodeId
//     */
//    protected void borrarCuadro(String nodeId) {
//        RootId rootId = new RootId(nodeId);
//        try {
//            cuadroService.deleteNode(rootId);
//        } catch (Exception e) {
//            // Devuelve error si no se borra correctamente
//            Assert.fail("Se ha producido un error borrando el cuadro " + e);
//        }
//    }
//
//
//
//
//    /**
//     * Borra la funcion y su padre
//     *
//     * @param cuadroId
//     * @param funciones
//     */
//    protected void borrarFuncion(String cuadroId, String... funciones) {
//        for(String funcionId : Arrays.asList(funciones)) {
//            borrarFuncion(funcionId);
//        }
//        borrarCuadro(cuadroId);
//    }
//
//    /**
//     * Borra la funcion
//     *
//     * @param funcionId
//     */
//    protected void borrarFuncion(String funcionId){
//        FunctionId nodeId = new FunctionId(funcionId);
//        try {
//            funcionService.deleteNode(nodeId);
//        } catch (Exception e) {
//            // Devuelve error si no se borra correctamente
//            Assert.fail("Se ha producido un error borrando la funcion " + e);
//        }
//    }
//
//    /**
//     * Borra la serie y sus padres
//     *
//     * @param cuadroId
//     * @param funciones
//     */
//    protected void borrarSerie(String cuadroId,String serieId, String... funciones) {
//        borrarSerie(serieId);
//        for(String funcionId : Arrays.asList(funciones)) {
//            borrarFuncion(funcionId);
//        }
//        borrarCuadro(cuadroId);
//    }
//
//    /**
//     * Borra la serie
//     *
//     * @param serieId
//     */
//    protected void borrarSerie(String serieId){
//        SerieId nodeId = new SerieId(serieId);
//        try {
//            serieService.deleteNode(nodeId);
//        } catch (Exception e) {
//            // Devuelve error si no se borra correctamente
//            Assert.fail("Se ha producido un error borrando la serie " + e);
//        }
//    }
//


}
