package biweekly.util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class ICalFloatFormatter extends DecimalFormat {
    public ICalFloatFormatter() {
        this(6);
    }

    public ICalFloatFormatter(int decimals) {
        setMaximumFractionDigits(decimals);
        if (decimals > 0) {
            setMinimumFractionDigits(1);
        }
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator('.');
        symbols.setMinusSign('-');
        setDecimalFormatSymbols(symbols);
    }
}
