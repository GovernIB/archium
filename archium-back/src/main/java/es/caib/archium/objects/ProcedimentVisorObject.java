package es.caib.archium.objects;

import es.caib.archium.persistence.model.Procediment;

import java.util.Objects;

public class ProcedimentVisorObject {
	
	private Long id;
	private String nom;
	private String codisia;
	private SerieDocumentalObject 			seriedocumental;
	
	
	public ProcedimentVisorObject(Long id, String nom, String codisia, SerieDocumentalObject seriedocumental) {
		
		this.id = id;
		this.nom = nom;
		this.codisia = codisia;
		this.seriedocumental = seriedocumental;
	}
	
	public ProcedimentVisorObject(Procediment i) {
		
		this.id = i.getId();
		this.nom = i.getNom();
		this.codisia = i.getCodisia();
		if(i.getAchSeriedocumental()!=null) {
			this.seriedocumental = new SerieDocumentalObject(i.getAchSeriedocumental());
		}
	}


	public ProcedimentVisorObject() {
		
	}
	

	public ProcedimentVisorObject(Long idProcediment) {
		super();
		this.setId(idProcediment);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProcedimentVisorObject other = (ProcedimentVisorObject) obj;
		return Objects.equals(id, other.id);
	}
	
	
	
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getNom() {
		return nom;
	}


	public void setNom(String nom) {
		this.nom = nom;
	}


	public String getCodisia() {
		return codisia;
	}


	public void setCodisia(String codisia) {
		this.codisia = codisia;
	}

	public SerieDocumentalObject getSeriedocumental() {
		return seriedocumental;
	}

	public void setSeriedocumental(SerieDocumentalObject seriedocumental) {
		this.seriedocumental = seriedocumental;
	}
	
	

}
