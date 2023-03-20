package cloud.cloud.dao;

import java.util.List;

import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

public class DynamoDB<T> {
    
    public static DynamoDbEnhancedClient client;
    private String tableName;
    private TableSchema<T> schema;
    private final Class<T> type;
    private DynamoDbTable<T> table;

    public DynamoDB(String tableName, Class<T> type) {
        this.tableName = tableName;
        this.type = type;
        this.schema = TableSchema.fromBean(this.type);
        this.init(); 
    }

    public void init() {
        if (DynamoDB.client == null) {
            System.out.println("Init Dynamodb Client");
            DynamoDB.client = DynamoDbEnhancedClient.create();
        }
        System.out.println(DynamoDB.client);
    }

    public Boolean createTable() {
        System.out.println("Creating table");

        try {
            this.table = this.getTableFromClient();
            this.table.createTable();
            this.table = this.getTableFromClient();
            System.out.println(this.table);
        } catch (Error error) {
            error.printStackTrace();
            return false;
        }
        return true;
    }

    public Boolean addItems(List<T> items) {
        
        if (this.table == null) {
            this.table = this.getTableFromClient();
        }

        try {
            for (T item : items) {
                this.table.putItem(item);
            }
        } catch (Error error) {
            error.printStackTrace();
            return false;
        }

        return true;
    }

    private DynamoDbTable<T> getTableFromClient() {
        return DynamoDB.client.table(this.tableName, this.schema);
    }
}
