package com.fasterxml.jackson.core.json;

import android.support.v4.view.MotionEventCompat;
import app.asu.SettingsActivity;
import com.fasterxml.jackson.core.Base64Variant;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.base.ParserBase;
import com.fasterxml.jackson.core.io.CharTypes;
import com.fasterxml.jackson.core.io.IOContext;
import com.fasterxml.jackson.core.sym.CharsToNameCanonicalizer;
import com.fasterxml.jackson.core.util.ByteArrayBuilder;
import com.fasterxml.jackson.core.util.TextBuffer;
import com.google.ads.AdSize;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;

public final class ReaderBasedJsonParser extends ParserBase {
    protected static final int[] _icLatin1 = CharTypes.getInputCodeLatin1();
    private static final int[] _icWS = CharTypes.getInputCodeWS();
    protected final int _hashSeed;
    protected char[] _inputBuffer;
    protected ObjectCodec _objectCodec;
    protected Reader _reader;
    protected final CharsToNameCanonicalizer _symbols;
    protected boolean _tokenIncomplete = false;

    public ReaderBasedJsonParser(IOContext iOContext, int i, Reader reader, ObjectCodec objectCodec, CharsToNameCanonicalizer charsToNameCanonicalizer) {
        super(iOContext, i);
        this._reader = reader;
        this._inputBuffer = iOContext.allocTokenBuffer();
        this._objectCodec = objectCodec;
        this._symbols = charsToNameCanonicalizer;
        this._hashSeed = charsToNameCanonicalizer.hashSeed();
    }

    public ObjectCodec getCodec() {
        return this._objectCodec;
    }

    public void setCodec(ObjectCodec objectCodec) {
        this._objectCodec = objectCodec;
    }

    public int releaseBuffered(Writer writer) throws IOException {
        int i = this._inputEnd - this._inputPtr;
        if (i < 1) {
            return 0;
        }
        writer.write(this._inputBuffer, this._inputPtr, i);
        return i;
    }

    public Object getInputSource() {
        return this._reader;
    }

    protected boolean loadMore() throws IOException {
        this._currInputProcessed += (long) this._inputEnd;
        this._currInputRowStart -= this._inputEnd;
        if (this._reader == null) {
            return false;
        }
        int read = this._reader.read(this._inputBuffer, 0, this._inputBuffer.length);
        if (read > 0) {
            this._inputPtr = 0;
            this._inputEnd = read;
            return true;
        }
        _closeInput();
        if (read != 0) {
            return false;
        }
        throw new IOException("Reader returned 0 characters when trying to read " + this._inputEnd);
    }

    protected char getNextChar(String str) throws IOException, JsonParseException {
        if (this._inputPtr >= this._inputEnd && !loadMore()) {
            _reportInvalidEOF(str);
        }
        char[] cArr = this._inputBuffer;
        int i = this._inputPtr;
        this._inputPtr = i + 1;
        return cArr[i];
    }

    protected void _closeInput() throws IOException {
        if (this._reader != null) {
            if (this._ioContext.isResourceManaged() || isEnabled(Feature.AUTO_CLOSE_SOURCE)) {
                this._reader.close();
            }
            this._reader = null;
        }
    }

    protected void _releaseBuffers() throws IOException {
        super._releaseBuffers();
        this._symbols.release();
        char[] cArr = this._inputBuffer;
        if (cArr != null) {
            this._inputBuffer = null;
            this._ioContext.releaseTokenBuffer(cArr);
        }
    }

    public String getText() throws IOException, JsonParseException {
        JsonToken jsonToken = this._currToken;
        if (jsonToken != JsonToken.VALUE_STRING) {
            return _getText2(jsonToken);
        }
        if (this._tokenIncomplete) {
            this._tokenIncomplete = false;
            _finishString();
        }
        return this._textBuffer.contentsAsString();
    }

    public String getValueAsString() throws IOException, JsonParseException {
        if (this._currToken != JsonToken.VALUE_STRING) {
            return super.getValueAsString(null);
        }
        if (this._tokenIncomplete) {
            this._tokenIncomplete = false;
            _finishString();
        }
        return this._textBuffer.contentsAsString();
    }

    public String getValueAsString(String str) throws IOException, JsonParseException {
        if (this._currToken != JsonToken.VALUE_STRING) {
            return super.getValueAsString(str);
        }
        if (this._tokenIncomplete) {
            this._tokenIncomplete = false;
            _finishString();
        }
        return this._textBuffer.contentsAsString();
    }

    protected String _getText2(JsonToken jsonToken) {
        if (jsonToken == null) {
            return null;
        }
        switch (jsonToken.id()) {
            case 5:
                return this._parsingContext.getCurrentName();
            case 6:
            case 7:
            case 8:
                return this._textBuffer.contentsAsString();
            default:
                return jsonToken.asString();
        }
    }

    public char[] getTextCharacters() throws IOException, JsonParseException {
        if (this._currToken == null) {
            return null;
        }
        switch (this._currToken.id()) {
            case 5:
                if (!this._nameCopied) {
                    String currentName = this._parsingContext.getCurrentName();
                    int length = currentName.length();
                    if (this._nameCopyBuffer == null) {
                        this._nameCopyBuffer = this._ioContext.allocNameCopyBuffer(length);
                    } else if (this._nameCopyBuffer.length < length) {
                        this._nameCopyBuffer = new char[length];
                    }
                    currentName.getChars(0, length, this._nameCopyBuffer, 0);
                    this._nameCopied = true;
                }
                return this._nameCopyBuffer;
            case 6:
                if (this._tokenIncomplete) {
                    this._tokenIncomplete = false;
                    _finishString();
                    break;
                }
                break;
            case 7:
            case 8:
                break;
            default:
                return this._currToken.asCharArray();
        }
        return this._textBuffer.getTextBuffer();
    }

    public int getTextLength() throws IOException, JsonParseException {
        if (this._currToken == null) {
            return 0;
        }
        switch (this._currToken.id()) {
            case 5:
                return this._parsingContext.getCurrentName().length();
            case 6:
                if (this._tokenIncomplete) {
                    this._tokenIncomplete = false;
                    _finishString();
                    break;
                }
                break;
            case 7:
            case 8:
                break;
            default:
                return this._currToken.asCharArray().length;
        }
        return this._textBuffer.size();
    }

    public int getTextOffset() throws IOException, JsonParseException {
        if (this._currToken == null) {
            return 0;
        }
        switch (this._currToken.id()) {
            case 6:
                if (this._tokenIncomplete) {
                    this._tokenIncomplete = false;
                    _finishString();
                    break;
                }
                break;
            case 7:
            case 8:
                break;
            default:
                return 0;
        }
        return this._textBuffer.getTextOffset();
    }

    public byte[] getBinaryValue(Base64Variant base64Variant) throws IOException, JsonParseException {
        if (this._currToken != JsonToken.VALUE_STRING && (this._currToken != JsonToken.VALUE_EMBEDDED_OBJECT || this._binaryValue == null)) {
            _reportError("Current token (" + this._currToken + ") not VALUE_STRING or VALUE_EMBEDDED_OBJECT, can not access as binary");
        }
        if (this._tokenIncomplete) {
            try {
                this._binaryValue = _decodeBase64(base64Variant);
                this._tokenIncomplete = false;
            } catch (IllegalArgumentException e) {
                throw _constructError("Failed to decode VALUE_STRING as base64 (" + base64Variant + "): " + e.getMessage());
            }
        } else if (this._binaryValue == null) {
            ByteArrayBuilder _getByteArrayBuilder = _getByteArrayBuilder();
            _decodeBase64(getText(), _getByteArrayBuilder, base64Variant);
            this._binaryValue = _getByteArrayBuilder.toByteArray();
        }
        return this._binaryValue;
    }

