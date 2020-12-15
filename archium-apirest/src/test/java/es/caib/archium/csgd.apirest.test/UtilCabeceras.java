package es.caib.archium.csgd.apirest.test;


import es.caib.archium.csgd.apirest.facade.pojos.cabecera.CabeceraPeticion;

public abstract class UtilCabeceras {

    private static String NOMBRE_USUARIO = "u104848";
    private static String NOMBRE_SOLICITANTE =  "archiumPruebaTest";
    private static String DOCUMENTO_SOLICITANTE = "123456789Z";

    private static String NOMBRE_PROCEDIMIENTO = "Gestión del Archivo (ARCHIUM)";
    private static String ORGANIZACION = "CAIB";


    private static String APLICACION_CLIENTE = "ARCHIUM";

    private static String VERSION_SERVICIO = "1.0";

    public static CabeceraPeticion generarCabeceraMock(){

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
