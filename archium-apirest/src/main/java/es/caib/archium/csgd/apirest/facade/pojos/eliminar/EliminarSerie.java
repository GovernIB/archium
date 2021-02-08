package es.caib.archium.csgd.apirest.facade.pojos.eliminar;

public class EliminarSerie extends EliminarNodo {
    public EliminarSerie(String nodeId) {
        super(nodeId);
    }

    @Override
    public String toString() {
        return "EliminarSerie{" +
                "nodoId='" + nodoId + '\'' +
                "} " + super.toString();
    }
}
