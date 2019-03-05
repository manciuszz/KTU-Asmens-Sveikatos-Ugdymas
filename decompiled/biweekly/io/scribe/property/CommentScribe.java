package biweekly.io.scribe.property;

import biweekly.property.Comment;

public class CommentScribe extends TextPropertyScribe<Comment> {
    public CommentScribe() {
        super(Comment.class, "COMMENT");
    }

    protected Comment newInstance(String value) {
        return new Comment(value);
    }
}
