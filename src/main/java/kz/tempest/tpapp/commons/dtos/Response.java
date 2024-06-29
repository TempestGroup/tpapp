package kz.tempest.tpapp.commons.dtos;

import java.util.IdentityHashMap;

public class Response extends IdentityHashMap<String, Object> {

    public static Response getResponse(String name, Object object) {
        return new Response() {{
            put(name, object);
        }};
    }
}
