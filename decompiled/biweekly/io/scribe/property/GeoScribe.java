package biweekly.io.scribe.property;

import biweekly.ICalDataType;
import biweekly.Warning;
import biweekly.io.CannotParseException;
import biweekly.io.json.JCalValue;
import biweekly.io.xml.XCalElement;
import biweekly.parameter.ICalParameters;
import biweekly.property.Geo;
import biweekly.util.ICalFloatFormatter;
import java.util.List;

public class GeoScribe extends ICalPropertyScribe<Geo> {
    public GeoScribe() {
        super(Geo.class, "GEO", ICalDataType.FLOAT);
    }

    protected String _writeText(Geo property) {
        ICalFloatFormatter formatter = new ICalFloatFormatter();
        StringBuilder sb = new StringBuilder();
        Double latitude = property.getLatitude();
        if (latitude == null) {
            latitude = Double.valueOf(0.0d);
        }
        sb.append(formatter.format(latitude));
        sb.append(';');
        Double longitude = property.getLongitude();
        if (longitude == null) {
            longitude = Double.valueOf(0.0d);
        }
        sb.append(formatter.format(longitude));
        return sb.toString();
    }

    protected Geo _parseText(String value, ICalDataType dataType, ICalParameters parameters, List<Warning> list) {
        SemiStructuredIterator it = ICalPropertyScribe.semistructured(value, true);
        String latitudeStr = it.next();
        String longitudeStr = it.next();
        if (latitudeStr != null && longitudeStr != null) {
            return parse(latitudeStr, longitudeStr);
        }
        throw new CannotParseException(20, new Object[0]);
    }

    protected void _writeXml(Geo property, XCalElement element) {
        ICalFloatFormatter formatter = new ICalFloatFormatter();
        Double latitude = property.getLatitude();
        if (latitude == null) {
            latitude = Double.valueOf(0.0d);
        }
        element.append("latitude", formatter.format(latitude));
        Double longitude = property.getLongitude();
        if (longitude == null) {
            longitude = Double.valueOf(0.0d);
        }
        element.append("longitude", formatter.format(longitude));
    }

    protected Geo _parseXml(XCalElement element, ICalParameters parameters, List<Warning> list) {
        String latitudeStr = element.first("latitude");
        String longitudeStr = element.first("longitude");
        if (latitudeStr == null && longitudeStr == null) {
            throw ICalPropertyScribe.missingXmlElements("latitude", "longitude");
        } else if (latitudeStr == null) {
            throw ICalPropertyScribe.missingXmlElements("latitude");
        } else if (longitudeStr != null) {
            return parse(latitudeStr, longitudeStr);
        } else {
            throw ICalPropertyScribe.missingXmlElements("longitude");
        }
    }

    protected JCalValue _writeJson(Geo property) {
        Double latitude = property.getLatitude();
        if (latitude == null) {
            latitude = Double.valueOf(0.0d);
        }
        Double longitude = property.getLongitude();
        if (longitude == null) {
            longitude = Double.valueOf(0.0d);
        }
        return JCalValue.structured(latitude, longitude);
    }

    protected Geo _parseJson(JCalValue value, ICalDataType dataType, ICalParameters parameters, List<Warning> list) {
        StructuredIterator it = ICalPropertyScribe.structured(value);
        return parse(it.nextString(), it.nextString());
    }

    private Geo parse(String latitudeStr, String longitudeStr) {
        Double latitude = null;
        if (latitudeStr != null) {
            try {
                latitude = Double.valueOf(latitudeStr);
            } catch (NumberFormatException e) {
                throw new CannotParseException(21, latitudeStr);
            }
        }
        Double longitude = null;
        if (longitudeStr != null) {
            try {
                longitude = Double.valueOf(longitudeStr);
            } catch (NumberFormatException e2) {
                throw new CannotParseException(22, longitudeStr);
            }
        }
        return new Geo(latitude, longitude);
    }
}
