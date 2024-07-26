package kz.tempest.tpapp.commons.exceptions;

public class TemplateNotFoundException extends RuntimeException {

    public TemplateNotFoundException() {
        super("Template not found!");
    }

}
