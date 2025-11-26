package daos.tp.centroasistencias.errores;

public class ErrorInfo {

    private String message;
    private int statusCode;
    private String uriRequested;

    public ErrorInfo(String message, int statusCode, String uriRequested) {
        this.message = message;
        this.statusCode = statusCode;
        this.uriRequested = uriRequested;
    }

    public String getMessage() { return message; }
    public int getStatusCode() { return statusCode; }
    public String getUriRequested() { return uriRequested; }
}
