package es.caib.archium.forms.serie.comun;

import java.io.Serializable;
import java.util.List;

public class ValoracionForm implements Serializable {

    private String valoracionSecundaria;
    private String valoracionesPrimarias;

    public String getValoracionSecundaria() {
        return valoracionSecundaria;
    }

    public void setValoracionSecundaria(String valoracionSecundaria) {
        this.valoracionSecundaria = valoracionSecundaria;
    }

    public String getValoracionesPrimarias() {
        return valoracionesPrimarias;
    }

    public void setValoracionesPrimarias(String valoracionesPrimarias) {
        this.valoracionesPrimarias = valoracionesPrimarias;
    }
}
