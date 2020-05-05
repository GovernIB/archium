package es.caib.archium.ejb.tablasMaestras;

import javax.ejb.Local;

import es.caib.archium.ejb.service.tablasMaestras.TipusvalorService;
import es.caib.archium.persistence.dao.AbstractDAO;
import es.caib.archium.persistence.model.Tipusvalor;

@Local
public class TipusvalorEJB extends AbstractDAO<Tipusvalor, Long> implements TipusvalorService{

}