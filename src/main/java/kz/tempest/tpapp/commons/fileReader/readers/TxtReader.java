package kz.tempest.tpapp.commons.fileReader.readers;

import kz.tempest.tpapp.commons.fileReader.Reader;
import kz.tempest.tpapp.commons.utils.LogUtil;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class TxtReader extends Reader {
    @Override
    public List<List<String>> read(byte[] fileBytes) {
        List<List<String>> fileData = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(fileBytes)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                List<String> rowData = new ArrayList<>();
                rowData.add(line);
                fileData.add(rowData);
            }
        } catch (IOException e) {
            LogUtil.write(e);
        }
        return fileData;
    }
}
