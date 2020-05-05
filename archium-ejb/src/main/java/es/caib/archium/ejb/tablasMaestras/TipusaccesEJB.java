package es.caib.archium.ejb.tablasMaestras;

import javax.ejb.Local;

import es.caib.archium.ejb.service.tablasMaestras.TipusaccesService;
import es.caib.archium.persistence.dao.AbstractDAO;
import es.caib.archium.persistence.model.Tipusacce;

@Local
public class TipusaccesEJB extends AbstractDAO<Tipusacce, Long> implements TipusaccesService {

}