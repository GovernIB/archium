package es.caib.archium.apirest.csgd.entidades.comunes;

public class RespuestaGenerica<T>{
	
	private ResultData result;
	private T resParam;
	
	
	public ResultData getResult() {
		return result;
	}
	public void setResult(ResultData result) {
		this.result = result;
	}
	public T getResParam() {
		return resParam;
	}
	public void setResParam(T resParam) {
		this.resParam = resParam;
	}

	
}
