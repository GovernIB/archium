package es.caib.archium.services;

import es.caib.archium.ejb.service.AplicacioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.*;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.*;

@Named("AdminService")
@ApplicationScoped
public class AdminFrontService {

    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    protected ResourceBundle messageBundle = ResourceBundle.getBundle("messages.messages");

    @PersistenceContext
    protected EntityManager entityManager;

    public List<Map<String, Object>> consulta(String query) {
        if (query == null || !query.toLowerCase().startsWith("select ")) {
            log.error("S'ha intentat executar la següent query: " + query + ". Només es permeten fer selects");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(messageBundle.getString("admin.query.error")));
            return null;
        }
        Query q = entityManager.createNativeQuery(query, Tuple.class);

        List<Tuple> result = q.getResultList();
        List<Map<String, Object>> resultats = new ArrayList<>();

        for (Tuple row: result) {

            Map<String, Object> fila = new LinkedHashMap<>();

            // Get Column Names
            List<TupleElement<?>> elements = row.getElements();
            for (TupleElement<?> element : elements ) {
                log.debug("Alias: " + element.getAlias() + ". Valor: " + row.get(element.getAlias()));
                fila.put(element.getAlias(), row.get(element.getAlias()));
            }
            resultats.add(fila);
        }
        return resultats;
    }
}
