package es.caib.archium.apirest.facade.pojos;

import es.caib.archium.apirest.constantes.Estado;

public class Funcion {

    private String codigoCuadro;
    private String codigo;
    private String funcionPadre;
    private Estado estado;

    public String getCodigoCuadro() {
        return codigoCuadro;
    }

    public void setCodigoCuadro(String codigoCuadro) {
        this.codigoCuadro = codigoCuadro;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getFuncionPadre() {
        return funcionPadre;
    }

    public void setFuncionPadre(String funcionPadre) {
        this.funcionPadre = funcionPadre;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }
}
