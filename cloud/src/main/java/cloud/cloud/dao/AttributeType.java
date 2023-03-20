package cloud.cloud.dao;

public enum AttributeType {
    STRING("S"),
    NUMBER("N");

    private final String value;

    private AttributeType(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return this.value;
    }
}
