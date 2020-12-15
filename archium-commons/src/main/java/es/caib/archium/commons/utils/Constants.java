package es.caib.archium.commons.utils;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    String ACH_CSGD = "ACH_ALFRESCO";

    /**
     * Retorna la lista de roles que pueden realizar llamadas al CSGD a traves del cliente Jersey
     *
     * @return
     */
    public static List<String> permissionsToCallCSGD(){
        List<String> permisos = new ArrayList<String>();
        permisos.add(ACH_CSGD);
        return permisos;
    }


    /**
     * Codigos de error devueltos por la capa CSGD en funcion de los diferentes errores
     *
     */
    static enum ExceptionConstants{

        GENERIC_ERROR("CSGD500"),
        CLIENT_ERROR("CSGD501"),
        MALFORMED_RESULT("CSGD502"),
        ERROR_RETURNED("CSGD503"),
        HEADER_ERROR("CSGD504");

        private String value;

        ExceptionConstants(String value){
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }


    /**
     * Constantes utilizadas en la logica de negocio
     *
     */
    static enum ArchiumConstants{
        DICTAMEN_ACTIVO("vigent"),
        DICTAMEN_RECIENTE_ESTADO("esborrany"),
        TIPO_CLASIFICACION_VALOR_POR_DEFECTO("Funcional");

        private String value;

        ArchiumConstants(String value){
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    /**
     * Codigos de error devueltos por el bus
     *
     */
    static enum CodigosRespuestaCSGD{
        RESPUESTA_OK("COD_000"),
        RESPUESTA_EXCEPCION("COD_021"),
        ERROR_VALIDACION("COD_010");

        private String value;

        CodigosRespuestaCSGD(String value){
            this.value=value;
        }

        public String getValue() {
            return value;
        }
    }
}
