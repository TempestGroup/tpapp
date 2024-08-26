package kz.tempest.tpapp.commons.dtos;

import kz.tempest.tpapp.commons.contexts.DeviceContext;
import kz.tempest.tpapp.commons.enums.Language;
import kz.tempest.tpapp.commons.enums.Module;
import kz.tempest.tpapp.commons.models.MenuItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuItemResponse {
    private Long id;
    private String name;
    private String link;
    private Module module;
    private String icon;

    public static MenuItemResponse from(MenuItem item, Language language) {
        return from(item, DeviceContext.isMobileDevice(), language);
    }

    public static MenuItemResponse from(MenuItem item, boolean isMobile, Language language) {
        return new MenuItemResponse(item.getId(), item.getName(language), item.getLink(), item.getModule(), item.getIcon(isMobile));
    }

    public static List<MenuItemResponse> from(List<MenuItem> items, Language language) {
        return from(items, DeviceContext.isMobileDevice(), language);
    }

    public static List<MenuItemResponse> from(List<MenuItem> items, boolean isMobile, Language language) {
        return items.stream().map(item -> from(item, isMobile, language)).toList();
    }
}
