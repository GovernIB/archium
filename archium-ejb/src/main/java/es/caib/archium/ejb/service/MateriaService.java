

package es.caib.archium.ejb.service;

import javax.ejb.Local;

import es.caib.archium.persistence.dao.DAO;
import es.caib.archium.persistence.model.Materia;

@Local
public interface MateriaService extends DAO<Materia, Long> {

}

