package kz.tempest.tpapp.commons.dtos;

import kz.tempest.tpapp.commons.enums.Language;
import kz.tempest.tpapp.commons.models.Setting;
import kz.tempest.tpapp.commons.services.SettingService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SettingResponse {
    private String key;
    private String name;
    private Object value;

    public static SettingResponse from(Setting setting, Language language) {
        return new SettingResponse(setting.getCode(), setting.getName(language), SettingService.getValue(setting));
    }
}
