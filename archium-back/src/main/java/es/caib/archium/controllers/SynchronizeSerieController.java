package es.caib.archium.controllers;

import es.caib.archium.commons.i18n.I18NException;
import es.caib.archium.csgd.apirest.facade.pojos.Serie;
import es.caib.archium.forms.serie.SerieDetailForm;
import es.caib.archium.objects.Document;
import es.caib.archium.objects.FuncioObject;
import es.caib.archium.objects.SerieDocumentalObject;
import es.caib.archium.persistence.model.Funcio;
import es.caib.archium.services.FuncioFrontService;
import es.caib.archium.services.SerieFrontService;
import es.caib.archium.utils.FrontExceptionTranslate;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Map;

/**
 * Se necesita un controlador propio, cambiando el funcionamiento normal de la sincronizacion, para poder mostrar la
 * modal resumen con los datos a sincronizar
 */
@Named("synchronizeSerieController")
@ViewScoped
public class SynchronizeSerieController implements Serializable {

    protected final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private Serie serieWs;
    private Long serieId;

    @Inject
    private SerieFrontService serieService;
    @Inject
    private FuncioFrontService functionService;

    private NuevaSerieController serieController;

    @PostConstruct
    public void init() {
        serieWs = null;
        serieId = null;
        Map<String, Object> viewMap = FacesContext.getCurrentInstance().getViewRoot().getViewMap();
        this.serieController = (NuevaSerieController) viewMap.get("nuevaSerieController");

    }

    /**
     * Prepara los datos a sincronizar y crea la modal de confirmacion
     *
     * @param serie
     */
    public void prepareSync(Document<SerieDocumentalObject> serie) {

        serieId = serie.getObject().getSerieId();
        serieWs = new Serie();
        try {

            // Obtenemos todos los datos a sincronizar
            Long parentId = this.serieService.prepareSync(serieId, serieWs);

            // Buscamos la funcion padre, para poder obtener su codigo y nombre
            FuncioObject parentFunction = this.functionService.findById(parentId);

            // Preparamos el form con los datos a mostrar
            SerieDetailForm form = new SerieDetailForm();
            form.converter(serieWs, parentFunction);
            // Guardamos el form en el contexto para acceder a el desde la vista
            FacesContext.getCurrentInstance().getViewRoot().getViewMap().put("serieDetail", form);

        } catch (I18NException e) {
            LOGGER.error(FrontExceptionTranslate.translate(e, this.serieController.funcBean.getLocale()));
            FacesContext.getCurrentInstance().validationFailed();
            FacesMessage message = new FacesMessage();
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            if ("excepcion.general.Exception".equals(e.getMessage())) {
                message.setSummary(this.serieController.messageBundle.getString("nuevaserie.sinc.error"));
                message.setDetail(this.serieController.messageBundle.getString("nuevaserie.sinc.error"));
            } else {
                message.setSummary(this.serieController.messageBundle.getString(e.getMessage()));
                message.setDetail(this.serieController.messageBundle.getString(e.getMessage()));
            }
            FacesContext.getCurrentInstance().addMessage("validationFailed", message);
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }

    /**
     * Proceso de sincronizacion de la serie en GDIB
     */
    public void synchronize() {
        LOGGER.debug("Se sincroniza la serie: " + this.serieId);

        if (this.serieWs == null || serieId == null) {
            LOGGER.error("No se ha seleccionadio serie a sincronizar");
            FacesContext.getCurrentInstance().validationFailed();
            FacesMessage message = new FacesMessage();
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            message.setSummary(this.serieController.messageBundle.getString("nuevaserie.sinc.error"));
            message.setDetail(this.serieController.messageBundle.getString("nuevaserie.sinc.error"));
            FacesContext.getCurrentInstance().addMessage(null, message);
            return;
        }

        try {
            SerieDocumentalObject upS = this.serieService.synchronize(this.serieWs, this.serieId);
            LOGGER.debug("Proceso de sincronizacion finalizado con exito");
            // Se carga de nuevo la lista de series para mostrar los datos de esta serie actualizados
            this.serieController.funcBean.getNodeFromFunctionId(serieId, "Serie", "update", upS);
            this.serieController.setListaSeries(serieService.getListaSeries());

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(this.serieController.messageBundle.getString("nuevaserie.sinc.ok")));
            cleanData();
        } catch (I18NException e) {
            LOGGER.error(FrontExceptionTranslate.translate(e, this.serieController.funcBean.getLocale()));
            FacesContext.getCurrentInstance().validationFailed();
            FacesMessage message = new FacesMessage();
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            if ("excepcion.general.Exception".equals(e.getMessage())) {
                message.setSummary(this.serieController.messageBundle.getString("nuevaserie.sinc.error"));
                message.setDetail(this.serieController.messageBundle.getString("nuevaserie.sinc.error"));
            } else {
                message.setSummary(this.serieController.messageBundle.getString(e.getMessage()));
                message.setDetail(this.serieController.messageBundle.getString(e.getMessage()));
            }
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }

    public void cancelSynchronize() {
        cleanData();
    }


    public StreamedContent download() {

        InputStream stream = null;
        try {
            stream = serieWs.getBinaryContent().getInputStream();
        } catch (IOException e) {
            LOGGER.error("Se ha producido un error preparando la descarga del archivo: " + e);
            FacesContext.getCurrentInstance().validationFailed();
            FacesMessage message = new FacesMessage();
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            message.setDetail( this.serieController.messageBundle.getString("nuevaserie.descarga.error"));
            message.setSummary( this.serieController.messageBundle.getString("nuevaserie.descarga.error"));
            FacesContext.getCurrentInstance().addMessage(null,message);
            return null;
        }
        return new DefaultStreamedContent(stream, "application/xml", "metadatos_"+serieWs.getCodigo()+".xml");
    }

    private void cleanData() {
        this.serieId = null;
        this.serieWs = null;
    }


    public Serie getSerieWs() {
        return serieWs;
    }

    public void setSerieWs(Serie serieWs) {
        this.serieWs = serieWs;
    }

    public SerieFrontService getSerieService() {
        return serieService;
    }

    public void setSerieService(SerieFrontService serieService) {
        this.serieService = serieService;
    }

    public Long getSerieId() {
        return serieId;
    }

    public void setSerieId(Long serieId) {
        this.serieId = serieId;
    }
}
