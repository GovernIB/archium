package es.caib.archium.csgd.apirest.csgd.entidades.comunes;

public class CausaLimitacionNormativa {

    private String causaLimitacion;
    private String normativa;

    public String getCausaLimitacion() {
        return causaLimitacion;
    }

    public void setCausaLimitacion(String causaLimitacion) {
        this.causaLimitacion = causaLimitacion;
    }

    public String getNormativa() {
        return normativa;
    }

    public void setNormativa(String normativa) {
        this.normativa = normativa;
    }

    @Override
    public String toString() {
        return "CausaLimitacionNormativa{" +
                "causaLimitacion='" + causaLimitacion + '\'' +
                ", normativa='" + normativa + '\'' +
                '}';
    }
}
