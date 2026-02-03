package es.caib.archium.controllers;

import es.caib.archium.commons.i18n.I18NException;
import es.caib.archium.objects.ColumnModel;
import es.caib.archium.objects.FormControlBuilder;
import es.caib.archium.persistence.model.TaulaMestra;
import es.caib.archium.services.ReflectionColumnModelBuilder;
import es.caib.archium.services.ReflectionDynaFormModelBuilder;
import es.caib.archium.services.TablaMaestraFrontService;
import es.caib.archium.utils.FrontExceptionTranslate;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.component.inputtext.InputText;
import org.primefaces.event.SelectEvent;
import org.primefaces.extensions.model.dynaform.DynaFormModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@Named("tablasMaestras")
@ViewScoped
public class TablasMaestrasController implements Serializable {

    private static final long serialVersionUID = -3157090024972868135L;
    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    Locale locale;

    private Map<String, Class<?>> taulesMestres = new HashMap<>(0);
    private List<ColumnModel> columns = new ArrayList<>(0);

    @Inject
    TablaMaestraFrontService tablaMaestraServices;

    private DynaFormModel formModel;
    private Serializable model;

    // Representa la relació de taules mestres que aniran al selector
    private Map<String, String> listaTabla = new LinkedHashMap<>();
    String tablaSelected = "";

    List<Object> registrosTabla;

    Serializable registroSelected;

    Boolean error = false;

    ResourceBundle messageBundle = ResourceBundle.getBundle("messages.messages");
    
    // Para eliminación
    private Long idAEliminar;
    private String nombreTabla;

    @PostConstruct
    public void init() {

        FacesContext context = FacesContext.getCurrentInstance();
        locale = context.getViewRoot().getLocale();
        registrosTabla = new ArrayList<>();
        try {
            this.listaTabla = iniTablas();
        } catch (I18NException e) {
            log.error(FrontExceptionTranslate.translate(e, this.getLocale()));
            error = true;
        }
    }

    private boolean actualizarDatos = false;
    public List<Object> getData() {
        log.debug("Obteniendo datos: " + tablaSelected);
        log.debug("Is taula selected? " + isTaulaSelected());
        log.debug("RegistrosTabla.size() = " + (registrosTabla != null? registrosTabla.size() : 0));
        log.debug("Actualizar datos? " + actualizarDatos);
        if (isTaulaSelected() && actualizarDatos) {
            registrosTabla = tablaMaestraServices.findAll(taulesMestres.get(tablaSelected));
            actualizarDatos = false;
        } else if (!isTaulaSelected()){
            registrosTabla = new ArrayList<>();
            actualizarDatos = false;
        }
        return registrosTabla;
    }

