package es.caib.archium.csgd.apirest.csgd.entidades.comunes;

public class RespuestaGenericaUnParametro {
	
	private ResultData result;
	
	public ResultData getResult() {
		return result;
	}
	public void setResult(ResultData result) {
		this.result = result;
	}


	@Override
	public String toString() {
		return "RespuestaGenericaUnParametro{" +
				"result=" + result +
				'}';
	}
}
