package biweekly;

import biweekly.component.ICalComponent;
import biweekly.io.json.JCalReader;
import biweekly.io.json.JCalWriter;
import biweekly.io.scribe.ScribeIndex;
import biweekly.io.scribe.component.ICalComponentScribe;
import biweekly.io.scribe.property.ICalPropertyScribe;
import biweekly.io.text.ICalReader;
import biweekly.io.text.ICalWriter;
import biweekly.io.xml.XCalDocument;
import biweekly.property.ICalProperty;
import biweekly.util.IOUtils;
import com.google.android.gms.plus.PlusShare;
import cz.msebera.android.httpclient.cookie.ClientCookie;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import javax.xml.transform.TransformerException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class Biweekly {
    public static final String URL;
    public static final String VERSION;

    static abstract class ParserChain<T> {
        final ScribeIndex index = new ScribeIndex();
        final T this_ = this;
        List<List<String>> warnings;

        public abstract List<ICalendar> all() throws IOException, SAXException;

        public abstract ICalendar first() throws IOException, SAXException;

        ParserChain() {
        }

        public T register(ICalPropertyScribe<? extends ICalProperty> scribe) {
            this.index.register((ICalPropertyScribe) scribe);
            return this.this_;
        }

        public T register(ICalComponentScribe<? extends ICalComponent> scribe) {
            this.index.register((ICalComponentScribe) scribe);
            return this.this_;
        }

        public T warnings(List<List<String>> warnings) {
            this.warnings = warnings;
            return this.this_;
        }
    }

    static abstract class WriterChain<T> {
        final Collection<ICalendar> icals;
        final ScribeIndex index = new ScribeIndex();
        final T this_ = this;

        WriterChain(Collection<ICalendar> icals) {
            this.icals = icals;
        }

        public T register(ICalPropertyScribe<? extends ICalProperty> scribe) {
            this.index.register((ICalPropertyScribe) scribe);
            return this.this_;
        }

        public T register(ICalComponentScribe<? extends ICalComponent> scribe) {
            this.index.register((ICalComponentScribe) scribe);
            return this.this_;
        }
    }

    static abstract class ParserChainJson<T> extends ParserChain<T> {
        final boolean closeWhenDone;

        abstract JCalReader _constructReader() throws IOException;

        private ParserChainJson(boolean closeWhenDone) {
            this.closeWhenDone = closeWhenDone;
        }

        public ICalendar first() throws IOException {
            JCalReader parser = constructReader();
            try {
                ICalendar ical = parser.readNext();
                if (this.warnings != null) {
                    this.warnings.add(parser.getWarnings());
                }
                if (this.closeWhenDone) {
                    IOUtils.closeQuietly(parser);
                }
                return ical;
            } catch (Throwable th) {
                if (this.closeWhenDone) {
                    IOUtils.closeQuietly(parser);
                }
            }
        }

        public List<ICalendar> all() throws IOException {
            JCalReader parser = constructReader();
            try {
                List<ICalendar> icals = new ArrayList();
                while (true) {
                    ICalendar ical = parser.readNext();
                    if (ical == null) {
                        break;
                    }
                    if (this.warnings != null) {
                        this.warnings.add(parser.getWarnings());
                    }
                    icals.add(ical);
                }
                return icals;
            } finally {
                if (this.closeWhenDone) {
                    IOUtils.closeQuietly(parser);
                }
            }
        }

        private JCalReader constructReader() throws IOException {
            JCalReader parser = _constructReader();
            parser.setScribeIndex(this.index);
            return parser;
        }
    }

    static abstract class ParserChainText<T> extends ParserChain<T> {
        boolean caretDecoding;
        final boolean closeWhenDone;

        abstract ICalReader _constructReader() throws IOException;

        private ParserChainText(boolean closeWhenDone) {
            this.caretDecoding = true;
            this.closeWhenDone = closeWhenDone;
        }

        public T caretDecoding(boolean enable) {
            this.caretDecoding = enable;
            return this.this_;
        }

        public ICalendar first() throws IOException {
            ICalReader parser = constructReader();
            try {
                ICalendar ical = parser.readNext();
                if (this.warnings != null) {
                    this.warnings.add(parser.getWarnings());
                }
                if (this.closeWhenDone) {
                    IOUtils.closeQuietly(parser);
                }
                return ical;
            } catch (Throwable th) {
                if (this.closeWhenDone) {
                    IOUtils.closeQuietly(parser);
                }
            }
        }

        public List<ICalendar> all() throws IOException {
            ICalReader parser = constructReader();
            try {
                List<ICalendar> icals = new ArrayList();
                while (true) {
                    ICalendar ical = parser.readNext();
                    if (ical == null) {
                        break;
                    }
                    if (this.warnings != null) {
                        this.warnings.add(parser.getWarnings());
                    }
                    icals.add(ical);
                }
                return icals;
            } finally {
                if (this.closeWhenDone) {
                    IOUtils.closeQuietly(parser);
                }
            }
        }

        private ICalReader constructReader() throws IOException {
            ICalReader parser = _constructReader();
            parser.setScribeIndex(this.index);
            parser.setCaretDecodingEnabled(this.caretDecoding);
            return parser;
        }
    }

    static abstract class ParserChainXml<T> extends ParserChain<T> {
        abstract XCalDocument _constructDocument() throws IOException, SAXException;

        ParserChainXml() {
        }

        public ICalendar first() throws IOException, SAXException {
            XCalDocument document = constructDocument();
            ICalendar ical = document.parseFirst();
            if (this.warnings != null) {
                this.warnings.addAll(document.getParseWarnings());
            }
            return ical;
        }

        public List<ICalendar> all() throws IOException, SAXException {
            XCalDocument document = constructDocument();
            List<ICalendar> icals = document.parseAll();
            if (this.warnings != null) {
                this.warnings.addAll(document.getParseWarnings());
            }
            return icals;
        }

        private XCalDocument constructDocument() throws SAXException, IOException {
            XCalDocument parser = _constructDocument();
            parser.setScribeIndex(this.index);
            return parser;
        }
    }

    public static class WriterChainJson extends WriterChain<WriterChainJson> {
        private boolean indent;

        private WriterChainJson(Collection<ICalendar> icals) {
            super(icals);
            this.indent = false;
        }

        public WriterChainJson indent(boolean indent) {
            this.indent = indent;
            return (WriterChainJson) this.this_;
        }

        public String go() {
            Writer sw = new StringWriter();
            try {
                go(sw);
            } catch (IOException e) {
            }
            return sw.toString();
        }

        public void go(OutputStream out) throws IOException {
            boolean z = true;
            if (this.icals.size() <= 1) {
                z = false;
            }
            go(new JCalWriter(out, z));
        }

        public void go(File file) throws IOException {
            boolean z = true;
            if (this.icals.size() <= 1) {
                z = false;
            }
            JCalWriter jcalWriter = new JCalWriter(file, z);
            try {
                go(jcalWriter);
            } finally {
                IOUtils.closeQuietly(jcalWriter);
            }
        }

        public void go(Writer writer) throws IOException {
            boolean z = true;
            if (this.icals.size() <= 1) {
                z = false;
            }
            go(new JCalWriter(writer, z));
        }

        private void go(JCalWriter jcalWriter) throws IOException {
            jcalWriter.setScribeIndex(this.index);
            jcalWriter.setIndent(this.indent);
            for (ICalendar ical : this.icals) {
                jcalWriter.write(ical);
                jcalWriter.flush();
            }
            jcalWriter.closeJsonStream();
        }
    }

    public static class WriterChainText extends WriterChain<WriterChainText> {
        boolean caretEncoding;

        private WriterChainText(Collection<ICalendar> icals) {
            super(icals);
            this.caretEncoding = false;
        }

        public WriterChainText caretEncoding(boolean enable) {
            this.caretEncoding = enable;
            return (WriterChainText) this.this_;
        }

        public String go() {
            Writer sw = new StringWriter();
            try {
                go(sw);
            } catch (IOException e) {
            }
            return sw.toString();
        }

        public void go(OutputStream out) throws IOException {
            go(new ICalWriter(out));
        }

        public void go(File file) throws IOException {
            go(file, false);
        }

        public void go(File file, boolean append) throws IOException {
            ICalWriter icalWriter = new ICalWriter(file, append);
            try {
                go(icalWriter);
            } finally {
                IOUtils.closeQuietly(icalWriter);
            }
        }

        public void go(Writer writer) throws IOException {
            go(new ICalWriter(writer));
        }

        private void go(ICalWriter icalWriter) throws IOException {
            icalWriter.setScribeIndex(this.index);
            icalWriter.setCaretEncodingEnabled(this.caretEncoding);
            for (ICalendar ical : this.icals) {
                icalWriter.write(ical);
                icalWriter.flush();
            }
        }
    }

    public static class WriterChainXml extends WriterChain<WriterChainXml> {
        int indent = -1;
        final Map<String, ICalDataType> parameterDataTypes = new HashMap(0);

        WriterChainXml(Collection<ICalendar> icals) {
            super(icals);
        }

        public WriterChainXml register(ICalPropertyScribe<? extends ICalProperty> scribe) {
            return (WriterChainXml) super.register((ICalPropertyScribe) scribe);
        }

        public WriterChainXml register(ICalComponentScribe<? extends ICalComponent> scribe) {
            return (WriterChainXml) super.register((ICalComponentScribe) scribe);
        }

        public WriterChainXml register(String parameterName, ICalDataType dataType) {
            this.parameterDataTypes.put(parameterName, dataType);
            return (WriterChainXml) this.this_;
        }

        public WriterChainXml indent(int indent) {
            this.indent = indent;
            return (WriterChainXml) this.this_;
        }

        public String go() {
            Writer sw = new StringWriter();
            try {
                go(sw);
            } catch (TransformerException e) {
            }
            return sw.toString();
        }

        public void go(OutputStream out) throws TransformerException {
            constructDocument().write(out, this.indent);
        }

        public void go(File file) throws TransformerException, IOException {
            constructDocument().write(file, this.indent);
        }

        public void go(Writer writer) throws TransformerException {
            constructDocument().write(writer, this.indent);
        }

        public Document dom() {
            return constructDocument().getDocument();
        }

        private XCalDocument constructDocument() {
            XCalDocument document = new XCalDocument();
            document.setScribeIndex(this.index);
            for (Entry<String, ICalDataType> entry : this.parameterDataTypes.entrySet()) {
                document.registerParameterDataType((String) entry.getKey(), (ICalDataType) entry.getValue());
            }
            for (ICalendar ical : this.icals) {
                document.add(ical);
            }
            return document;
        }
    }

    public static class ParserChainJsonReader extends ParserChainJson<ParserChainJsonReader> {
        private final File file;
        private final InputStream in;
        private final Reader reader;

        public /* bridge */ /* synthetic */ List all() throws IOException {
            return super.all();
        }

        public /* bridge */ /* synthetic */ ICalendar first() throws IOException {
            return super.first();
        }

        private ParserChainJsonReader(InputStream in) {
            super(false);
            this.in = in;
            this.reader = null;
            this.file = null;
        }

        private ParserChainJsonReader(File file) {
            super(true);
            this.in = null;
            this.reader = null;
            this.file = file;
        }

        private ParserChainJsonReader(Reader reader) {
            super(false);
            this.in = null;
            this.reader = reader;
            this.file = null;
        }

        public ParserChainJsonReader register(ICalPropertyScribe<? extends ICalProperty> scribe) {
            return (ParserChainJsonReader) super.register((ICalPropertyScribe) scribe);
        }

        public ParserChainJsonReader register(ICalComponentScribe<? extends ICalComponent> scribe) {
            return (ParserChainJsonReader) super.register((ICalComponentScribe) scribe);
        }

        public ParserChainJsonReader warnings(List<List<String>> warnings) {
            return (ParserChainJsonReader) super.warnings(warnings);
        }

        JCalReader _constructReader() throws IOException {
            if (this.in != null) {
                return new JCalReader(this.in);
            }
            if (this.file != null) {
                return new JCalReader(this.file);
            }
            return new JCalReader(this.reader);
        }
    }

    public static class ParserChainJsonString extends ParserChainJson<ParserChainJsonString> {
        private final String text;

        private ParserChainJsonString(String text) {
            super(false);
            this.text = text;
        }

        public ParserChainJsonString register(ICalPropertyScribe<? extends ICalProperty> scribe) {
            return (ParserChainJsonString) super.register((ICalPropertyScribe) scribe);
        }

        public ParserChainJsonString register(ICalComponentScribe<? extends ICalComponent> scribe) {
            return (ParserChainJsonString) super.register((ICalComponentScribe) scribe);
        }

        public ParserChainJsonString warnings(List<List<String>> warnings) {
            return (ParserChainJsonString) super.warnings(warnings);
        }

        JCalReader _constructReader() {
            return new JCalReader(this.text);
        }

        public ICalendar first() {
            try {
                return super.first();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        public List<ICalendar> all() {
            try {
                return super.all();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static class ParserChainTextReader extends ParserChainText<ParserChainTextReader> {
        private final File file;
        private final InputStream in;
        private final Reader reader;

        public /* bridge */ /* synthetic */ List all() throws IOException {
            return super.all();
        }

        public /* bridge */ /* synthetic */ ICalendar first() throws IOException {
            return super.first();
        }

        private ParserChainTextReader(InputStream in) {
            super(false);
            this.in = in;
            this.reader = null;
            this.file = null;
        }

        private ParserChainTextReader(File file) {
            super(true);
            this.in = null;
            this.reader = null;
            this.file = file;
        }

        private ParserChainTextReader(Reader reader) {
            super(false);
            this.in = null;
            this.reader = reader;
            this.file = null;
        }

        public ParserChainTextReader register(ICalPropertyScribe<? extends ICalProperty> scribe) {
            return (ParserChainTextReader) super.register((ICalPropertyScribe) scribe);
        }

        public ParserChainTextReader register(ICalComponentScribe<? extends ICalComponent> scribe) {
            return (ParserChainTextReader) super.register((ICalComponentScribe) scribe);
        }

        public ParserChainTextReader warnings(List<List<String>> warnings) {
            return (ParserChainTextReader) super.warnings(warnings);
        }

        public ParserChainTextReader caretDecoding(boolean enable) {
            return (ParserChainTextReader) super.caretDecoding(enable);
        }

        ICalReader _constructReader() throws IOException {
            if (this.in != null) {
                return new ICalReader(this.in);
            }
            if (this.file != null) {
                return new ICalReader(this.file);
            }
            return new ICalReader(this.reader);
        }
    }

    public static class ParserChainTextString extends ParserChainText<ParserChainTextString> {
        private final String text;

        private ParserChainTextString(String text) {
            super(false);
            this.text = text;
        }

        public ParserChainTextString register(ICalPropertyScribe<? extends ICalProperty> scribe) {
            return (ParserChainTextString) super.register((ICalPropertyScribe) scribe);
        }

        public ParserChainTextString register(ICalComponentScribe<? extends ICalComponent> scribe) {
            return (ParserChainTextString) super.register((ICalComponentScribe) scribe);
        }

        public ParserChainTextString warnings(List<List<String>> warnings) {
            return (ParserChainTextString) super.warnings(warnings);
        }

        public ParserChainTextString caretDecoding(boolean enable) {
            return (ParserChainTextString) super.caretDecoding(enable);
        }

        ICalReader _constructReader() {
            return new ICalReader(this.text);
        }

        public ICalendar first() {
            try {
                return super.first();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        public List<ICalendar> all() {
            try {
                return super.all();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static class ParserChainXmlDocument extends ParserChainXml<ParserChainXmlDocument> {
        private final Document document;

        private ParserChainXmlDocument(Document document) {
            this.document = document;
        }

        public ParserChainXmlDocument register(ICalPropertyScribe<? extends ICalProperty> scribe) {
            return (ParserChainXmlDocument) super.register((ICalPropertyScribe) scribe);
        }

        public ParserChainXmlDocument register(ICalComponentScribe<? extends ICalComponent> scribe) {
            return (ParserChainXmlDocument) super.register((ICalComponentScribe) scribe);
        }

        public ParserChainXmlDocument warnings(List<List<String>> warnings) {
            return (ParserChainXmlDocument) super.warnings(warnings);
        }

        XCalDocument _constructDocument() {
            return new XCalDocument(this.document);
        }

        public ICalendar first() {
            try {
                return super.first();
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (SAXException e2) {
                throw new RuntimeException(e2);
            }
        }

        public List<ICalendar> all() {
            try {
                return super.all();
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (SAXException e2) {
                throw new RuntimeException(e2);
            }
        }
    }

    public static class ParserChainXmlReader extends ParserChainXml<ParserChainXmlReader> {
        private final File file;
        private final InputStream in;
        private final Reader reader;

        public /* bridge */ /* synthetic */ List all() throws IOException, SAXException {
            return super.all();
        }

        public /* bridge */ /* synthetic */ ICalendar first() throws IOException, SAXException {
            return super.first();
        }

        private ParserChainXmlReader(InputStream in) {
            this.in = in;
            this.reader = null;
            this.file = null;
        }

        private ParserChainXmlReader(File file) {
            this.in = null;
            this.reader = null;
            this.file = file;
        }

        private ParserChainXmlReader(Reader reader) {
            this.in = null;
            this.reader = reader;
            this.file = null;
        }

        public ParserChainXmlReader register(ICalPropertyScribe<? extends ICalProperty> scribe) {
            return (ParserChainXmlReader) super.register((ICalPropertyScribe) scribe);
        }

        public ParserChainXmlReader register(ICalComponentScribe<? extends ICalComponent> scribe) {
            return (ParserChainXmlReader) super.register((ICalComponentScribe) scribe);
        }

        public ParserChainXmlReader warnings(List<List<String>> warnings) {
            return (ParserChainXmlReader) super.warnings(warnings);
        }

        XCalDocument _constructDocument() throws IOException, SAXException {
            if (this.in != null) {
                return new XCalDocument(this.in);
            }
            if (this.file != null) {
                return new XCalDocument(this.file);
            }
            return new XCalDocument(this.reader);
        }
    }

    public static class ParserChainXmlString extends ParserChainXml<ParserChainXmlString> {
        private final String xml;

        private ParserChainXmlString(String xml) {
            this.xml = xml;
        }

        public ParserChainXmlString register(ICalPropertyScribe<? extends ICalProperty> scribe) {
            return (ParserChainXmlString) super.register((ICalPropertyScribe) scribe);
        }

        public ParserChainXmlString register(ICalComponentScribe<? extends ICalComponent> scribe) {
            return (ParserChainXmlString) super.register((ICalComponentScribe) scribe);
        }

        public ParserChainXmlString warnings(List<List<String>> warnings) {
            return (ParserChainXmlString) super.warnings(warnings);
        }

        XCalDocument _constructDocument() throws SAXException {
            return new XCalDocument(this.xml);
        }

        public ICalendar first() throws SAXException {
            try {
                return super.first();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        public List<ICalendar> all() throws SAXException {
            try {
                return super.all();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    static {
        InputStream in = null;
        try {
            in = Biweekly.class.getResourceAsStream("/biweekly.properties");
            Properties props = new Properties();
            props.load(in);
            VERSION = props.getProperty(ClientCookie.VERSION_ATTR);
            URL = props.getProperty(PlusShare.KEY_CALL_TO_ACTION_URL);
            IOUtils.closeQuietly(in);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Throwable th) {
            IOUtils.closeQuietly(in);
        }
    }

    public static ParserChainTextString parse(String ical) {
        return new ParserChainTextString(ical);
    }

    public static ParserChainTextReader parse(File file) {
        return new ParserChainTextReader(file);
    }

    public static ParserChainTextReader parse(InputStream in) {
        return new ParserChainTextReader(in);
    }

    public static ParserChainTextReader parse(Reader reader) {
        return new ParserChainTextReader(reader);
    }

    public static WriterChainText write(ICalendar... icals) {
        return write(Arrays.asList(icals));
    }

    public static WriterChainText write(Collection<ICalendar> icals) {
        return new WriterChainText(icals);
    }

    public static ParserChainXmlString parseXml(String xml) {
        return new ParserChainXmlString(xml);
    }

    public static ParserChainXmlReader parseXml(File file) {
        return new ParserChainXmlReader(file);
    }

    public static ParserChainXmlReader parseXml(InputStream in) {
        return new ParserChainXmlReader(in);
    }

    public static ParserChainXmlReader parseXml(Reader reader) {
        return new ParserChainXmlReader(reader);
    }

    public static ParserChainXmlDocument parseXml(Document document) {
        return new ParserChainXmlDocument(document);
    }

    public static WriterChainXml writeXml(ICalendar... icals) {
        return writeXml(Arrays.asList(icals));
    }

    public static WriterChainXml writeXml(Collection<ICalendar> icals) {
        return new WriterChainXml(icals);
    }

    public static ParserChainJsonString parseJson(String json) {
        return new ParserChainJsonString(json);
    }

    public static ParserChainJsonReader parseJson(File file) {
        return new ParserChainJsonReader(file);
    }

    public static ParserChainJsonReader parseJson(InputStream in) {
        return new ParserChainJsonReader(in);
    }

    public static ParserChainJsonReader parseJson(Reader reader) {
        return new ParserChainJsonReader(reader);
    }

    public static WriterChainJson writeJson(ICalendar... icals) {
        return writeJson(Arrays.asList(icals));
    }

    public static WriterChainJson writeJson(Collection<ICalendar> icals) {
        return new WriterChainJson(icals);
    }

    private Biweekly() {
    }
}
