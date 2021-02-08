package es.caib.archium.csgd.apirest.facade.pojos.eliminar;

public class EliminarFuncion extends EliminarNodo {
    public EliminarFuncion(String nodeId) {
        super(nodeId);
    }

    @Override
    public String toString() {
        return "EliminarFuncion{" +
                "nodoId='" + nodoId + '\'' +
                "} " + super.toString();
    }
}
