package es.caib.archium.services;

import es.caib.archium.commons.i18n.I18NException;
import es.caib.archium.commons.utils.Constants;
import es.caib.archium.communication.exception.CSGDException;
import es.caib.archium.communication.iface.CSGDFuncionService;
import es.caib.archium.csgd.apirest.constantes.Estado;
import es.caib.archium.csgd.apirest.facade.pojos.Funcion;
import es.caib.archium.csgd.apirest.facade.pojos.comun.FuncionId;
import es.caib.archium.csgd.apirest.facade.pojos.mover.MoverFuncion;
import es.caib.archium.ejb.service.*;
import es.caib.archium.objects.*;
import es.caib.archium.persistence.model.Dictamen;
import es.caib.archium.persistence.model.Funcio;
import es.caib.archium.persistence.model.Seriedocumental;
import es.caib.archium.persistence.model.Tipusserie;
import es.caib.archium.utils.CalculoUtils;
import es.caib.archium.validators.CSGDValidator;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
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
public class FuncioFrontService {

    protected final Logger log = LoggerFactory.getLogger(FuncioFrontService.class);

    @Inject
    FuncioService funcionesEJB;

    @Inject
    QuadreClassificacioService quadreclassificacioEJB;

    @Inject
    TipusSerieService tipusSerieEJB;

    @Inject
    DictamenService dictamenEJB;

    @Inject
    SerieService serieEJB;

    @Inject
    DictamenFrontService dictamenServices;

    @Inject
    CSGDFuncionService csgdFuncionService;

    @Inject
    private CSGDValidator funcionValidator;

    @Inject
    private CalculoUtils calculoUtils;

    @Transactional
    public TreeNode getContent(QuadreObject quadre) throws I18NException {

        TreeNode root = new DefaultTreeNode(new Document(0, "All", "All", "All", null, null, null, null, null), null);
        this.recursiveTree(quadre, root, null);

        return root;

    }


