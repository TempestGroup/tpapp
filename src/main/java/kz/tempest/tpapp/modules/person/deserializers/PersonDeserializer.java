package kz.tempest.tpapp.modules.person.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import kz.tempest.tpapp.modules.person.models.Person;
import kz.tempest.tpapp.modules.person.services.PersonService;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class PersonDeserializer extends JsonDeserializer<Person> {

    private final PersonService personService;

    public PersonDeserializer(PersonService personService) {
        this.personService = personService;
    }

    @Override
    public Person deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException {
        return personService.getById(jsonParser.getLongValue());
    }
}
