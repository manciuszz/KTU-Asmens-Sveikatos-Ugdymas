package com.fasterxml.jackson.core.util;

import com.fasterxml.jackson.core.Base64Variant;
import com.fasterxml.jackson.core.FormatSchema;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.core.JsonParser.NumberType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonStreamContext;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.type.TypeReference;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Iterator;

public class JsonParserDelegate extends JsonParser {
    protected JsonParser delegate;

    public JsonParserDelegate(JsonParser jsonParser) {
        this.delegate = jsonParser;
    }

    public void setCodec(ObjectCodec objectCodec) {
        this.delegate.setCodec(objectCodec);
    }

    public ObjectCodec getCodec() {
        return this.delegate.getCodec();
    }

    public JsonParser enable(Feature feature) {
        this.delegate.enable(feature);
        return this;
    }

    public JsonParser disable(Feature feature) {
        this.delegate.disable(feature);
        return this;
    }

    public boolean isEnabled(Feature feature) {
        return this.delegate.isEnabled(feature);
    }

    public JsonParser configure(Feature feature, boolean z) {
        return this.delegate.configure(feature, z);
    }

    public int getFeatureMask() {
        return this.delegate.getFeatureMask();
    }

    public JsonParser setFeatureMask(int i) {
        this.delegate.setFeatureMask(i);
        return this;
    }

    public FormatSchema getSchema() {
        return this.delegate.getSchema();
    }

    public void setSchema(FormatSchema formatSchema) {
        this.delegate.setSchema(formatSchema);
    }

    public boolean canUseSchema(FormatSchema formatSchema) {
        return this.delegate.canUseSchema(formatSchema);
    }

    public Version version() {
        return this.delegate.version();
    }

    public Object getInputSource() {
        return this.delegate.getInputSource();
    }

    public boolean requiresCustomCodec() {
        return this.delegate.requiresCustomCodec();
    }

    public void close() throws IOException {
        this.delegate.close();
    }

    public boolean isClosed() {
        return this.delegate.isClosed();
    }

    public JsonToken getCurrentToken() {
        return this.delegate.getCurrentToken();
    }

    public int getCurrentTokenId() {
        return this.delegate.getCurrentTokenId();
    }

    public boolean hasCurrentToken() {
        return this.delegate.hasCurrentToken();
    }

    public String getCurrentName() throws IOException, JsonParseException {
        return this.delegate.getCurrentName();
    }

    public JsonLocation getCurrentLocation() {
        return this.delegate.getCurrentLocation();
    }

    public JsonStreamContext getParsingContext() {
        return this.delegate.getParsingContext();
    }

    public boolean isExpectedStartArrayToken() {
        return this.delegate.isExpectedStartArrayToken();
    }

    public void clearCurrentToken() {
        this.delegate.clearCurrentToken();
    }

    public JsonToken getLastClearedToken() {
        return this.delegate.getLastClearedToken();
    }

    public void overrideCurrentName(String str) {
        this.delegate.overrideCurrentName(str);
    }

    public String getText() throws IOException, JsonParseException {
        return this.delegate.getText();
    }

    public boolean hasTextCharacters() {
        return this.delegate.hasTextCharacters();
    }

    public char[] getTextCharacters() throws IOException, JsonParseException {
        return this.delegate.getTextCharacters();
    }

    public int getTextLength() throws IOException, JsonParseException {
        return this.delegate.getTextLength();
    }

    public int getTextOffset() throws IOException, JsonParseException {
        return this.delegate.getTextOffset();
    }

    public String nextTextValue() throws IOException, JsonParseException {
        return this.delegate.nextTextValue();
    }

    public BigInteger getBigIntegerValue() throws IOException, JsonParseException {
        return this.delegate.getBigIntegerValue();
    }

    public boolean getBooleanValue() throws IOException, JsonParseException {
        return this.delegate.getBooleanValue();
    }

    public byte getByteValue() throws IOException, JsonParseException {
        return this.delegate.getByteValue();
    }

    public short getShortValue() throws IOException, JsonParseException {
        return this.delegate.getShortValue();
    }

    public BigDecimal getDecimalValue() throws IOException, JsonParseException {
        return this.delegate.getDecimalValue();
    }

    public double getDoubleValue() throws IOException, JsonParseException {
        return this.delegate.getDoubleValue();
    }

    public float getFloatValue() throws IOException, JsonParseException {
        return this.delegate.getFloatValue();
    }

    public int getIntValue() throws IOException, JsonParseException {
        return this.delegate.getIntValue();
    }

    public long getLongValue() throws IOException, JsonParseException {
        return this.delegate.getLongValue();
    }