    public int readBinaryValue(Base64Variant base64Variant, OutputStream outputStream) throws IOException, JsonParseException {
        if (this._tokenIncomplete && this._currToken == JsonToken.VALUE_STRING) {
            byte[] allocBase64Buffer = this._ioContext.allocBase64Buffer();
            try {
                int _readBinary = _readBinary(base64Variant, outputStream, allocBase64Buffer);
                return _readBinary;
            } finally {
                this._ioContext.releaseBase64Buffer(allocBase64Buffer);
            }
        } else {
            byte[] binaryValue = getBinaryValue(base64Variant);
            outputStream.write(binaryValue);
            return binaryValue.length;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    protected int _readBinary(com.fasterxml.jackson.core.Base64Variant r12, java.io.OutputStream r13, byte[] r14) throws java.io.IOException, com.fasterxml.jackson.core.JsonParseException {
        /*
        r11 = this;
        r10 = 3;
        r9 = 34;
        r8 = -2;
        r1 = 0;
        r0 = r14.length;
        r5 = r0 + -3;
        r0 = r1;
        r2 = r1;
    L_0x000a:
        r3 = r11._inputPtr;
        r4 = r11._inputEnd;
        if (r3 < r4) goto L_0x0013;
    L_0x0010:
        r11.loadMoreGuaranteed();
    L_0x0013:
        r3 = r11._inputBuffer;
        r4 = r11._inputPtr;
        r6 = r4 + 1;
        r11._inputPtr = r6;
        r4 = r3[r4];
        r3 = 32;
        if (r4 <= r3) goto L_0x000a;
    L_0x0021:
        r3 = r12.decodeBase64Char(r4);
        if (r3 >= 0) goto L_0x0038;
    L_0x0027:
        if (r4 != r9) goto L_0x0032;
    L_0x0029:
        r11._tokenIncomplete = r1;
        if (r2 <= 0) goto L_0x0031;
    L_0x002d:
        r0 = r0 + r2;
        r13.write(r14, r1, r2);
    L_0x0031:
        return r0;
    L_0x0032:
        r3 = r11._decodeBase64Escape(r12, r4, r1);
        if (r3 < 0) goto L_0x000a;
    L_0x0038:
        r4 = r3;
        if (r2 <= r5) goto L_0x013b;
    L_0x003b:
        r0 = r0 + r2;
        r13.write(r14, r1, r2);
        r3 = r1;
    L_0x0040:
        r2 = r11._inputPtr;
        r6 = r11._inputEnd;
        if (r2 < r6) goto L_0x0049;
    L_0x0046:
        r11.loadMoreGuaranteed();
    L_0x0049:
        r2 = r11._inputBuffer;
        r6 = r11._inputPtr;
        r7 = r6 + 1;
        r11._inputPtr = r7;
        r6 = r2[r6];
        r2 = r12.decodeBase64Char(r6);
        if (r2 >= 0) goto L_0x005e;
    L_0x0059:
        r2 = 1;
        r2 = r11._decodeBase64Escape(r12, r6, r2);
    L_0x005e:
        r4 = r4 << 6;
        r4 = r4 | r2;
        r2 = r11._inputPtr;
        r6 = r11._inputEnd;
        if (r2 < r6) goto L_0x006a;
    L_0x0067:
        r11.loadMoreGuaranteed();
    L_0x006a:
        r2 = r11._inputBuffer;
        r6 = r11._inputPtr;
        r7 = r6 + 1;
        r11._inputPtr = r7;
        r6 = r2[r6];
        r2 = r12.decodeBase64Char(r6);
        if (r2 >= 0) goto L_0x00d7;
    L_0x007a:
        if (r2 == r8) goto L_0x0091;
    L_0x007c:
        if (r6 != r9) goto L_0x008c;
    L_0x007e:
        r2 = r12.usesPadding();
        if (r2 != 0) goto L_0x008c;
    L_0x0084:
        r4 = r4 >> 4;
        r2 = r3 + 1;
        r4 = (byte) r4;
        r14[r3] = r4;
        goto L_0x0029;
    L_0x008c:
        r2 = 2;
        r2 = r11._decodeBase64Escape(r12, r6, r2);
    L_0x0091:
        if (r2 != r8) goto L_0x00d7;
    L_0x0093:
        r2 = r11._inputPtr;
        r6 = r11._inputEnd;
        if (r2 < r6) goto L_0x009c;
    L_0x0099:
        r11.loadMoreGuaranteed();
    L_0x009c:
        r2 = r11._inputBuffer;
        r6 = r11._inputPtr;
        r7 = r6 + 1;
        r11._inputPtr = r7;
        r2 = r2[r6];
        r6 = r12.usesPaddingChar(r2);
        if (r6 != 0) goto L_0x00ce;
    L_0x00ac:
        r0 = new java.lang.StringBuilder;
        r0.<init>();
        r1 = "expected padding character '";
        r0 = r0.append(r1);
        r1 = r12.getPaddingChar();
        r0 = r0.append(r1);
        r1 = "'";
        r0 = r0.append(r1);
        r0 = r0.toString();
        r0 = r11.reportInvalidBase64Char(r12, r2, r10, r0);
        throw r0;
    L_0x00ce:
        r4 = r4 >> 4;
        r2 = r3 + 1;
        r4 = (byte) r4;
        r14[r3] = r4;
        goto L_0x000a;
    L_0x00d7:
        r4 = r4 << 6;
        r4 = r4 | r2;
        r2 = r11._inputPtr;
        r6 = r11._inputEnd;
        if (r2 < r6) goto L_0x00e3;
    L_0x00e0:
        r11.loadMoreGuaranteed();
    L_0x00e3:
        r2 = r11._inputBuffer;
        r6 = r11._inputPtr;
        r7 = r6 + 1;
        r11._inputPtr = r7;
        r6 = r2[r6];
        r2 = r12.decodeBase64Char(r6);
        if (r2 >= 0) goto L_0x0123;
    L_0x00f3:
        if (r2 == r8) goto L_0x0111;
    L_0x00f5:
        if (r6 != r9) goto L_0x010d;
    L_0x00f7:
        r2 = r12.usesPadding();
        if (r2 != 0) goto L_0x010d;
    L_0x00fd:
        r4 = r4 >> 2;
        r5 = r3 + 1;
        r2 = r4 >> 8;
        r2 = (byte) r2;
        r14[r3] = r2;
        r2 = r5 + 1;
        r3 = (byte) r4;
        r14[r5] = r3;
        goto L_0x0029;
    L_0x010d:
        r2 = r11._decodeBase64Escape(r12, r6, r10);
    L_0x0111:
        if (r2 != r8) goto L_0x0123;
    L_0x0113:
        r4 = r4 >> 2;
        r6 = r3 + 1;
        r2 = r4 >> 8;
        r2 = (byte) r2;
        r14[r3] = r2;
        r2 = r6 + 1;
        r3 = (byte) r4;
        r14[r6] = r3;
        goto L_0x000a;
    L_0x0123:
        r4 = r4 << 6;
        r4 = r4 | r2;
        r2 = r3 + 1;
        r6 = r4 >> 16;
        r6 = (byte) r6;
        r14[r3] = r6;
        r3 = r2 + 1;
        r6 = r4 >> 8;
        r6 = (byte) r6;
        r14[r2] = r6;
        r2 = r3 + 1;
        r4 = (byte) r4;
        r14[r3] = r4;
        goto L_0x000a;
    L_0x013b:
        r3 = r2;
        goto L_0x0040;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.fasterxml.jackson.core.json.ReaderBasedJsonParser._readBinary(com.fasterxml.jackson.core.Base64Variant, java.io.OutputStream, byte[]):int");
    }

    public JsonToken nextToken() throws IOException, JsonParseException {
        this._numTypesValid = 0;
        if (this._currToken == JsonToken.FIELD_NAME) {
            return _nextAfterName();
        }
        if (this._tokenIncomplete) {
            _skipString();
        }
        int _skipWSOrEnd = _skipWSOrEnd();
        if (_skipWSOrEnd < 0) {
            close();
            this._currToken = null;
            return null;
        }
        this._tokenInputTotal = (this._currInputProcessed + ((long) this._inputPtr)) - 1;
        this._tokenInputRow = this._currInputRow;
        this._tokenInputCol = (this._inputPtr - this._currInputRowStart) - 1;
        this._binaryValue = null;
        JsonToken jsonToken;
        if (_skipWSOrEnd == 93) {
            if (!this._parsingContext.inArray()) {
                _reportMismatchedEndMarker(_skipWSOrEnd, '}');
            }
            this._parsingContext = this._parsingContext.getParent();
            jsonToken = JsonToken.END_ARRAY;
            this._currToken = jsonToken;
            return jsonToken;
        } else if (_skipWSOrEnd == 125) {
            if (!this._parsingContext.inObject()) {
                _reportMismatchedEndMarker(_skipWSOrEnd, ']');
            }
            this._parsingContext = this._parsingContext.getParent();
            jsonToken = JsonToken.END_OBJECT;
            this._currToken = jsonToken;
            return jsonToken;
        } else {
            if (this._parsingContext.expectComma()) {
                if (_skipWSOrEnd != 44) {
                    _reportUnexpectedChar(_skipWSOrEnd, "was expecting comma to separate " + this._parsingContext.getTypeDesc() + " entries");
                }
                _skipWSOrEnd = _skipWS();
            }
            boolean inObject = this._parsingContext.inObject();
            if (inObject) {
                this._parsingContext.setCurrentName(_parseName(_skipWSOrEnd));
                this._currToken = JsonToken.FIELD_NAME;
                _skipWSOrEnd = _skipWS();
                if (_skipWSOrEnd != 58) {
                    _reportUnexpectedChar(_skipWSOrEnd, "was expecting a colon to separate field name and value");
                }
                _skipWSOrEnd = _skipWS();
            }
            switch (_skipWSOrEnd) {
                case MotionEventCompat.AXIS_GENERIC_3 /*34*/:
                    this._tokenIncomplete = true;
                    jsonToken = JsonToken.VALUE_STRING;
                    break;
                case MotionEventCompat.AXIS_GENERIC_14 /*45*/:
                case 48:
                case 49:
                case AdSize.PORTRAIT_AD_HEIGHT /*50*/:
                case 51:
                case 52:
                case 53:
                case 54:
                case 55:
                case 56:
                case 57:
                    jsonToken = _parseNumber(_skipWSOrEnd);
                    break;
                case 91:
                    if (!inObject) {
                        this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
                    }
                    jsonToken = JsonToken.START_ARRAY;
                    break;
                case 93:
                case 125:
                    _reportUnexpectedChar(_skipWSOrEnd, "expected a value");
                    break;
                case 102:
                    _matchToken("false", 1);
                    jsonToken = JsonToken.VALUE_FALSE;
                    break;
                case 110:
                    _matchToken("null", 1);
                    jsonToken = JsonToken.VALUE_NULL;
                    break;
                case 116:
                    break;
                case 123:
                    if (!inObject) {
                        this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
                    }
                    jsonToken = JsonToken.START_OBJECT;
                    break;
                default:
                    jsonToken = _handleOddValue(_skipWSOrEnd);
                    break;
            }
            _matchToken("true", 1);
            jsonToken = JsonToken.VALUE_TRUE;
            if (inObject) {
                this._nextToken = jsonToken;
                return this._currToken;
            }
            this._currToken = jsonToken;
            return jsonToken;
        }
    }

