package es.caib.archium.controllers;

import es.caib.archium.commons.i18n.I18NException;
import es.caib.archium.commons.i18n.Idioma;
import es.caib.archium.commons.utils.I18nUtils;
import es.caib.archium.commons.utils.StringUtilitats;
import es.caib.archium.ejb.objects.QuadreClassificacioDto;
import es.caib.archium.ejb.objects.SerieDto;
import es.caib.archium.ejb.service.*;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.component.inputtext.InputText;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.el.MethodExpression;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

@Named("cercadorController")
@ViewScoped
public class CercadorController implements Serializable {

    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    ResourceBundle messageBundle;

    private List<SerieDto> qdc = new ArrayList<>();
    private List<SerieDto> listaFilter = new ArrayList<>();

    private SerieDto serieSelected;

    private Boolean error = false;

    private List<QuadreClassificacioDto> llistaQdC = new ArrayList<>();

    private String codiQdCSeleccionat = null;

    private List<String> possiblesAccessos;
    private String accesSelected;
    private List<String> possiblesTipusDictamen;
    private String tipusDictamenSelected;
    private List<String> possiblesAplicacions;

    private List<Boolean> possiblesEnviaments;


    private String appSelected;

    private String enviatSATSelected;

    private List<String> possiblesAmbitsFuncionals;


    private Idioma idiomaSelected = Idioma.CATALA;

    @Inject
    protected QuadreClassificacioService quadreClassificacioEJB;

    @Inject
    protected TipuAccesService tipuAccesEJB;

    @Inject
    protected TipusDictamenService tipusDictamenEJB;

    @Inject
    protected AplicacioService aplicacioEJB;

    @Inject
    protected FuncioService funcioEJB;

    @Inject
    private StringUtilitats stringUtilitats;

    @Inject
    private I18nUtils i18nUtils;

    private boolean actualitzarDades = false;

