package es.caib.archium.ejb;

import es.caib.archium.ejb.service.MateriaService;
import es.caib.archium.persistence.dao.AbstractDAO;
import es.caib.archium.persistence.model.Materia;

import javax.ejb.Local;
import javax.ejb.Stateless;

@Local
public class MateriaEJB extends AbstractDAO<Materia, Long> implements MateriaService  {

		
}