    private JsonToken _nextAfterName() {
        this._nameCopied = false;
        JsonToken jsonToken = this._nextToken;
        this._nextToken = null;
        if (jsonToken == JsonToken.START_ARRAY) {
            this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
        } else if (jsonToken == JsonToken.START_OBJECT) {
            this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
        }
        this._currToken = jsonToken;
        return jsonToken;
    }

    public String nextTextValue() throws IOException, JsonParseException {
        if (this._currToken == JsonToken.FIELD_NAME) {
            this._nameCopied = false;
            JsonToken jsonToken = this._nextToken;
            this._nextToken = null;
            this._currToken = jsonToken;
            if (jsonToken == JsonToken.VALUE_STRING) {
                if (this._tokenIncomplete) {
                    this._tokenIncomplete = false;
                    _finishString();
                }
                return this._textBuffer.contentsAsString();
            } else if (jsonToken == JsonToken.START_ARRAY) {
                this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
                return null;
            } else if (jsonToken != JsonToken.START_OBJECT) {
                return null;
            } else {
                this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
                return null;
            }
        } else if (nextToken() == JsonToken.VALUE_STRING) {
            return getText();
        } else {
            return null;
        }
    }

    public int nextIntValue(int i) throws IOException, JsonParseException {
        if (this._currToken != JsonToken.FIELD_NAME) {
            return nextToken() == JsonToken.VALUE_NUMBER_INT ? getIntValue() : i;
        } else {
            this._nameCopied = false;
            JsonToken jsonToken = this._nextToken;
            this._nextToken = null;
            this._currToken = jsonToken;
            if (jsonToken == JsonToken.VALUE_NUMBER_INT) {
                return getIntValue();
            }
            if (jsonToken == JsonToken.START_ARRAY) {
                this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
                return i;
            } else if (jsonToken != JsonToken.START_OBJECT) {
                return i;
            } else {
                this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
                return i;
            }
        }
    }

    public long nextLongValue(long j) throws IOException, JsonParseException {
        if (this._currToken != JsonToken.FIELD_NAME) {
            return nextToken() == JsonToken.VALUE_NUMBER_INT ? getLongValue() : j;
        } else {
            this._nameCopied = false;
            JsonToken jsonToken = this._nextToken;
            this._nextToken = null;
            this._currToken = jsonToken;
            if (jsonToken == JsonToken.VALUE_NUMBER_INT) {
                return getLongValue();
            }
            if (jsonToken == JsonToken.START_ARRAY) {
                this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
                return j;
            } else if (jsonToken != JsonToken.START_OBJECT) {
                return j;
            } else {
                this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
                return j;
            }
        }
    }

    public Boolean nextBooleanValue() throws IOException, JsonParseException {
        JsonToken jsonToken;
        if (this._currToken == JsonToken.FIELD_NAME) {
            this._nameCopied = false;
            jsonToken = this._nextToken;
            this._nextToken = null;
            this._currToken = jsonToken;
            if (jsonToken == JsonToken.VALUE_TRUE) {
                return Boolean.TRUE;
            }
            if (jsonToken == JsonToken.VALUE_FALSE) {
                return Boolean.FALSE;
            }
            if (jsonToken == JsonToken.START_ARRAY) {
                this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
                return null;
            } else if (jsonToken != JsonToken.START_OBJECT) {
                return null;
            } else {
                this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
                return null;
            }
        }
        jsonToken = nextToken();
        if (jsonToken == null) {
            return null;
        }
        int id = jsonToken.id();
        if (id == 9) {
            return Boolean.TRUE;
        }
        if (id == 10) {
            return Boolean.FALSE;
        }
        return null;
    }

