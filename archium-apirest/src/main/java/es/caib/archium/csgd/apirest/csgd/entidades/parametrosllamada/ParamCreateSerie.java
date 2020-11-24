package es.caib.archium.csgd.apirest.csgd.entidades.parametrosllamada;

import es.caib.archium.csgd.apirest.csgd.entidades.comunes.SerieNode;

public class ParamCreateSerie {
    private String parentId;
    private SerieNode serie;

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public SerieNode getSerie() {
        return serie;
    }

    public void setSerie(SerieNode serie) {
        this.serie = serie;
    }

    @Override
    public String toString() {
        return "ParamCreateSerie{" +
                "parentId='" + parentId + '\'' +
                ", serie=" + serie +
                '}';
    }
}
