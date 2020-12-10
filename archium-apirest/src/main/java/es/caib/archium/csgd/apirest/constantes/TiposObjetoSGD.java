package es.caib.archium.csgd.apirest.constantes;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Tipos documentales aceptados por el Archivo
 * 
 * @author u104848
 *
 */
public enum TiposObjetoSGD {
	
	SERIE_DOCUMENTAL  		("gdib:serie"),
	FUNCION 		  		("gdib:funcion"),
	CUADRO_CLASIFICACION 	("gdib:cuadro_clasificacion");


    private String value;

    private TiposObjetoSGD(String value) {
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

	
