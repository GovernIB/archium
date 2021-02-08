package es.caib.archium.csgd.apirest.csgd.peticiones;

import es.caib.archium.csgd.apirest.csgd.entidades.parametrosllamada.ParamMoveNode;

public class MoveSerie {

    private Request<ParamMoveNode> moveSerieRequest;

    public Request<ParamMoveNode> getMoveSerieRequest() {
        return moveSerieRequest;
    }

    public void setMoveSerieRequest(Request<ParamMoveNode> moveSerieRequest) {
        this.moveSerieRequest = moveSerieRequest;
    }

    @Override
    public String toString() {
        return "MoveSerie{" +
                "moveSerieRequest=" + moveSerieRequest +
                '}';
    }
}
