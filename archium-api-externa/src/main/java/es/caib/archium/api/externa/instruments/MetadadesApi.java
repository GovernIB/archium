package es.caib.archium.api.externa.instruments;

import es.caib.archium.commons.i18n.Idioma;
import es.caib.archium.commons.rest.RestConstants;
import es.caib.archium.ejb.objects.SerieDto;
import es.caib.archium.ejb.service.ProcedimientoService;
import es.caib.archium.ejb.service.SerieService;
import es.caib.archium.persistence.model.Procediment;
import es.caib.archium.persistence.model.Seriedocumental;
//import es.caib.plugins.arxiu.api.ExpedientMetadades;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

@Path("/public/v1/metadades")
@Produces(RestConstants.APPLICATION_JSON_UTF_8)
@Consumes(RestConstants.APPLICATION_JSON_UTF_8)
@OpenAPIDefinition(tags = @Tag(name = "Metadades", description = "Esquema de metadades"))
public class MetadadesApi {

    @Inject
    ProcedimientoService procedimientoEJB;

    @Inject
    SerieService serieEJB;

    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    // Esperar en tenir una nova versió del plugin, actualitzar la versió del plugin d'arxiu a les dependències maven i provar
//    @Transactional
//    @Operation(
//            tags = "Metadades",
//            operationId = "serie_documental-from-metadades",
//            summary = "Control de la sèrie documental que li correspon a l'expedient a partir de les seves metadades",
//            method = "post")
//    @ApiResponses(
//            value = {
//                    @ApiResponse(
//                            responseCode = "404",
//                            description = "Paràmetres incorrectes",
//                            content = @Content(mediaType = RestConstants.APPLICATION_JSON_UTF_8)),
//                    @ApiResponse(
//                            responseCode = "200",
//                            description = "Informació de control sobre la sèrie documental",
//                            content = @Content(
//                                    mediaType = RestConstants.APPLICATION_JSON_UTF_8,
//                                    schema = @Schema(implementation = SerieDto.class)))})
//    @POST
//    @Produces(RestConstants.APPLICATION_JSON_UTF_8)
//    @Consumes(RestConstants.APPLICATION_JSON_UTF_8)
//    @Path("/serie_documental-from-metadades")
//    public Response controlSerieDocumental(
//            @RequestBody(
//                    description = "Metadades de l'expedient",
//                    required = true)
//            ExpedientMetadades expedientMetadadesRequest,
//            @Parameter(
//                    description = "Codi de l'idioma",
//                    required = false,
//                    example = "ca",
//                    schema = @Schema(implementation = String.class))
//            @QueryParam("idioma") String idiomaRequest
//    ) {
//        try {
//            log.debug("Cridada api rest per tal de controlar la sèrie documental d'un expedient: expedientMetadades = " + ToStringBuilder.reflectionToString(expedientMetadadesRequest) + "; idioma = " + idiomaRequest);
//
//            SerieDto serieDocumentalDto = null;
//            if (expedientMetadadesRequest.getClassificacio() == null || expedientMetadadesRequest.getClassificacio().trim().length() < 1) {
//                throw new RuntimeException("És necessari especificar les metadades de l'expedient amb, al manco, el seu codi SIA");
//            }
//
//            // Si han especificat el codi SIA, llavors mira de cercar el procediment per codi SIA
//            if (expedientMetadadesRequest.getClassificacio() != null) {
//
//                String codiSia = expedientMetadadesRequest.getClassificacio();
//                Procediment p = procedimientoEJB.findProcedimentByCodiSia(codiSia);
//
//                if (p != null) {
//                    log.debug("Trobat procediment " + p.getCodisia() + " (" + p.getId() + ")");
//                    Seriedocumental serie = p.getAchSeriedocumental();
//                    if (serie != null) {
//                        log.debug("Sèrie trobada: " + serie.getCodi());
//                        if (Idioma.CASTELLA.getCodi().equalsIgnoreCase(idiomaRequest)) {
//                            serieDocumentalDto = new SerieDto(serie.getCodi(), serie.getNomcas());
//                        } else {
//                            serieDocumentalDto = new SerieDto(serie.getCodi(), serie.getNom());
//                        }
//                    } else {
//                        log.debug("El procediment no té sèrie associada");
//                    }
//                }
//            }
//
//            // Si encara la sèrie continua sense trobar, possiblement sigui una sèrie comuna...
//            // TODO Pendent especificar les regles aquí (exemple, contractes)
//            // Segons Carlota, això és responsabilitat dels tramitadors... no cal
//            if (serieDocumentalDto == null) {
//
//            }
//
//            Response resposta;
//            if (serieDocumentalDto != null) {
//                resposta = Response.ok().entity(serieDocumentalDto).build();
//            } else {
//                resposta = Response.status(Response.Status.NOT_FOUND)
//                        .entity("{ \"error\" : " + "\" Sèrie documental no trobada\" }").build();
//            }
//            return resposta;
//        } catch (Throwable th) {
//            String msg = th.getMessage();
//            log.error("Error cridada api rest d'obtenció de la sèrie a partir de les metadades de l'expedient: " + msg, th);
//
//            return Response.status(Response.Status.BAD_REQUEST)
//                    .entity("{ \"error\" : " + "\"" + msg + "\" }").build();
//
//        }
//    }

//    @Transactional
//    @Operation(
//            tags = "Metadades",
//            description = "Permet conèixer la sèrie documental a partir d'un codi SIA de procediment. Nota important: Només és vàlid per aquells casos en què el codi de sèrie és inferible a partir de la informació del procediment administratiu. No és aplicable a procediments comuns la sèrie documental dels quals s'ha d'inferir a partir del context de l'expedient (totes les metadades de l'expedient). Per aquest darrer cas, emprar la versió POST",
//            operationId = "serie_documental-from-sia",
//            summary = "Control de la sèrie documental que li correspon a l'expedient a partir de les seves metadades",
//            method = "get")
//    @ApiResponses(
//            value = {
//                    @ApiResponse(
//                            responseCode = "404",
//                            description = "Paràmetres incorrectes",
//                            content = @Content(mediaType = RestConstants.APPLICATION_JSON_UTF_8)),
//                    @ApiResponse(
//                            responseCode = "400",
//                            description = "No hi ha cap sèrie associada al procediment indicat",
//                            content = @Content(mediaType = RestConstants.APPLICATION_JSON_UTF_8)),
//                    @ApiResponse(
//                            responseCode = "200",
//                            description = "Informació sobre la sèrie documental associada al procediment",
//                            content = @Content(
//                                    mediaType = RestConstants.APPLICATION_JSON_UTF_8,
//                                    schema = @Schema(implementation = SerieDto.class)))})
//    @GET
//    @Produces(RestConstants.APPLICATION_JSON_UTF_8)
//    @Consumes(RestConstants.APPLICATION_JSON_UTF_8)
//    @Path("/serie_documental-from-sia")
//    public Response serieDocumentalFromSia(
//            @Parameter(
//                    description = "Codi SIA del procediment",
//                    required = true,
//                    example = "2894648",
//                    schema = @Schema(implementation = String.class))
//            @QueryParam("codisia") String codiSiaRequest,
//            @Parameter(
//                    description = "Codi de l'idioma",
//                    required = false,
//                    example = "ca",
//                    schema = @Schema(implementation = String.class))
//            @QueryParam("idioma") String idiomaRequest
//    ) {
//        log.debug("Cridada api rest per tal de controlar la sèrie documental d'un expedient: codiSia = " + codiSiaRequest + "; idioma = " + idiomaRequest);
//        ExpedientMetadades expedientMetadades = new ExpedientMetadades();
//        expedientMetadades.setClassificacio(codiSiaRequest);
//        return controlSerieDocumental(expedientMetadades, idiomaRequest);
//    }

//    @Transactional
//    @Operation(
//            tags = "Metadades",
//            description = "Permet conèixer el codi de classificació específic d'una sèrie documental, per al cas concret d'un òrgan responsable de la tramitació de l'agrupació documental",
//            operationId = "classificacio-no-sia",
//            summary = "Utilitat per conèixer el codi de classificació específic que li correspon a agrupacions documentals no procedimentals",
//            method = "get")
//    @ApiResponses(
//            value = {
//                    @ApiResponse(
//                            responseCode = "404",
//                            description = "Paràmetres incorrectes",
//                            content = @Content(mediaType = RestConstants.APPLICATION_JSON_UTF_8)),
//                    @ApiResponse(
//                            responseCode = "400",
//                            description = "No hi ha cap sèrie",
//                            content = @Content(mediaType = RestConstants.APPLICATION_JSON_UTF_8)),
//                    @ApiResponse(
//                            responseCode = "200",
//                            description = "Informació sobre el codi de classificació específic de la sèrie",
//                            content = @Content(
//                                    mediaType = RestConstants.APPLICATION_JSON_UTF_8,
//                                    schema = @Schema(implementation = SerieDto.class)))})
//    @GET
//    @Produces(RestConstants.APPLICATION_JSON_UTF_8)
//    @Consumes(RestConstants.APPLICATION_JSON_UTF_8)
//    @Path("/classificacio_no_sia")
//    public Response classificacioNoSia(
//            @Parameter(
//                    description = "Codi de sèrie documental",
//                    required = true,
//                    example = "SD01101",
//                    schema = @Schema(implementation = String.class))
//            @QueryParam("codiserie") String codiSerieRequest,
//            @Parameter(
//                    description = "Codi DIR3 d'òrgan responsable de la tramitació",
//                    required = true,
//                    example = "A04026930",
//                    schema = @Schema(implementation = String.class))
//            @QueryParam("dir3organ") String codiOrganRequest,
//            @Parameter(
//                    description = "Opcional, versió del codi DIR3 d'òrgan responsable de la tramitació",
//                    required = false,
//                    example = "v1",
//                    schema = @Schema(implementation = String.class))
//            @QueryParam("versioDir3organ") String versioOrganRequest
//    ) {
//        log.debug("Cridada api rest per conèixer el codi específic de classificació de la sèrie: codiserie = " + codiSerieRequest + "; dir3organ = " + codiOrganRequest + "; versioDir3organ = " + versioOrganRequest);
//        try {
//            Seriedocumental serie = serieEJB.findByCodi(codiSerieRequest);
//            Response resposta;
//            if (serie != null ) {
//                String classificacio = serie.getCodi() + (serie.getAchOrganCollegiat() != null ? serie.getAchOrganCollegiat().getCodi() : "");
//
//                String sb = codiOrganRequest +
//                        versioOrganRequest +
//                        "_PRO_" +
//                        StringUtils.leftPad(classificacio, 30, "0");
//
//                resposta = Response.ok().entity(sb).build();
//            } else {
//                resposta = Response.status(Response.Status.NOT_FOUND)
//                        .entity("{ \"error\" : " + "\" Sèrie documental no trobada o amb codi duplicat\" }").build();
//            }
//
//            return resposta;
//        } catch (Throwable th) {
//            String msg = th.getMessage();
//            log.error("Error cridada api rest d'obtenció de codi específic de classificació a partir de codi de sèrie i òrgan: " + msg, th);
//
//            return Response.status(Response.Status.BAD_REQUEST)
//                    .entity("{ \"error\" : " + "\"" + msg + "\" }").build();
//        }
//    }
}
