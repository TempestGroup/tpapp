package kz.tempest.tpapp.commons.utils;

import kz.tempest.tpapp.commons.enums.Extension;
import kz.tempest.tpapp.commons.enums.Module;
import kz.tempest.tpapp.commons.enums.Right;
import kz.tempest.tpapp.modules.person.models.Person;
import java.util.Map;

public class AccessRightUtil {

    public static boolean checkAccess(Person person, Module... modules) {
        for(Module module : modules) {
            for (Map.Entry<Extension, Right> entry : person.getPersonModuleExtensionRights().entrySet()) {
                if (entry.getKey().getModule().equals(module)) {
                    return entry.getValue() == Right.WRITE || entry.getValue() == Right.READ;
                }
            }
        }
        return true;
    }

    public static boolean checkAccess(Person person, Extension... extensions) {
        for(Extension extension : extensions) {
            for (Map.Entry<Extension, Right> entry : person.getPersonModuleExtensionRights().entrySet()) {
                if (entry.getKey().equals(extension)) {
                    return entry.getValue() == Right.WRITE || entry.getValue() == Right.READ;
                }
            }
        }
        return true;
    }

}
