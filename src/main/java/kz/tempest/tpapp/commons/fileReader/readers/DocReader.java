package kz.tempest.tpapp.commons.fileReader.readers;

import kz.tempest.tpapp.commons.fileReader.Reader;
import kz.tempest.tpapp.commons.utils.LogUtil;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.tika.Tika;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DocReader extends Reader {
    @Override
    public List<String> read() {
        List<String> paragraphs = new ArrayList<>();
        Tika tika = new Tika();
        String fileType;
        try {
            fileType = tika.detect(bytes);
            try (ByteArrayInputStream bais = new ByteArrayInputStream(bytes)) {
                if ("application/msword".equals(fileType)) {
                    try (HWPFDocument doc = new HWPFDocument(bais)) {
                        WordExtractor extractor = new WordExtractor(doc);
                        for (String paragraph : extractor.getParagraphText()) {
                            paragraphs.add(paragraph.trim());
                        }
                    }
                } else if ("application/vnd.openxmlformats-officedocument.wordprocessingml.document".equals(fileType)) {
                    try (XWPFDocument docx = new XWPFDocument(bais)) {
                        for (XWPFParagraph paragraph : docx.getParagraphs()) {
                            paragraphs.add(paragraph.getText().trim());
                        }
                    }
                }
            }
        } catch (IOException e) {
            LogUtil.write(e);
        }
        return paragraphs;
    }
}
