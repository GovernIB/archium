package es.caib.archium.csgd.apirest.csgd.entidades.parametrosllamada;

import es.caib.archium.csgd.apirest.csgd.entidades.comunes.SerieNode;

public class ParamSetSerie {
    private SerieNode serie;

    public SerieNode getSerie() {
        return serie;
    }

    public void setSerie(SerieNode serie) {
        this.serie = serie;
    }

    @Override
    public String toString() {
        return "ParamSetSerie{" +
                ", serie=" + serie +
                '}';
    }
}
