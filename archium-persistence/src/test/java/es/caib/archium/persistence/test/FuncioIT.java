package es.caib.archium.persistence.test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.FlushModeType;
import javax.persistence.Persistence;
import java.util.Properties;

public class FuncioIT {




    public static EntityManager initDB() {
        Properties prop = new Properties();

        /*prop.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        prop.put("javax.persistence.jdbc.driver", "org.postgresql.Driver");
        prop.put("javax.persistence.jdbc.url", "jdbc:postgresql://localhost:5432/carpeta");
        prop.put("javax.persistence.jdbc.user", "carpeta");
        prop.put("javax.persistence.jdbc.password", "carpeta");

        prop.put("hibernate.connection.driver_class", "org.postgresql.Driver");
        prop.put("hibernate.connection.url", "jdbc:postgresql://localhost:5432/carpeta");
        prop.put("hibernate.connection.username", "carpeta");
        prop.put("hibernate.connection.password", "carpeta");*/

        prop.put("hibernate.show_sql", "true");

        EntityManagerFactory emf;

        // Veure persistence.xml
        emf = Persistence.createEntityManagerFactory("archiumPUTest", prop);

        EntityManager em = emf.createEntityManager();
        em.setFlushMode(FlushModeType.AUTO);

        return em;
    }
}
