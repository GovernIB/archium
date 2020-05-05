package es.caib.archium.ejb.tablasMaestras;

import javax.ejb.Local;

import es.caib.archium.ejb.service.tablasMaestras.CausalimitacioService;
import es.caib.archium.persistence.dao.AbstractDAO;
import es.caib.archium.persistence.model.Causalimitacio;

@Local
public class CausalimitacioEJB extends AbstractDAO<Causalimitacio, Long> implements CausalimitacioService{

}