    protected JsonToken _parseNumber(int i) throws IOException {
        int i2 = 1;
        int i3 = 0;
        boolean z = i == 45;
        int i4 = this._inputPtr;
        int i5 = i4 - 1;
        int i6 = this._inputEnd;
        int i7;
        char c;
        int i8;
        int i9;
        char c2;
        char c3;
        if (!z) {
            i7 = i4;
            if (i != 48) {
                while (i7 < this._inputEnd) {
                    i4 = i7 + 1;
                    c = this._inputBuffer[i7];
                    if (c >= '0') {
                    }
                    if (c != '.') {
                        i8 = 0;
                        i9 = i4;
                        c2 = c;
                        i7 = i9;
                    } else {
                        i7 = 0;
                        i8 = i4;
                        while (i8 < i6) {
                            i4 = i8 + 1;
                            c3 = this._inputBuffer[i8];
                            if (c3 >= '0') {
                            }
                            if (i7 == 0) {
                                reportUnexpectedNumberChar(c3, "Decimal point not followed by a digit");
                            }
                            i9 = i7;
                            i7 = i4;
                            i4 = c3;
                            i8 = i9;
                        }
                    }
                    if (i7 < i6) {
                        i4 = i7 + 1;
                        c = this._inputBuffer[i7];
                        if (c == '-') {
                        }
                        if (i4 < i6) {
                            i7 = i4 + 1;
                            i4 = this._inputBuffer[i4];
                            while (i4 <= 57) {
                                i3++;
                                if (i7 < i6) {
                                    i9 = i7 + 1;
                                    c2 = this._inputBuffer[i7];
                                    i7 = i9;
                                }
                            }
                            if (i3 == 0) {
                                reportUnexpectedNumberChar(i4, "Exponent indicator not followed by a digit");
                            }
                            i7--;
                            this._inputPtr = i7;
                            if (this._parsingContext.inRoot()) {
                                _verifyRootSpace(i4);
                            }
                            this._textBuffer.resetWithShared(this._inputBuffer, i5, i7 - i5);
                            return reset(z, i2, i8, i3);
                        }
                    }
                }
            }
        } else if (i4 < this._inputEnd) {
            i7 = i4 + 1;
            i = this._inputBuffer[i4];
            if (i > 57 || i < 48) {
                this._inputPtr = i7;
                return _handleInvalidNumberStart(i, true);
            }
            if (i != 48) {
                while (i7 < this._inputEnd) {
                    i4 = i7 + 1;
                    c = this._inputBuffer[i7];
                    if (c >= '0' || c > '9') {
                        if (c != '.') {
                            i7 = 0;
                            i8 = i4;
                            while (i8 < i6) {
                                i4 = i8 + 1;
                                c3 = this._inputBuffer[i8];
                                if (c3 >= '0' || c3 > '9') {
                                    if (i7 == 0) {
                                        reportUnexpectedNumberChar(c3, "Decimal point not followed by a digit");
                                    }
                                    i9 = i7;
                                    i7 = i4;
                                    i4 = c3;
                                    i8 = i9;
                                } else {
                                    i7++;
                                    i8 = i4;
                                }
                            }
                        } else {
                            i8 = 0;
                            i9 = i4;
                            c2 = c;
                            i7 = i9;
                        }
                        if (i4 == 101 || i4 == 69) {
                            if (i7 < i6) {
                                i4 = i7 + 1;
                                c = this._inputBuffer[i7];
                                if (c == '-' && c != '+') {
                                    i9 = i4;
                                    c2 = c;
                                    i7 = i9;
                                } else if (i4 < i6) {
                                    i7 = i4 + 1;
                                    i4 = this._inputBuffer[i4];
                                }
                                while (i4 <= 57 && i4 >= 48) {
                                    i3++;
                                    if (i7 < i6) {
                                        i9 = i7 + 1;
                                        c2 = this._inputBuffer[i7];
                                        i7 = i9;
                                    }
                                }
                                if (i3 == 0) {
                                    reportUnexpectedNumberChar(i4, "Exponent indicator not followed by a digit");
                                }
                            }
                        }
                        i7--;
                        this._inputPtr = i7;
                        if (this._parsingContext.inRoot()) {
                            _verifyRootSpace(i4);
                        }
                        this._textBuffer.resetWithShared(this._inputBuffer, i5, i7 - i5);
                        return reset(z, i2, i8, i3);
                    }
                    i2++;
                    i7 = i4;
                }
            }
        }
        if (z) {
            i2 = i5 + 1;
        } else {
            i2 = i5;
        }
        this._inputPtr = i2;
        return _parseNumber2(z);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private com.fasterxml.jackson.core.JsonToken _parseNumber2(boolean r15) throws java.io.IOException {
        /*
        r14 = this;
        r10 = 45;
        r12 = 57;
        r11 = 48;
        r1 = 1;
        r2 = 0;
        r0 = r14._textBuffer;
        r4 = r0.emptyAndGetCurrentSegment();
        if (r15 == 0) goto L_0x01af;
    L_0x0010:
        r4[r2] = r10;
        r0 = r1;
    L_0x0013:
        r3 = r14._inputPtr;
        r5 = r14._inputEnd;
        if (r3 >= r5) goto L_0x0134;
    L_0x0019:
        r3 = r14._inputBuffer;
        r5 = r14._inputPtr;
        r6 = r5 + 1;
        r14._inputPtr = r6;
        r3 = r3[r5];
    L_0x0023:
        if (r3 != r11) goto L_0x0029;
    L_0x0025:
        r3 = r14._verifyNoLeadingZeroes();
    L_0x0029:
        r5 = r2;
        r13 = r3;
        r3 = r4;
        r4 = r13;
    L_0x002d:
        if (r4 < r11) goto L_0x01a8;
    L_0x002f:
        if (r4 > r12) goto L_0x01a8;
    L_0x0031:
        r5 = r5 + 1;
        r6 = r3.length;
        if (r0 < r6) goto L_0x003e;
    L_0x0036:
        r0 = r14._textBuffer;
        r0 = r0.finishCurrentSegment();
        r3 = r0;
        r0 = r2;
    L_0x003e:
        r6 = r0 + 1;
        r3[r0] = r4;
        r0 = r14._inputPtr;
        r4 = r14._inputEnd;
        if (r0 < r4) goto L_0x013c;
    L_0x0048:
        r0 = r14.loadMore();
        if (r0 != 0) goto L_0x013c;
    L_0x004e:
        r7 = r1;
        r0 = r2;
        r9 = r5;
        r4 = r3;
        r5 = r6;
    L_0x0053:
        if (r9 != 0) goto L_0x0075;
    L_0x0055:
        r3 = new java.lang.StringBuilder;
        r3.<init>();
        r6 = "Missing integer part (next char ";
        r3 = r3.append(r6);
        r6 = com.fasterxml.jackson.core.base.ParserMinimalBase._getCharDesc(r0);
        r3 = r3.append(r6);
        r6 = ")";
        r3 = r3.append(r6);
        r3 = r3.toString();
        r14.reportInvalidNumber(r3);
    L_0x0075:
        r3 = 46;
        if (r0 != r3) goto L_0x01a1;
    L_0x0079:
        r3 = r5 + 1;
        r4[r5] = r0;
        r5 = r4;
        r4 = r3;
        r3 = r0;
        r0 = r2;
    L_0x0081:
        r6 = r14._inputPtr;
        r8 = r14._inputEnd;
        if (r6 < r8) goto L_0x0149;
    L_0x0087:
        r6 = r14.loadMore();
        if (r6 != 0) goto L_0x0149;
    L_0x008d:
        r6 = r3;
        r3 = r1;
    L_0x008f:
        if (r0 != 0) goto L_0x0096;
    L_0x0091:
        r7 = "Decimal point not followed by a digit";
        r14.reportUnexpectedNumberChar(r6, r7);
    L_0x0096:
        r8 = r0;
        r0 = r4;
        r13 = r3;
        r3 = r5;
        r5 = r13;
    L_0x009b:
        r4 = 101; // 0x65 float:1.42E-43 double:5.0E-322;
        if (r6 == r4) goto L_0x00a3;
    L_0x009f:
        r4 = 69;
        if (r6 != r4) goto L_0x0196;
    L_0x00a3:
        r4 = r3.length;
        if (r0 < r4) goto L_0x00ae;
    L_0x00a6:
        r0 = r14._textBuffer;
        r0 = r0.finishCurrentSegment();
        r3 = r0;
        r0 = r2;
    L_0x00ae:
        r4 = r0 + 1;
        r3[r0] = r6;
        r0 = r14._inputPtr;
        r6 = r14._inputEnd;
        if (r0 >= r6) goto L_0x016d;
    L_0x00b8:
        r0 = r14._inputBuffer;
        r6 = r14._inputPtr;
        r7 = r6 + 1;
        r14._inputPtr = r7;
        r6 = r0[r6];
    L_0x00c2:
        if (r6 == r10) goto L_0x00c8;
    L_0x00c4:
        r0 = 43;
        if (r6 != r0) goto L_0x0191;
    L_0x00c8:
        r0 = r3.length;
        if (r4 < r0) goto L_0x018e;
    L_0x00cb:
        r0 = r14._textBuffer;
        r3 = r0.finishCurrentSegment();
        r0 = r2;
    L_0x00d2:
        r4 = r0 + 1;
        r3[r0] = r6;
        r0 = r14._inputPtr;
        r6 = r14._inputEnd;
        if (r0 >= r6) goto L_0x0175;
    L_0x00dc:
        r0 = r14._inputBuffer;
        r6 = r14._inputPtr;
        r7 = r6 + 1;
        r14._inputPtr = r7;
        r0 = r0[r6];
    L_0x00e6:
        r6 = r2;
        r13 = r0;
        r0 = r4;
        r4 = r13;
    L_0x00ea:
        if (r4 > r12) goto L_0x018a;
    L_0x00ec:
        if (r4 < r11) goto L_0x018a;
    L_0x00ee:
        r6 = r6 + 1;
        r7 = r3.length;
        if (r0 < r7) goto L_0x00fb;
    L_0x00f3:
        r0 = r14._textBuffer;
        r0 = r0.finishCurrentSegment();
        r3 = r0;
        r0 = r2;
    L_0x00fb:
        r7 = r0 + 1;
        r3[r0] = r4;
        r0 = r14._inputPtr;
        r10 = r14._inputEnd;
        if (r0 < r10) goto L_0x017d;
    L_0x0105:
        r0 = r14.loadMore();
        if (r0 != 0) goto L_0x017d;
    L_0x010b:
        r2 = r6;
        r0 = r1;
        r1 = r7;
    L_0x010e:
        if (r2 != 0) goto L_0x0115;
    L_0x0110:
        r3 = "Exponent indicator not followed by a digit";
        r14.reportUnexpectedNumberChar(r4, r3);
    L_0x0115:
        r3 = r1;
        r1 = r4;
    L_0x0117:
        if (r0 != 0) goto L_0x012a;
    L_0x0119:
        r0 = r14._inputPtr;
        r0 = r0 + -1;
        r14._inputPtr = r0;
        r0 = r14._parsingContext;
        r0 = r0.inRoot();
        if (r0 == 0) goto L_0x012a;
    L_0x0127:
        r14._verifyRootSpace(r1);
    L_0x012a:
        r0 = r14._textBuffer;
        r0.setCurrentLength(r3);
        r0 = r14.reset(r15, r9, r8, r2);
        return r0;
    L_0x0134:
        r3 = "No digit following minus sign";
        r3 = r14.getNextChar(r3);
        goto L_0x0023;
    L_0x013c:
        r0 = r14._inputBuffer;
        r4 = r14._inputPtr;
        r7 = r4 + 1;
        r14._inputPtr = r7;
        r4 = r0[r4];
        r0 = r6;
        goto L_0x002d;
    L_0x0149:
        r3 = r14._inputBuffer;
        r6 = r14._inputPtr;
        r8 = r6 + 1;
        r14._inputPtr = r8;
        r3 = r3[r6];
        if (r3 < r11) goto L_0x019d;
    L_0x0155:
        if (r3 <= r12) goto L_0x015b;
    L_0x0157:
        r6 = r3;
        r3 = r7;
        goto L_0x008f;
    L_0x015b:
        r0 = r0 + 1;
        r6 = r5.length;
        if (r4 < r6) goto L_0x019b;
    L_0x0160:
        r4 = r14._textBuffer;
        r5 = r4.finishCurrentSegment();
        r6 = r2;
    L_0x0167:
        r4 = r6 + 1;
        r5[r6] = r3;
        goto L_0x0081;
    L_0x016d:
        r0 = "expected a digit for number exponent";
        r6 = r14.getNextChar(r0);
        goto L_0x00c2;
    L_0x0175:
        r0 = "expected a digit for number exponent";
        r0 = r14.getNextChar(r0);
        goto L_0x00e6;
    L_0x017d:
        r0 = r14._inputBuffer;
        r4 = r14._inputPtr;
        r10 = r4 + 1;
        r14._inputPtr = r10;
        r4 = r0[r4];
        r0 = r7;
        goto L_0x00ea;
    L_0x018a:
        r2 = r6;
        r1 = r0;
        r0 = r5;
        goto L_0x010e;
    L_0x018e:
        r0 = r4;
        goto L_0x00d2;
    L_0x0191:
        r0 = r4;
        r4 = r6;
        r6 = r2;
        goto L_0x00ea;
    L_0x0196:
        r1 = r6;
        r3 = r0;
        r0 = r5;
        goto L_0x0117;
    L_0x019b:
        r6 = r4;
        goto L_0x0167;
    L_0x019d:
        r6 = r3;
        r3 = r7;
        goto L_0x008f;
    L_0x01a1:
        r8 = r2;
        r6 = r0;
        r3 = r4;
        r0 = r5;
        r5 = r7;
        goto L_0x009b;
    L_0x01a8:
        r7 = r2;
        r9 = r5;
        r5 = r0;
        r0 = r4;
        r4 = r3;
        goto L_0x0053;
    L_0x01af:
        r0 = r2;
        goto L_0x0013;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.fasterxml.jackson.core.json.ReaderBasedJsonParser._parseNumber2(boolean):com.fasterxml.jackson.core.JsonToken");
    }

    private char _verifyNoLeadingZeroes() throws IOException {
        if (this._inputPtr >= this._inputEnd && !loadMore()) {
            return '0';
        }
        char c = this._inputBuffer[this._inputPtr];
        if (c < '0' || c > '9') {
            return '0';
        }
        if (!isEnabled(Feature.ALLOW_NUMERIC_LEADING_ZEROS)) {
            reportInvalidNumber("Leading zeroes not allowed");
        }
        this._inputPtr++;
        if (c != '0') {
            return c;
        }
        do {
            if (this._inputPtr >= this._inputEnd && !loadMore()) {
                return c;
            }
            c = this._inputBuffer[this._inputPtr];
            if (c < '0' || c > '9') {
                return '0';
            }
            this._inputPtr++;
        } while (c == '0');
        return c;
    }

    protected JsonToken _handleInvalidNumberStart(int i, boolean z) throws IOException {
        double d = Double.NEGATIVE_INFINITY;
        if (i == 73) {
            if (this._inputPtr >= this._inputEnd && !loadMore()) {
                _reportInvalidEOFInValue();
            }
            char[] cArr = this._inputBuffer;
            int i2 = this._inputPtr;
            this._inputPtr = i2 + 1;
            i = cArr[i2];
            String str;
            if (i == 78) {
                str = z ? "-INF" : "+INF";
                _matchToken(str, 3);
                if (isEnabled(Feature.ALLOW_NON_NUMERIC_NUMBERS)) {
                    if (!z) {
                        d = Double.POSITIVE_INFINITY;
                    }
                    return resetAsNaN(str, d);
                }
                _reportError("Non-standard token '" + str + "': enable JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS to allow");
            } else if (i == 110) {
                str = z ? "-Infinity" : "+Infinity";
                _matchToken(str, 3);
                if (isEnabled(Feature.ALLOW_NON_NUMERIC_NUMBERS)) {
                    if (!z) {
                        d = Double.POSITIVE_INFINITY;
                    }
                    return resetAsNaN(str, d);
                }
                _reportError("Non-standard token '" + str + "': enable JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS to allow");
            }
        }
        reportUnexpectedNumberChar(i, "expected digit (0-9) to follow minus sign, for valid numeric value");
        return null;
    }

    private final void _verifyRootSpace(int i) throws IOException {
        this._inputPtr++;
        switch (i) {
            case 9:
            case 32:
                return;
            case 10:
                this._currInputRow++;
                this._currInputRowStart = this._inputPtr;
                return;
            case 13:
                _skipCR();
                return;
            default:
                _reportMissingRootWS(i);
                return;
        }
    }

    protected String _parseName(int i) throws IOException {
        if (i != 34) {
            return _handleOddName(i);
        }
        int i2 = this._inputPtr;
        int i3 = this._hashSeed;
        int i4 = this._inputEnd;
        if (i2 < i4) {
            int[] iArr = _icLatin1;
            char length = iArr.length;
            do {
                char c = this._inputBuffer[i2];
                if (c >= length || iArr[c] == 0) {
                    i3 = (i3 * 33) + c;
                    i2++;
                } else if (c == '\"') {
                    i4 = this._inputPtr;
                    this._inputPtr = i2 + 1;
                    return this._symbols.findSymbol(this._inputBuffer, i4, i2 - i4, i3);
                }
            } while (i2 < i4);
        }
        i4 = this._inputPtr;
        this._inputPtr = i2;
        return _parseName2(i4, i3, 34);
    }

    private String _parseName2(int i, int i2, int i3) throws IOException {
        this._textBuffer.resetWithShared(this._inputBuffer, i, this._inputPtr - i);
        char[] currentSegment = this._textBuffer.getCurrentSegment();
        int currentSegmentSize = this._textBuffer.getCurrentSegmentSize();
        while (true) {
            char _decodeEscaped;
            if (this._inputPtr >= this._inputEnd && !loadMore()) {
                _reportInvalidEOF(": was expecting closing '" + ((char) i3) + "' for name");
            }
            char[] cArr = this._inputBuffer;
            int i4 = this._inputPtr;
            this._inputPtr = i4 + 1;
            char c = cArr[i4];
            if (c <= '\\') {
                if (c == '\\') {
                    _decodeEscaped = _decodeEscaped();
                    i2 = (i2 * 33) + c;
                    i4 = currentSegmentSize + 1;
                    currentSegment[currentSegmentSize] = _decodeEscaped;
                    if (i4 < currentSegment.length) {
                        currentSegment = this._textBuffer.finishCurrentSegment();
                        currentSegmentSize = 0;
                    } else {
                        currentSegmentSize = i4;
                    }
                } else if (c <= i3) {
                    if (c == i3) {
                        this._textBuffer.setCurrentLength(currentSegmentSize);
                        TextBuffer textBuffer = this._textBuffer;
                        return this._symbols.findSymbol(textBuffer.getTextBuffer(), textBuffer.getTextOffset(), textBuffer.size(), i2);
                    } else if (c < ' ') {
                        _throwUnquotedSpace(c, SettingsActivity.NAME);
                    }
                }
            }
            _decodeEscaped = c;
            i2 = (i2 * 33) + c;
            i4 = currentSegmentSize + 1;
            currentSegment[currentSegmentSize] = _decodeEscaped;
            if (i4 < currentSegment.length) {
                currentSegmentSize = i4;
            } else {
                currentSegment = this._textBuffer.finishCurrentSegment();
                currentSegmentSize = 0;
            }
        }
    }

    protected String _handleOddName(int i) throws IOException {
        if (i == 39 && isEnabled(Feature.ALLOW_SINGLE_QUOTES)) {
            return _parseAposName();
        }
        if (!isEnabled(Feature.ALLOW_UNQUOTED_FIELD_NAMES)) {
            _reportUnexpectedChar(i, "was expecting double-quote to start field name");
        }
        int[] inputCodeLatin1JsNames = CharTypes.getInputCodeLatin1JsNames();
        char length = inputCodeLatin1JsNames.length;
        boolean isJavaIdentifierPart = i < length ? inputCodeLatin1JsNames[i] == 0 : Character.isJavaIdentifierPart((char) i);
        if (!isJavaIdentifierPart) {
            _reportUnexpectedChar(i, "was expecting either valid name character (for unquoted name) or double-quote (for quoted) to start field name");
        }
        int i2 = this._inputPtr;
        int i3 = this._hashSeed;
        int i4 = this._inputEnd;
        if (i2 < i4) {
            do {
                char c = this._inputBuffer[i2];
                int i5;
                if (c < length) {
                    if (inputCodeLatin1JsNames[c] != 0) {
                        i5 = this._inputPtr - 1;
                        this._inputPtr = i2;
                        return this._symbols.findSymbol(this._inputBuffer, i5, i2 - i5, i3);
                    }
                } else if (!Character.isJavaIdentifierPart((char) c)) {
                    i5 = this._inputPtr - 1;
                    this._inputPtr = i2;
                    return this._symbols.findSymbol(this._inputBuffer, i5, i2 - i5, i3);
                }
                i3 = (i3 * 33) + c;
                i2++;
            } while (i2 < i4);
        }
        int i6 = this._inputPtr - 1;
        this._inputPtr = i2;
        return _handleOddName2(i6, i3, inputCodeLatin1JsNames);
    }

    protected String _parseAposName() throws IOException {
        int i = this._inputPtr;
        int i2 = this._hashSeed;
        int i3 = this._inputEnd;
        if (i < i3) {
            int[] iArr = _icLatin1;
            char length = iArr.length;
            do {
                char c = this._inputBuffer[i];
                if (c != '\'') {
                    if (c < length && iArr[c] != 0) {
                        break;
                    }
                    i2 = (i2 * 33) + c;
                    i++;
                } else {
                    i3 = this._inputPtr;
                    this._inputPtr = i + 1;
                    return this._symbols.findSymbol(this._inputBuffer, i3, i - i3, i2);
                }
            } while (i < i3);
        }
        i3 = this._inputPtr;
        this._inputPtr = i;
        return _parseName2(i3, i2, 39);
    }

    protected JsonToken _handleOddValue(int i) throws IOException {
        switch (i) {
            case MotionEventCompat.AXIS_GENERIC_8 /*39*/:
                if (isEnabled(Feature.ALLOW_SINGLE_QUOTES)) {
                    return _handleApos();
                }
                break;
            case MotionEventCompat.AXIS_GENERIC_12 /*43*/:
                if (this._inputPtr >= this._inputEnd && !loadMore()) {
                    _reportInvalidEOFInValue();
                }
                char[] cArr = this._inputBuffer;
                int i2 = this._inputPtr;
                this._inputPtr = i2 + 1;
                return _handleInvalidNumberStart(cArr[i2], false);
            case 73:
                _matchToken("Infinity", 1);
                if (!isEnabled(Feature.ALLOW_NON_NUMERIC_NUMBERS)) {
                    _reportError("Non-standard token 'Infinity': enable JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS to allow");
                    break;
                }
                return resetAsNaN("Infinity", Double.POSITIVE_INFINITY);
            case 78:
                _matchToken("NaN", 1);
                if (!isEnabled(Feature.ALLOW_NON_NUMERIC_NUMBERS)) {
                    _reportError("Non-standard token 'NaN': enable JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS to allow");
                    break;
                }
                return resetAsNaN("NaN", Double.NaN);
        }
        if (Character.isJavaIdentifierStart(i)) {
            _reportInvalidToken("" + ((char) i), "('true', 'false' or 'null')");
        }
        _reportUnexpectedChar(i, "expected a valid value (number, String, array, object, 'true', 'false' or 'null')");
        return null;
    }

