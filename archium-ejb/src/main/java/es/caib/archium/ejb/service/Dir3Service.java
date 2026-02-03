package es.caib.archium.ejb.service;

import es.caib.archium.commons.i18n.I18NException;
import es.caib.archium.ejb.objects.Dir3;

import javax.ejb.Local;
import java.util.List;

@Local
public interface Dir3Service {

	public List<Dir3> getAll() throws I18NException;

	public Dir3 findByCodi(String codi) throws I18NException;
	
}
