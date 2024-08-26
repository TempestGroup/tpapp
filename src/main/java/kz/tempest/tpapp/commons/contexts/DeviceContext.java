package kz.tempest.tpapp.commons.contexts;

import kz.tempest.tpapp.commons.enums.DeviceType;
import kz.tempest.tpapp.commons.filters.DeviceFilter;

public class DeviceContext {

    private static final ThreadLocal<DeviceType> currentDevice = new ThreadLocal<>();

    public static void setDeviceType(DeviceType type) {
        currentDevice.set(type);
    }

    public static DeviceType getDeviceType() {
        return currentDevice.get() == null ? DeviceFilter.DEFAULT_TYPE : currentDevice.get();
    }

    public static boolean isMobileDevice() {
        return getDeviceType() == DeviceType.MOBILE;
    }

    public static void clear() {
        currentDevice.remove();
    }

}
