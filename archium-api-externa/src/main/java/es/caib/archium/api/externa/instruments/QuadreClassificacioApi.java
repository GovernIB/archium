package es.caib.archium.api.externa.instruments;


import es.caib.archium.commons.i18n.Idioma;
import es.caib.archium.commons.model.Estat;
import es.caib.archium.commons.query.OrderBy;
import es.caib.archium.commons.query.OrderType;
import es.caib.archium.commons.rest.RestConstants;
import es.caib.archium.ejb.objects.ProcedimentDto;
import es.caib.archium.ejb.objects.QuadreClassificacioDto;
import es.caib.archium.ejb.objects.SerieDto;
import es.caib.archium.ejb.service.*;
import es.caib.archium.persistence.model.*;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

@Path("/public/v1/instruments-arxiu")
@Produces(RestConstants.APPLICATION_JSON_UTF_8)
@Consumes(RestConstants.APPLICATION_JSON_UTF_8)
@OpenAPIDefinition(tags = @Tag(name = "QdC", description = "Quadre de classificació funcional"))
public class QuadreClassificacioApi {

    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    @Inject
    QuadreClassificacioService quadreClassificacioEJB;

    @Transactional
    @Operation(
            tags = "QdC",
            operationId = "qdcs",
            summary = "Llista de quadres de classificació funcional definits a Archium",
            method = "get")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "404",
                            description = "Paràmetres incorrectes",
                            content = @Content(mediaType = RestConstants.APPLICATION_JSON_UTF_8)),
                    @ApiResponse(
                            responseCode = "200",
                            description = "Llista de quadres de classificació funcionals",
                            content = @Content(mediaType = RestConstants.APPLICATION_JSON_UTF_8,
                                    array = @ArraySchema(schema = @Schema(implementation = QuadreClassificacioDto.class))))})
    @GET
    @Produces(RestConstants.APPLICATION_JSON_UTF_8)
    @Consumes(RestConstants.APPLICATION_JSON_UTF_8)
    @Path("/qdcs")
    public Response llistaQuadresClassificacio(
            @Parameter(
                    description = "Estat d'elaboració del quadre de classificació. Opcional (per defecte, només retorna un quadre de classificació si es troba en estat Vigent",
                    required = false,
                    example = "ESBORRANY",
                    schema = @Schema(implementation = String.class))
            @QueryParam("estat") String estatRequest,
            @Parameter(
                    description = "Codi de l'idioma",
                    required = false,
                    example = "ca",
                    schema = @Schema(implementation = String.class))
            @QueryParam("idioma") String idiomaRequest

    ) {
        try {
            log.debug("Cridada api rest de quadres de classificació: estat = " + estatRequest + "; idioma = " + idiomaRequest);

            List<QuadreClassificacioDto> qdcsDto = quadreClassificacioEJB.getLlistaQuadreClassificacio(estatRequest, Idioma.getIdioma(idiomaRequest));
            return Response.ok().entity(qdcsDto).build();
        }  catch (Throwable th) {
            String msg = th.getMessage();
            log.error("Error cridada api rest quadres de classificació: " + msg, th);

            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{ \"error\" : " + "\"" + msg + "\" }").build();

        }
    }

    @Transactional
    @Operation(
            tags = "QdC",
            operationId = "qdc",
            summary = "Retorna el quadre de classificació funcional amb les sèries documentals",
            method = "get")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "404",
                            description = "Paràmetres incorrectes",
                            content = @Content(mediaType = RestConstants.APPLICATION_JSON_UTF_8)),
                    @ApiResponse(
                            responseCode = "200",
                            description = "Quadre de classificació funcional",
                            content = @Content(mediaType = RestConstants.APPLICATION_JSON_UTF_8,
                                    array = @ArraySchema(schema = @Schema(implementation = SerieDto.class))))})
    @GET
    @Produces(RestConstants.APPLICATION_JSON_UTF_8)
    @Consumes(RestConstants.APPLICATION_JSON_UTF_8)
    @Path("/qdc")
    public Response quadreClassificacio(
            @Parameter(
                    description = "Codi identificador del quadre de classificació. Opcional (per defecte, retorna el quadre de classificació funcional de l'Administració de la Comunitat Autònoma de les Illes Balears)",
                    required = false,
                    example = "2",
                    schema = @Schema(implementation = Long.class))
            @QueryParam("codi") Long codiQuadreRequest,
            @Parameter(
                    description = "Estat d'elaboració del quadre de classificació. Opcional (per defecte, només retorna un quadre de classificació si es troba en estat Vigent",
                    required = false,
                    example = "ESBORRANY",
                    schema = @Schema(implementation = String.class))
            @QueryParam("estat") String estatRequest,
            @Parameter(
                    description = "Codi de l'idioma",
                    required = false,
                    example = "ca",
                    schema = @Schema(implementation = String.class))
            @QueryParam("idioma") String idiomaRequest

    ) {
        try {
            log.debug("Cridada api rest de quadre de classificació: codiQuadre = " + codiQuadreRequest + "; estat = " + estatRequest + "; idioma = " + idiomaRequest);
            Long quadreId;
            if (codiQuadreRequest == null) {
                quadreId = quadreClassificacioEJB.getQuadreClassificacioPerDefecte();
            } else {
                quadreId = codiQuadreRequest;
            }

            Estat estat;
            if (estatRequest == null) {
                estat = Estat.VIGENT;
            } else {
                estat = Estat.getEstat(estatRequest);
            }
            List<SerieDto> seriesDto = quadreClassificacioEJB.getQuadreClassificacio(quadreId, Idioma.getIdioma(idiomaRequest), estat);

            return Response.ok().entity(seriesDto).build();
        }  catch (Throwable th) {
            String msg = th.getMessage();
            log.error("Error cridada api rest quadre de classificació: " + msg, th);

            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{ \"error\" : " + "\"" + msg + "\" }").build();

        }
    }
}
