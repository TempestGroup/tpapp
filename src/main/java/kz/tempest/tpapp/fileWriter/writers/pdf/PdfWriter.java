package kz.tempest.tpapp.fileWriter.writers.pdf;

import fr.opensagres.poi.xwpf.converter.pdf.PdfConverter;
import fr.opensagres.poi.xwpf.converter.pdf.PdfOptions;
import kz.tempest.tpapp.commons.enums.Language;
import kz.tempest.tpapp.commons.models.TemplateInfo;
import kz.tempest.tpapp.commons.utils.FontUtil;
import kz.tempest.tpapp.commons.utils.LogUtil;
import kz.tempest.tpapp.fileWriter.Writer;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class PdfWriter extends Writer {

    @Override
    public byte[] write(Object obj) {
        return write(obj, null, null);
    }

    @Override
    public byte[] write(Object obj, TemplateInfo templateInfo, Language language) {
        return serialize(obj);
    }

    private byte[] serialize(Object obj) {
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream((byte[]) obj);
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            XWPFDocument document = new XWPFDocument(inputStream);
            PdfOptions options = PdfOptions.create();
            options.fontProvider(FontUtil.getTimesNewRomanFontProvider());
            PdfConverter.getInstance().convert(document, outputStream, options);
            return outputStream.toByteArray();
        } catch (IOException e) {
            LogUtil.write(e);
            return null;
        }
    }
}
