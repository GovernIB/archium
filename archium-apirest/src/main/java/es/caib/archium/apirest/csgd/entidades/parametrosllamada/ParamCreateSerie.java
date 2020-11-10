package es.caib.archium.apirest.csgd.entidades.parametrosllamada;

import es.caib.archium.apirest.csgd.entidades.comunes.SerieNode;

public class ParamCreateSerie {
    private String parent;
    private SerieNode serie;

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public SerieNode getSerie() {
        return serie;
    }

    public void setSerie(SerieNode serie) {
        this.serie = serie;
    }
}
