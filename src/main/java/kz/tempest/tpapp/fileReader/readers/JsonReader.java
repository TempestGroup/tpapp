package kz.tempest.tpapp.fileReader.readers;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import kz.tempest.tpapp.fileReader.Reader;
import kz.tempest.tpapp.commons.utils.LogUtil;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class JsonReader extends Reader {

    @Override
    public Serializable read() {
        try {
            InputStream inputStream = new ByteArrayInputStream(bytes);
            JsonFactory factory = new JsonFactory();
            JsonParser parser = factory.createParser(inputStream);
            return processNode(parser);
        } catch (IOException e) {
            LogUtil.write(e);
            return null;
        }
    }

    private Serializable processNode(JsonParser parser) throws IOException {
        HashMap<String, Object> result = new HashMap<>();
        if (parser.nextToken() == JsonToken.START_OBJECT) {
            while (parser.nextToken() != JsonToken.END_OBJECT) {
                String fieldName = parser.getCurrentName();
                parser.nextToken();
                result.put(fieldName, processValue(parser));
            }
        }
        return result;
    }

    private Object processValue(JsonParser parser) throws IOException {
        JsonToken token = parser.getCurrentToken();
        switch (token) {
            case START_OBJECT -> {
                return processNode(parser);
            }
            case START_ARRAY -> {
                List<Object> list = new ArrayList<>();
                while (parser.nextToken() != JsonToken.END_ARRAY) {
                    list.add(processValue(parser));
                }
                return list;
            }
            case VALUE_STRING -> {
                return parser.getText();
            }
            case VALUE_NUMBER_INT -> {
                return parser.getIntValue();
            }
            case VALUE_NUMBER_FLOAT -> {
                return parser.getFloatValue();
            }
            case VALUE_TRUE -> {
                return true;
            }
            case VALUE_FALSE -> {
                return false;
            }
            default -> {
                return null;
            }
        }
    }
}