    @PostConstruct
    public void init() {
        log.debug("Iniciant llistes de valors...");

        // Reset de filtres
        resetFiltres();

        // Establiment del local en funció de l'idioma
        Locale localeSelected = i18nUtils.setLocale(this.idiomaSelected);
        // Change the locale of the view
        FacesContext.getCurrentInstance().getViewRoot().setLocale(localeSelected);
        messageBundle = ResourceBundle.getBundle("messages.messages");

        // Càrrega dels tipus d'accés i dels tipus de dictamen
        try {
            log.debug("Obtenint les llistes de valors en " + idiomaSelected);
            possiblesAccessos = tipuAccesEJB.llistaAccessos(idiomaSelected);
            possiblesTipusDictamen = tipusDictamenEJB.llistaTipusDictamen(idiomaSelected);
            possiblesAplicacions = aplicacioEJB.llistaAplicacions();
            possiblesEnviaments = new ArrayList<>();
            possiblesEnviaments.add(Boolean.TRUE);
            possiblesEnviaments.add(Boolean.FALSE);
            possiblesEnviaments.add(null);
            /*possiblesEnviaments.add(messageBundle.getString("cercador.enviament.si"));
            possiblesEnviaments.add(messageBundle.getString("cercador.enviament.no"));
            possiblesEnviaments.add(messageBundle.getString("cercador.enviament.null"));*/

            possiblesAmbitsFuncionals = funcioEJB.llistaAmbitsFuncionals(idiomaSelected);
        } catch (I18NException e) {
            log.error("Error obtenint els tipus d'accés per al filtre del cercador general");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, messageBundle.getString("cercador.init.error"), null));
        }

        // Càrrega dels quadres de classificació i marca del quadre per defecte
        this.llistaQdC = quadreClassificacioEJB.getLlistaQuadreClassificacio(null, idiomaSelected);

        if (codiQdCSeleccionat == null) {
            Long quadreId = quadreClassificacioEJB.getQuadreClassificacioPerDefecte();
            codiQdCSeleccionat = quadreId.toString();
        }
        actualitzarDades = true;
    }


    public void ChangeEvent(ValueChangeEvent event) {
        try {
            log.debug("Ens han seleccionat el quadre amb codi " + event.getNewValue() + " (fins ara teníem " + codiQdCSeleccionat + ")");
            codiQdCSeleccionat = (String) event.getNewValue();

            resetFiltres();

            actualitzarDades = true;
        } catch (RuntimeException e) {
            log.error("Error actualitzant quadre de classificació del cercador general...", e);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, messageBundle.getString("cercador.init.error"), null));
        }
    }

    public boolean filtreSenseAccents(Object value, Object filter, Locale locale) {
        String filterText = (filter == null) ? null : filter.toString().trim();

        // Volem filtrar per nulls
        if ("-".equals(filterText)) {
            return value == null || value.toString().trim().length() < 1;
        } else if ("+".equals(filterText)) {
            return value != null && value.toString().trim().length() >= 1;
        }

        if (StringUtils.isBlank(filterText)) {
            return true;
        }

        if (value == null) {
            return false;
        }
        return StringUtils.containsIgnoreCase(stringUtilitats.llevarAccents((String) value), stringUtilitats.llevarAccents(filterText));
    }

    public boolean globalFilterFunction(Object value, Object filter, Locale locale) {
        String filterText = (filter == null) ? null : filter.toString().trim().toLowerCase();

        if (StringUtils.isBlank(filterText)) {
            return true;
        }

        SerieDto serieDto = (SerieDto) value;

        // Volem filtrar per nulls
        if ("-".equals(filterText)) {
            return serieDto.getProcediments() == null || serieDto.getProcediments().trim().length() < 1;
        } else if ("+".equals(filterText)) {
            return serieDto.getProcediments() != null && serieDto.getProcediments().trim().length() >= 1;
        }

        return StringUtils.containsIgnoreCase(stringUtilitats.llevarAccents(serieDto.getCodiFuncio()), stringUtilitats.llevarAccents(filterText))
                || StringUtils.containsIgnoreCase(stringUtilitats.llevarAccents(serieDto.getAmbitFuncional()), stringUtilitats.llevarAccents(filterText))
                || StringUtils.containsIgnoreCase(stringUtilitats.llevarAccents(serieDto.getCodiSerie()), stringUtilitats.llevarAccents(filterText))
                || StringUtils.containsIgnoreCase(stringUtilitats.llevarAccents(serieDto.getSeriesArgen()), stringUtilitats.llevarAccents(filterText))
                || StringUtils.containsIgnoreCase(stringUtilitats.llevarAccents(serieDto.getNomSerie()), stringUtilitats.llevarAccents(filterText))
                || StringUtils.containsIgnoreCase(stringUtilitats.llevarAccents(serieDto.getProcediments()), stringUtilitats.llevarAccents(filterText))
                || StringUtils.containsIgnoreCase(stringUtilitats.llevarAccents(serieDto.getAcces()), stringUtilitats.llevarAccents(filterText))
                || StringUtils.containsIgnoreCase(stringUtilitats.llevarAccents(serieDto.getTipusDictamen()), stringUtilitats.llevarAccents(filterText))
                || StringUtils.containsIgnoreCase(stringUtilitats.llevarAccents(serieDto.getAplicacions()), stringUtilitats.llevarAccents(filterText))
                ;
    }

    public int cercaPerEnviat(Object obj1, Object obj2) {
        log.debug("Ordenando obj1: " + obj1 + " y obj2: " + obj2);
        return 0;
    }

    /*public MethodExpression getCercaPerEnviat() {
        FacesContext context = FacesContext.getCurrentInstance();
        return context.getApplication().getExpressionFactory().createMethodExpression(context.getELContext(), "#{cercadorController.cercaPerEnviat}", Integer.class, new Class[]{Object.class, Object.class});
    }*/

    public List<SerieDto> getQdc() {
        log.debug("Obtenint les dades del quadre amb codi: " + codiQdCSeleccionat + " idioma: " + idiomaSelected);
        log.debug("Actualitzant? " + actualitzarDades);
        if (actualitzarDades) {
            log.debug("Actualitzant dades... codi quadre: " + codiQdCSeleccionat + " idioma: " + idiomaSelected);
            qdc = quadreClassificacioEJB.getQuadreClassificacio(Long.parseLong(codiQdCSeleccionat), idiomaSelected, null);
            listaFilter = qdc;
            actualitzarDades = false;
        }
        return qdc;
    }

    public void setQdc(List<SerieDto> qdc) {
        this.qdc = qdc;
    }

    public SerieDto getSerieSelected() {
        return serieSelected;
    }

    public void setSerieSelected(SerieDto serieSelected) {
        this.serieSelected = serieSelected;
    }

    public List<SerieDto> getListaFilter() {
        return listaFilter;
    }

    public void setListaFilter(List<SerieDto> listaFilter) {
        this.listaFilter = listaFilter;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public List<QuadreClassificacioDto> getLlistaQdC() {
        return llistaQdC;
    }

    public void setLlistaQdC(List<QuadreClassificacioDto> llistaQdC) {
        this.llistaQdC = llistaQdC;
    }

    public String getCodiQdCSeleccionat() {
        return codiQdCSeleccionat;
    }

    public void setCodiQdCSeleccionat(String codiQdCSeleccionat) {
        this.codiQdCSeleccionat = codiQdCSeleccionat;
    }

    public List<String> getPossiblesAccessos() {
        return possiblesAccessos;
    }

    public List<String> getPossiblesTipusDictamen() {
        return possiblesTipusDictamen;
    }

    public List<String> getPossiblesAplicacions() {
        return possiblesAplicacions;
    }

    public List<String> getPossiblesAmbitsFuncionals() {
        return possiblesAmbitsFuncionals;
    }

    public Idioma getIdiomaSelected() {
        return idiomaSelected;
    }

    public void setIdiomaSelected(Idioma idiomaSelected) {
        this.idiomaSelected = idiomaSelected;

        // Recarregam les llistes en l'idioma seleccionat
        init();
    }

    public ResourceBundle getMessages() {
        return messageBundle;
    }

    private void resetFiltres() {
        // Reseteamos la tabla por si hubiese algún filtro con valor incorporado...
        final FacesContext fc = FacesContext.getCurrentInstance();
        final DataTable taulaDades = (DataTable) fc.getViewRoot().findComponent(":cercador:Series");
        taulaDades.reset();

        final InputText cercadorGeneral = (InputText) fc.getViewRoot().findComponent(":cercador:Series:globalFilter");
        cercadorGeneral.setValue(null);

        appSelected = null;
        enviatSATSelected = null;
        tipusDictamenSelected = null;
        accesSelected = null;
    }

    public String getAccesSelected() {
        return accesSelected;
    }

    public void setAccesSelected(String accesSelected) {
        this.accesSelected = accesSelected;
    }

    public String getTipusDictamenSelected() {
        return tipusDictamenSelected;
    }

    public void setTipusDictamenSelected(String tipusDictamenSelected) {
        this.tipusDictamenSelected = tipusDictamenSelected;
    }

    public List<Boolean> getPossiblesEnviaments() {
        return possiblesEnviaments;
    }

    public String getAppSelected() {
        return appSelected;
    }

    public void setAppSelected(String appSelected) {
        this.appSelected = appSelected;
    }

    public String getEnviatSATSelected() {
        return enviatSATSelected;
    }

    public void setEnviatSATSelected(String enviatSATSelected) {
        this.enviatSATSelected = enviatSATSelected;
    }
}
