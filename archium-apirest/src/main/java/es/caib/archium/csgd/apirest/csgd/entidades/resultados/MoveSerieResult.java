package es.caib.archium.csgd.apirest.csgd.entidades.resultados;

import es.caib.archium.csgd.apirest.csgd.entidades.comunes.RespuestaGenerica;
import es.caib.archium.csgd.apirest.csgd.entidades.comunes.RespuestaGenericaUnParametro;
import es.caib.archium.csgd.apirest.csgd.entidades.comunes.SerieNode;

public class MoveSerieResult {

    private RespuestaGenericaUnParametro moveSerieResult;


    public RespuestaGenericaUnParametro getMoveSerieResult() {
        return moveSerieResult;
    }

    public void setMoveSerieResult(RespuestaGenericaUnParametro moveSerieResult) {
        this.moveSerieResult = moveSerieResult;
    }

    @Override
    public String toString() {
        return "MoveSerieResult{" +
                "moveSerieResult=" + moveSerieResult +
                '}';
    }
}
