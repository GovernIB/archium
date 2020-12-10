package es.caib.archium.csgd.apirest.facade.pojos;

import es.caib.archium.csgd.apirest.constantes.Estado;

public class CuadroClasificacion extends Nodo{

    private Estado estado;

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }
}
