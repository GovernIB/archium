package es.caib.archium.ejb.tablasMaestras;

import javax.ejb.Local;

import es.caib.archium.ejb.service.tablasMaestras.FamiliaprocedimentService;
import es.caib.archium.persistence.dao.AbstractDAO;
import es.caib.archium.persistence.model.Familiaprocediment;
@Local
public class FamiliaprocedimentEJB extends AbstractDAO<Familiaprocediment, Long> implements FamiliaprocedimentService{

}