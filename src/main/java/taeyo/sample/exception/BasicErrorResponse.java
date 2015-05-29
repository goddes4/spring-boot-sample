package taeyo.sample.exception;

import lombok.Data;

/**
 * Created by tykim on 2015-05-29.
 */
@Data
public class BasicErrorResponse {
    private String code;
    private String message;

    public BasicErrorResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
