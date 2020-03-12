package team.mediasoft.education.tracker.exception;

public class ErrorResponse {

    private final int code;

    private final String reasonPhrase;

    private String message;

    public ErrorResponse(int code, String reasonPhrase) {
        this.code = code;
        this.reasonPhrase = reasonPhrase;
    }

    public ErrorResponse(int code, String reasonPhrase, String message) {
        this.code = code;
        this.reasonPhrase = reasonPhrase;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getReasonPhrase() {
        return reasonPhrase;
    }

    public String getMessage() {
        return message;
    }
}
