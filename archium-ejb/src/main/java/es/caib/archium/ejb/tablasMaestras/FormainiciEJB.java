package es.caib.archium.ejb.tablasMaestras;

import javax.ejb.Local;

import es.caib.archium.ejb.service.tablasMaestras.FormainiciService;
import es.caib.archium.persistence.dao.AbstractDAO;
import es.caib.archium.persistence.model.Formainici;

@Local
public class FormainiciEJB extends AbstractDAO<Formainici, Long> implements FormainiciService  {

}