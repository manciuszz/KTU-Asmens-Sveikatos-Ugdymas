package biweekly.io.scribe.property;

import biweekly.property.ProductId;

public class ProductIdScribe extends TextPropertyScribe<ProductId> {
    public ProductIdScribe() {
        super(ProductId.class, "PRODID");
    }

    protected ProductId newInstance(String value) {
        return new ProductId(value);
    }
}
