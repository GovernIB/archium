package es.caib.archium.communication.exception;

public enum ExceptionConstants {
    GENERIC_ERROR("CSGD500"),
    CLIENT_ERROR("CSGD501"),
    MALFORMED_RESULT("CSGD502"),
    ERROR_RETURNED("CSGD503");

    private String value;

    ExceptionConstants(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