    protected JsonToken _handleApos() throws IOException {
        char[] emptyAndGetCurrentSegment = this._textBuffer.emptyAndGetCurrentSegment();
        int currentSegmentSize = this._textBuffer.getCurrentSegmentSize();
        while (true) {
            if (this._inputPtr >= this._inputEnd && !loadMore()) {
                _reportInvalidEOF(": was expecting closing quote for a string value");
            }
            char[] cArr = this._inputBuffer;
            int i = this._inputPtr;
            this._inputPtr = i + 1;
            char c = cArr[i];
            if (c <= '\\') {
                if (c == '\\') {
                    c = _decodeEscaped();
                } else if (c <= '\'') {
                    if (c == '\'') {
                        this._textBuffer.setCurrentLength(currentSegmentSize);
                        return JsonToken.VALUE_STRING;
                    } else if (c < ' ') {
                        _throwUnquotedSpace(c, "string value");
                    }
                }
            }
            if (currentSegmentSize >= emptyAndGetCurrentSegment.length) {
                emptyAndGetCurrentSegment = this._textBuffer.finishCurrentSegment();
                i = 0;
            } else {
                i = currentSegmentSize;
            }
            currentSegmentSize = i + 1;
            emptyAndGetCurrentSegment[i] = c;
        }
    }

