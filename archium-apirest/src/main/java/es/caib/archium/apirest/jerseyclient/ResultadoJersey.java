package es.caib.archium.apirest.jerseyclient;

public class ResultadoJersey {
	private Object contenido;
	private int estadoRespuestaHttp;
	
	public Object getContenido() {
		return contenido;
	}
	public void setContenido(Object contenido) {
		this.contenido = contenido;
	}
	public int getEstadoRespuestaHttp() {
		return estadoRespuestaHttp;
	}
	public void setEstadoRespuestaHttp(int estadoRespuestaHttp) {
		this.estadoRespuestaHttp = estadoRespuestaHttp;
	}
	
	public String toString(){
		String rtdo="ResultadoJersey:\n";
		rtdo+=" - contenido:"+contenido+"\n";
		rtdo+=" - estadoRespuestaHttp:"+estadoRespuestaHttp+"\n";

		return rtdo;
	}
	
	
}
