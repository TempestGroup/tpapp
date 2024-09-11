package kz.tempest.tpapp.commons.enums;

import kz.tempest.tpapp.commons.dtos.MenuItemResponse;
import kz.tempest.tpapp.commons.models.MenuItem;
import kz.tempest.tpapp.commons.utils.ClassUtil;
import kz.tempest.tpapp.modules.person.models.Person;

import java.util.*;

public enum Module {
    PERSON("person", "Пайдаланушылар", "Пользователи", "Person"),
    DATA("data", "Деректер", "Данные", "Data"),
    PROJECTS("projects", "Жобалар", "Проекты", "Projects"),
    JOBS("jobs", "Жүйелік тапсырмалар", "Системные задания", "System jobs");

    private final String code;
    private final String nameKK;
    private final String nameRU;
    private final String nameEN;
    private final static Map<String, Module> moduleByCode = new HashMap<>();

    static {
        for (Module module: values()) {
            moduleByCode.put(module.getCode(), module);
        }
    }

    Module(
            String code,
            String nameKK,
            String nameRU,
            String nameEN
    ) {
        this.code = code;
        this.nameKK = nameKK;
        this.nameRU = nameRU;
        this.nameEN = nameEN;
    }

    public String getName(Language language) {
        return (String) ClassUtil.getLocalizedFieldValue(getClass(), this, "name", language);
    }

    public static Module getByCode(String code) {
        return moduleByCode.getOrDefault(code, null);
    }

    public static boolean contains(String code) {
        return moduleByCode.containsKey(code);
    }

    public String getCode() {
        return code;
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

    public static List<Module> getModules(Person person) {
        Set<Module> modules = new HashSet<>();
        for(Map.Entry<Extension, Right> entry : person.getPersonModuleExtensionRights().entrySet()) {
            modules.add(entry.getKey().getModule());
        }
        return modules.stream().toList();
    }

    public static Comparator<MenuItemResponse> getComparatorByMenuItemResponse() {
        return Comparator.comparing(MenuItemResponse::getModule);
    }

    public static Comparator<MenuItem> getComparatorByMenuItem() {
        return Comparator.comparing(MenuItem::getModule);
    }
}
