package es.caib.archium.apirest.facade.pojos.borrar;


import es.caib.archium.apirest.csgd.entidades.comunes.RespuestaGenerica;

public class GetDocumentResult {
	private RespuestaGenerica<DocumentNode> getDocumentResult;

	public RespuestaGenerica<DocumentNode> getGetDocumentResult() {
		return getDocumentResult;
	}

	public void setGetDocumentResult(RespuestaGenerica<DocumentNode> getDocumentResult) {
		this.getDocumentResult = getDocumentResult;
	}
	
	
}
