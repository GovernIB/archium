package es.caib.archium.csgd.apirest.constantes;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Aspects {
	
	CLASIFICABLE 		("eemgde:clasificable"),
	SENSIBLE 			("eemgde:sensible_datos_personales"),
	CLASIFICABLE_ENS 	("eemgde:clasificable_ens"),
	REUTILIZABLE 		("eemgde:reutilizable"),
	CALIFICABLE 		("eemgde:calificable"),
	RESELLABLE 			("gdib:resellable"),
	VALORABLE			("eemgde:valrable"),
	DICTAMINABLE		("eemgde:dictaminable"),
	ESENCIAL			("eemgde:esencial"),
	VALORABLE_PRIMARIO	("eemgde:valorable_primario");

	
    private String value;

    private Aspects(String value) {
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