    private List<Funcio> getFuncioByParent(QuadreObject quadre, Funcio f) throws I18NException {
        try {
            Long idFuncio = (f != null ? f.getId() : null);
            return funcionesEJB.getByParentAndQuadre(idFuncio, quadre.getId());
        } catch (NullPointerException e) {
            throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "getFuncioByParent");
        } catch (Exception e) {
            throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "getFuncioByParent");
        }
    }

    private List<Dictamen> getDictamenBySerie(Seriedocumental s) throws I18NException {
        try {
            return dictamenEJB.getBySerieId(s.getId());
        } catch (NullPointerException e) {
            throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "getDictamenBySerie");
        } catch (Exception e) {
            throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "getDictamenBySerie");
        }
    }

    private List<Seriedocumental> getSeriesByFuncio(Funcio f) throws I18NException {
        try {
            Long idFuncio = (f != null ? f.getId() : null);
            return serieEJB.getByFuncio(idFuncio);
        } catch (NullPointerException e) {
            throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "getSeriesByFuncio");
        } catch (Exception e) {
            throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "getSeriesByFuncio");
        }
    }


    private void recursiveTree(QuadreObject quadre, TreeNode node, Funcio funcio) throws I18NException {

        try {
            List<Funcio> resF = this.getFuncioByParent(quadre, funcio);
            if (funcio != null) {
                List<Seriedocumental> resS = this.getSeriesByFuncio(funcio);
                if (resS != null) {
                    for (Seriedocumental s : resS) {
                        TreeNode serieNode = new DefaultTreeNode(new Document<SerieDocumentalObject>(s.getId(), s.getCodi(), s.getNom(), "Serie",s.getAchFuncio() == null? null :  s.getAchFuncio().getAchQuadreclassificacio() == null ? null : s.getAchFuncio().getAchQuadreclassificacio().getId(), s.getAchFuncio() == null ? null : s.getAchFuncio().getId(), s.getNodeId(), s.isSynchronized(), new SerieDocumentalObject(s)), node);
                        List<Dictamen> resD = this.getDictamenBySerie(s);

                        if (resD.size() > 0) {
                            // En la tabla se marca con un check el dictamen activo, lo comprueba por el booleano dictamenActivo,
                            // calculamos cual es el dictamen activo para activar este booleano
                            Dictamen dictamenActivo = this.calculoUtils.getDictamenActivo(s);
                            for (Dictamen d : resD) {
                                TreeNode dictamenNode = new DefaultTreeNode(new Document<DictamenObject>(d.getId(),
                                        d.getCodi(), d.getAcciodictaminada(), "Dictamen",
                                        d.getId().equals(dictamenActivo.getId()),new DictamenObject(d)), serieNode);
                            }
                        }
                    }
                }
            }


            if (resF.size() > 0) {
                for (Funcio f : resF) {
                    TreeNode funcioNode = new DefaultTreeNode(new Document<FuncioObject>(f.getId(), f.getCodi(), f.getNom(), "Funcio", f.getAchQuadreclassificacio() == null ? null : f.getAchQuadreclassificacio().getId(), f.getAchFuncio() == null ? null : f.getAchFuncio().getId(), f.getNodeId(), f.isSynchronized(), new FuncioObject(f)), node);
                    this.recursiveTree(quadre, funcioNode, f);
                }
            }
        } catch (NullPointerException e) {
            throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "recursiveTree");
        } catch (Exception e) {
            throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "recursiveTree");
        }

    }

    public FuncioObject findById(Long id) throws I18NException {
        try {
            Funcio f = this.funcionesEJB.findById(id);

            if (f != null) {
                return new FuncioObject(f);
            }

            return null;
        } catch (NullPointerException e) {
            throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "findById");
        } catch (Exception e) {
            throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "findById");
        }

    }

    public List<FuncioObject> findAllByQuadre(QuadreObject quadre) throws I18NException {


        try {
            List<FuncioObject> listaFunciones = new ArrayList<FuncioObject>();

            List<Funcio> res = funcionesEJB.getByQuadre(quadre.getId());

            for (Funcio f : res) {
                listaFunciones.add(new FuncioObject(f));
            }

            return listaFunciones;
        } catch (NullPointerException e) {
            throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "findAllByQuadre");
        } catch (Exception e) {
            throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "findAllByQuadre");
        }


    }

    public List<FuncioObject> findAll() throws I18NException {

        try {
            List<FuncioObject> listaFunciones = new ArrayList<FuncioObject>();

            List<Funcio> res = funcionesEJB.findAll();

            for (Funcio f : res) {
                listaFunciones.add(new FuncioObject(f));
            }

            return listaFunciones;
        } catch (NullPointerException e) {
            throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "findAll");
        } catch (Exception e) {
            throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "findAll");
        }


    }

    public List<FuncioObject> loadTree(Long quadreId, Long fromFuncioId) throws I18NException {

        try {
            List<FuncioObject> listaFunciones = new ArrayList<FuncioObject>();

            List<Funcio> res = funcionesEJB.loadTree(quadreId, fromFuncioId);

            this.recursiveTreeFuncios(res, listaFunciones, 0);

            return listaFunciones;
        } catch (NullPointerException e) {
            throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "loadTree");
        } catch (Exception e) {
            throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "loadTree");
        }

    }

    private void recursiveTreeFuncios(List<Funcio> listaDb, List<FuncioObject> lista, int nivel) throws I18NException {

        try {
            for (Funcio f : listaDb) {
                FuncioObject fObj = new FuncioObject(f);
                fObj.setNumTabs(nivel);
                lista.add(fObj);
                this.recursiveTreeFuncios(f.getAchFuncios(), lista, nivel + 1);

            }
        } catch (NullPointerException e) {
            throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "recursiveTreeFuncios");
        } catch (Exception e) {
            throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "recursiveTreeFuncios");
        }

    }

    @Transactional
    public FuncioObject create(FuncioObject funcioObject, QuadreObject fkQuadreObject, FuncioObject fkFuncioObject, TipusSerieObject fkTipusserie) throws I18NException {

        try {
            Funcio funcio = funcioObject.toDbObject(null, null, null);
            funcio.setAchQuadreclassificacio(fkQuadreObject != null ? this.quadreclassificacioEJB.getReference(fkQuadreObject.getId()) : null);
            funcio.setAchFuncio(fkFuncioObject != null ? this.funcionesEJB.getReference(fkFuncioObject.getId()) : null);
            funcio.setAchTipusserie(fkTipusserie != null ? this.tipusSerieEJB.getReference(fkTipusserie.getId()) : null);
            return new FuncioObject(this.funcionesEJB.create(funcio));
        } catch (NullPointerException e) {
            throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "create");
        } catch (Exception e) {
            throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "create");
        }


    }

    @Transactional
    public FuncioObject update(FuncioObject funcioObject) throws I18NException {

        try {
            Funcio funcio = this.funcionesEJB.getReference(funcioObject.getId());
            funcio.setCodi(funcioObject.getCodi());
            funcio.setNom(funcioObject.getNom());
            funcio.setNomcas(funcioObject.getNomcas());
            funcio.setEstat(funcioObject.getEstat());
            funcio.setOrdre(funcioObject.getOrdre());
            funcio.setSynchronized(false);
            if (funcioObject.getFuncioPare() == null)
                funcio.setAchFuncio(null);
            else
                funcio.setAchFuncio(funcionesEJB.getReference(funcioObject.getFuncioPare().getId()));

            funcio.setAchTipusserie(funcioObject.getTipoSerie() != null ? this.tipusSerieEJB.getReference(funcioObject.getTipoSerie().getId()) : null);
            funcio.setModificacio(new Date());
            return new FuncioObject(funcionesEJB.update(funcio));
        } catch (NullPointerException e) {
            throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "update");
        } catch (Exception e) {
            throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "update");
        }

    }

    public List<TipusSerieObject> findAllTipusSerie() throws I18NException {

        try {
            List<TipusSerieObject> listaTipusserie = new ArrayList<TipusSerieObject>();
            List<Tipusserie> res = tipusSerieEJB.findAll();
            for (Tipusserie ts : res) {
                listaTipusserie.add(new TipusSerieObject(ts));
            }
            return listaTipusserie;
        } catch (NullPointerException e) {
            throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "findAllTipusSerie");
        } catch (Exception e) {
            throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "findAllTipusSerie");
        }

    }

    @Transactional
    public void deleteFuncio(Long idFuncio) throws I18NException {
        log.debug("Se procede a eliminar la funcion ["+idFuncio+"]");
        Funcio funcion = funcionesEJB.getReference(idFuncio);

        if(StringUtils.isNotEmpty(funcion.getNodeId())) {
            log.debug("La funcion existe en Alfresco, así que procedemos a eliminarla");
            try {
                this.csgdFuncionService.deleteNode(new FuncionId(funcion.getNodeId()));
                log.info("La funcion ["+idFuncio+"] ha sido eliminada de Alfresco");
            } catch (CSGDException e) {
                throw new I18NException(getExceptionI18n(e.getClientErrorCode()), this.getClass().getSimpleName(), "deleteFunction");
            } catch (EJBAccessException e){
                log.error("No se cuenta con los permisos adecuados para realiziar la llamada al csgd");
                throw new I18NException("csgd.permiso.denegado", this.getClass().getSimpleName(), "synchronizeFunction");
            } catch (Exception e) {
                log.error("Se ha producido un error desconocido en la llamada a Alfresco");
                throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "deleteFunction");
            }
        }

        try {
            this.funcionesEJB.delete(idFuncio);

        } catch (NullPointerException e) {
            log.error("Se ha producido un error de NullPointerException borrando la entidad: "+e);
            throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "deleteFuncio");
        } catch (Exception e) {
            log.error("Se ha producido un error desconocido borrando la entidad: "+e);
            throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "deleteFuncio");
        }
    }

    private String getExceptionI18n(String clientErrorCode) {
        if (Constants.ExceptionConstants.CLIENT_ERROR.getValue().equals(clientErrorCode)) {
            return "csgd.client.error";
        } else if (Constants.ExceptionConstants.ERROR_RETURNED.getValue().equals(clientErrorCode)) {
            return "csgd.error.returned";
        } else if (Constants.ExceptionConstants.GENERIC_ERROR.getValue().equals(clientErrorCode)) {
            return "funciones.sincro.error";
        } else if (Constants.ExceptionConstants.MALFORMED_RESULT.getValue().equals(clientErrorCode)) {
            return "csgd.malformed.result";
        }
        return "funciones.sincro.error";
    }

    /**
     * Proceso de sincronizacion de la funcion en GDIB
     *
     * @param functionId
     */
    @Transactional
    public FuncioObject synchronize(Long functionId) throws I18NException {
        log.debug("Procedemos a sincronizar la funcion con id=[" + functionId + "]");
        log.debug("Obtenemos los datos de la funcion...");

        Funcio funciondb = null;
        try {
            // Recuperamos la funcion de base de datos
            funciondb = funcionesEJB.getReference(functionId);
        } catch (Exception e) {
            log.error("Error obteniendo la entidad de la funcion: " + e);
            throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "get entity");
        }

        String parent = null;
        //Comprobamos que todos sus padres esten sincronizados, obtenemos el padre mas cercano
        parent = this.funcionValidator.checkSynchronizedParents(funciondb);

        log.info("Todos los padres de la funcion se encuentran sincronizados, se procede con la sincronizacion");

        this.funcionValidator.validarFuncion(funciondb);

        Funcion funcion = new Funcion();
        funcion.setCodigo(funciondb.getCodi());
        funcion.setEstado(Estado.getEstado(funciondb.getEstat()));
        funcion.setNodeId(funciondb.getNodeId());
        // Hayamos los hijos de la funcion, para ver si es funcion padre
        funcion.setFuncionPadre(isFuncionPadre(funciondb.getId(),funciondb.getAchQuadreclassificacio().getId()));

        log.debug("Dto creado, llamamos al servicio de sincronizacion");
        String nodeId = null;
        try {
            // Realizamos la llamada para sincronizar la informacion en Alfresco
            nodeId = this.csgdFuncionService.synchronizeNode(funcion,parent);
        } catch (CSGDException e) {
            throw new I18NException(getExceptionI18n(e.getClientErrorCode()), this.getClass().getSimpleName(), "synchronizeFunction");
        } catch (EJBAccessException e){
            log.error("No se cuenta con los permisos adecuados para realiziar la llamada al csgd");
            throw new I18NException("csgd.permiso.denegado", this.getClass().getSimpleName(), "synchronizeFunction");
        } catch (Exception e) {
            log.error("Error sincronizando la funcion: " + e);
            throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "synchronizeFunction");
        }

        log.debug("Creada funcion con id [" + nodeId + "]. Se procede a guardar en base de datos");

        // Asignamos a la funcion el nodo creado en Alfresco
        if (StringUtils.isNotEmpty(nodeId)) {
            try {
                if(StringUtils.trimToNull(funciondb.getNodeId())==null) {
                    funciondb.setNodeId(nodeId);
                }
                funciondb.setSynchronized(true);
                funcionesEJB.update(funciondb);
                return new FuncioObject(funciondb);
            } catch (I18NException e) {
                log.error("Error sincronizando la informacion de la funcion: " + e);
                throw e;
            } catch (Exception e) {
                log.error("Error sincronizando la informacion de la funcion: " + e);
                throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "update");
            }
        } else {
            log.error("Se ha devuelto null como nodo de alfresco en la sincronizacion de la funcion");
            throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "synchronizeError");
        }
    }



    /**
     * Hayamos los hijos de la funcion, si devuelve algún resultado es que es funcion padre
     *
     * @param funcionId
     * @param cuadroId
     * @return
     */
    private Boolean isFuncionPadre(Long funcionId, Long cuadroId) throws I18NException {
        List<Funcio> funcionesHijas = null;
        try {
            funcionesHijas = funcionesEJB.getByParentAndQuadre(funcionId,cuadroId);
        } catch (I18NException e) {
            // Esta excepcion no deberia saltar nunca
            log.error("La funcion con id ["+funcionId+"] no tiene informado su cuadro de clasificacion");
            throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "synchronizeFunction");
        }
        return (funcionesHijas!=null && !funcionesHijas.isEmpty());
    }


    /**
     * Mueve una funcion cambiándola de padre, lo que hara que su arbol completo de hijos se mueva con ella
     *
     *  @param elementId
     * @param rootId
     * @param parentFunctionId
     * @return
     */
    @Transactional
    public FuncioObject moveFunction(long funcionId, Long rootId, Long parentFunctionId) throws I18NException {
        log.info("Se comienza servicio de mover funcion");
        Funcio functiondb = this.funcionesEJB.getReference(funcionId);
        Funcio parentFunction = null;
        // Si tiene funcion padre la obtenemos
        if(parentFunctionId != null){
            // Si la funcion a la que se intenta mover es hija de la actual, no lo permitimos
            if(this.funcionValidator.validarFuncionHija(functiondb,parentFunctionId)){
                log.error("No puedes mover la funcion a una hija, debe cambiar antes el padre de esta");
                throw new I18NException("funcion.mover.hija", this.getClass().getSimpleName(), "moveFunction");
            }

            parentFunction = this.funcionesEJB.getReference(parentFunctionId);
            log.debug("Obtenida funcion padre ["+parentFunction.getCodi()+"]");
        }

        // El if es por eficiencia, si es el mismo cuadro nos ahorramos volver a buscarlo, sino le seteamos el nuevo,
        // si tiene una funcion padre, el cuadro sera el de esa funcion, por lo que tambien nos ahorramos realizar la busqueda
        if(!functiondb.getAchQuadreclassificacio().getId().equals(rootId)) {
            if(parentFunction!=null){
                // Hacemos esta comprobacion para verificar la integridad de los datos, ya que el cuadro elegido deberia
                // ser el mismo que al que pertenece la funcion padre al que se movera
                if(!parentFunction.getAchQuadreclassificacio().getId().equals(rootId)){
                    log.error("Datos inconsistentes. El cuadro elegido no es el mismo que el cuadro del padre elegido");
                    throw new I18NException("funcion.mover.parent.error", this.getClass().getSimpleName(), "moveFunction");
                }
                functiondb.setAchQuadreclassificacio(parentFunction.getAchQuadreclassificacio());
            }else {
                functiondb.setAchQuadreclassificacio(quadreclassificacioEJB.getReference(rootId));
            }
        }
        functiondb.setAchFuncio(parentFunction);

        // Si la funcion tiene asignado un nodeId en alfresco, procedemos a actualizarla
        if(StringUtils.trimToNull(functiondb.getNodeId())!=null) {
            log.debug("Se procede a sincronizar con Alfresco el movimiento de la funcion");
            // Preparamos el dto para enviar a GDIB
            MoverFuncion moveFunction = new MoverFuncion();
            moveFunction.setParentId(functiondb.getAchFuncio() == null ? functiondb.getAchQuadreclassificacio().getNodeId()
                    : functiondb.getAchFuncio().getNodeId());
            moveFunction.setNodoId(functiondb.getNodeId());

            try {
                this.csgdFuncionService.moveNode(moveFunction);
            } catch (CSGDException e) {
                throw new I18NException(getExceptionI18n(e.getClientErrorCode()), this.getClass().getSimpleName(), "moveFunction");
            } catch (EJBAccessException e) {
                log.error("No se cuenta con los permisos adecuados para realiziar la llamada al csgd");
                throw new I18NException("csgd.permiso.denegado", this.getClass().getSimpleName(), "moveFunction");
            } catch (Exception e) {
                log.error("Error moviendo la funcion: " + e);
                throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "moveFunction");
            }

            log.debug("Completado proceso de mover en el CSGD, se procede a guardar en base de datos");
        }

        // Modificamos la funcion
        try {
            functiondb = this.funcionesEJB.update(functiondb);
            log.debug("funcion modificada");
        } catch (I18NException e) {
            log.error("Error moviendo la funcion: " + e);
            throw e;
        } catch (Exception e) {
            log.error("Error moviendo la funcion: " + e);
            throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "update");
        }


        return new FuncioObject(functiondb);
    }
}
