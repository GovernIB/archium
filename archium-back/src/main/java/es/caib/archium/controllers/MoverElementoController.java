package es.caib.archium.controllers;

import es.caib.archium.commons.i18n.I18NException;
import es.caib.archium.objects.Document;
import es.caib.archium.objects.FuncioObject;
import es.caib.archium.objects.QuadreObject;
import es.caib.archium.objects.SerieDocumentalObject;
import es.caib.archium.services.FuncioFrontService;
import es.caib.archium.utils.FrontExceptionTranslate;
import org.primefaces.model.TreeNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Named("moverElementoController")
@ViewScoped
public class MoverElementoController implements Serializable {

    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    @Inject
    private FuncioFrontService servicesFunciones;
    private FuncionesController funcBean = null;

    private List<QuadreObject> listaCuadros;
    private List<FuncioObject> listaFunciones;
    private QuadreObject cuadroSeleccionado;
    private FuncioObject padreElegido;
    private Document<SerieDocumentalObject> serie;
    private Document<FuncioObject> funcion;
    private Boolean isMoverSerie;


    /**
     * Guarda la lista de cuadros disponibles, y actualiza la lista de funciones con el cuadro elegido actualmente
     */
    @PostConstruct
    public void init() {
        serie = null;
        funcion = null;
        Map<String, Object> viewMap = FacesContext.getCurrentInstance().getViewRoot().getViewMap();
        this.funcBean = (FuncionesController) viewMap.get("funciones");
        if (this.funcBean != null) {
            prepararListas();
        }
    }

    private void prepararListas() {
        this.listaCuadros = this.funcBean.getListaCuadrosClasificacion();
        this.cuadroSeleccionado = this.funcBean.getCuadroSeleccionado();
        // Se setea de esta manera para romper la referencia, y que al cambiar la lista de funciones no afecte a la
        // lista del controller
        this.listaFunciones = new ArrayList<>();
        this.funcBean.getListaFunciones().forEach( x -> listaFunciones.add(x));
    }

    /**
     * Al cambiar el cuadro seleccionado, se actualiza la lista de funciones
     */
    public void updateFunctionsList() {

        try {
            listaFunciones = this.servicesFunciones.loadTree(this.cuadroSeleccionado.getId(), null);
            this.padreElegido = null;
            // Si hay funcion seleccionada, comprobamos si esta en la lista y la eliminamos
            checkIfFunctionInList();
        } catch (I18NException e) {
            log.error(FrontExceptionTranslate.translate(e, this.funcBean.getLocale()));
        }
    }


    /**
     * Se actualiza el elemento que se intenta mover, y se prepara el combobox con el padre actual de este elemento
     *
     * @param element a mover
     */
    public void prepararMoverSerie(Document<SerieDocumentalObject> element) {
        serie = element;
        funcion = null;
        isMoverSerie = true;
        prepararListas();
        getParent(element.getParentFunction());
    }

    /**
     * Selecciona el padre del elemento elegido como elemento seleccionado por defecto en el combobox
     *
     * @param parentFunction
     */
    private void getParent(Long parentFunction) {
        if (parentFunction != null) {
            List<FuncioObject> padre = listaFunciones.stream().filter(x -> x.getId().equals(parentFunction)).collect(Collectors.toList());
            if (padre != null && padre.size() == 1) {
                padreElegido = padre.get(0);
            } else {
                padre = null;
            }
        }
    }

    /**
     * Se actualiza el elemento que se intenta mover, y se prepara el combobox con el padre actual de este elemento
     *
     * @param element a mover
     */
    public void prepararMoverFuncion(Document<FuncioObject> element) {
        serie = null;
        funcion = element;
        isMoverSerie = false;
        prepararListas();

        getParent(element.getParentFunction());
        // Eliminamos la funcion propia de la lista
        checkIfFunctionInList();

    }

    /**
     * Comprueba si la funcion seleccionada esta en la lista de funciones del checkbox, y de ser asi la borra
     */
    private void checkIfFunctionInList() {
        if (isMoverSerie != null && !isMoverSerie && funcion != null) {
            if (listaFunciones != null && !listaFunciones.isEmpty()) {
                TreeNode nodo = this.funcBean.getNodeFromFunctionId(funcion.getId(), "Funcio", "insert", null);
                removeNodeAndChildrens(nodo);
            }
        }
    }

    /**
     * Borra la funcion y todos sus hijos de la lista de funciones del combobox
     *
     * @param nodo
     */
    private void removeNodeAndChildrens(TreeNode nodo) {

        listaFunciones.removeIf(x -> ((Document) nodo.getData()).getObject() instanceof FuncioObject
                && x.getId().equals(((Document) nodo.getData()).getId()));
        if (nodo.getChildren() != null && !nodo.getChildren().isEmpty()) {
            nodo.getChildren().forEach(x -> {
                if (((Document) x.getData()).getObject() instanceof FuncioObject) {
                    removeNodeAndChildrens(x);
                }
            });
        }
    }

    public Boolean getMoverSerie() {
        return isMoverSerie;
    }

    public void setMoverSerie(Boolean moverSerie) {
        this.isMoverSerie = moverSerie;
    }

    public Document<SerieDocumentalObject> getSerie() {
        return serie;
    }

    public void setSerie(Document<SerieDocumentalObject> serie) {
        this.serie = serie;
    }

    public Document<FuncioObject> getFuncion() {
        return funcion;
    }

    public void setFuncion(Document<FuncioObject> funcion) {
        this.funcion = funcion;
    }

    public FuncioObject getPadreElegido() {
        return padreElegido;
    }

    public void setPadreElegido(FuncioObject padreElegido) {
        this.padreElegido = padreElegido;
    }

    public QuadreObject getCuadroSeleccionado() {
        return cuadroSeleccionado;
    }

    public void setCuadroSeleccionado(QuadreObject cuadroSeleccionado) {
        this.cuadroSeleccionado = cuadroSeleccionado;
    }

    public FuncionesController getFuncBean() {
        return funcBean;
    }

    public void setFuncBean(FuncionesController funcBean) {
        this.funcBean = funcBean;
    }

    public List<QuadreObject> getListaCuadros() {
        return listaCuadros;
    }

    public void setListaCuadros(List<QuadreObject> listaCuadros) {
        this.listaCuadros = listaCuadros;
    }

    public List<FuncioObject> getListaFunciones() {
        return listaFunciones;
    }

    public void setListaFunciones(List<FuncioObject> listaFunciones) {
        this.listaFunciones = listaFunciones;
    }

    public FuncioFrontService getServicesFunciones() {
        return servicesFunciones;
    }

    public void setServicesFunciones(FuncioFrontService servicesFunciones) {
        this.servicesFunciones = servicesFunciones;
    }
}
