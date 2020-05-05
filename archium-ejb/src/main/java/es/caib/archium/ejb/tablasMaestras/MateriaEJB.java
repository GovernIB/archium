package es.caib.archium.ejb.tablasMaestras;

import javax.ejb.Local;

import es.caib.archium.ejb.service.tablasMaestras.MateriaService;
import es.caib.archium.persistence.dao.AbstractDAO;
import es.caib.archium.persistence.model.Materia;

@Local
public class MateriaEJB extends AbstractDAO<Materia, Long> implements MateriaService{

}