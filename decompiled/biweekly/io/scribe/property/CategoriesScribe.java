package biweekly.io.scribe.property;

import biweekly.ICalDataType;
import biweekly.parameter.ICalParameters;
import biweekly.property.Categories;

public class CategoriesScribe extends TextListPropertyScribe<Categories> {
    public CategoriesScribe() {
        super(Categories.class, "CATEGORIES");
    }

    public Categories newInstance(ICalDataType dataType, ICalParameters parameters) {
        return new Categories();
    }
}
