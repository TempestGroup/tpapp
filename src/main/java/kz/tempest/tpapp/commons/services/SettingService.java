package kz.tempest.tpapp.commons.services;

import kz.tempest.tpapp.commons.constants.SettingKey;
import kz.tempest.tpapp.commons.dtos.SettingResponse;
import kz.tempest.tpapp.commons.enums.SettingType;
import kz.tempest.tpapp.commons.models.Setting;
import kz.tempest.tpapp.commons.utils.DateUtil;
import kz.tempest.tpapp.commons.utils.StringUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service @RequiredArgsConstructor
public class SettingService {
    private static JdbcTemplate jdbcTemplate;

    @Autowired
    public SettingService(JdbcTemplate jdbcTemplate) {
        SettingService.jdbcTemplate = jdbcTemplate;
    }

    public static Map<String, Setting> settings = new HashMap<>();

    private static void initMap() {
        settings.clear();
        settings.put(SettingKey.LIMIT_SPACE_DISK,
            new Setting(SettingKey.LIMIT_SPACE_DISK,
            "Дискілік кеңістікті шектеуі",
            "Ограничить дисковое пространство",
            "Limit disk space",
            4
            )
        );
    }

    public static void initDB() {
        initMap();
        for (Map.Entry<String, Setting> entry : settings.entrySet()) {
            setSettingValue(entry.getValue());
        }
    }

    public static Object getSettingValue(String key) {
        return getValue(Objects.requireNonNull(jdbcTemplate.query("SELECT * FROM settings WHERE code = ?", SettingService::mapRowToSetting, key)));
    }

    public static List<Setting> getSettings(List<String> keys) {
        return jdbcTemplate.query("SELECT * FROM settings WHERE key IN (" + StringUtil.join(keys.stream().map(key -> "'" + key + "'").toList(), ", ") + ")",
                SettingService::mapRowToSettingList);
    }

    public static List<Setting> getSettings() {
        return getSettings(settings.keySet().stream().toList());
    }

    public static void setSettingValue(String key, String nameKK, String nameRU, String nameEN, Object value) {
        setSettingValue(new Setting(key, nameKK, nameRU, nameEN, value));
    }

    public static void setSettingValue(Setting setting) {
        jdbcTemplate.execute("INSERT INTO settings(code, value, type, name_kk, name_ru, name_en) VALUES ('" + setting.getCode() + "', '" +
                setting.getValue() + "', '" + setting.getType().name() + "', '" + setting.getNameKK() + "', '" + setting.getNameRU() + "', '" +
                setting.getNameEN() + "') ON DUPLICATE KEY UPDATE value = VALUES(value), type = VALUES(type), name_kk = VALUES(name_kk), " +
                " name_ru = VALUES(name_ru), name_en = VALUES(name_en)");
        setInMap(setting);
    }

    public static void setSettings(List<SettingResponse> settings) {
        for (SettingResponse setting : settings) {
            setSettingValue(setting);
        }
    }

    private static void setSettingValue(SettingResponse settingResponse) {
        jdbcTemplate.execute("UPDATE settings SET value = '" + getValueString(settingResponse.getValue()) +
                "', type = '" + getType(settingResponse.getValue()) + "' WHERE code = '" + settingResponse.getKey() + "'");
        setInMap(settingResponse);
    }

    private static void setInMap(Setting setting) {
        settings.put(setting.getCode(), setting);
    }

    private static void setInMap(SettingResponse settingResponse) {
        Setting setting = settings.get(settingResponse.getKey());
        setting.setValue(getValueString(settingResponse.getValue()));
        setting.setType(getType(settingResponse.getValue()));
        settings.put(settingResponse.getKey(), setting);
    }

    private static Setting mapRowToSetting(ResultSet rs) throws SQLException {
        return rs.next() ? new Setting(rs.getString("code"), rs.getString("name_kk"),
                rs.getString("name_ru"), rs.getString("name_en"),
                rs.getString("value"), SettingType.valueOf(rs.getString("type"))) : null;
    }

    private static List<Setting> mapRowToSettingList(ResultSet rs) throws SQLException {
        List<Setting> settings = new ArrayList<>();
        while (rs.next()) {
            settings.add(new Setting(rs.getString("code"), rs.getString("name_kk"),
                    rs.getString("name_ru"), rs.getString("name_en"),
                    rs.getString("value"), SettingType.valueOf(rs.getString("type"))));
        }
        return settings;
    }

    public static Object getValue(Setting setting) {
        return getValue(setting.getValue(), setting.getType());
    }

    private static Object getValue(String value, SettingType type) {
        if (type.equals(SettingType.INTEGER)) {
            return Integer.parseInt(value);
        } else if (type.equals(SettingType.DOUBLE)) {
            return Double.valueOf(value);
        } else if (type.equals(SettingType.DATE)) {
            return LocalDate.parse(value, DateUtil.PATTERN_DASH_DD_MM_YYYY);
        } else if (type.equals(SettingType.LIST_INTEGER)) {
            value = value.replaceAll("[\\[\\]]", "");
            return Arrays.stream(value.split(",\\s*")).map(Integer::valueOf).toList();
        } else if (type.equals(SettingType.LIST_DOUBLE)) {
            value = value.replaceAll("[\\[\\]]", "");
            return Arrays.stream(value.split(",\\s*")).map(Double::valueOf).toList();
        } else if (type.equals(SettingType.LIST_STRING)) {
            value = value.replaceAll("[\\[\\]]", "");
            return Arrays.stream(value.split(",\\s*")).toList();
        }
        return value;
    }

    private static String getValueString(Object value) {
        if (value instanceof LocalDate) {
            return ((LocalDate) value).format(DateUtil.PATTERN_DASH_DD_MM_YYYY);
        }
        return String.valueOf(value);
    }

    private static SettingType getType(Object value) {
        if (value instanceof Integer) {
            return SettingType.INTEGER;
        } else if (value instanceof Double) {
            return SettingType.DOUBLE;
        } else if (value instanceof LocalDate) {
            return SettingType.DATE;
        } else if (value instanceof List<?> list) {
            for (Object element : list) {
                if (element instanceof Integer) {
                    return SettingType.LIST_INTEGER;
                } else if (element instanceof Double) {
                    return SettingType.LIST_DOUBLE;
                } else if (element instanceof String) {
                    return SettingType.LIST_STRING;
                }
            }
        }
        return SettingType.STRING;
    }
}
