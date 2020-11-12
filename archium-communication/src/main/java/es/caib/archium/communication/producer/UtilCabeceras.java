package es.caib.archium.communication.producer;


import es.caib.archium.apirest.facade.pojos.cabecera.CabeceraLogin;
import es.caib.archium.apirest.facade.pojos.cabecera.CabeceraPeticion;

public abstract class UtilCabeceras {

    private static String NOMBRE_USUARIO = "u104848";
    private static String NOMBRE_SOLICITANTE = "Víctor Herrera";
    private static String DOCUMENTO_SOLICITANTE = "123456789Z";

    private static String NOMBRE_PROCEDIMIENTO = "Subvenciones empleo";
    private static String ORGANIZACION = "CAIB";


    private static String APLICACION_CLIENTE = "TEST";

    private static String VERSION_SERVICIO = "1.0";

    public static CabeceraPeticion generarCabeceraMock() {
        CabeceraPeticion cabecera = new CabeceraPeticion();
        // intern api
        cabecera.setServiceVersion(VERSION_SERVICIO);
        // aplicacio
        cabecera.setCodiAplicacion(APLICACION_CLIENTE);
        cabecera.setUsuarioSeguridad("app1");
        cabecera.setPasswordSeguridad("app1");
        cabecera.setOrganizacion(ORGANIZACION);
        // info login
        cabecera.setNombreSolicitante(NOMBRE_SOLICITANTE);
        cabecera.setDocumentoSolicitante(DOCUMENTO_SOLICITANTE);
        cabecera.setNombreUsuario(NOMBRE_USUARIO);
        // info peticio
        cabecera.setNombreProcedimiento(NOMBRE_PROCEDIMIENTO);

        return cabecera;
    }


}
