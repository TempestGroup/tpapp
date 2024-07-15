package kz.tempest.tpapp.commons.exceptions;

public class FileNotExistException extends RuntimeException {

    public FileNotExistException() {
        super("File is empty!");
    }

    public FileNotExistException(Long id) {
        super("File with id '#" + id + "' isn't exist!");
    }

}
