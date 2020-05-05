package es.caib.archium.ejb.tablasMaestras;

import javax.ejb.Local;

import es.caib.archium.ejb.service.tablasMaestras.TipuspublicService;
import es.caib.archium.persistence.dao.AbstractDAO;
import es.caib.archium.persistence.model.Tipuspublic;

@Local
public class TipuspublicEJB extends AbstractDAO<Tipuspublic, Long> implements TipuspublicService{

}