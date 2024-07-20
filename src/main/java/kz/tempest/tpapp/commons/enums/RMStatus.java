package kz.tempest.tpapp.commons.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum RMStatus {
    SUCCESS,
    WARNING,
    ERROR;

    @JsonValue
    public Object serialize() {
        return name();
    }
}
