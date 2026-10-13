package es.caib.archium.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.caib.archium.commons.i18n.I18NException;
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
import es.caib.archium.persistence.model.Seriedocumental;
import es.caib.archium.persistence.model.TipusSerie;

@Named
@ApplicationScoped
public class FuncioFrontService {
	
	protected final Logger log = LoggerFactory.getLogger(this.getClass());
	
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
		log.debug("Dentro del método 'getContent' para el objeto Quadre " + quadre.getId());
		TreeNode root = new DefaultTreeNode(new Document(0, "All", "All", "All", null), null);
		log.debug("Generando cuadro a partir del nodo raíz ...");
//		this.recursiveTree(quadre, root, null);
		this.initQuadre(quadre, root);
		
		return root;
		
	}
	
	
	private List<Funcio> getFuncioByParent(QuadreObject quadre, Funcio f) throws I18NException{		
		try {
			Long idFuncio = (f!=null ? f.getId() : null);
			return funcionesEJB.getByParentAndQuadre(idFuncio, quadre.getId());
		} catch(NullPointerException e) {
			throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "getFuncioByParent");
		} catch(Exception e) {
			throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "getFuncioByParent");
		}
	}
	
	public List<Funcio> getFuncioByParentId(QuadreObject quadre, Long idFuncio) throws I18NException{		
		try {
			return funcionesEJB.getByParentAndQuadre(idFuncio, quadre.getId());
		} catch(NullPointerException e) {
			throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "getFuncioByParent");
		} catch(Exception e) {
			throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "getFuncioByParent");
		}
	}
	
	public List<Dictamen> getDictamenBySerie(Seriedocumental s) throws I18NException{		
		try {
			return dictamenEJB.getBySerieId(s.getId());
		} catch(NullPointerException e) {
			throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "getDictamenBySerie");
		} catch(Exception e) {
			throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "getDictamenBySerie");
		}
	}
	
	private List<Seriedocumental> getSeriesByFuncio(Funcio f) throws I18NException{
		try {
			Long idFuncio = (f!=null ? f.getId() : null);
			return serieEJB.getByFuncio(idFuncio);
		} catch(NullPointerException e) {
			throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "getSeriesByFuncio");
		} catch(Exception e) {
			throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "getSeriesByFuncio");
		}
	}
	
	public List<Seriedocumental> getSeriesByIdFuncio(Long idFuncio) throws I18NException{
		try {
			return serieEJB.getByFuncio(idFuncio);
		} catch(NullPointerException e) {
			throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "getSeriesByFuncio");
		} catch(Exception e) {
			throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "getSeriesByFuncio");
		}
	}
	
	public void initQuadre(QuadreObject quadre, TreeNode node) throws I18NException {
		log.debug("Dentro del método 'initQuadre'");
		try {
			List<Funcio> resF = this.getFuncioByParent(quadre, null);
			
			if (resF.size()>0) {
				for(Funcio f : resF)
				{				
					TreeNode funcioNode = new DefaultTreeNode(new Document<FuncioObject>(f.getId(), f.getCodi(), f.getNom(), "Funcio", new FuncioObject(f)), node);
					new DefaultTreeNode("DUMMY", new Document<String>(-1L, "DUMMY", "DUMMY", "DUMMY", "DUMMY"), funcioNode);
//					this.recursiveTree(quadre, funcioNode, f);
				}
			}
		} catch(NullPointerException e) {
			throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "recursiveTree");
		} catch(Exception e) {
			throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "recursiveTree");
		}
		log.debug("Saliendo del método 'initQuadre'");
	}
	
	public void expandQuadre(QuadreObject quadre, TreeNode node, Long idFuncio) 
	        throws I18NException {
	    log.debug("Dentro del método 'expandQuadre'");
	    try {
	        // 1) Si ya tiene hijos reales, no recargar
	        if (node.getChildCount() > 0) {
	            TreeNode first = node.getChildren().get(0);
	            if (!"DUMMY".equals(first.getType())) {
	                log.debug("Nodo ya procesado previamente (hijos reales presentes)");
	                return;
	            } else {
	                log.debug("Nodo DUMMY encontrado, eliminando para cargar hijos reales");
	                node.getChildren().clear();
	            }
	        }

	        // 2) Cargar series si es una función concreta
	        if (idFuncio != null) {
	            List<Seriedocumental> resS = this.getSeriesByIdFuncio(idFuncio);
	            if (resS != null) {
	                for (Seriedocumental s : resS) {
	                    TreeNode serieNode = new DefaultTreeNode(
	                        new Document<SerieDocumentalObject>(
	                            s.getId(), s.getCodi(), s.getNom(), "Serie",
	                            s.getEnviatSAT(), new SerieDocumentalObject(s)),
	                        node
	                    );
	                    // DUMMY para que la serie sea expandible
	                    new DefaultTreeNode("DUMMY", null, serieNode);
	                }
	            }
	        }

	        // 3) Cargar sub-funciones hijas
	        List<Funcio> resF = this.getFuncioByParentId(quadre, idFuncio);
	        if (resF != null && !resF.isEmpty()) {
	            for (Funcio f : resF) {
	                TreeNode funcioNode = new DefaultTreeNode(
	                    new Document<FuncioObject>(
	                        f.getId(), f.getCodi(), f.getNom(), "Funcio",
	                        new FuncioObject(f)),
	                    node
	                );
	                new DefaultTreeNode("DUMMY", null, funcioNode);
	            }
	        }

	    } catch (NullPointerException e) {
	        throw new I18NException("excepcion.general.NullPointerException",
	            this.getClass().getSimpleName(), "expandQuadre");
	    } catch (Exception e) {
	        throw new I18NException("excepcion.general.Exception",
	            this.getClass().getSimpleName(), "expandQuadre");
	    }
	    log.debug("Saliendo del método 'expandQuadre'");
	}
	
	public void expandSerie(QuadreObject quadre, TreeNode node, Long idSerie) throws I18NException {
	    log.debug("Dentro del método 'expandSerie' para serieId=" + idSerie);
	    try {
	        if (node.getChildCount() > 0) {
	            TreeNode first = node.getChildren().get(0);
	            if (!"DUMMY".equals(first.getType())) {
	                log.debug("Este nodo serie ya se ha procesado previamente (hijos reales presentes)");
	                return; // ya cargado
	            } else {
	                log.debug("Nodo DUMMY en serie, se elimina para cargar dictámenes");
	                node.getChildren().clear();
	            }
	        }

	        
	        List<DictamenObject> resD = this.getDictamenBySerieId(idSerie);

	        if (resD != null && !resD.isEmpty()) {
	            for (DictamenObject d : resD) {
	                new DefaultTreeNode(
	                    new Document<DictamenObject>(d.getId(), d.getCodi(), d.getAccioDictaminada(), "Dictamen", d),
	                    node
	                );
	            }
	        }
	    } catch (NullPointerException e) {
	        throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "expandSerie");
	    } catch (Exception e) {
	        throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "expandSerie");
	    }
	    log.debug("Saliendo del método 'expandSerie' para serieId=" + idSerie);
	}
	
	private void recursiveTree(QuadreObject quadre, TreeNode node, Funcio funcio) throws I18NException {
		log.debug("Dentro del método 'recursiveTree' para funcion " + (funcio != null ? funcio.getNom() : null));
		try {
			List<Funcio> resF = this.getFuncioByParent(quadre, funcio);
			if (funcio!=null) {
				List<Seriedocumental> resS = this.getSeriesByFuncio(funcio);
				if (resS!=null) {
					for(Seriedocumental s : resS)
					{				
						TreeNode serieNode = new DefaultTreeNode(new Document<SerieDocumentalObject>(s.getId(), s.getCodi(), s.getNom(), "Serie", s.getEnviatSAT(), new SerieDocumentalObject(s)), node);
						List<Dictamen> resD = this.getDictamenBySerie(s);
						
						if (resD.size()>0) {
							for(Dictamen d : resD)
							{	
								TreeNode dictamenNode = new DefaultTreeNode(new Document<DictamenObject>(d.getId(), d.getCodi(), d.getAcciodictaminada(), "Dictamen", new DictamenObject(d)), serieNode);
							}
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
		} catch(NullPointerException e) {
			throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "recursiveTree");
		} catch(Exception e) {
			throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "recursiveTree");
		}
		log.debug("Saliendo del método 'recursiveTree' para funcion " + funcio.getId());
	}
	
	public FuncioObject findById(Long id) throws I18NException {
		try {
			Funcio f = this.funcionesEJB.findById(id);
			
			if (f!=null) {
				return new FuncioObject(f);
			}
			
			return null;
		} catch(NullPointerException e) {
			throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "findById");
		} catch(Exception e) {
			throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "findById");
		}
		
	}
	
	public List<FuncioObject> findAllByQuadre(QuadreObject quadre)throws I18NException{	
		
		
		try {
			List<FuncioObject> listaFunciones = new ArrayList<FuncioObject>();
			
			List<Funcio> res= funcionesEJB.getByQuadre(quadre.getId());
					
			for(Funcio f : res)
			{				
				listaFunciones.add(new FuncioObject(f));
			}
			
			return listaFunciones;
		} catch(NullPointerException e) {
			throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "findAllByQuadre");
		} catch(Exception e) {
			throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "findAllByQuadre");
		}
		
		
	}
	
	public List<FuncioObject> findAll() throws I18NException{	
		
		try {
			List<FuncioObject> listaFunciones = new ArrayList<FuncioObject>();
			
			List<Funcio> res= funcionesEJB.findAll();
			
			for(Funcio f : res)
			{				
				listaFunciones.add(new FuncioObject(f));
			}
			
			return listaFunciones;
		} catch(NullPointerException e) {
			throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "findAll");
		} catch(Exception e) {
			throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "findAll");
		}
		
		
	}
	
	public List<FuncioObject> loadTree(Long quadreId, Long fromFuncioId) throws I18NException{	
		log.debug("Dentro del método 'loadTree'");
		try {
			List<FuncioObject> listaFunciones = new ArrayList<FuncioObject>();
			
			List<Funcio> res= funcionesEJB.loadTree(quadreId, fromFuncioId);
			
			this.recursiveTreeFuncios(res, listaFunciones, 0);
			log.debug("Saliendo del método 'loadTree'");
			return listaFunciones;
		} catch(NullPointerException e) {
			throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "loadTree");
		} catch(Exception e) {
			throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "loadTree");
		}
		
	}
	
	private void recursiveTreeFuncios(List<Funcio> listaDb, List<FuncioObject> lista, int nivel) throws I18NException {
			
		try {
			for(Funcio f:listaDb) {
				FuncioObject fObj = new FuncioObject(f);
				fObj.setNumTabs(nivel);
				lista.add(fObj);
				this.recursiveTreeFuncios(f.getAchFuncios(), lista, nivel+1);
				
			}
		} catch(NullPointerException e) {
			throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "recursiveTreeFuncios");
		} catch(Exception e) {
			throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "recursiveTreeFuncios");
		}

	}
	
	@Transactional
	public FuncioObject create(FuncioObject funcioObject, QuadreObject fkQuadreObject, FuncioObject fkFuncioObject, TipusSerieObject fkTipusserie) throws I18NException {		
		
		try {
			Funcio funcio = funcioObject.toDbObject(null, null, null);
			funcio.setAchQuadreclassificacio(fkQuadreObject!=null ? this.quadreclassificacioEJB.getReference(fkQuadreObject.getId()): null);
			funcio.setAchFuncio(fkFuncioObject!=null ? this.funcionesEJB.getReference(fkFuncioObject.getId()): null);
			funcio.setAchTipusserie(fkTipusserie!=null ? this.tipusSerieEJB.getReference(fkTipusserie.getId()): null);
			return new FuncioObject(this.funcionesEJB.create(funcio));
		} catch(NullPointerException e) {
			throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "create");
		} catch(Exception e) {
			throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "create");
		}
		
		
	}
	
	@Transactional
	public FuncioObject update(FuncioObject funcioObject) throws I18NException {
		
		try {
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
		} catch(NullPointerException e) {
			throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "update");
		} catch(Exception e) {
			throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "update");
		}
		
	}


	public List<TipusSerieObject> findAllTipusSerie() throws I18NException{
		
		try {
			List<TipusSerieObject> listaTipusserie = new ArrayList<>();
			List<TipusSerie> res= tipusSerieEJB.findAll();
			for(TipusSerie ts : res)
			{				
				listaTipusserie.add(new TipusSerieObject(ts));
			}
			return listaTipusserie;
		} catch(NullPointerException e) {
			throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "findAllTipusSerie");
		} catch(Exception e) {
			throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "findAllTipusSerie");
		}
	}

	public TipusSerieObject findTipusSerieById(Long tipusSerieId) throws I18NException{

		try {
			return new TipusSerieObject(tipusSerieEJB.findById(tipusSerieId));

		} catch(NullPointerException e) {
			throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "findAllTipusSerie");
		} catch(Exception e) {
			throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "findAllTipusSerie");
		}
	}
	
	@Transactional
	public void deleteFuncio(Long idFuncio) throws I18NException{
		try {
			this.funcionesEJB.delete(idFuncio);
		} catch(NullPointerException e) {
			throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "deleteFuncio");
		} catch(Exception e) {
			throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "deleteFuncio");
		}
	}


	public TreeNode crearTreeNodeFuncio(FuncioObject seleccionado, Map<Long, TreeNode> funcioMap, TreeNode root) {

		if (!funcioMap.containsKey(seleccionado.getId())) {
			TreeNode padreVisual = null;
		    if (seleccionado.getFuncioPare() != null) {
		    	if (funcioMap.containsKey(seleccionado.getFuncioPare().getId())) {
		    		padreVisual = funcioMap.get(seleccionado.getFuncioPare().getId());
		    	} else {
		    		try {
						padreVisual = crearTreeNodeFuncio(findById(seleccionado.getFuncioPare().getId()), funcioMap, root);
					} catch (I18NException e) {
						e.printStackTrace();
					}
		    	}
		    } else {
		        padreVisual = root;
		    }
	
		    TreeNode nuevoNodo = new DefaultTreeNode(new Document<FuncioObject>(seleccionado.getId(), seleccionado.getCodi(), seleccionado.getNom(), "Funcio", seleccionado), padreVisual);  
		    nuevoNodo.setExpanded(true);
		    funcioMap.put(seleccionado.getId(), nuevoNodo);
		    
		    return nuevoNodo;
		}
		return funcioMap.get(seleccionado.getId());
	}
	
	
	public TreeNode crearTreeNodeSerie(SerieDocumentalObject seleccionado, Map<Long, TreeNode> funcioMap, Map<Long, TreeNode> serieMap, TreeNode root) {
		
		if (!serieMap.containsKey(seleccionado.getSerieId())) {
			TreeNode padreVisual;
		    if (seleccionado.getFuncio() != null) {
		    	if (funcioMap.containsKey(seleccionado.getFuncio().getId())) {
		    		padreVisual = funcioMap.get(seleccionado.getFuncio().getId());
		    	} else {
		    		padreVisual = crearTreeNodeFuncio(seleccionado.getFuncio(), funcioMap, root);
		    	}
		    } else {
		        padreVisual = null;
		    }
	
		    TreeNode nuevoNodo = new DefaultTreeNode(new Document<SerieDocumentalObject>(seleccionado.getSerieId(), seleccionado.getCodi(), seleccionado.getNom(), "Serie", seleccionado.getEnviatSAT(), seleccionado), padreVisual);
		    nuevoNodo.setExpanded(true);
		    serieMap.put(seleccionado.getSerieId(), nuevoNodo);
		    
		    return nuevoNodo;
		}
		return serieMap.get(seleccionado.getSerieId());
	}
	
	public TreeNode crearTreeNodeDictamen(DictamenObject seleccionado, Map<Long, TreeNode> funcioMap, Map<Long, TreeNode> serieMap, Map<Long, TreeNode> dictamenMap, TreeNode root) {
		
		if (!dictamenMap.containsKey(seleccionado.getId())) {
			TreeNode padreVisual;
		    if (seleccionado.getSerieDocumental() != null) {
		    	if (serieMap.containsKey(seleccionado.getSerieDocumental().getSerieId())) {
		    		padreVisual = serieMap.get(seleccionado.getSerieDocumental().getSerieId());
		    	} else {
		    		padreVisual = crearTreeNodeSerie(seleccionado.getSerieDocumental(), funcioMap, serieMap, root);
		    	}
		    } else {
		        padreVisual = null;
		    }
	
		    TreeNode nuevoNodo = new DefaultTreeNode(new Document<DictamenObject>(seleccionado.getId(), seleccionado.getCodi(), seleccionado.getAccioDictaminada(), "Dictamen", seleccionado), padreVisual);
		    nuevoNodo.setExpanded(true);
		    dictamenMap.put(seleccionado.getId(), nuevoNodo);
		    
		    return nuevoNodo;
		}
		return dictamenMap.get(seleccionado.getId());
	}
	
	

	public List<FuncioObject> filtrarFunciones(String searchText, Set<Long> set) throws I18NException {
		try {
			List<FuncioObject> listaFuncioObject = new ArrayList<>();
			List<Funcio> res = funcionesEJB.findFiltrado(searchText, set);
			for(Funcio ts : res)
			{				
				listaFuncioObject.add(new FuncioObject(ts));
			}
			return listaFuncioObject;
		} catch(NullPointerException e) {
			throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "filtrarFunciones");
		} catch(Exception e) {
			throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "filtrarFunciones");
		}
	}


	public List<SerieDocumentalObject> filtrarSeries(String searchText) throws I18NException {
		try {
			List<SerieDocumentalObject> listaSeriesObject = new ArrayList<>();
			List<Seriedocumental> res = serieEJB.findFiltrado(searchText);
			for(Seriedocumental ts : res)
			{				
				listaSeriesObject.add(new SerieDocumentalObject(ts));
			}
			return listaSeriesObject;
		} catch(NullPointerException e) {
			throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "findFiltrado");
		} catch(Exception e) {
			throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "findFiltrado");
		}
	}


	public List<DictamenObject> filtrarDictamenes(String searchText) throws I18NException {
		try {
			List<DictamenObject> listaDictamenObject = new ArrayList<>();
			List<Dictamen> res = dictamenEJB.findFiltrado(searchText);
			for(Dictamen ts : res)
			{				
				listaDictamenObject.add(new DictamenObject(ts));
			}
			return listaDictamenObject;
		} catch(NullPointerException e) {
			throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "findFiltrado");
		} catch(Exception e) {
			throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "findFiltrado");
		}
	}



	public List<DictamenObject> getDictamenBySerieId(Long serieId) throws I18NException {		
		try {
			List<DictamenObject> listaDictamenObject = new ArrayList<>();
			List<Dictamen> res = dictamenEJB.getBySerieId(serieId);
			for(Dictamen ts : res)
			{				
				listaDictamenObject.add(new DictamenObject(ts));
			}
			return listaDictamenObject;
		} catch(NullPointerException e) {
			throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "getDictamenBySerieId");
		} catch(Exception e) {
			throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "getDictamenBySerieId");
		}
	}


	public FuncioObject extraerFuncionRaizDeSerie(SerieDocumentalObject serie) {
		
		return extraerFuncionRaiz(serie.getFuncio());
	}
	
	public FuncioObject extraerFuncionRaiz(FuncioObject funcio) {
		if (funcio.getFuncioPare() != null) {
			extraerFuncionRaiz(funcio.getFuncioPare());
		}
		return funcio;
	}

}
