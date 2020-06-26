package es.caib.archium.ejb.service;

import java.util.List;

import javax.ejb.Local;

import es.caib.archium.commons.i18n.I18NException;
import es.caib.archium.ejb.objects.Dir3;

@Local
public interface Dir3Service {

	public List<Dir3> getAll() throws I18NException;
	
}
