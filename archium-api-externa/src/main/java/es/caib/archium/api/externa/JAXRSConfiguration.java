package es.caib.archium.api.externa;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@OpenAPIDefinition(
        info = @Info(
                title = "API REST EXTERNA d'Archium",
                description = "Conjunt de Serveis REST d'Archium per ser accedits des de l'exterior. En concret, permet a altres aplicacions obtenir informació dels diferents instruments tècnics d'arxiu, com ara el Quadre de tipus documentals, Quadre de classificació o el Catàleg documental. També ofereix informació de control o validació que la poden fer servir els tramitadors d'expedients o el propi gestor documental",
                version = "1.0.0",
                license = @License(
                        name = "License Apache 2.0",
                        url = "http://www.apache.org/licenses/LICENSE-2.0"),
                contact = @Contact(
                        name = "Servei d'Arxiu i Gestió Documental",
                        email = "arxiu@caib.es",
                        url = "https://arxiu.caib.es/")

        ),
        servers = {@Server(url = "../../archiumapi/externa"),
                @Server(url = "https://localhost:8443/archiumapi/externa"),
                @Server(url = "https://dev.caib.es/archiumapi/externa"),
                @Server(url = "https://proves.caib.es/archiumapi/externa"),
                @Server(url = "https://se.caib.es/archiumapi/externa"),
                @Server(url = "https://www.caib.es/archiumapi/externa")}
)
@ApplicationPath("/")
public class JAXRSConfiguration extends Application {

    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * Les aplicacions JAX-RS necessiten un constructor buit
     */
    public JAXRSConfiguration() {
    }

    /**
     * Podem introduir tasques a realitzar per la inicialització de l'API REST.
     */
    @PostConstruct
    private void init() {
        log.info("Iniciant API REST EXTERNA d'Archium");
    }

}
