package es.caib.archium.apirest.facade.pojos.borrar;

public class DocumentId {
	private String nodeId;
	private RequestMigratedDocId migratedDocId;
	private String csv;
	
	public String getNodeId() {
		return nodeId;
	}
	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}
	public RequestMigratedDocId getMigratedDocId() {
		return migratedDocId;
	}
	public void setMigratedDocId(RequestMigratedDocId migratedDocId) {
		this.migratedDocId = migratedDocId;
	}
	public String getCsv() {
		return csv;
	}
	public void setCsv(String csv) {
		this.csv = csv;
	}
	
	
}
