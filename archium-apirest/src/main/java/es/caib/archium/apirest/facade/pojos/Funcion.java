package es.caib.archium.apirest.facade.pojos;

import es.caib.archium.apirest.constantes.Estado;

public class Funcion {

    private String codigo;
    private Estado estado;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }
}
