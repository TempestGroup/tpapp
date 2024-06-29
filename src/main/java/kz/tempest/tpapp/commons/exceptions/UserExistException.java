package kz.tempest.tpapp.commons.exceptions;

public class UserExistException extends RuntimeException {
    public UserExistException() {
        super("User is exist!");
    }
}
