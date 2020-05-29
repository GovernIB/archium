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
import es.caib.archium.ejb.QuadreClassificacioEJB;
import es.caib.archium.ejb.SerieEJB;
import es.caib.archium.ejb.TipuDictamenEJB;
import es.caib.archium.ejb.TipusSerieEJB;
import es.caib.archium.ejb.service.DictamenService;
import es.caib.archium.ejb.service.FuncioService;
import es.caib.archium.ejb.service.QuadreClassificacioService;
import es.caib.archium.ejb.service.SerieService;
import es.caib.archium.ejb.service.TipusSerieService;
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
public class FuncioFrontService {
	
	@Inject
	FuncioService funcionesEJB;
	
	@Inject
	QuadreClassificacioService quadreclassificacioEJB;
	
	@Inject
	TipusSerieService tipusSerieEJB;
	
	@Inject
	DictamenService dictamenEJB;
	
	@Inject
	SerieService serieEJB;

	@Inject
	DictamenFrontService dictamenServices;
	
	@Transactional
	public TreeNode getContent(QuadreObject quadre) throws I18NException {	

		TreeNode root = new DefaultTreeNode(new Document(0, "All", "All", "All", null), null);
		this.recursiveTree(quadre, root, null);
		
		return root;
		
	}
	
	
	private List<Funcio> getFuncioByParent(QuadreObject quadre, Funcio f) throws I18NException{		
		Long idFuncio = (f!=null ? f.getId() : null);
		return funcionesEJB.getByParentAndQuadre(idFuncio, quadre.getId());
	}
	
	private List<Dictamen> getDictamenBySerie(Seriedocumental s) throws I18NException{
		return dictamenEJB.getBySerieId(s.getId());
	}
	
	private List<Seriedocumental> getSeriesByFuncio(Funcio f) throws I18NException{
		Long idFuncio = (f!=null ? f.getId() : null);
		return serieEJB.getByFuncio(idFuncio);
	}
	
	
	private void recursiveTree(QuadreObject quadre, TreeNode node, Funcio funcio) throws I18NException {
			
		
		List<Funcio> resF = this.getFuncioByParent(quadre, funcio);
		List<Seriedocumental> resS = this.getSeriesByFuncio(funcio != null ?funcio : null);
		if (resS!=null && funcio != null) {
			for(Seriedocumental s : resS)
			{				
				TreeNode serieNode = new DefaultTreeNode(new Document<SerieDocumentalObject>(s.getId(), s.getCodi(), s.getNom(), "Serie", new SerieDocumentalObject(s)), node);
				List<Dictamen> resD = this.getDictamenBySerie(s);
				
				if (resD.size()>0) {
					for(Dictamen d : resD)
					{	
						TreeNode dictamenNode = new DefaultTreeNode(new Document<DictamenObject>(d.getId(), d.getCodi(), d.getAcciodictaminada(), "Dictamen", new DictamenObject(d)), serieNode);
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
		
		Funcio f = this.funcionesEJB.findById(id);
		
		if (f!=null) {
			return new FuncioObject(f);
		}
		
		return null;
	}
	
	public List<FuncioObject> findAllByQuadre(QuadreObject quadre)throws I18NException{	
		
		List<FuncioObject> listaFunciones = new ArrayList<FuncioObject>();
		
		List<Funcio> res= funcionesEJB.getByQuadre(quadre.getId());
				
		for(Funcio f : res)
		{				
			listaFunciones.add(new FuncioObject(f));
		}
		
		return listaFunciones;
	}
	
	public List<FuncioObject> findAll() throws I18NException{	
		
		List<FuncioObject> listaFunciones = new ArrayList<FuncioObject>();
		
		List<Funcio> res= funcionesEJB.findAll();
		
		for(Funcio f : res)
		{				
			listaFunciones.add(new FuncioObject(f));
		}
		
		return listaFunciones;
	}
	
	public List<FuncioObject> loadTree(Long quadreId, Long fromFuncioId) throws I18NException{	
		
		List<FuncioObject> listaFunciones = new ArrayList<FuncioObject>();
		
		List<Funcio> res= funcionesEJB.loadTree(quadreId, fromFuncioId);
		
		this.recursiveTreeFuncios(res, listaFunciones, 0);
		
		return listaFunciones;
		
	}
	
	private void recursiveTreeFuncios(List<Funcio> listaDb, List<FuncioObject> lista, int nivel) {
				
		for(Funcio f:listaDb) {
			FuncioObject fObj = new FuncioObject(f);
			fObj.setNumTabs(nivel);
			lista.add(fObj);
			this.recursiveTreeFuncios(f.getAchFuncios(), lista, nivel+1);
			
		}
		
	}
	
	@Transactional
	public FuncioObject create(FuncioObject funcioObject, QuadreObject fkQuadreObject, FuncioObject fkFuncioObject, TipusSerieObject fkTipusserie) throws I18NException {		
		Funcio funcio = funcioObject.toDbObject(null, null, null);
		funcio.setAchQuadreclassificacio(fkQuadreObject!=null ? this.quadreclassificacioEJB.getReference(fkQuadreObject.getId()): null);
		funcio.setAchFuncio(fkFuncioObject!=null ? this.funcionesEJB.getReference(fkFuncioObject.getId()): null);
		funcio.setAchTipusserie(fkTipusserie!=null ? this.tipusSerieEJB.getReference(fkTipusserie.getId()): null);
		return new FuncioObject(this.funcionesEJB.create(funcio));
	}
	
	
	@Transactional
	public FuncioObject update(FuncioObject funcioObject) throws I18NException {
		Funcio funcio = this.funcionesEJB.getReference(funcioObject.getId());
		funcio.setCodi(funcioObject.getCodi());
		funcio.setNom(funcioObject.getNom());
		funcio.setNomcas(funcioObject.getNomcas());
		funcio.setEstat(funcioObject.getEstat());
		funcio.setOrdre(funcioObject.getOrdre());
		if(funcioObject.getFuncioPare() == null)
			funcio.setAchFuncio(null);
		else
			funcio.setAchFuncio(funcionesEJB.getReference(funcioObject.getFuncioPare().getId()));
		
		funcio.setAchTipusserie(funcioObject.getTipoSerie()!=null ? this.tipusSerieEJB.getReference(funcioObject.getTipoSerie().getId()): null);
		funcio.setModificacio(new Date());
		return new FuncioObject(funcionesEJB.update(funcio));
	}
	
	public List<TipusSerieObject> findAllTipusSerie() throws I18NException{

		List<TipusSerieObject> listaTipusserie = new ArrayList<TipusSerieObject>();
		List<Tipusserie> res= tipusSerieEJB.findAll();		
		for(Tipusserie ts : res)
		{				
			listaTipusserie.add(new TipusSerieObject(ts));
		}
		return listaTipusserie;
	}
	
	
}
