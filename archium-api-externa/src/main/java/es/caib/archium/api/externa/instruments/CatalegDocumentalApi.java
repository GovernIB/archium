package es.caib.archium.api.externa.instruments;

import es.caib.archium.commons.i18n.Idioma;
import es.caib.archium.commons.query.OrderBy;
import es.caib.archium.commons.query.OrderType;
import es.caib.archium.commons.rest.RestConstants;
import es.caib.archium.ejb.objects.ProcedimentDto;
import es.caib.archium.ejb.objects.TipusDocumentalDto;
import es.caib.archium.ejb.objects.TipusDocumentalProcedimentDto;
import es.caib.archium.ejb.service.ProcedimientoService;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.*;

/**
 * El catàleg documental no és més que l'inventari de procediments amb la informació documental
 */
@Path("/public/v1/instruments-arxiu")
@Produces(RestConstants.APPLICATION_JSON_UTF_8)
@Consumes(RestConstants.APPLICATION_JSON_UTF_8)
@OpenAPIDefinition(tags = @Tag(name = "Catàleg documental", description = "Serveis REST relacionats amb el catàleg documental. El catàleg documental és l'inventari de procediments administratius amb informació documental de cada un d'ells, és a dir, informació de la seva sèrie documental i dels tipus documentals que poden anar als expedients del procediment."))
public class CatalegDocumentalApi {

    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    @Inject
    ProcedimientoService procedimientoEJB;

