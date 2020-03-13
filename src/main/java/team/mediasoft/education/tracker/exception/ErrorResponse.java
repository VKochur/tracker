package team.mediasoft.education.tracker.exception;

public class ErrorResponse {

    private final int code;

    private final String reasonPhrase;

    public ErrorResponse(int code, String reasonPhrase) {
        this.code = code;
        this.reasonPhrase = reasonPhrase;
    }

    public ErrorResponse(int code, String reasonPhrase, String message) {
        this.code = code;
        this.reasonPhrase = reasonPhrase;
    }

    public int getCode() {
        return code;
    }

    public String getReasonPhrase() {
        return reasonPhrase;
    }
}
