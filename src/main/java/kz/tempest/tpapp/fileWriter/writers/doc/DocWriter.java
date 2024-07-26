package kz.tempest.tpapp.fileWriter.writers.doc;

import kz.tempest.tpapp.commons.enums.Language;
import kz.tempest.tpapp.commons.enums.LogType;
import kz.tempest.tpapp.commons.exceptions.TemplateNotFoundException;
import kz.tempest.tpapp.commons.models.TemplateInfo;
import kz.tempest.tpapp.commons.utils.ClassUtil;
import kz.tempest.tpapp.commons.utils.DateUtil;
import kz.tempest.tpapp.commons.utils.LogUtil;
import kz.tempest.tpapp.fileWriter.Writer;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;

import javax.xml.bind.JAXBException;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class DocWriter extends Writer {

    @Override
    public byte[] write(Object obj) {
        return write(obj, null, null);
    }

    @Override
    public byte[] write(Object obj, TemplateInfo templateInfo, Language language) {
        if (templateInfo == null) {
            throw new TemplateNotFoundException();
        }
        if (language == null) {
            language = Language.ru;
        }
        return serialize(obj, templateInfo, language);
    }

    private byte[] serialize(Object obj, TemplateInfo templateInfo, Language language)  {
        Map<String, String> replacements = (Map<String, String>) obj;
        try (InputStream inputStream = new ByteArrayInputStream(templateInfo.getFiles().get(language).getFile())) {
            WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(inputStream);
            MainDocumentPart documentPart = wordMLPackage.getMainDocumentPart();
            documentPart.variableReplace(getReplacements(replacements, language));
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            wordMLPackage.save(outputStream);
            return outputStream.toByteArray();
        } catch (Docx4JException | IOException | JAXBException e) {
            LogUtil.write(e);
            return null;
        }
    }

    private static Map<String, String> getReplacements(Map<String, String> replacements, Language language) {
        Map<String, String> variables = new HashMap<>();
        for (Map.Entry<String, String> replacement : replacements.entrySet()) {
            if (replacement.getKey().equals("date")) {
                variables.put(replacement.getKey(), DateUtil.modifyStringDateForDocument(replacement.getValue(), language));
            } else {
                variables.put(replacement.getKey(), replacement.getValue());
            }
        }
        return variables;
    }
}
