package es.caib.archium.ejb.objects;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.ArrayList;
import java.util.List;

@Schema(name = "QuadreTipusDocumentals")
public class QuadreTipusDocumentalsDto {
    @Schema(name = "tipusDocumentals")
    List<TipusDocumentalDto> tipus;

    public QuadreTipusDocumentalsDto() {
        tipus = new ArrayList<>();
    }

    public List<TipusDocumentalDto> getTipus() {
        return tipus;
    }

    public void setTipus(List<TipusDocumentalDto> tipus) {
        this.tipus = tipus;
    }

    public void afegirTipus(TipusDocumentalDto tipus) {
        this.tipus.add(tipus);
    }
}
