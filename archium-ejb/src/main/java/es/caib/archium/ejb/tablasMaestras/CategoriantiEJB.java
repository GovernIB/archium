package es.caib.archium.ejb.tablasMaestras;

import javax.ejb.Local;

import es.caib.archium.ejb.service.tablasMaestras.CategoriantService;
import es.caib.archium.persistence.dao.AbstractDAO;
import es.caib.archium.persistence.model.Categorianti;

@Local
public class CategoriantiEJB extends AbstractDAO<Categorianti, Long> implements CategoriantService{

}
