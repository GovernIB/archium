package es.caib.archium.objects;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import es.caib.archium.persistence.model.Aplicacio;
import es.caib.archium.persistence.model.Familiaprocediment;
import es.caib.archium.persistence.model.Formainici;
import es.caib.archium.persistence.model.Materia;
import es.caib.archium.persistence.model.Nivellelectronic;
import es.caib.archium.persistence.model.Normativa;
import es.caib.archium.persistence.model.Procediment;
import es.caib.archium.persistence.model.Seriedocumental;
import es.caib.archium.persistence.model.Silenci;
import es.caib.archium.persistence.model.TipusdocumentProcediment;
import es.caib.archium.persistence.model.Tipusdocumental;
import es.caib.archium.persistence.model.Tipuspublic;
import es.caib.archium.services.ProcedimentServices;

public class ProcedimentObject {
	private Long 			id;
	private String 			codisia;
	private String 			nom;
	private String 			objecte;
	//private EstatObject   Estat;  //En el futuroo se añadira esta tabla y tendra que transformarse
	private String 			estat;
	private String 			destinataris;
	private String 			observacions;
	private String 			uri;
	
	private FamiliaprocedimentObject 		familiaprocediment;
	private FormainiciObject 				formainici;
	private SilenciObject 					silenci;
	private NivellelectronicObject 			nivellelectronic;
	private SerieDocumentalObject 			seriedocumental;
	private AplicacioObject				 	aplicacio;
	
	private String 			codirolsac; 			
	private String 			termine;
	private String 			termininotif;
	private Boolean		fiViaAdministrativa;
	private Boolean		taxa;
	private String 			dir3Resolvent;
	private String 			dir3Instructor;
	private Date 			publicacio;
	private Date 			caducitat;
	private Date 			modificacio;
	private String 			gestor;
	
