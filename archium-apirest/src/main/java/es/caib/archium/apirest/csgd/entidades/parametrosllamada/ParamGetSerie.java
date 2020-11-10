package es.caib.archium.apirest.csgd.entidades.parametrosllamada;

import es.caib.archium.apirest.csgd.entidades.comunes.SerieId;

public class ParamGetSerie {
	
	private SerieId documentId;

	public SerieId getDocumentId() {
		return documentId;
	}

	public void setDocumentId(SerieId documentId) {
		this.documentId = documentId;
	}
}