    private String _handleOddName2(int i, int i2, int[] iArr) throws IOException {
        this._textBuffer.resetWithShared(this._inputBuffer, i, this._inputPtr - i);
        char[] currentSegment = this._textBuffer.getCurrentSegment();
        int currentSegmentSize = this._textBuffer.getCurrentSegmentSize();
        char length = iArr.length;
        while (true) {
            if (this._inputPtr >= this._inputEnd && !loadMore()) {
                break;
            }
            char c = this._inputBuffer[this._inputPtr];
            if (c > length) {
                if (!Character.isJavaIdentifierPart(c)) {
                    break;
                }
            } else if (iArr[c] != 0) {
                break;
            }
            this._inputPtr++;
            i2 = (i2 * 33) + c;
            int i3 = currentSegmentSize + 1;
            currentSegment[currentSegmentSize] = c;
            if (i3 >= currentSegment.length) {
                currentSegment = this._textBuffer.finishCurrentSegment();
                currentSegmentSize = 0;
            } else {
                currentSegmentSize = i3;
            }
        }
        this._textBuffer.setCurrentLength(currentSegmentSize);
        TextBuffer textBuffer = this._textBuffer;
        return this._symbols.findSymbol(textBuffer.getTextBuffer(), textBuffer.getTextOffset(), textBuffer.size(), i2);
    }

