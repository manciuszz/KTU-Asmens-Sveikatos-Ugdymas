package biweekly.property;

import biweekly.parameter.RelationshipType;

public class RelatedTo extends TextProperty {
    public RelatedTo(String uid) {
        super(uid);
    }

    public RelationshipType getRelationshipType() {
        return this.parameters.getRelationshipType();
    }

    public void setRelationshipType(RelationshipType relationshipType) {
        this.parameters.setRelationshipType(relationshipType);
    }
}
