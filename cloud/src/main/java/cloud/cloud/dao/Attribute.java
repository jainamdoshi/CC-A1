package cloud.cloud.dao;

import software.amazon.awssdk.services.dynamodb.model.KeyType;
import software.amazon.awssdk.services.dynamodb.model.ScalarAttributeType;

public class Attribute {
    
    private String name;
    private ScalarAttributeType type;
    private KeyType keyType;

    public Attribute(String name, ScalarAttributeType type, KeyType keyType) {
        this.name = name;
        this.type = type;
        this.keyType = keyType;
    }

    public ScalarAttributeType getType() {
        return this.type;
    }

    public KeyType getKeyType() {
        return this.keyType;
    }

    public String getName() {
        return this.name;
    }
}
