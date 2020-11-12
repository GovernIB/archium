package es.caib.archium.apirest.csgd.entidades.comunes;

import es.caib.archium.apirest.constantes.Estado;

public class FunctionNode {

    private String code;
    private Estado state;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Estado getState() {
        return state;
    }

    public void setState(Estado state) {
        this.state = state;
    }
}