    protected void _finishString() throws IOException {
        int i = this._inputPtr;
        int i2 = this._inputEnd;
        if (i < i2) {
            int[] iArr = _icLatin1;
            char length = iArr.length;
            do {
                char c = this._inputBuffer[i];
                if (c >= length || iArr[c] == 0) {
                    i++;
                } else if (c == '\"') {
                    this._textBuffer.resetWithShared(this._inputBuffer, this._inputPtr, i - this._inputPtr);
                    this._inputPtr = i + 1;
                    return;
                }
            } while (i < i2);
        }
        this._textBuffer.resetWithCopy(this._inputBuffer, this._inputPtr, i - this._inputPtr);
        this._inputPtr = i;
        _finishString2();
    }

    protected void _finishString2() throws IOException {
        char[] currentSegment = this._textBuffer.getCurrentSegment();
        int currentSegmentSize = this._textBuffer.getCurrentSegmentSize();
        while (true) {
            if (this._inputPtr >= this._inputEnd && !loadMore()) {
                _reportInvalidEOF(": was expecting closing quote for a string value");
            }
            char[] cArr = this._inputBuffer;
            int i = this._inputPtr;
            this._inputPtr = i + 1;
            char c = cArr[i];
            if (c <= '\\') {
                if (c == '\\') {
                    c = _decodeEscaped();
                } else if (c <= '\"') {
                    if (c == '\"') {
                        this._textBuffer.setCurrentLength(currentSegmentSize);
                        return;
                    } else if (c < ' ') {
                        _throwUnquotedSpace(c, "string value");
                    }
                }
            }
            if (currentSegmentSize >= currentSegment.length) {
                currentSegment = this._textBuffer.finishCurrentSegment();
                i = 0;
            } else {
                i = currentSegmentSize;
            }
            currentSegmentSize = i + 1;
            currentSegment[i] = c;
        }
    }

    protected void _skipString() throws IOException {
        this._tokenIncomplete = false;
        int i = this._inputPtr;
        int i2 = this._inputEnd;
        char[] cArr = this._inputBuffer;
        while (true) {
            if (i >= i2) {
                this._inputPtr = i;
                if (!loadMore()) {
                    _reportInvalidEOF(": was expecting closing quote for a string value");
                }
                i = this._inputPtr;
                i2 = this._inputEnd;
            }
            int i3 = i + 1;
            char c = cArr[i];
            if (c <= '\\') {
                if (c == '\\') {
                    this._inputPtr = i3;
                    _decodeEscaped();
                    i = this._inputPtr;
                    i2 = this._inputEnd;
                } else if (c <= '\"') {
                    if (c == '\"') {
                        this._inputPtr = i3;
                        return;
                    } else if (c < ' ') {
                        this._inputPtr = i3;
                        _throwUnquotedSpace(c, "string value");
                    }
                }
            }
            i = i3;
        }
    }

    protected void _skipCR() throws IOException {
        if ((this._inputPtr < this._inputEnd || loadMore()) && this._inputBuffer[this._inputPtr] == '\n') {
            this._inputPtr++;
        }
        this._currInputRow++;
        this._currInputRowStart = this._inputPtr;
    }

    private int _skipWS() throws IOException {
        int[] iArr = _icWS;
        while (true) {
            if (this._inputPtr < this._inputEnd || loadMore()) {
                char[] cArr = this._inputBuffer;
                int i = this._inputPtr;
                this._inputPtr = i + 1;
                char c = cArr[i];
                if (c < '@') {
                    switch (iArr[c]) {
                        case -1:
                            _throwInvalidSpace(c);
                            break;
                        case 0:
                            break;
                        case 1:
                            break;
                        case 10:
                            this._currInputRow++;
                            this._currInputRowStart = this._inputPtr;
                            continue;
                        case 13:
                            _skipCR();
                            continue;
                        case MotionEventCompat.AXIS_GENERIC_4 /*35*/:
                            if (!_skipYAMLComment()) {
                                break;
                            }
                            continue;
                        case MotionEventCompat.AXIS_GENERIC_16 /*47*/:
                            _skipComment();
                            continue;
                        default:
                            continue;
                    }
                }
                return c;
            }
            throw _constructError("Unexpected end-of-input within/between " + this._parsingContext.getTypeDesc() + " entries");
        }
    }

    private int _skipWSOrEnd() throws IOException {
        int[] iArr = _icWS;
        while (true) {
            if (this._inputPtr < this._inputEnd || loadMore()) {
                char[] cArr = this._inputBuffer;
                int i = this._inputPtr;
                this._inputPtr = i + 1;
                int i2 = cArr[i];
                if (i2 < 64) {
                    switch (iArr[i2]) {
                        case -1:
                            _throwInvalidSpace(i2);
                            return i2;
                        case 0:
                            return i2;
                        case 1:
                            break;
                        case 10:
                            this._currInputRow++;
                            this._currInputRowStart = this._inputPtr;
                            break;
                        case 13:
                            _skipCR();
                            break;
                        case MotionEventCompat.AXIS_GENERIC_4 /*35*/:
                            if (_skipYAMLComment()) {
                                break;
                            }
                            return i2;
                        case MotionEventCompat.AXIS_GENERIC_16 /*47*/:
                            _skipComment();
                            break;
                        default:
                            break;
                    }
                }
                return i2;
            }
            _handleEOF();
            return -1;
        }
    }

    private void _skipComment() throws IOException {
        if (!isEnabled(Feature.ALLOW_COMMENTS)) {
            _reportUnexpectedChar(47, "maybe a (non-standard) comment? (not recognized as one since Feature 'ALLOW_COMMENTS' not enabled for parser)");
        }
        if (this._inputPtr >= this._inputEnd && !loadMore()) {
            _reportInvalidEOF(" in a comment");
        }
        char[] cArr = this._inputBuffer;
        int i = this._inputPtr;
        this._inputPtr = i + 1;
        char c = cArr[i];
        if (c == '/') {
            _skipLine();
        } else if (c == '*') {
            _skipCComment();
        } else {
            _reportUnexpectedChar(c, "was expecting either '*' or '/' for a comment");
        }
    }

