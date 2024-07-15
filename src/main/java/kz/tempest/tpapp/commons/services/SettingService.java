package kz.tempest.tpapp.commons.services;

import kz.tempest.tpapp.commons.dtos.SettingResponse;
import kz.tempest.tpapp.commons.enums.SettingType;
import kz.tempest.tpapp.commons.models.Setting;
import kz.tempest.tpapp.commons.utils.StringUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@RequiredArgsConstructor
public class SettingService {
    private static @Lazy JdbcTemplate jdbcTemplate;
    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    public static final String LIMIT_SPACE_DISK = "limit_space_disk";

    public static Map<String, Setting> settings = new HashMap<>();

    private static void initMap() {
        settings.clear();
        settings.put(LIMIT_SPACE_DISK, new Setting(
                LIMIT_SPACE_DISK,
                "Дискілік кеңістікті шектеуі",
                "Ограничить дисковое пространство",
                "Limit disk space",
                4));
    }

    public static void initDB() {
        initMap();
        for (Map.Entry<String, Setting> entry : settings.entrySet()) {
            setSettingValue(entry.getValue());
        }
    }

    public static Object getSettingValue(String key) {
        String sql = "SELECT * FROM settings WHERE key = ?";
        Setting setting = jdbcTemplate.query(sql, SettingService::mapRowToSetting, key);
        return setting == null ? null : getValue(Objects.requireNonNull(setting));
    }

    public static List<Setting> getSettings(List<String> keys) {
        String sql = "SELECT * FROM settings WHERE key IN (" + StringUtil.join(keys.stream().map(key -> "'" + key + "'").toList(), ", ") + ")";
        return jdbcTemplate.query(sql, SettingService::mapRowToSettingList);
    }

    public static List<Setting> getSettings() {
        return getSettings(settings.keySet().stream().toList());
    }

    public static boolean setSettingValue(String key, String nameKK, String nameRU, String nameEN, Object value) {
        return setSettingValue(new Setting(key, nameKK, nameRU, nameEN, value));
    }

    public static boolean setSettingValue(Setting setting) {
        String sql = "INSERT INTO settings(key, value, type, name_kk, name_ru, name_en) VALUES ('" + setting.getKey() + "', '" +
                setting.getValue() + "', '" + setting.getType() + "', '" + setting.getNameKK() + "', '" + setting.getNameRU() + "', '" +
                setting.getNameEN() + "') ON DUPLICATE KEY UPDATE value = VALUES(value), type = VALUES(type), name_kk = VALUES(name_kk), " +
                " name_ru = VALUES(name_ru), name_en = VALUES(name_en)";
        jdbcTemplate.execute(sql);
        setInMap(setting);
        return true;
    }

    public static boolean setSettings(List<SettingResponse> settings) {
        boolean res = false;
        for (SettingResponse setting : settings) {
            res = setSettingValue(setting.getKey(), setting.getNameKK(), setting.getNameRU(),
                    setting.getNameEN(), setting.getValue());
        }
        return res;
    }

    private static void setInMap(Setting setting) {
        settings.put(setting.getKey(), setting);
    }

    private static Setting mapRowToSetting(ResultSet rs) throws SQLException {
        if (rs.next()) {
            return new Setting(rs.getString("key"),
                    rs.getString("name_kk"),
                    rs.getString("name_ru"),
                    rs.getString("name_en"),
                    rs.getString("value"), SettingType.valueOf(rs.getString("type")));
        }
        return null;
    }

    private static List<Setting> mapRowToSettingList(ResultSet rs) throws SQLException {
        List<Setting> settings = new ArrayList<>();
        while (rs.next()) {
            settings.add(new Setting(rs.getString("key"),
                    rs.getString("name_kk"),
                    rs.getString("name_ru"),
                    rs.getString("name_en"),
                    rs.getString("value"), SettingType.valueOf(rs.getString("type"))));
        }
        return settings;
    }

    public static Object getValue(Setting setting) {
        return getValue(setting.getValue(), setting.getType());
    }

    public static Object getValue(String value, SettingType type) {
        if (type.equals(SettingType.INTEGER)) {
            return Integer.parseInt(value);
        } else if (type.equals(SettingType.DOUBLE)) {
            return Double.valueOf(value);
        } else if (type.equals(SettingType.DATE)) {
            return LocalDate.parse(value, formatter);
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
}
