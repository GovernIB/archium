package es.caib.archium.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import es.caib.archium.commons.i18n.I18NException;
import es.caib.archium.commons.query.OrderBy;
import es.caib.archium.commons.query.OrderType;
import es.caib.archium.ejb.DictamenEJB;
import es.caib.archium.ejb.FuncioEJB;
import es.caib.archium.ejb.QuadreclassificacioEJB;
import es.caib.archium.ejb.SerieEJB;
import es.caib.archium.ejb.TipuDictamenEJB;
import es.caib.archium.ejb.TipusserieEJB;
import es.caib.archium.ejb.service.DictamenService;
import es.caib.archium.objects.DictamenObject;
import es.caib.archium.objects.Document;
import es.caib.archium.objects.FuncioObject;
import es.caib.archium.objects.QuadreObject;
import es.caib.archium.objects.SerieDocumentalObject;
import es.caib.archium.objects.TipusSerieObject;
import es.caib.archium.persistence.model.Dictamen;
import es.caib.archium.persistence.model.Funcio;
import es.caib.archium.persistence.model.Quadreclassificacio;
import es.caib.archium.persistence.model.Seriedocumental;
import es.caib.archium.persistence.model.Tipusserie;

@Named
@ApplicationScoped
public class FuncioService {
	
	@Inject
	FuncioEJB funcionesEjb;
	
	@Inject
	QuadreclassificacioEJB quadreclassificacioEjb;
	
	@Inject
	TipusserieEJB tipusserieEjb;
	
	@Inject
	DictamenEJB dictamenEJB;
	
	@Inject
	SerieEJB serieEJB;

	@Inject
	DictamenServices dictamenService;
	
	@Transactional
	public TreeNode getContent(QuadreObject quadre) throws I18NException {	

		TreeNode root = new DefaultTreeNode(new Document(0, "All", "All", "All", null), null);
		this.recursiveTree(quadre, root, null);
		
		return root;
		
	}
	
	private List<Funcio> getFuncioByParent(QuadreObject quadre, Funcio f) throws I18NException{
		Map<String, Object> filtersF = new HashMap<String, Object>();		
		filtersF.put("achFuncio", f);
		filtersF.put("achQuadreclassificacio", this.quadreclassificacioEjb.getReference(quadre.getId()));
		OrderBy orderF = new OrderBy("id", OrderType.ASC);	
		
		return funcionesEjb.findFiltered(filtersF, orderF);
	}
	
	private List<Dictamen> getDictamenBySerie(Seriedocumental s) throws I18NException{
		Map<String, Object> filtersD = new HashMap<String, Object>();		
		filtersD.put("achSeriedocumental", s);
		OrderBy orderD = new OrderBy("id", OrderType.DESC);	
		return dictamenEJB.findFiltered(filtersD, orderD);
	}
	
	private List<Seriedocumental> getSeriesByFuncio(Funcio f) throws I18NException{
		Map<String, Object> filtersS = new HashMap<String, Object>();		
		filtersS.put("achFuncio", f);
		OrderBy orderS = new OrderBy("id", OrderType.DESC);	
		return serieEJB.findFiltered(filtersS, orderS);
	}
	
	
	private void recursiveTree(QuadreObject quadre, TreeNode node, Funcio funcio) throws I18NException {
			
		
		List<Funcio> resF = this.getFuncioByParent(quadre, funcio);
		List<Seriedocumental> resS = this.getSeriesByFuncio(funcio != null ?funcio : null);
		if (resS.size()>0 && funcio != null) {
			for(Seriedocumental s : resS)
			{				
				TreeNode serieNode = new DefaultTreeNode(new Document<SerieDocumentalObject>(s.getId(), s.getCodi(), s.getNom(), "Serie", new SerieDocumentalObject(s)), node);
				List<Dictamen> resD = this.getDictamenBySerie(s);
				
				if (resD.size()>0) {
					for(Dictamen d : resD)
					{	
						TreeNode dictamenNode = new DefaultTreeNode(new Document<DictamenObject>(d.getId(), "ID"+d.getId(), d.getAcciodictaminada(), "Dictamen", new DictamenObject(d)), serieNode);
					}
				}
				
			}
		}
		
		if (resF.size()>0) {
			for(Funcio f : resF)
			{				
				TreeNode funcioNode = new DefaultTreeNode(new Document<FuncioObject>(f.getId(), f.getCodi(), f.getNom(), "Funcio", new FuncioObject(f)), node);
				this.recursiveTree(quadre, funcioNode, f);
			}
		}
	}
	
	public FuncioObject findById(long id) {
		
		Funcio f = this.funcionesEjb.findById(id);
		
		if (f!=null) {
			return new FuncioObject(f);
		}
		
		return null;
	}
	
