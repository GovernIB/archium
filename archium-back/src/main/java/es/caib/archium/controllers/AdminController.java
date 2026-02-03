package es.caib.archium.controllers;

import es.caib.archium.services.AdminFrontService;
import es.caib.archium.services.ProcedimentFrontService;
import org.primefaces.component.datatable.DataTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.time.temporal.Temporal;
import java.util.*;

@Named("adminController")
@ViewScoped
public class AdminController implements Serializable {

    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    @Inject
    private AdminFrontService adminService;

    private String query;

    private List<Object[]> dades = new ArrayList<>();

    private List<String> columns;

    private boolean actualizarDatos = false;

    public void updateDades() {
        actualizarDatos = true;
        getDades();
    }
    public List<Object[]> getDades() {
        log.debug("Obtenint les dades a partir de la query: " + query + ". actualizarDatos? " + actualizarDatos);
        if (actualizarDatos && query != null && query.trim().toLowerCase().startsWith("select ")) {
            List<Map<String, Object>> resultats = adminService.consulta(query);
            if (resultats != null) {
                List<Object[]> conversio = new ArrayList<>(resultats.size());
                columns = new ArrayList(resultats.get(0).keySet());
                for (Map<String, Object> resultat : resultats) {
                    conversio.add(resultat.values().toArray());
                }
                dades = conversio;
            }
        }
        actualizarDatos = false;
        log.debug("dades.size() = " + (dades != null ? dades.size() : 0));
        return dades;
    }


    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public void setDades(List<Object[]> dades) {
        this.dades = dades;
    }

    public List<String> getColumns() {
        return columns;
    }

    public void setColumns(List<String> columns) {
        this.columns = columns;
    }
}
