package es.caib.archium.csgd.apirest.facade.pojos.comun;

public class NodoAlfresco {

    protected String nodoId;

    public NodoAlfresco(){}

    public NodoAlfresco(String nodoId) {
        this.nodoId = nodoId;
    }

    public String getNodoId() {
        return nodoId;
    }

    public void setNodoId(String nodoId) {
        this.nodoId = nodoId;
    }

    @Override
    public String toString() {
        return "NodoAlfresco{" +
                "nodoId='" + nodoId + '\'' +
                '}';
    }
}
