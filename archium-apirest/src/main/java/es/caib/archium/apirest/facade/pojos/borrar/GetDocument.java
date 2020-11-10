package es.caib.archium.apirest.facade.pojos.borrar;


import es.caib.archium.apirest.csgd.peticiones.Request;

public class GetDocument {
	private Request<ParamGetDocument> getDocumentRequest;

	public Request<ParamGetDocument> getGetDocumentRequest() {
		return getDocumentRequest;
	}

	public void setGetDocumentRequest(Request<ParamGetDocument> getDocumentRequest) {
		this.getDocumentRequest = getDocumentRequest;
	}
	
}
