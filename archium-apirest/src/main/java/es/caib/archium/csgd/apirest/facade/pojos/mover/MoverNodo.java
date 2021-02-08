package es.caib.archium.csgd.apirest.facade.pojos.mover;

import es.caib.archium.csgd.apirest.facade.pojos.comun.NodoAlfresco;

public class MoverNodo extends NodoAlfresco {

    private String parentId;

    public MoverNodo(){
    }

    public MoverNodo(String nodoId) {
        super(nodoId);
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    @Override
    public String toString() {
        return "MoverNodo{" +
                "nodoId='" + nodoId + '\'' +
                ", parentId='" + parentId + '\'' +
                "} " + super.toString();
    }
}
