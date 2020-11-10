package es.caib.archium.apirest.csgd.entidades.comunes;

public class PublicServantAuditInfo {
	private PersonIdentAuditInfo identificationData;
	private String organization;
	
	public PersonIdentAuditInfo getIdentificationData() {
		return identificationData;
	}
	public void setIdentificationData(PersonIdentAuditInfo identificationData) {
		this.identificationData = identificationData;
	}
	public String getOrganization() {
		return organization;
	}
	public void setOrganization(String organization) {
		this.organization = organization;
	}
	
	
}