    public void ChangeEvent(ValueChangeEvent event) {
        try {
            log.debug("Han seleccionado la tabla maestra: " + event.getNewValue());
            if (event.getNewValue() != null) {
                this.setTablaSelected(event.getNewValue().toString());
                Class<?> classeTaulaMestra = taulesMestres.get(this.getTablaSelected());
                if (classeTaulaMestra != null) {
                    columns = new ReflectionColumnModelBuilder(classeTaulaMestra, messageBundle).
                            setExcludedProperties(classeTaulaMestra.getAnnotation(TaulaMestra.class).excludedFields()).build();
                } else {
                    columns = new ArrayList<>();
                }
            }
            // Reseteamos la tabla por si hubiese algún filtro con valor incorporado...
            final FacesContext fc = FacesContext.getCurrentInstance();
            final DataTable taulaDades = (DataTable) fc.getViewRoot().findComponent(":taula:dades");
            taulaDades.reset();
            final InputText cercadorGeneral = (InputText) fc.getViewRoot().findComponent(":taula:dades:globalFilter");
            cercadorGeneral.setValue(null);

            actualizarDatos = true;

        } catch (I18NException e) {
            log.error(FrontExceptionTranslate.translate(e, this.getLocale()));
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            messageBundle.getString("tablasmaestras.cargadatos.error"),
                            messageBundle.getString("tablasmaestras.cargadatos.error")
                    )
            );
        }

    }

    private Map<String, String> iniTablas() throws I18NException {
        taulesMestres = tablaMaestraServices.findTaules();

        Map<String, String> mapaSelectorTaules = taulesMestres.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                                e -> messageBundle.getString(e.getKey())
                        )
                );
        return sortByValue(mapaSelectorTaules);
    }

    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        List<Map.Entry<K, V>> list = new ArrayList<>(map.entrySet());
        list.sort(Map.Entry.comparingByValue());

        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }

        return result;
    }

    public void abrirModal() {
        log.debug("Abriendo ventana modal para insertar nuevo registro en tabla maestra");
        this.registroSelected = null;
        this.model = null;
        inicializarForm();
    }

    @Transactional
    public void save() {
        try {
            Class<?> currentClass = taulesMestres.get(tablaSelected);
            tablaMaestraServices.save(currentClass, model);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(messageBundle.getString("tablasmaestras.insert.ok"), messageBundle.getString("tablasmaestras.insert.ok.detalle")));
            actualizarDatos = true;
        } catch (Exception e) {
            log.error("Problema guardando registro desde modal de tablas maestras", e);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, messageBundle.getString("tablasmaestras.insert.error"), e.getMessage()));
        }
    }

    public void saveUpdate() {
        if (this.registroSelected == null) {
            // Nueva inserción
            save();
        } else { // Update
            update();
        }
    }

    public void update() {
        Class<?> currentEntityClass = taulesMestres.get(tablaSelected);
        try {
            tablaMaestraServices.update(model, currentEntityClass);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(messageBundle.getString("tablasmaestras.update.ok"),messageBundle.getString("tablasmaestras.update.ok.detalle")));
        } catch (I18NException e) {
            log.error("Problema guardando actualización de registro " + currentEntityClass.getName() + " desde modal de tablas maestras", e);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, messageBundle.getString("tablasmaestras.update.error"), e.getMessage()));
        }
    }
    
    public void prepararEliminacion(Long id) {
        this.idAEliminar = id;
        this.nombreTabla = listaTabla.get(tablaSelected);
    }
    
    public void prepararEdicion() {
        try {
            log.debug("Preparando edición. registroSelected = {}", registroSelected);
            initForm();             
        } catch (Exception e) {
            log.error("Error preparando edición", e);
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    messageBundle.getString("tablasmaestras.update.error"),
                    e.getMessage()));
        }
    }
    
    @Transactional
    public void eliminar() {
        try {
        	tablaMaestraServices.delete(
                    taulesMestres.get(tablaSelected), 
                    idAEliminar
                );
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(messageBundle.getString("tablasmaestras.delete.ok"),messageBundle.getString("tablasmaestras.delete.ok.detalle")));
            actualizarDatos = true;
        } catch (I18NException e) {
        	
        	String mensaje = FrontExceptionTranslate.translate(e, messageBundle.getLocale());
        	 
        	 FacesContext.getCurrentInstance().addMessage(null, 
        	            new FacesMessage(FacesMessage.SEVERITY_ERROR, 
        	                messageBundle.getString("tablasmaestras.delete.error"), 
        	                mensaje));  
        	
        } catch (Exception ex) {
        	log.error("Error inesperado tratando de eliminar el registro de la entidad: {}", taulesMestres.get(tablaSelected).getSimpleName());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, messageBundle.getString("tablasmaestras.delete.error.inesperat"), ex.getMessage()));
        }
    }

    private Class<?> initForm() {
        Class<?> currentClass = taulesMestres.get(tablaSelected);
        this.formModel = new ReflectionDynaFormModelBuilder(currentClass, messageBundle)
                .setExcludedProperties(currentClass.getAnnotation(TaulaMestra.class).excludedFields())
                .setPropertySortComparator(getPropertyComparator())
                .putCustomBuilders(getCustomBuilders())
                .build();
        return currentClass;
    }

    private void inicializarForm() {
        log.debug("Inicializando formulario modal de tablas maestras...");
        try {
            Class<?> currentClass = initForm();
            this.model = (Serializable) currentClass.getConstructor().newInstance();
        } catch (Exception e) {
            log.error("Problema inicializando form modal de tablas maestras", e);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, messageBundle.getString("tablasmaestras.init.error"), e.getMessage()));
        }
    }


    public void onRowSelect(SelectEvent event) {
        try {
            inicializarUpdate();
        } catch (I18NException e) {
            log.error(FrontExceptionTranslate.translate(e, this.getLocale()));
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, messageBundle.getString("tablasmaestras.update.abrirUpdate.error"), messageBundle.getString("procediment.update.abrirUpdate.error"));
            FacesContext.getCurrentInstance().addMessage("message-global", msg);
        }

    }

    public void inicializarUpdate() throws I18NException {
        log.debug("Inicializando update de " + tablaSelected + ". registroSelected = " + registroSelected);
        initForm();
        this.model = registroSelected;
    }

    protected Comparator<PropertyDescriptor> getPropertyComparator() {
        return ReflectionDynaFormModelBuilder.DEFAULT_PROPERTY_COMPARATOR;
    }

    protected Map<String, FormControlBuilder> getCustomBuilders() {
        //No Custom
        return new HashMap<String, FormControlBuilder>(0);
    }

    public String getTablaSelected() {
        return tablaSelected;
    }

    public void setTablaSelected(String tablaSelected) throws I18NException {
        this.tablaSelected = tablaSelected;
    }

    public List<ColumnModel> getColumns() {
        return columns;
    }

    public void setColumns(List<ColumnModel> columns) {
        this.columns = columns;
    }

    public Map<String, String> getListaTabla() {
        return listaTabla;
    }

    public void setListaTabla(Map<String, String> listatabla) {
        this.listaTabla = listatabla;
    }

    public DynaFormModel getFormModel() {
        return formModel;
    }

    public Serializable getModel() {
        return model;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public boolean isTaulaSelected() {
        return tablaSelected != null && tablaSelected.length() > 0;
    }


    public Serializable getRegistroSelected() {
        return registroSelected;
    }

    public void setRegistroSelected(Serializable registroSelected) {
        this.registroSelected = registroSelected;
    }

	public Long getIdAEliminar() {
		return idAEliminar;
	}

	public void setIdAEliminar(Long idAEliminar) {
		this.idAEliminar = idAEliminar;
	}

	public String getNombreTabla() {
		return nombreTabla;
	}

	public void setNombreTabla(String nombreTabla) {
		this.nombreTabla = nombreTabla;
	}

	public void setModel(Serializable model) {
		this.model = model;
	}
}
