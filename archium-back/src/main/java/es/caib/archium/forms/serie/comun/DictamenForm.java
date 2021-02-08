package es.caib.archium.forms.serie.comun;

import java.io.Serializable;
import java.util.List;

public class DictamenForm  implements Serializable {
    private String tipoDictamen;
    private String accionDictaminada;
    private String plazo;
    private List<String> tiposDocumentales;

    public String getTipoDictamen() {
        return tipoDictamen;
    }

    public void setTipoDictamen(String tipoDictamen) {
        this.tipoDictamen = tipoDictamen;
    }

    public String getAccionDictaminada() {
        return accionDictaminada;
    }

    public void setAccionDictaminada(String accionDictaminada) {
        this.accionDictaminada = accionDictaminada;
    }

    public String getPlazo() {
        return plazo;
    }

    public void setPlazo(String plazo) {
        this.plazo = plazo;
    }

    public List<String> getTiposDocumentales() {
        return tiposDocumentales;
    }

    public void setTiposDocumentales(List<String> tiposDocumentales) {
        this.tiposDocumentales = tiposDocumentales;
    }
}
