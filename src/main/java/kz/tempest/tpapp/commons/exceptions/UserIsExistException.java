package kz.tempest.tpapp.commons.exceptions;

public class UserIsExistException extends RuntimeException {
    public UserIsExistException() {
        super("User is exist!");
    }
}
