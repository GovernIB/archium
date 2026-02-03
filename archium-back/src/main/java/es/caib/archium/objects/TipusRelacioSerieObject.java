package es.caib.archium.objects;

import es.caib.archium.persistence.model.TipusRelacioSerie;

import java.util.Objects;

public class TipusRelacioSerieObject {
	private Long 	id;
	private String 	nom;
	private String 	nomcas;
	private String 	descripcio;
	private String 	descripciocas;

	//methods

	public TipusRelacioSerieObject() {
		super();
	}

	public TipusRelacioSerieObject(Long id, String nom) {
		super();
		this.id = id;
		this.nom = nom;
	}

	public TipusRelacioSerieObject(TipusRelacioSerie bd) {
		super();
		this.id = bd.getId();
		this.nom 		= bd.getNom();
		this.nomcas 		= bd.getNomcas();
		this.descripcio 	= bd.getDescripcio();
		this.descripciocas 	= bd.getDescripciocas();
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

	public String getNomcas() {
		return nomcas;
	}

	public void setNomcas(String nomcas) {
		this.nomcas = nomcas;
	}

	public String getDescripcio() {
		return descripcio;
	}

	public void setDescripcio(String descripcio) {
		this.descripcio = descripcio;
	}

	public String getDescripciocas() {
		return descripciocas;
	}

	public void setDescripciocas(String descripciocas) {
		this.descripciocas = descripciocas;
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
		TipusRelacioSerieObject other = (TipusRelacioSerieObject) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "TipusRelacioSerieObject{" +
				"id=" + id +
				", nom='" + nom + '\'' +
				", nomcas='" + nomcas + '\'' +
				", descripcio='" + descripcio + '\'' +
				", descripciocas='" + descripciocas + '\'' +
				'}';
	}
}