	public List<FuncioObject> findAllByQuadre(QuadreObject quadre)throws I18NException{	
		
		List<FuncioObject> listaFunciones = new ArrayList<FuncioObject>();
		
		Map<String, Object> filtersF = new HashMap<String, Object>();		
		filtersF.put("achQuadreclassificacio", this.quadreclassificacioEjb.getReference(quadre.getId()));
		OrderBy orderF = new OrderBy("ordre", OrderType.ASC);	
		List<Funcio> res= funcionesEjb.findFiltered(filtersF, orderF);
				
		for(Funcio f : res)
		{				
			listaFunciones.add(new FuncioObject(f));
		}
		
		return listaFunciones;
	}
	
	public List<FuncioObject> findAll() throws I18NException{	
		
		List<FuncioObject> listaFunciones = new ArrayList<FuncioObject>();
		
		List<Funcio> res= funcionesEjb.findAll();
		
		for(Funcio f : res)
		{				
			listaFunciones.add(new FuncioObject(f));
		}
		
		return listaFunciones;
	}
	
	@Transactional
	public FuncioObject create(FuncioObject funcioObject, QuadreObject fkQuadreObject, FuncioObject fkFuncioObject, TipusSerieObject fkTipusserie) throws I18NException {		
		Funcio funcio = funcioObject.toDbObject(null, null, null);
		funcio.setAchQuadreclassificacio(fkQuadreObject!=null ? this.quadreclassificacioEjb.getReference(fkQuadreObject.getId()): null);
		funcio.setAchFuncio(fkFuncioObject!=null ? this.funcionesEjb.getReference(fkFuncioObject.getId()): null);
		funcio.setAchTipusserie(fkTipusserie!=null ? this.tipusserieEjb.getReference(fkTipusserie.getId()): null);
		return new FuncioObject(this.funcionesEjb.create(funcio));
	}
	
	
	@Transactional
	public FuncioObject update(FuncioObject funcioObject) throws I18NException {
		Funcio funcio = this.funcionesEjb.getReference(funcioObject.getId());
		funcio.setCodi(funcioObject.getCodi());
		funcio.setNom(funcioObject.getNom());
		funcio.setNomcas(funcioObject.getNomcas());
		funcio.setEstat(funcioObject.getEstat());
		funcio.setOrdre(funcioObject.getOrdre());
		if(funcioObject.getFuncioPare() == null)
			funcio.setAchFuncio(null);
		else
			funcio.setAchFuncio(funcionesEjb.getReference(funcioObject.getFuncioPare().getId()));
		
		funcio.setAchTipusserie(funcioObject.getTipoSerie()!=null ? this.tipusserieEjb.getReference(funcioObject.getTipoSerie().getId()): null);
		funcio.setModificacio(new Date());
		return new FuncioObject(funcionesEjb.update(funcio));
	}
	
	public List<TipusSerieObject> findAllTipusSerie() throws I18NException{

		List<TipusSerieObject> listaTipusserie = new ArrayList<TipusSerieObject>();
		List<Tipusserie> res= tipusserieEjb.findAll();		
		for(Tipusserie ts : res)
		{				
			listaTipusserie.add(new TipusSerieObject(ts));
		}
		return listaTipusserie;
	}
	
	/*private Funcio parseObject2Db(FuncioObject obFuncio){
		
		Funcio f = new Funcio();
		f.setCodi(obFuncio.getCodi());
		f.setNom(obFuncio.getNom());
		f.setNomcas(obFuncio.getNomcas());
		f.setEstat(obFuncio.getEstat());
		f.setOrdre(obFuncio.getOrdre());
		f.setInici(obFuncio.getInici());
		f.setModificacio(obFuncio.getModificacio());
		f.setFi(obFuncio.getFi());
		
		return f;
	}
	
	private FuncioObject parseDbO2bject(Funcio dbFuncio) {
		
		QuadreObject q = null;
		FuncioObject fp = null;
		TipusSerieObject ts = null;
		
		
		if(dbFuncio.getAchQuadreclassificacio()!=null) {
			q = new QuadreObject();
			q.setId(dbFuncio.getAchQuadreclassificacio().getId());
			q.setNom(dbFuncio.getAchQuadreclassificacio().getNom());
		}
		
		if(dbFuncio.getAchFuncio()!=null) {
			fp = new FuncioObject();
			fp.setId(dbFuncio.getAchFuncio().getId());
			fp.setCodi(dbFuncio.getAchFuncio().getCodi());
			fp.setNom(dbFuncio.getAchFuncio().getNom());
		}
		
		if(dbFuncio.getAchTipusserie()!=null) {
			ts = new TipusSerieObject();
			ts.setId(dbFuncio.getAchTipusserie().getId());
			ts.setNom(dbFuncio.getAchTipusserie().getNom());
		}
		
		FuncioObject f = new FuncioObject(dbFuncio.getId(), 
								dbFuncio.getCodi(), 
								dbFuncio.getNom(), 
								dbFuncio.getNomcas(), 
								dbFuncio.getEstat(), 
								dbFuncio.getOrdre(), 
								dbFuncio.getInici(), 
								dbFuncio.getModificacio(),
								dbFuncio.getFi(), 
								ts,
								q, 
								fp
				);
		
		return f;
	}*/

	
}