    private void _skipCComment() throws IOException {
        while (true) {
            if (this._inputPtr >= this._inputEnd && !loadMore()) {
                break;
            }
            char[] cArr = this._inputBuffer;
            int i = this._inputPtr;
            this._inputPtr = i + 1;
            char c = cArr[i];
            if (c <= '*') {
                if (c == '*') {
                    if (this._inputPtr >= this._inputEnd && !loadMore()) {
                        break;
                    } else if (this._inputBuffer[this._inputPtr] == '/') {
                        this._inputPtr++;
                        return;
                    }
                } else if (c < ' ') {
                    if (c == '\n') {
                        this._currInputRow++;
                        this._currInputRowStart = this._inputPtr;
                    } else if (c == '\r') {
                        _skipCR();
                    } else if (c != '\t') {
                        _throwInvalidSpace(c);
                    }
                }
            }
        }
        _reportInvalidEOF(" in a comment");
    }

    private boolean _skipYAMLComment() throws IOException {
        if (!isEnabled(Feature.ALLOW_YAML_COMMENTS)) {
            return false;
        }
        _skipLine();
        return true;
    }

    private void _skipLine() throws IOException {
        while (true) {
            if (this._inputPtr < this._inputEnd || loadMore()) {
                char[] cArr = this._inputBuffer;
                int i = this._inputPtr;
                this._inputPtr = i + 1;
                char c = cArr[i];
                if (c < ' ') {
                    if (c == '\n') {
                        this._currInputRow++;
                        this._currInputRowStart = this._inputPtr;
                        return;
                    } else if (c == '\r') {
                        _skipCR();
                        return;
                    } else if (c != '\t') {
                        _throwInvalidSpace(c);
                    }
                }
            } else {
                return;
            }
        }
    }

    protected char _decodeEscaped() throws IOException {
        int i = 0;
        if (this._inputPtr >= this._inputEnd && !loadMore()) {
            _reportInvalidEOF(" in character escape sequence");
        }
        char[] cArr = this._inputBuffer;
        int i2 = this._inputPtr;
        this._inputPtr = i2 + 1;
        char c = cArr[i2];
        switch (c) {
            case MotionEventCompat.AXIS_GENERIC_3 /*34*/:
            case MotionEventCompat.AXIS_GENERIC_16 /*47*/:
            case '\\':
                return c;
            case 'b':
                return '\b';
            case 'f':
                return '\f';
            case 'n':
                return '\n';
            case 'r':
                return '\r';
            case 't':
                return '\t';
            case 'u':
                for (int i3 = 0; i3 < 4; i3++) {
                    if (this._inputPtr >= this._inputEnd && !loadMore()) {
                        _reportInvalidEOF(" in character escape sequence");
                    }
                    char[] cArr2 = this._inputBuffer;
                    int i4 = this._inputPtr;
                    this._inputPtr = i4 + 1;
                    char c2 = cArr2[i4];
                    i4 = CharTypes.charToHex(c2);
                    if (i4 < 0) {
                        _reportUnexpectedChar(c2, "expected a hex-digit for character escape sequence");
                    }
                    i = (i << 4) | i4;
                }
                return (char) i;
            default:
                return _handleUnrecognizedCharacterEscape(c);
        }
    }

    protected void _matchToken(String str, int i) throws IOException {
        int length = str.length();
        do {
            if (this._inputPtr >= this._inputEnd && !loadMore()) {
                _reportInvalidToken(str.substring(0, i));
            }
            if (this._inputBuffer[this._inputPtr] != str.charAt(i)) {
                _reportInvalidToken(str.substring(0, i));
            }
            this._inputPtr++;
            i++;
        } while (i < length);
        if (this._inputPtr < this._inputEnd || loadMore()) {
            char c = this._inputBuffer[this._inputPtr];
            if (c >= '0' && c != ']' && c != '}' && Character.isJavaIdentifierPart(c)) {
                _reportInvalidToken(str.substring(0, i));
            }
        }
    }

    protected byte[] _decodeBase64(Base64Variant base64Variant) throws IOException {
        ByteArrayBuilder _getByteArrayBuilder = _getByteArrayBuilder();
        while (true) {
            if (this._inputPtr >= this._inputEnd) {
                loadMoreGuaranteed();
            }
            char[] cArr = this._inputBuffer;
            int i = this._inputPtr;
            this._inputPtr = i + 1;
            char c = cArr[i];
            if (c > ' ') {
                int decodeBase64Char = base64Variant.decodeBase64Char(c);
                if (decodeBase64Char < 0) {
                    if (c == '\"') {
                        return _getByteArrayBuilder.toByteArray();
                    }
                    decodeBase64Char = _decodeBase64Escape(base64Variant, c, 0);
                    if (decodeBase64Char < 0) {
                        continue;
                    }
                }
                if (this._inputPtr >= this._inputEnd) {
                    loadMoreGuaranteed();
                }
                char[] cArr2 = this._inputBuffer;
                int i2 = this._inputPtr;
                this._inputPtr = i2 + 1;
                char c2 = cArr2[i2];
                i = base64Variant.decodeBase64Char(c2);
                if (i < 0) {
                    i = _decodeBase64Escape(base64Variant, c2, 1);
                }
                i |= decodeBase64Char << 6;
                if (this._inputPtr >= this._inputEnd) {
                    loadMoreGuaranteed();
                }
                cArr = this._inputBuffer;
                i2 = this._inputPtr;
                this._inputPtr = i2 + 1;
                c2 = cArr[i2];
                decodeBase64Char = base64Variant.decodeBase64Char(c2);
                if (decodeBase64Char < 0) {
                    if (decodeBase64Char != -2) {
                        if (c2 != '\"' || base64Variant.usesPadding()) {
                            decodeBase64Char = _decodeBase64Escape(base64Variant, c2, 2);
                        } else {
                            _getByteArrayBuilder.append(i >> 4);
                            return _getByteArrayBuilder.toByteArray();
                        }
                    }
                    if (decodeBase64Char == -2) {
                        if (this._inputPtr >= this._inputEnd) {
                            loadMoreGuaranteed();
                        }
                        cArr = this._inputBuffer;
                        i2 = this._inputPtr;
                        this._inputPtr = i2 + 1;
                        char c3 = cArr[i2];
                        if (base64Variant.usesPaddingChar(c3)) {
                            _getByteArrayBuilder.append(i >> 4);
                        } else {
                            throw reportInvalidBase64Char(base64Variant, c3, 3, "expected padding character '" + base64Variant.getPaddingChar() + "'");
                        }
                    }
                }
                i = (i << 6) | decodeBase64Char;
                if (this._inputPtr >= this._inputEnd) {
                    loadMoreGuaranteed();
                }
                cArr = this._inputBuffer;
                i2 = this._inputPtr;
                this._inputPtr = i2 + 1;
                c2 = cArr[i2];
                decodeBase64Char = base64Variant.decodeBase64Char(c2);
                if (decodeBase64Char < 0) {
                    if (decodeBase64Char != -2) {
                        if (c2 != '\"' || base64Variant.usesPadding()) {
                            decodeBase64Char = _decodeBase64Escape(base64Variant, c2, 3);
                        } else {
                            _getByteArrayBuilder.appendTwoBytes(i >> 2);
                            return _getByteArrayBuilder.toByteArray();
                        }
                    }
                    if (decodeBase64Char == -2) {
                        _getByteArrayBuilder.appendTwoBytes(i >> 2);
                    }
                }
                _getByteArrayBuilder.appendThreeBytes(decodeBase64Char | (i << 6));
            }
        }
    }

    protected void _reportInvalidToken(String str) throws IOException {
        _reportInvalidToken(str, "'null', 'true', 'false' or NaN");
    }

    protected void _reportInvalidToken(String str, String str2) throws IOException {
        StringBuilder stringBuilder = new StringBuilder(str);
        while (true) {
            if (this._inputPtr >= this._inputEnd && !loadMore()) {
                break;
            }
            char c = this._inputBuffer[this._inputPtr];
            if (!Character.isJavaIdentifierPart(c)) {
                break;
            }
            this._inputPtr++;
            stringBuilder.append(c);
        }
        _reportError("Unrecognized token '" + stringBuilder.toString() + "': was expecting " + str2);
    }
}
