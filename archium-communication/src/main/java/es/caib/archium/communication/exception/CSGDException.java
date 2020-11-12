package es.caib.archium.communication.exception;

public class CSGDException extends Exception{


    private String code;
    private String clientErrorCode;
    private String clientMessage;

    public CSGDException(String code, String message, Throwable e){
        super(message,e);
        this.code = code;
    }

    public CSGDException(String code, String message){
        super(message);
        this.code = code;
    }

    public CSGDException(String code, String clientErrorCode, String clientMessage, String message){
        super(message);
        this.code = code;
        this.clientErrorCode = clientErrorCode;
        this.clientMessage = clientMessage;
    }

    public String getCode() {
        return code;
    }

    public String getClientErrorCode() {
        return clientErrorCode;
    }

    public String getClientMessage() {
        return clientMessage;
    }
}
