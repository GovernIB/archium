package es.caib.archium.csgd.apirest.csgd.entidades.comunes;

import org.apache.commons.lang3.StringEscapeUtils;

/**
 * Tipo de datos compuesto que representa la información de identificación 
 * de un procedimiento administrativo.
 * 
 * @author u104848
 *
 */
public class ProceedingsAuditInfo {
	private String id;
	private String name;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		//Se hace el escapeado para evitar problemas con caracteres especiales
		this.name = StringEscapeUtils.escapeXml(name);
	}

	@Override
	public String toString() {
		return "ProceedingsAuditInfo{" +
				"id='" + id + '\'' +
				", name='" + name + '\'' +
				'}';
	}
}
