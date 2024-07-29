package kz.tempest.tpapp.commons.enums;

import kz.tempest.tpapp.commons.utils.ClassUtil;

import java.util.*;
import java.util.stream.Collectors;

public enum Extension {
    UPDATE_PERSON_SELF_PROFILE_DATA("", "", "", Module.PERSON),
    UPDATE_PERSONS_PROFILE_DATA("", "", "", Module.PERSON),
    PERSON_SEARCH("", "", "", Module.PERSON),
    DELETE_PERSON("", "", "", Module.PERSON),
    CREATE_COUNTRY_DATA("", "", "", Module.DATA),
    DELETE_COUNTRY_DATA("", "", "", Module.DATA),
    UPDATE_COUNTRY_DATA("", "", "", Module.DATA),
    CREATE_NATIONALITY_DATA("", "", "", Module.DATA),
    DELETE_NATIONALITY_DATA("", "", "", Module.DATA),
    UPDATE_NATIONALITY_DATA("", "", "", Module.DATA),
    CREATE_CITY_DATA("", "", "", Module.DATA),
    DELETE_CITY_DATA("", "", "", Module.DATA),
    UPDATE_CITY_DATA("", "", "", Module.DATA),
    CREATE_UNIVERSITY_DATA("", "", "", Module.DATA),
    DELETE_UNIVERSITY_DATA("", "", "", Module.DATA),
    UPDATE_UNIVERSITY_DATA("", "", "", Module.DATA),
    ;

    private final String nameKK;
    private final String nameRU;
    private final String nameEN;
    private final Module module;

    private static final Map<Module, List<Extension>> extensionsByModule = Arrays.stream(Module.values())
            .collect(Collectors.toMap(m -> m, m -> new ArrayList<>()));

    static {
        for (Extension extension : values()) {
            if (extension.module != null) {
                extensionsByModule.get(extension.module).add(extension);
            }
        }
    }

    Extension(String nameKK, String nameRU, String nameEN, Module module) {
        this.nameKK = nameKK;
        this.nameRU = nameRU;
        this.nameEN = nameEN;
        this.module = module;
    }

    public static List<Extension> getByModule(Module module) {
        return extensionsByModule.get(module);
    }

    public String getName(Language language) {
        return (String) ClassUtil.getLocalizedFieldValue(getClass(), this, "name", language);
    }

    public String getNameKK() {
        return nameKK;
    }

    public String getNameRU() {
        return nameRU;
    }

    public String getNameEN() {
        return nameEN;
    }

    public Module getModule() {
        return module;
    }
}
