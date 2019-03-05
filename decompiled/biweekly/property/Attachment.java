package biweekly.property;

import biweekly.Warning;
import biweekly.component.ICalComponent;
import biweekly.util.IOUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

public class Attachment extends ICalProperty {
    private byte[] data;
    private String uri;

    public Attachment(String formatType, File file) throws IOException {
        this.data = IOUtils.toByteArray(new FileInputStream(file), true);
        setFormatType(formatType);
    }

    public Attachment(String formatType, byte[] data) {
        this.data = data;
        setFormatType(formatType);
    }

    public Attachment(String formatType, String uri) {
        this.uri = uri;
        setFormatType(formatType);
    }

    public byte[] getData() {
        return this.data;
    }

    public void setData(byte[] data) {
        this.data = data;
        this.uri = null;
    }

    public String getUri() {
        return this.uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
        this.data = null;
    }

    public String getFormatType() {
        return super.getFormatType();
    }

    public void setFormatType(String formatType) {
        super.setFormatType(formatType);
    }

    protected void validate(List<ICalComponent> list, List<Warning> warnings) {
        if (this.uri == null && this.data == null) {
            warnings.add(Warning.validate(26, new Object[0]));
        }
    }
}
