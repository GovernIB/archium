package es.caib.archium.csgd.apirest.constantes;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Permisos {
	
	READ 	        ("read"),
	WRITE 	        ("write"),
    COORDINATOR     ("coordinator");
	
	
    private String value;

    private Permisos(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
    
    @JsonValue
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}

