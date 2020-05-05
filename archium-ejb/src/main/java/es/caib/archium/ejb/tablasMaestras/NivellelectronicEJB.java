package es.caib.archium.ejb.tablasMaestras;

import javax.ejb.Local;

import es.caib.archium.ejb.service.tablasMaestras.NivellelectronicService;
import es.caib.archium.persistence.dao.AbstractDAO;
import es.caib.archium.persistence.model.Nivellelectronic;

@Local
public class NivellelectronicEJB extends AbstractDAO<Nivellelectronic, Long> implements NivellelectronicService{

}