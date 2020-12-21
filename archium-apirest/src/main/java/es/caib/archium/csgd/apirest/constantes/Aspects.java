package es.caib.archium.csgd.apirest.constantes;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;
import java.util.List;

public enum Aspects {

	// Aspectos de la serie
	CLASIFICABLE 		("eemgde:clasificable"),
	SENSIBLE 			("eemgde:sensible_datos_personales"),
	CLASIFICABLE_ENS 	("eemgde:clasificable_ens"),
	REUTILIZABLE 		("eemgde:reutilizable"),
	CALIFICABLE 		("eemgde:calificable"),
	RESELLABLE 			("eemgde:resellable"),
	VALORABLE			("eemgde:valorable"),
	DICTAMINABLE		("eemgde:dictaminable"),
	ESENCIAL			("eemgde:esencial"),
	VALORABLE_PRIMARIO	("eemgde:valorable_primario"),
	// Aspecto de funcion y cuadro
	OBSOLETO			("gdib:obsoleto");

	
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

	/**
	 * Retorna la lista de aspectos propios de la serie
	 *
	 * @return
	 */
	public static List<Aspects> getAspectosSerie(){
    	return Arrays.asList(CLASIFICABLE,SENSIBLE,CLASIFICABLE_ENS,REUTILIZABLE,CALIFICABLE,RESELLABLE,VALORABLE,
				DICTAMINABLE,ESENCIAL,VALORABLE_PRIMARIO);
	}

	/**
	 * Retorna la lista de aspectos propios de la funcion
	 *
	 * @return
	 */
	public static List<Aspects> getAspectosFuncion(){
		return Arrays.asList(OBSOLETO);
	}

	/**
	 * Retorna la lista de aspectos propios del cuadro
	 *
	 * @return
	 */
	public static List<Aspects> getAspectosCuadro() {
		return Arrays.asList(OBSOLETO);
	}

}
