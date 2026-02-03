package es.caib.archium.ejb.service;

import es.caib.archium.commons.i18n.I18NException;
import es.caib.archium.ejb.objects.Dir3;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import java.util.List;

//@Stateless
//@RolesAllowed({"ACH_GESTOR"})
public class Dir3RestEJB /*implements Dir3Service*/ {

    private static final String SERVEI_ORGANIGRAMA = "rest/organigrama/";
    private static final String SERVEI_OBTENIR_UNITATS = "ws/Dir3CaibObtenerUnidades";

    //@Override
    public List<Dir3> getAll() throws I18NException {
        return null;
    }

    //@Override
    public Dir3 findByCodi(String codi) throws I18NException {
        return null;
    }

    // TODO Ver package es.caib.ripea.plugin.caib.unitat.UnitatsOrganitzativesPluginDir3

}
