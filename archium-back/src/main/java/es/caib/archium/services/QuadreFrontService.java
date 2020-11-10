package es.caib.archium.services;

import es.caib.archium.apirest.constantes.Estado;
import es.caib.archium.apirest.facade.pojos.CuadroClasificacion;
import es.caib.archium.commons.i18n.I18NException;
import es.caib.archium.communication.iface.CSGDCuadroService;
import es.caib.archium.communication.impl.CSGGDCuadroServiceImpl;
import es.caib.archium.ejb.service.QuadreClassificacioService;
import es.caib.archium.objects.QuadreObject;
import es.caib.archium.persistence.model.Quadreclassificacio;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Named
@ApplicationScoped
public class QuadreFrontService {

    protected final Logger log = LoggerFactory.getLogger(QuadreFrontService.class);

    @Inject
    QuadreClassificacioService quadreEjb;

    @Inject
    CSGDCuadroService csgdCuadroService;

    public Boolean checkNameUnique(Long id, String nombre) throws I18NException {

        for (QuadreObject i : this.findAll()) {

            if (id == null) {
                if (i.getNom().equalsIgnoreCase(nombre.trim())) {
                    return false;
                }
            } else {
                if (i.getNom().equalsIgnoreCase(nombre.trim()) && !i.getId().equals(id)) {
                    return false;
                }
            }

        }

        return true;

    }

    public QuadreObject getQuadreById(Long id) throws I18NException {
        try {
            Quadreclassificacio res = quadreEjb.findById(id);

            if (res != null) {
                return new QuadreObject(res);
            }

            return null;
        } catch (NullPointerException e) {
            throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "getQuadreById");
        } catch (Exception e) {
            throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "getQuadreById");
        }

    }

    public List<QuadreObject> findAll() throws I18NException {

        try {
            List<QuadreObject> listaQuadres = new ArrayList<QuadreObject>();

            List<Quadreclassificacio> res = quadreEjb.findAll();

            for (Quadreclassificacio q : res) {
                listaQuadres.add(new QuadreObject(q));
            }

            return listaQuadres;
        } catch (NullPointerException e) {
            throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "findAll");
        } catch (Exception e) {
            throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "findAll");
        }


    }

    @Transactional
    public QuadreObject save(QuadreObject object) throws I18NException {

        try {

            QuadreObject nuevo = new QuadreObject();
            nuevo.setCodi(object.getCodi().trim());
            nuevo.setNom(object.getNom().trim());
            nuevo.setNomCas(object.getNomCas() != null ? object.getNomCas() : null);
            nuevo.setEstat(object.getEstat());
            nuevo.setInici(new Date());
            nuevo.setModificacio(new Date());
            nuevo.setVersio("1.0");
            nuevo.setFi(null);

            Quadreclassificacio nuevoBD = new Quadreclassificacio();
            nuevoBD.setCodi(nuevo.getCodi());
            nuevoBD.setNom(nuevo.getNom());
            nuevoBD.setNomcas(nuevo.getNomCas());
            nuevoBD.setEstat(nuevo.getEstat());
            nuevoBD.setInici(nuevo.getInici());
            nuevoBD.setModificacio(nuevo.getModificacio());
            nuevoBD.setVersio(nuevo.getVersio());
            nuevoBD.setFi(nuevo.getFi());

            return new QuadreObject(quadreEjb.create(nuevoBD));
        } catch (NullPointerException e) {
            throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "save");
        } catch (Exception e) {
            throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "save");
        }


    }

    @Transactional
    public QuadreObject update(QuadreObject object) throws I18NException {

        try {
            Quadreclassificacio baseDato = this.quadreEjb.getReference(object.getId());
            baseDato.setNom(object.getNom());
            baseDato.setCodi(object.getCodi());
            baseDato.setNomcas(object.getNomCas());
            baseDato.setEstat(object.getEstat());
            baseDato.setFi(object.getFi());
            baseDato.setVersio(object.getVersio());
            baseDato.setModificacio(new Date());
            baseDato.setSynchronized(object.getSynchronized());
            baseDato.setNodeId(object.getNodeId());
            return new QuadreObject(quadreEjb.update(baseDato));
        } catch (NullPointerException e) {
            throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "update");
        } catch (Exception e) {
            throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "update");
        }
    }

    @Transactional
    public void synchronize(Long tableId) throws I18NException {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Se sincroniza cuadro"));
        log.error("Procedemos a sincronizar el cuadro de clasificacion con id=[" + tableId + "]");
        log.debug("Obtenemos los datos del cuadro...");
        //Obtenemos el cuadro de clasificacion de base de datos
        Quadreclassificacio cuadrodb = quadreEjb.getReference(tableId);

        CuadroClasificacion cuadroWs = new CuadroClasificacion();
        cuadroWs.setCodigo(cuadrodb.getCodi());
        cuadroWs.setEstado(Estado.getEstado(cuadrodb.getEstat()));

        log.error("Dto creado, llamamos al servicio de sincronizacion");
        String nodeId = null;
        try {
            // Realizamos la llamada para la sincronizacion en Alfresco de los datos
            nodeId = csgdCuadroService.synchronizeClassificationTable(cuadroWs);
        } catch (Exception e) {
            //TODO: la excepcion...
            log.error("Error sincronizando el cuadro: " + e.getMessage());
        }

        log.error("Creado cuadro con id [" + nodeId + "]. Se procede a guardar en base de datos");

        if (StringUtils.isNotEmpty(nodeId)) {
            try {
                //Asignamos el nodo creado en Alfresco al cuadro...
                cuadrodb.setNodeId(nodeId);
                cuadrodb.setSynchronized(true);
                quadreEjb.update(cuadrodb);
                log.debug("Proceso de sincronizacion finalizado con exito");
            } catch (Exception e) {
                // Cambiar i18n
                //TODO: Controlamos y personalizamos la excepcion...
                log.error("Error sincronizando el cuadro: " + e.getMessage());
            }
        } else {
            //TODO: Excepcion genenrica
            throw new I18NException("excepcion.general.Exception",this.getClass().getSimpleName(), "synchronize");
        }
    }
}