    public NumberType getNumberType() throws IOException, JsonParseException {
        return this.delegate.getNumberType();
    }

    public Number getNumberValue() throws IOException, JsonParseException {
        return this.delegate.getNumberValue();
    }

    public Boolean nextBooleanValue() throws IOException, JsonParseException {
        return this.delegate.nextBooleanValue();
    }

    public int nextIntValue(int i) throws IOException, JsonParseException {
        return this.delegate.nextIntValue(i);
    }

    public long nextLongValue(long j) throws IOException, JsonParseException {
        return this.delegate.nextLongValue(j);
    }

    public int getValueAsInt() throws IOException, JsonParseException {
        return this.delegate.getValueAsInt();
    }

    public int getValueAsInt(int i) throws IOException, JsonParseException {
        return this.delegate.getValueAsInt(i);
    }

    public long getValueAsLong() throws IOException, JsonParseException {
        return this.delegate.getValueAsLong();
    }

    public long getValueAsLong(long j) throws IOException, JsonParseException {
        return this.delegate.getValueAsLong(j);
    }

    public double getValueAsDouble() throws IOException, JsonParseException {
        return this.delegate.getValueAsDouble();
    }

    public double getValueAsDouble(double d) throws IOException, JsonParseException {
        return this.delegate.getValueAsDouble(d);
    }

    public boolean getValueAsBoolean() throws IOException, JsonParseException {
        return this.delegate.getValueAsBoolean();
    }

    public boolean getValueAsBoolean(boolean z) throws IOException, JsonParseException {
        return this.delegate.getValueAsBoolean(z);
    }

    public String getValueAsString() throws IOException, JsonParseException {
        return this.delegate.getValueAsString();
    }

    public String getValueAsString(String str) throws IOException, JsonParseException {
        return this.delegate.getValueAsString(str);
    }

    public <T> T readValueAs(Class<T> cls) throws IOException, JsonProcessingException {
        return this.delegate.readValueAs((Class) cls);
    }

    public <T> T readValueAs(TypeReference<?> typeReference) throws IOException, JsonProcessingException {
        return this.delegate.readValueAs((TypeReference) typeReference);
    }

    public <T> Iterator<T> readValuesAs(Class<T> cls) throws IOException, JsonProcessingException {
        return this.delegate.readValuesAs((Class) cls);
    }

    public <T> Iterator<T> readValuesAs(TypeReference<?> typeReference) throws IOException, JsonProcessingException {
        return this.delegate.readValuesAs((TypeReference) typeReference);
    }

    public <T extends TreeNode> T readValueAsTree() throws IOException, JsonProcessingException {
        return this.delegate.readValueAsTree();
    }

    public Object getEmbeddedObject() throws IOException, JsonParseException {
        return this.delegate.getEmbeddedObject();
    }

    public byte[] getBinaryValue(Base64Variant base64Variant) throws IOException, JsonParseException {
        return this.delegate.getBinaryValue(base64Variant);
    }

    public byte[] getBinaryValue() throws IOException, JsonParseException {
        return this.delegate.getBinaryValue();
    }

    public int readBinaryValue(Base64Variant base64Variant, OutputStream outputStream) throws IOException, JsonParseException {
        return this.delegate.readBinaryValue(base64Variant, outputStream);
    }

    public int readBinaryValue(OutputStream outputStream) throws IOException, JsonParseException {
        return this.delegate.readBinaryValue(outputStream);
    }

    public JsonLocation getTokenLocation() {
        return this.delegate.getTokenLocation();
    }

    public JsonToken nextToken() throws IOException, JsonParseException {
        return this.delegate.nextToken();
    }

    public JsonToken nextValue() throws IOException, JsonParseException {
        return this.delegate.nextValue();
    }

    public boolean nextFieldName(SerializableString serializableString) throws IOException, JsonParseException {
        return this.delegate.nextFieldName(serializableString);
    }

    public JsonParser skipChildren() throws IOException, JsonParseException {
        this.delegate.skipChildren();
        return this;
    }

    public int releaseBuffered(OutputStream outputStream) throws IOException {
        return this.delegate.releaseBuffered(outputStream);
    }

    public int releaseBuffered(Writer writer) throws IOException {
        return this.delegate.releaseBuffered(writer);
    }

    public boolean canReadObjectId() {
        return this.delegate.canReadObjectId();
    }

    public boolean canReadTypeId() {
        return this.delegate.canReadTypeId();
    }

    public Object getObjectId() throws IOException, JsonGenerationException {
        return this.delegate.getObjectId();
    }

    public Object getTypeId() throws IOException, JsonGenerationException {
        return this.delegate.getTypeId();
    }
}