	private List<MateriaObject> 					listaMateria;
	private List<NormativaObject> 					listaNormativa;
	private List<tipusPublicObject> 				listaTipuPublic;
	private List<TipuDocumentalProcedimentObject> 	listaTipuDocumentalProcedimiento;
	private List<TipuDocumentalObject> 				listaTipuDocumental;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCodisia() {
		return codisia;
	}
	public void setCodisia(String codisia) {
		this.codisia = codisia;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getObjecte() {
		return objecte;
	}
	public void setObjecte(String objecte) {
		this.objecte = objecte;
	}
	public String getEstat() {
		return estat;
	}
	public void setEstat(String estat) {
		this.estat = estat;
	}
	public String getDestinataris() {
		return destinataris;
	}
	public void setDestinataris(String destinataris) {
		this.destinataris = destinataris;
	}
	public String getObservacions() {
		return observacions;
	}
	public void setObservacions(String observacions) {
		this.observacions = observacions;
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	public FamiliaprocedimentObject getFamiliaprocediment() {
		return familiaprocediment;
	}
	public void setFamiliaprocediment(FamiliaprocedimentObject familiaprocediment) {
		this.familiaprocediment = familiaprocediment;
	}
	public FormainiciObject getFormainici() {
		return formainici;
	}
	public void setFormainici(FormainiciObject formainici) {
		this.formainici = formainici;
	}
	public SilenciObject getSilenci() {
		return silenci;
	}
	public void setSilenci(SilenciObject silenci) {
		this.silenci = silenci;
	}
	public NivellelectronicObject getNivellelectronic() {
		return nivellelectronic;
	}
	public void setNivellelectronic(NivellelectronicObject nivellelectronic) {
		this.nivellelectronic = nivellelectronic;
	}
	public SerieDocumentalObject getSeriedocumental() {
		return seriedocumental;
	}
	public void setSeriedocumental(SerieDocumentalObject seriedocumental) {
		this.seriedocumental = seriedocumental;
	}
	public AplicacioObject getAplicacio() {
		return aplicacio;
	}
	public void setAplicacio(AplicacioObject aplicacio) {
		this.aplicacio = aplicacio;
	}
	public String getCodirolsac() {
		return codirolsac;
	}
	public void setCodirolsac(String codirolsac) {
		this.codirolsac = codirolsac;
	}
	public String getTermine() {
		return termine;
	}
	public void setTermine(String termine) {
		this.termine = termine;
	}
	public String getTermininotif() {
		return termininotif;
	}
	public void setTermininotif(String termininotif) {
		this.termininotif = termininotif;
	}
	public Boolean getFiViaAdministrativa() {
		return fiViaAdministrativa;
	}
	public void setFiViaAdministrativa(Boolean fiViaAdministrativa) {
		this.fiViaAdministrativa = fiViaAdministrativa;
	}
	public Boolean getTaxa() {
		return taxa;
	}
	public void setTaxa(Boolean taxa) {
		this.taxa = taxa;
	}
	public String getDir3Resolvent() {
		return dir3Resolvent;
	}
	public void setDir3Resolvent(String dir3Resolvent) {
		this.dir3Resolvent = dir3Resolvent;
	}
	public String getDir3Instructor() {
		return dir3Instructor;
	}
	public void setDir3Instructor(String dir3Instructor) {
		this.dir3Instructor = dir3Instructor;
	}
	public Date getPublicacio() {
		return publicacio;
	}
	public void setPublicacio(Date publicacio) {
		this.publicacio = publicacio;
	}
	public Date getCaducitat() {
		return caducitat;
	}
	public void setCaducitat(Date caducitat) {
		this.caducitat = caducitat;
	}
	public Date getModificacio() {
		return modificacio;
	}
	public void setModificacio(Date modificacio) {
		this.modificacio = modificacio;
	}
	public String getGestor() {
		return gestor;
	}
	public void setGestor(String gestor) {
		this.gestor = gestor;
	}
	public ProcedimentObject() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public ProcedimentObject(String codisia, String nom, String objecte, String estat, String destinataris,
			String observacions, String uri, FamiliaprocedimentObject familiaprocediment, FormainiciObject formainici,
			SilenciObject silenci, NivellelectronicObject nivellelectronic, SerieDocumentalObject seriedocumental, AplicacioObject aplicacio,
			String codirolsac, String termine, String termininotif, Boolean fiViaAdministrativa, Boolean taxa,
			String dir3Resolvent, String dir3Instructor, Date publicacio, Date caducitat, Date modificacio,String gestor) {
		super();		
		this.codisia = codisia;
		this.nom = nom;
		this.objecte = objecte;
		this.estat = estat;
		this.destinataris = destinataris;
		this.observacions = observacions;
		this.uri = uri;
		this.familiaprocediment = familiaprocediment;
		this.formainici = formainici;
		this.silenci = silenci;
		this.nivellelectronic = nivellelectronic;
		this.seriedocumental = seriedocumental;
		this.aplicacio = aplicacio;
		this.codirolsac = codirolsac;
		this.termine = termine;
		this.termininotif = termininotif;
		this.fiViaAdministrativa = fiViaAdministrativa;
		this.taxa = taxa;
		this.dir3Resolvent = dir3Resolvent;
		this.dir3Instructor = dir3Instructor;
		this.publicacio = publicacio;
		this.caducitat = caducitat;
		this.gestor = gestor;
		this.modificacio 	= modificacio;
	}
	
	public ProcedimentObject(Procediment i) {
		super();
		this.id				= i.getId();
		this.codisia 		= i.getCodirolsac();
		this.nom 			= i.getNom();
		this.objecte 		= i.getObjecte();
		this.estat 			= i.getEstat();
		this.destinataris 	= i.getDestinataris();
		this.observacions 	= i.getObservacions();
		this.uri 			= i.getUri();
		this.familiaprocediment = new FamiliaprocedimentObject(i.getAchFamiliaprocediment());
		this.formainici 	= new FormainiciObject(i.getAchFormainici());
		this.silenci 		= new SilenciObject(i.getAchSilenci());
		this.nivellelectronic = new NivellelectronicObject(i.getAchNivellelectronic());
		this.seriedocumental = new SerieDocumentalObject(i.getAchSeriedocumental());
		this.aplicacio 		= new AplicacioObject(i.getAchAplicacio());
		this.codirolsac 	= i.getCodirolsac();
		this.termine 		= i.getTermini();
		this.termininotif 	= i.getTermininotif();
		if(i.getFiviaadministrativa()!=null) {
			this.fiViaAdministrativa = (i.getFiviaadministrativa().signum() == 1 ? true : false);
		} else {
			this.fiViaAdministrativa = null;
		}
		if(i.getTaxa()!=null) {
			this.taxa = (i.getTaxa().signum() == 1 ? true : false);
		} else {
			this.taxa = null;
		}		
		this.dir3Resolvent 	= i.getDir3Resolvent();
		this.dir3Instructor = i.getDir3Instructor();
		this.publicacio 	= i.getPublicacio();
		this.caducitat 		= i.getCaducitat();
		this.gestor 		= i.getGestor();
		this.modificacio 	= i.getModificacio();
		
		if(i.getAchTipusdocumentProcediments()!=null) {
			List<TipuDocumentalProcedimentObject> list1 = new ArrayList<>();
			for(TipusdocumentProcediment var: i.getAchTipusdocumentProcediments())
			{
				list1.add(new TipuDocumentalProcedimentObject(var));
			}
			this.setListaTipuDocumentalProcedimiento(list1);
		}
		
		if(i.getAchNormativas()!=null) {
			List<NormativaObject> list2 = new ArrayList<>();
			for(Normativa var: i.getAchNormativas())
			{
				list2.add(new NormativaObject(var));
			}
			this.listaNormativa = list2;
		}
		
		if(i.getAchMaterias()!=null) {
			List<MateriaObject> list3 = new ArrayList<>();
			for(Materia var: i.getAchMaterias())
			{
				list3.add(new MateriaObject(var));
			}
			this.listaMateria = list3;
		}
		
		if(i.getAchTipuspublics()!=null) {
			List<tipusPublicObject> list4 = new ArrayList<>();
			for(Tipuspublic var: i.getAchTipuspublics())
			{
				list4.add(new tipusPublicObject(var));
			}
			this.listaTipuPublic = list4;
		}
	}
	
	public ProcedimentObject(ProcedimentObject i) {
		// TODO Auto-generated constructor stub
		this.id 			= i.getId();
		this.codisia 		= i.getCodisia();
		this.nom 			= i.getNom();
		this.objecte 		= i.getObjecte();
		this.estat 			= i.getEstat();
		this.destinataris 	= i.getDestinataris();
		this.observacions 	= i.getObservacions();
		this.uri 			= i.getUri();
		this.familiaprocediment = i.getFamiliaprocediment();
		this.formainici 	= i.getFormainici();
		this.silenci 		= i.getSilenci();
		this.nivellelectronic 	= i.getNivellelectronic();
		this.seriedocumental 	= i.getSeriedocumental();
		this.aplicacio 		= i.getAplicacio();
		this.codirolsac 	= i.getCodirolsac();
		this.termine 		= i.getTermine();
		this.termininotif 	= i.getTermininotif();
		this.fiViaAdministrativa = i.getFiViaAdministrativa();
		this.taxa 			= i.getTaxa();
		this.dir3Resolvent 	= i.getDir3Resolvent();
		this.dir3Instructor = i.getDir3Instructor();
		this.publicacio 	= i.getPublicacio();
		this.caducitat	 	= i.getCaducitat();
		this.gestor 		= i.getGestor();
		this.modificacio 	= i.getModificacio();

	}
	
		
	@Override
	public String toString() {
		return "ProcedimentObject [id=" + id + ", codisia=" + codisia + ", nom=" + nom + ", uri="
				+ uri + ", familiaprocediment=" + familiaprocediment + ", formainici=" + formainici + ", silenci="
				+ silenci + ", nivellelectronic=" + nivellelectronic + ", seriedocumental=" + seriedocumental
				+ ", aplicacio=" + aplicacio + ", codirolsac=" + codirolsac + ", termine=" + termine + ", termininotif="
				+ termininotif + ", fiViaAdministrativa=" + fiViaAdministrativa + ", taxa=" + taxa + ", dir3Resolvent="
				+ dir3Resolvent + ", dir3Instructor=" + dir3Instructor + ", publicacio=" + publicacio + ", caducitat="
				+ caducitat + ", gestor=" + gestor + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProcedimentObject other = (ProcedimentObject) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	public List<MateriaObject> getListaMateria() {
		return listaMateria;
	}
	public void setListaMateria(List<MateriaObject> listaMateria) {
		this.listaMateria = listaMateria;
	}
	public List<NormativaObject> getListaNormativa() {
		return listaNormativa;
	}
	public void setListaNormativa(List<NormativaObject> listaNormativa) {
		this.listaNormativa = listaNormativa;
	}
	
	public List<TipuDocumentalProcedimentObject> getListaTipuDocumentalProcedimiento() {
		return listaTipuDocumentalProcedimiento;
	}
	public void setListaTipuDocumentalProcedimiento(List<TipuDocumentalProcedimentObject> listaTipuDocumentalProcedimiento) {
		this.listaTipuDocumentalProcedimiento = listaTipuDocumentalProcedimiento;
	}
	
	public List<TipuDocumentalObject> getListaTipuDocumental() {
		return listaTipuDocumental;
	}
	public void setListaTipuDocumental(List<TipuDocumentalObject> listaTipuDocumental) {
		this.listaTipuDocumental = listaTipuDocumental;
	}
	public List<tipusPublicObject> getListaTipuPublic() {
		return listaTipuPublic;
	}
	public void setListaTipuPublic(List<tipusPublicObject> listaTipuPublic) {
		this.listaTipuPublic = listaTipuPublic;
	}
	
}
