package es.caib.archium.csgd.apirest.facade.pojos.eliminar;

public class EliminarCuadro extends EliminarNodo {


    public EliminarCuadro(String nodeId) {
        super(nodeId);
    }

    @Override
    public String toString() {
        return "EliminarCuadro{" +
                "nodoId='" + nodoId + '\'' +
                "} " + super.toString();
    }
}
