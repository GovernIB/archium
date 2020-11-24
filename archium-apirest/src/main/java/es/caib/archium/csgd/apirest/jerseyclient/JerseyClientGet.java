package es.caib.archium.csgd.apirest.jerseyclient;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import es.caib.archium.csgd.apirest.csgd.entidades.resultados.ExceptionResult;
import org.glassfish.jersey.client.ClientConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

public class JerseyClientGet {

    private static Client client = null;
    protected static final Logger log = LoggerFactory.getLogger(JerseyClientGet.class);

    // creador sincronizado para protegerse de posibles problemas  multi-hilo
    private synchronized static void createClient(String user, String password) {
        if (client == null) {
            log.debug(">>> Inicializando cliente Jersey");

            client = ClientBuilder.newClient(new ClientConfig().register(JacksonJsonProvider.class));

        }
    }

    private static Client getInstance() {
        if (client == null) createClient(null, null);
        return client;
    }

    public static <T> ResultadoJersey post(String url, String path, Object object, Class<T> entity) throws IOException, RuntimeException {
        return post(url, path, object, entity, Boolean.TRUE);
    }

    public static <T> ResultadoJersey post(String url, String path, Object object, Class<T> entity, Boolean trazas) throws IOException {
        ResultadoJersey output = new ResultadoJersey();
        Client client = JerseyClientGet.getInstance();

        if (trazas) {
            log.info("\n JerseyClientGet url: " + url);
            log.info("\n JerseyClientGet request: " + object.toString());
        }

        WebTarget webTarget = client.target(url).path(path);

        Response response = webTarget
                .request()
                .post(Entity.entity(object, MediaType.APPLICATION_JSON_TYPE));

        output.setEstadoRespuestaHttp(response.getStatus());
        if (output.getEstadoRespuestaHttp() == 200) {
            output.setContenido(response.readEntity(entity));
        } else {
            output.setContenido(response.readEntity(ExceptionResult.class));
        }

        if (trazas) {
            log.info("\n JerseyClientGet url: " + url);
            log.info("\n JerseyClientGet output: " + output);
        }

        return output;
    }
}
