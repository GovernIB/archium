package es.caib.archium.commons.utils;


/**
 * Constants emprades dins tota l'aplicació.
 *
 * @author anadal
 * @author areus
 */
public interface Constants {

    /**
     * Constant pel rol d'Gestor
     */
    String ACH_GESTOR = "ACH_GESTOR";

    /**
     * Constant pel rol d'Usuari
     */
    String ACH_USER = "ACH_USER";

    /**
     * Constant pel rol d'Alfresco
     */
    String ACH_ALFRESCO = "ACH_ALFRESCO";


    static enum ExceptionConstants{

        GENERIC_ERROR("CSGD500"),
        CLIENT_ERROR("CSGD501"),
        MALFORMED_RESULT("CSGD502"),
        ERROR_RETURNED("CSGD503");

        private String value;

        ExceptionConstants(String value){
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}
