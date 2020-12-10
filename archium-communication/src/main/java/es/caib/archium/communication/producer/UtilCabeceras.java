package es.caib.archium.communication.producer;


import es.caib.archium.csgd.apirest.facade.pojos.cabecera.CabeceraPeticion;

public abstract class UtilCabeceras {

    public static CabeceraPeticion generarCabecera(String usuario, String solicitanteNombre, String solicitanteDocumento,
                                                   String procedimiento, String organizacion, String aplicacionCliente,
                                                   String servicioVersion, String user, String pass) {
        CabeceraPeticion cabecera = new CabeceraPeticion();
        // intern api
        cabecera.setServiceVersion(servicioVersion);
        // aplicacio
        cabecera.setCodiAplicacion(aplicacionCliente);
        cabecera.setUsuarioSeguridad(user);
        cabecera.setPasswordSeguridad(pass);
        cabecera.setOrganizacion(organizacion);
        // info login
        cabecera.setNombreSolicitante(solicitanteNombre);
        cabecera.setDocumentoSolicitante(solicitanteDocumento);
        cabecera.setNombreUsuario(usuario);
        // info peticio
        cabecera.setNombreProcedimiento(procedimiento);

        return cabecera;
    }
}
