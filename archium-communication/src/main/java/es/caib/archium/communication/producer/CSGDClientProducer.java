package es.caib.archium.communication.producer;

import es.caib.archium.apirest.ApiArchium;
import es.caib.archium.apirest.facade.pojos.cabecera.CabeceraPeticion;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;

public class CSGDClientProducer {

    @Produces
    public ApiArchium csgdClientProducer(){
        CabeceraPeticion cabecera = UtilCabeceras.generarCabeceraMock();

        return new ApiArchium("http://sdesfiresb2.caib.es:8280",cabecera);
    }
}
