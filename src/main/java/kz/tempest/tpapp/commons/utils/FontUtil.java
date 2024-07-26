package kz.tempest.tpapp.commons.utils;

import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.pdf.BaseFont;
import fr.opensagres.xdocreport.itext.extension.font.IFontProvider;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class FontUtil {

    public static IFontProvider getTimesNewRomanFontProvider() {
        return new IFontProvider() {
            @Override
            public Font getFont(String fontFamily, String encoding, float size, int style, Color color) {
                String fontPath = FileUtil.FONTS_DIRECTORY + "TimesNewRoman/font.ttf";
                return FontFactory.getFont(fontPath, BaseFont.IDENTITY_H, false, size, style, color);
            }
        };
    }

    public static IFontProvider getMonoscapeFontProvider() {
        return new IFontProvider() {
            @Override
            public Font getFont(String fontFamily, String encoding, float size, int style, Color color) {
                String fontPath = FileUtil.FONTS_DIRECTORY + "Monoscape/font.ttf";
                return FontFactory.getFont(fontPath, BaseFont.IDENTITY_H, true, size, style, color);
            }
        };
    }

    public static Map<String, Font> getMonoscapeFonts() {
        return new HashMap<>() {{
            put("font", getMonoscapeFontProvider().getFont("Monoscape", BaseFont.IDENTITY_H, 8, 0, Color.BLACK));
            put("fontBold", getMonoscapeFontProvider().getFont("Monoscape", BaseFont.IDENTITY_H, 9, 1, Color.BLACK));
            put("fontItalic", getMonoscapeFontProvider().getFont("Monoscape", BaseFont.IDENTITY_H, 9, 2, Color.BLACK));
            put("fontBoldItalic", getMonoscapeFontProvider().getFont("Monoscape", BaseFont.IDENTITY_H, 9, 3, Color.BLACK));
        }};
    }

    public static Map<String, Font> getTimesNewRomanFonts() {
        return new HashMap<>() {{
            put("font", getTimesNewRomanFontProvider().getFont("TimesNewRoman", BaseFont.IDENTITY_H, 8, 0, Color.BLACK));
            put("fontBold", getTimesNewRomanFontProvider().getFont("TimesNewRoman", BaseFont.IDENTITY_H, 9, 1, Color.BLACK));
            put("fontItalic", getTimesNewRomanFontProvider().getFont("TimesNewRoman", BaseFont.IDENTITY_H, 9, 2, Color.BLACK));
            put("fontBoldItalic", getTimesNewRomanFontProvider().getFont("TimesNewRoman", BaseFont.IDENTITY_H, 9, 3, Color.BLACK));
        }};
    }

    public static Font getMonoscapeFont(String type) {
        return getMonoscapeFonts().getOrDefault(type, getMonoscapeFontProvider().getFont("Monoscape", BaseFont.IDENTITY_H, 8, 0, Color.BLACK));
    }

    public static Font getTimesNewRomanFont(String type) {
        return getTimesNewRomanFonts().getOrDefault(type, getTimesNewRomanFontProvider().getFont("TimesNewRoman", BaseFont.IDENTITY_H, 8, 0, Color.BLACK));
    }

}
