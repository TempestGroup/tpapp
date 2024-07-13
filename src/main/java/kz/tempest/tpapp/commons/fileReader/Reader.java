package kz.tempest.tpapp.commons.fileReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Reader {
    public final Logger logger = LoggerFactory.getLogger(getClass());

    public ReaderType getType() {
        return ReaderType.getByClass(getClass());
    }

    public abstract Object read(byte[] fileBytes);
}
