package cloud.cloud.dao;

import java.util.ArrayList;
import java.util.List;

import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.PageIterable;

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
    }

    public Boolean createTable() {
        System.out.println("Creating table");

        try {
            this.table = this.initTableFromClient();
            this.table.createTable();
            this.table = DynamoDB.client.table(this.tableName, this.schema);
            System.out.println(this.table);
        } catch (Error error) {
            error.printStackTrace();
            return false;
        }
        return true;
    }

    public Boolean addItems(List<T> items) {
        
        this.initTableFromClient();

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

    public List<T> getAllItems() {
        System.out.println("Getting all items");
        this.initTableFromClient();
        PageIterable<T> items = this.table.scan();
        List<T> itemList = new ArrayList<T>();
        items.stream().forEach(pages -> pages.items().forEach(item -> itemList.add(item)));
        return itemList;
    }

    private DynamoDbTable<T> initTableFromClient() {
        if (this.table == null) {
            this.table = DynamoDB.client.table(this.tableName, this.schema);
        }
        return this.table;
    }
}
