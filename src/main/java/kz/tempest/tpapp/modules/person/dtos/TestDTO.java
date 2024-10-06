package kz.tempest.tpapp.modules.person.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import kz.tempest.tpapp.modules.person.deserializers.PersonDeserializer;
import kz.tempest.tpapp.modules.person.models.Person;
import lombok.Data;

@Data
public class TestDTO {
    private Long id;
    @JsonProperty("personId")
    @JsonDeserialize(using = PersonDeserializer.class)
    private Person person;
}
