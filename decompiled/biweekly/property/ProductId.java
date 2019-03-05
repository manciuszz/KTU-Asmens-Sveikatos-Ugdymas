package biweekly.property;

import biweekly.Biweekly;

public class ProductId extends TextProperty {
    public ProductId(String value) {
        super(value);
    }

    public static ProductId biweekly() {
        return new ProductId("-//Michael Angstadt//biweekly " + Biweekly.VERSION + "//EN");
    }
}
