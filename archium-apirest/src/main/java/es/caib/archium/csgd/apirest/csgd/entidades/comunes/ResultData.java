package es.caib.archium.csgd.apirest.csgd.entidades.comunes;

/**
 * Tipo de datos compuesto que representa el resultado retornado al invocar los servicios de repositorio y migración de la capa CSGD.
 * 
 * @author u104848
 *
 */
public class ResultData {
	private String code;
	private String description;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}


	@Override
	public String toString() {
		return "ResultData{" +
				"code='" + code + '\'' +
				", description='" + description + '\'' +
				'}';
	}
}
