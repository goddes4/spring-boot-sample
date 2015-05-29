package taeyo.sample.exception;

/**
 * Created by tykim on 2015-05-29.
 */
public class AccountNotFoundException extends RuntimeException {

    public AccountNotFoundException(int id) {
        super(String.format("Account (%d) Not Found", id));
    }
}
