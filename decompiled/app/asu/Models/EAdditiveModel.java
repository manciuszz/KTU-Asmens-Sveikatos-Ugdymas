package app.asu.Models;

public class EAdditiveModel {
    private String code;
    private String description;
    private int type_id;

    public EAdditiveModel(String code, int type_id, String description) {
        this.code = code;
        this.type_id = type_id;
        this.description = description;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getType_id() {
        return this.type_id;
    }

    public void setType_id(int type_id) {
        this.type_id = type_id;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
