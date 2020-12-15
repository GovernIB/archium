package es.caib.archium.services;

import es.caib.archium.commons.i18n.I18NException;
import es.caib.archium.commons.utils.Constants;
import es.caib.archium.communication.exception.CSGDException;
import es.caib.archium.communication.iface.CSGDCuadroService;
import es.caib.archium.csgd.apirest.constantes.Estado;
import es.caib.archium.csgd.apirest.csgd.entidades.comunes.RootId;
import es.caib.archium.csgd.apirest.facade.pojos.CuadroClasificacion;
import es.caib.archium.ejb.service.QuadreClassificacioService;
import es.caib.archium.objects.QuadreObject;
import es.caib.archium.persistence.model.Quadreclassificacio;
import es.caib.archium.validators.CSGDValidator;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJBAccessException;
import javax.enterprise.context.ApplicationScoped;
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

    @Inject
    private CSGDValidator cuadroValidator;

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
            log.error("Se ha producido un nullPointerException en el findAll de los cuadros de clasificacion: "+e);
            throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "findAll");
        } catch (Exception e) {
            log.error("Se ha producido un error en el findAll de los cuadros de clasificacion: "+e);
            throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "findAll");
        }


    }

    @Transactional
    public QuadreObject save(QuadreObject object) throws I18NException {

        try {

            QuadreObject nuevo = new QuadreObject();
            nuevo.setNom(object.getNom().trim());
            nuevo.setNomCas(object.getNomCas() != null ? object.getNomCas() : null);
            nuevo.setEstat(object.getEstat());
            nuevo.setInici(new Date());
            nuevo.setModificacio(new Date());
            nuevo.setVersio("1.0");
            nuevo.setFi(null);

            Quadreclassificacio nuevoBD = new Quadreclassificacio();
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
    public void deleteClassificationTable(Long idClassificationTable) throws I18NException {
        log.debug("Se procede a eliminar el cuadro ["+idClassificationTable+"]");
        Quadreclassificacio cuadro = quadreEjb.getReference(idClassificationTable);
        if(StringUtils.isNotEmpty(cuadro.getNodeId())) {
            log.debug("El cuadro existe en Alfresco, así que procedemos a eliminarlo");
            try {
                this.csgdCuadroService.deleteNode(new RootId(cuadro.getNodeId()));
                log.info("El cuadro ["+idClassificationTable+"] ha sido eliminada de Alfresco");
            } catch (CSGDException e) {
                throw new I18NException(getExceptionI18n(e.getClientErrorCode()), this.getClass().getSimpleName(), "deleteClassificationTable");
            } catch (EJBAccessException e){
                log.error("No se cuenta con los permisos adecuados para realiziar la llamada al csgd");
                throw new I18NException("csgd.permiso.denegado", this.getClass().getSimpleName(), "synchronizeFunction");
            }catch (Exception e) {
                throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "deleteClassificationTable");
            }
        }

        // Si se elimina correctamente de gdib, se borra en la bbdd
        try {
            this.quadreEjb.delete(idClassificationTable);

        } catch (NullPointerException e) {
            log.error("Se ha producido un error de NullPointerException borrando la entidad: "+e);
            throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "deleteClassificationTable");
        } catch (Exception e) {
            log.error("Se ha producido un error desconocido borrando la entidad: "+e);
            throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "deleteClassificationTable");
        }
    }

    @Transactional
    public void synchronize(Long rootId) throws I18NException {
        log.debug("Procedemos a sincronizar el cuadro de clasificacion con id=[" + rootId + "]");
        log.debug("Obtenemos los datos del cuadro...");
        //Obtenemos el cuadro de clasificacion de base de datos
        Quadreclassificacio cuadrodb = null;
        try {
            cuadrodb = quadreEjb.getReference(rootId);
        } catch (Exception e) {
            log.error("Error obteniendo la entidad del cuadro: " + e);
            throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "get entity");
        }

        // Validamos que los datos requeridos por gdib estan informados en el cuadro
        this.cuadroValidator.validarCuadro(cuadrodb);

        CuadroClasificacion cuadroWs = new CuadroClasificacion();
        cuadroWs.setCodigo(cuadrodb.getNomcas());
        cuadroWs.setEstado(Estado.getEstado(cuadrodb.getEstat()));

        log.debug("Dto creado, llamamos al servicio de sincronizacion");
        String nodeId = null;
        try {
            // Realizamos la llamada para la sincronizacion en Alfresco de los datos
            nodeId = csgdCuadroService.synchronizeNode(cuadroWs);
        } catch (CSGDException e) {
            throw new I18NException(getExceptionI18n(e.getClientErrorCode()), this.getClass().getSimpleName(), "synchronizeClassificationTable");
        } catch (EJBAccessException e){
            log.error("No se cuenta con los permisos adecuados para realiziar la llamada al csgd");
            throw new I18NException("csgd.permiso.denegado", this.getClass().getSimpleName(), "synchronizeFunction");
        }catch (Exception e) {
            log.error("Error sincronizando el cuadro: " + e);
            throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "synchronizeClassificationTable");
        }

        log.debug("Creado cuadro con id [" + nodeId + "]. Se procede a guardar en base de datos");

        if (StringUtils.isNotEmpty(nodeId)) {
            try {
                //Asignamos el nodo creado en Alfresco al cuadro...
                cuadrodb.setNodeId(nodeId);
                cuadrodb.setSynchronized(true);
                quadreEjb.update(cuadrodb);
            } catch (I18NException e) {
                log.error("Error sincronizando la informacion del cuadro: " + e);
                throw e;
            } catch (Exception e) {
                log.error("Error sincronizando la informacion del cuadro: " + e);
                throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "update");
            }
        } else {
            log.error("Se ha devuelto null como nodo de alfresco en la sincronizacion del cuadro");
            throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "synchronizeError");
        }
    }




    private String getExceptionI18n(String clientErrorCode) {
        if (Constants.ExceptionConstants.CLIENT_ERROR.getValue().equals(clientErrorCode)) {
            return "csgd.client.error";
        } else if (Constants.ExceptionConstants.ERROR_RETURNED.getValue().equals(clientErrorCode)) {
            return "csgd.error.returned";
        } else if (Constants.ExceptionConstants.GENERIC_ERROR.getValue().equals(clientErrorCode)) {
            return "nuevocuadro.sincro.error";
        } else if (Constants.ExceptionConstants.MALFORMED_RESULT.getValue().equals(clientErrorCode)) {
            return "csgd.malformed.result";
        }
        return "nuevocuadro.sincro.error";
    }
}
