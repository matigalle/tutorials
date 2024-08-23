package tutorials.exception;

import lombok.Getter;

@Getter
public class ErrorResponse {

    private final int status;
    private final String error;

    public ErrorResponse(int status, String errorMessage) {
        this.status = status;
        this.error = errorMessage;
    }

}
