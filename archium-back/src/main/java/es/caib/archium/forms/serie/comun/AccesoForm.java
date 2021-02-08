package es.caib.archium.forms.serie.comun;

import java.io.Serializable;
import java.util.List;

public class AccesoForm implements Serializable {
    private String tipoAcceso;
    private String condicionReutilizacion;
    private String causaLimitacion;


    public String getCondicionReutilizacion() {
        return condicionReutilizacion;
    }

    public void setCondicionReutilizacion(String condicionReutilizacion) {
        this.condicionReutilizacion = condicionReutilizacion;
    }

    public String getTipoAcceso() {
        return tipoAcceso;
    }

    public void setTipoAcceso(String tipoAcceso) {
        this.tipoAcceso = tipoAcceso;
    }

    public String getCausaLimitacion() {
        return causaLimitacion;
    }

    public void setCausaLimitacion(String causaLimitacion) {
        this.causaLimitacion = causaLimitacion;
    }
}