    @Transactional
    @Operation(
            tags = "Catàleg documental",
            operationId = "cataleg-documental",
            summary = "Catàleg documental, opcionalment filtrat pels estats dels procediments",
            method = "get")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "404",
                            description = "Paràmetres incorrectes",
                            content = @Content(mediaType = RestConstants.APPLICATION_JSON_UTF_8)),
                    @ApiResponse(
                            responseCode = "200",
                            description = "Catàleg documental",
                            content = @Content(mediaType = RestConstants.APPLICATION_JSON_UTF_8,
                                    array = @ArraySchema(schema = @Schema(implementation = ProcedimentDto.class))))})
    @GET
    @Produces(RestConstants.APPLICATION_JSON_UTF_8)
    @Consumes(RestConstants.APPLICATION_JSON_UTF_8)
    @Path("/cataleg-documental")
    public Response catalegDocumental(
            @Parameter(
                    description = "Filtre per estat dels procediments. Opcional (per defecte, només retorna procediments en estat Vigent",
                    required = false,
                    example = "ESBORRANY",
                    schema = @Schema(implementation = String.class))
            @QueryParam("estat") String estatRequest
    ) {
        try {
            log.debug("Cridada api rest del catàleg documental: estat = " + estatRequest);
            Map<String, Object> filtres = new HashMap<>();

            String estat = Objects.requireNonNullElse(estatRequest, "VIGENT");
            filtres.put("estat", estat);

            OrderBy orderByCodiSia = new OrderBy("codisia", OrderType.ASC);
            List<Procediment> procediments = procedimientoEJB.findFiltered(filtres, orderByCodiSia);

            List<ProcedimentDto> items = new ArrayList<>();
            for (Procediment prc : procediments) {
                List<TipusDocumentalProcedimentDto> tipusDocumentals = new ArrayList<>();
                List<TipusdocumentProcediment> tipusDoc = prc.getAchTipusdocumentProcediments();
                if (tipusDoc != null) {
                    for (TipusdocumentProcediment tdp : tipusDoc) {
                        TipusDocumental td = tdp.getAchTipusdocumental();
                        TipusDocumentalDto tdDto = new TipusDocumentalDto(td.getCodi(), td.getNomcas(), td.getTipusNti().getCodi(), td.getTipusNti().getNomcas(), td.getDefiniciocas(), td.getEstat(), RestConstants.sdf.format(td.getModificacio()));
                        tipusDocumentals.add(new TipusDocumentalProcedimentDto(tdDto, tdp.getObligatori() != null && tdp.getObligatori().compareTo(BigDecimal.ONE) == 0, tdp.getMultiple() != null && tdp.getMultiple().compareTo(BigDecimal.ONE) == 0));
                    }
                }
                items.add(new ProcedimentDto(prc.getCodirolsac(), prc.getCodisia(), prc.getNom(), prc.getObjecte(), prc.getEstat(), (prc.getAchSeriedocumental() != null ? prc.getAchSeriedocumental().getCodi() + " - " + prc.getAchSeriedocumental().getNom() : null), tipusDocumentals, RestConstants.sdf.format(prc.getModificacio())));
            }

            return Response.ok().entity(items).build();
        } catch (Throwable th) {
            String msg = th.getMessage();
            log.error("Error cridada api rest quadre de catàleg documental: " + msg, th);

            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{ \"error\" : " + "\"" + msg + "\" }").build();

        }
    }

    @Transactional
    @Operation(
            tags = "Catàleg documental",
            operationId = "tipus-documentals-from-procediment",
            summary = "Tipus documentals admesos a un procediment concret (especificat pel codi SIA del procediment)",
            method = "get",
            description = "Permet obtenir la informació dels tipus documentals admesos a un procediment (identificat pel seu codi SIA). En principi GDIB hauria de validar que al manco figuren els tipus documentals que aquí estan marcats com a obligatoris abans del tancament de l'expedient")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "404",
                            description = "Paràmetres incorrectes",
                            content = @Content(mediaType = RestConstants.APPLICATION_JSON_UTF_8)),
                    @ApiResponse(
                            responseCode = "200",
                            description = "Informació sobre els tipus documentals associats al procediment",
                            content = @Content(mediaType = RestConstants.APPLICATION_JSON_UTF_8,
                                    array = @ArraySchema(schema = @Schema(implementation = TipusDocumentalProcedimentDto.class))))
                           })
    @GET
    @Produces(RestConstants.APPLICATION_JSON_UTF_8)
    @Consumes(RestConstants.APPLICATION_JSON_UTF_8)
    @Path("/procediment/tipus-documentals")
    public Response tipusDocumentalsOfProcediment(
            @Parameter(
                    description = "Codi SIA del procediment",
                    required = true,
                    example = "1512174",
                    schema = @Schema(implementation = String.class))
            @QueryParam("codisia") String codiSiaRequest,
            @Parameter(
                    description = "Codi de l'idioma",
                    required = false,
                    example = "ca",
                    schema = @Schema(implementation = String.class))
            @QueryParam("idioma") String idiomaRequest
    ) {
        try {
            log.debug("Cridada api rest per tal de controlar els tipus documentals d'un expedient: codiSia = " + codiSiaRequest + "; idioma = " + idiomaRequest);

            List<TipusDocumentalProcedimentDto> tipusDocumentals = new ArrayList<>();

            if (codiSiaRequest == null || codiSiaRequest.trim().length() < 1) {
                throw new RuntimeException("És necessari especificar el codi SIA del procediment (codisia = " + codiSiaRequest + ")");
            }

            // Si han especificat el codi SIA, llavors mira de cercar el procediment per codi SIA
            Map<String, Object> filtres = new HashMap<>();
            filtres.put("codisia", codiSiaRequest);
            List<Procediment> procediments = procedimientoEJB.findFiltered(filtres);


            if (procediments != null) {
                for (Procediment p : procediments) {
                    log.debug("Trobat procediment " + p.getCodisia() + " (" + p.getId() + ")");
                    Seriedocumental serie = p.getAchSeriedocumental();
                    if (serie != null) {
                        log.debug("Inicialitzant la llista de tipus documentals...");
                        List<TipusdocumentProcediment> tipusDoc = p.getAchTipusdocumentProcediments();
                        if (tipusDoc != null) {
                            for (TipusdocumentProcediment tdp : tipusDoc) {
                                TipusDocumental td = tdp.getAchTipusdocumental();
                                TipusDocumentalDto tdDto = null;
                                if (Idioma.CASTELLA.getCodi().equalsIgnoreCase(idiomaRequest)) {
                                    tdDto = new TipusDocumentalDto(td.getCodi(), td.getNomcas(), td.getTipusNti().getCodi(), td.getTipusNti().getNomcas(), td.getDefiniciocas(), td.getEstat(), RestConstants.sdf.format(td.getModificacio()));
                                } else {
                                    tdDto = new TipusDocumentalDto(td.getCodi(), td.getNom(), td.getTipusNti().getCodi(), td.getTipusNti().getNom(), td.getDefinicio(), td.getEstat(), RestConstants.sdf.format(td.getModificacio()));
                                }
                                tipusDocumentals.add(new TipusDocumentalProcedimentDto(tdDto, tdp.getObligatori() != null && tdp.getObligatori().compareTo(BigDecimal.ONE) == 0, tdp.getMultiple() != null && tdp.getMultiple().compareTo(BigDecimal.ONE) == 0));
                            }
                        }
                        log.debug("Tipus documentals trobats: " + tipusDocumentals.size() + " Resposta: " + tipusDocumentals);
                    } else {
                        log.debug("El procediment no té sèrie associada");
                    }
                }
            }

            return Response.ok().entity(tipusDocumentals).build();
        } catch (Throwable th) {
            String msg = th.getMessage();
            log.error("Error cridada api rest d'obtenció dels tipus documentals d'un procediment: " + msg, th);

            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{ \"error\" : " + "\"" + msg + "\" }").build();

        }
    }
}
