package taeyo.sample.dto;

import taeyo.sample.domain.Account;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;

/**
 * Created by tykim on 2015-05-29.
 */
public class AccountDTO {

    @Data
    public static class Request {
        @NotBlank @Size(min = 5)
        private String username;

        @NotBlank
        private String password;
    }

    @Data
    public static class Response {
        private String id;
        private String username;
        private Account.Role role;
    }

    @Data
    public static class Update {
        private String id;
        private String username;
        private String password;
    }
}
