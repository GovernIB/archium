package es.caib.archium.api.externa.instruments;

import es.caib.archium.ejb.objects.ProcedimentDto;
import es.caib.archium.ejb.objects.TipusDocumentalDto;
import es.caib.archium.ejb.objects.TipusNtiDto;
import es.caib.archium.commons.i18n.Idioma;
import es.caib.archium.commons.model.Estat;
import es.caib.archium.commons.query.OrderBy;
import es.caib.archium.commons.query.OrderType;
import es.caib.archium.commons.rest.RestConstants;
import es.caib.archium.ejb.objects.QuadreTipusDocumentalsDto;
import es.caib.archium.ejb.service.QuadreTipusDocumentalService;
import es.caib.archium.ejb.service.TipusDocumentalService;
import es.caib.archium.ejb.service.TipusNtiService;
import es.caib.archium.persistence.model.QuadreTipusDocumental;
import es.caib.archium.persistence.model.TipusDocumental;
import es.caib.archium.persistence.model.TipusNti;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.*;

@Path("/public/v1/instruments-arxiu")
@Produces(RestConstants.APPLICATION_JSON_UTF_8)
@Consumes(RestConstants.APPLICATION_JSON_UTF_8)
@OpenAPIDefinition(tags = @Tag(name = "QdT", description = "Quadre de tipus documentals"))
public class QuadreTipusDocumentalsApi {

    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    @Inject
    TipusDocumentalService tipusDocumentalEJB;

    @Inject
    TipusNtiService tipusNtiEJB;

    @Inject
    QuadreTipusDocumentalService quadreTipusDocumentalEJB;

