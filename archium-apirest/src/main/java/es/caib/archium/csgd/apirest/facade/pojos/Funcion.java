package es.caib.archium.csgd.apirest.facade.pojos;

import es.caib.archium.csgd.apirest.constantes.Estado;

public class Funcion extends Nodo{

    private Estado estado;
    private Boolean isFuncionPadre;

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Boolean getFuncionPadre() {
        return isFuncionPadre;
    }

    public void setFuncionPadre(Boolean funcionPadre) {
        isFuncionPadre = funcionPadre;
    }
}
