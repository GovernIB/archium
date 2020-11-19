package es.caib.csgd.apirest.csgd.peticiones;


import es.caib.csgd.apirest.csgd.entidades.comunes.ServiceHeader;

public class Request<T> {

	private ServiceHeader serviceHeader;
	private T param;

	public ServiceHeader getServiceHeader() {
		return serviceHeader;
	}
	public void setServiceHeader(ServiceHeader serviceHeader) {
		this.serviceHeader = serviceHeader;
	}
	public T getParam() {
		return param;
	}
	public void setParam(T param) {
		this.param = param;
	}


}