    @Transactional
    @Operation(
            tags = "QdT",
            operationId = "qt",
            summary = "Quadre de tipus documentals",
            description = "Permet obtenir els tipus documentals definit en un quadre de tipus documentals. Si no s'especifica cap codi de quadre de tipus, per defecte, retorna el quadre de tipus documentals establert per defecte",
            method = "get")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "404",
                            description = "Paràmetres incorrectes",
                            content = @Content(mediaType = RestConstants.APPLICATION_JSON_UTF_8)),
                    @ApiResponse(
                            responseCode = "200",
                            description = "Quadre de tipus documentals",
                            content = @Content(mediaType = RestConstants.APPLICATION_JSON_UTF_8,
                                    array = @ArraySchema(schema = @Schema(implementation = TipusDocumentalDto.class))))})
    @GET
    @Produces(RestConstants.APPLICATION_JSON_UTF_8)
    @Consumes(RestConstants.APPLICATION_JSON_UTF_8)
    @Path("/qdt")
    public Response quadreTipusDocumentals(
            @Parameter(
                    description = "Codi identificador del quadre de tipus. Opcional (per defecte, retorna el quadre de tipus documentals de la CAIB)",
                    required = false,
                    example = "1",
                    schema = @Schema(implementation = Long.class))
            @QueryParam("codiQuadre") Long codiQuadreRequest,
            @Parameter(
                    description = "Estat d'elaboració del tipus documental. Opcional (per defecte, només retorna els tipus en estat Vigent)",
                    required = false,
                    example = "ESBORRANY",
                    schema = @Schema(implementation = String.class))
            @QueryParam("estatTipus") String estatTipusRequest,
            @Parameter(
                    description = "Codi de l'idioma",
                    required = false,
                    example = "ca",
                    schema = @Schema(implementation = String.class))
            @QueryParam("idioma") String idiomaRequest
    ) {
        try {
            log.debug("Cridada api rest de quadre de tipus documentals: codiQuadre = " + codiQuadreRequest + "; estatTipus = " + estatTipusRequest  + "; idioma = " + idiomaRequest);
            Map<String, Object> filters = new HashMap<>();

            Long quadreId;
            if (codiQuadreRequest == null) {
                quadreId = quadreTipusDocumentalEJB.getQuadreTipusDocumentalPerDefecteId();
            } else {
                quadreId = codiQuadreRequest;
            }

            if (idiomaRequest == null) {
                idiomaRequest = "ca";
            }

            if (estatTipusRequest == null) {
                estatTipusRequest = "Vigent";
            }

            QuadreTipusDocumentalsDto quadreTipusDocumentalDto = quadreTipusDocumentalEJB.getQuadreTipusDocumental(quadreId, Idioma.getIdioma(idiomaRequest), Estat.getEstat(estatTipusRequest));

            return Response.ok().entity(quadreTipusDocumentalDto.getTipus()).build();
        }  catch (Throwable th) {
            String msg = th.getMessage();
            log.error("Error cridada api rest quadre de tipus documentals: " + msg, th);

            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{ \"error\" : " + "\"" + msg + "\" }").build();

        }
    }

    @Transactional
    @Operation(
            tags = "QdT",
            operationId = "tipus-nti",
            summary = "Tipus documentals NTI",
            method = "get")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "404",
                            description = "Paràmetres incorrectes",
                            content = @Content(mediaType = RestConstants.APPLICATION_JSON_UTF_8)),
                    @ApiResponse(
                            responseCode = "200",
                            description = "Tipus documentals NTI",
                            content = @Content(mediaType = RestConstants.APPLICATION_JSON_UTF_8,
                                    array = @ArraySchema(schema = @Schema(implementation = TipusNtiDto.class))))})
    @GET
    @Produces(RestConstants.APPLICATION_JSON_UTF_8)
    @Consumes(RestConstants.APPLICATION_JSON_UTF_8)
    @Path("/qdt-nti")
    public Response tipusDocumentalsNti(
            @Parameter(
                    description = "Codi de l'idioma",
                    required = false,
                    example = "ca",
                    schema = @Schema(implementation = String.class))
            @QueryParam("idioma") String idiomaRequest
    ) {
        try {
            log.debug("Cridada api rest de tipus documentals NTI: idioma = " + idiomaRequest);

            OrderBy orderByCodi = new OrderBy("codi", OrderType.ASC);
            List<TipusNti> tipusNti = tipusNtiEJB.findAll(orderByCodi);
            List<TipusNtiDto> items = new ArrayList<>();

            for (TipusNti nti : tipusNti) {
                if (Idioma.CASTELLA.getCodi().equalsIgnoreCase(idiomaRequest)) {
                    items.add(new TipusNtiDto(nti.getCodi(), nti.getNomcas(), nti.getAchCategorianti().getNomcas()));
                } else {
                    items.add(new TipusNtiDto(nti.getCodi(), nti.getNom(), nti.getAchCategorianti().getNom()));
                }
            }

            return Response.ok().entity(items).build();
        }  catch (Throwable th) {
            String msg = th.getMessage();
            log.error("Error cridada api rest de tipus documentals NTI: " + msg, th);

            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{ \"error\" : " + "\"" + msg + "\" }").build();

        }
    }

    @Transactional
    @Operation(
            tags = "QdT",
            operationId = "td",
            summary = "Informació detallada d'un tipus documental definit a un quadre de tipus documentals",
            method = "get")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "404",
                            description = "Paràmetres incorrectes",
                            content = @Content(mediaType = RestConstants.APPLICATION_JSON_UTF_8)),
                    @ApiResponse(
                            responseCode = "200",
                            description = "Informació detallada del tipus documental",
                            content = @Content(
                                    mediaType = RestConstants.APPLICATION_JSON_UTF_8,
                                    schema = @Schema(implementation = TipusDocumentalDto.class))) })
    @GET
    @Produces(RestConstants.APPLICATION_JSON_UTF_8)
    @Consumes(RestConstants.APPLICATION_JSON_UTF_8)
    @Path("/qdt/tipus_documental")
    public Response tipusDocumental(
            @Parameter(
                    description = "Codi identificador del quadre de tipus. Opcional (per defecte, retorna el quadre de tipus documentals de la CAIB)",
                    required = false,
                    example = "1",
                    schema = @Schema(implementation = Long.class))
            @QueryParam("codiQuadre") Long codiQuadreRequest,
            @Parameter(
                    description = "Estat d'elaboració del tipus. Opcional (per defecte, només retorna un quadre de tipus si es troba en estat Vigent)",
                    required = false,
                    example = "ESBORRANY",
                    schema = @Schema(implementation = String.class))
            @QueryParam("estatTipus") String estatTipusRequest,
            @Parameter(
                    description = "Codi identificador del tipus dintre del quadre de tipus. Obligatori",
                    required = true,
                    example = "TD01-011",
                    schema = @Schema(implementation = String.class))
            @QueryParam("codiTipus") String codiTipusRequest,
            @Parameter(
                    description = "Codi de l'idioma",
                    required = false,
                    example = "ca",
                    schema = @Schema(implementation = String.class))
            @QueryParam("idioma") String idiomaRequest
    ) {
        try {
            log.debug("Cridada api rest de tipus documental concret: codiQuadre = " + codiQuadreRequest + "; estatTipus = " + estatTipusRequest + ";  codiTipus = " + codiTipusRequest  + "idioma = " + idiomaRequest);


            QuadreTipusDocumental quadreTD;
            if (codiQuadreRequest != null) {
                quadreTD = quadreTipusDocumentalEJB.getReference(codiQuadreRequest);
            } else {
                quadreTD = quadreTipusDocumentalEJB.getQuadreTipusDocumentalPerDefecte();
            }

            Map<String, Object> filters = new HashMap<>();
            filters.put("quadreTipusDocumental", quadreTD);

            String estat = Objects.requireNonNullElse(estatTipusRequest, "VIGENT");
            filters.put("estat", estat);

            filters.put("codi", codiTipusRequest);

            OrderBy orderByCodi = new OrderBy("codi", OrderType.ASC);
            List<TipusDocumental> tipusTrobats = tipusDocumentalEJB.findFiltered(filters, orderByCodi);

            TipusDocumentalDto tipusDocumentDto = null;

            if (tipusTrobats != null && tipusTrobats.size() > 1) {
                return Response.status(Response.Status.CONFLICT)
                        .entity("{ \"error\" : " + "\"Existeixen múltiples tipus documentals amb el codi '" + codiTipusRequest + "'. Per favor, contactau amb el servei d'arxiu (arxiu@caib.es)\" }").build();
            } else if (tipusTrobats != null) {
                TipusDocumental td = tipusTrobats.get(0);
                if (Idioma.CASTELLA.getCodi().equalsIgnoreCase(idiomaRequest)) {
                    tipusDocumentDto = new TipusDocumentalDto(td.getCodi(), td.getNomcas(), td.getTipusNti().getCodi(), td.getTipusNti().getNomcas(), td.getDefiniciocas(), td.getEstat(), RestConstants.sdf.format(td.getModificacio()));
                } else {
                    tipusDocumentDto = new TipusDocumentalDto(td.getCodi(), td.getNom(), td.getTipusNti().getCodi(), td.getTipusNti().getNom(), td.getDefinicio(), td.getEstat(), RestConstants.sdf.format(td.getModificacio()));
                }
            }
            return Response.ok().entity(tipusDocumentDto).build();
        }  catch (Throwable th) {
            String msg = th.getMessage();
            log.error("Error cridada api rest tipus documental específic: " + msg, th);

            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{ \"error\" : " + "\"" + msg + "\" }").build();

        }
    }
}
