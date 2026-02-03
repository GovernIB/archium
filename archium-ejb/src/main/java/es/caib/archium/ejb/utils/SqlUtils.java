package es.caib.archium.ejb.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.sql.Clob;
import java.sql.SQLException;

@Named("sqlUtils")
@ApplicationScoped
public class SqlUtils {

    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    public String convertClob(Object obj) {
        if (obj instanceof Clob) {
            Clob clob = (Clob)obj;
            try {
                return clob.getSubString(1, (int) clob.length());
            } catch (SQLException e) {
                log.error("Error accediendo a Clob", e);
            }
        }
        return obj != null ? obj.toString() : null;
    }
}
