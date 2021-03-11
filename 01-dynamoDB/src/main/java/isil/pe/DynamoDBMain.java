package isil.pe;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.*;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.UUID;

public class DynamoDBMain {

    static AWSCredentials credentials = new BasicAWSCredentials("AKIAQKS6V3RVGWHSMN7L","Cf3J5OyOvAKTsa6tdreRfc8r/tFaNg5hJu/ydpMa");
   static AmazonDynamoDB dynamoDBClient = AmazonDynamoDBClientBuilder
                                        .standard()
                                        .withCredentials(new AWSStaticCredentialsProvider(credentials))
                                        .withRegion(Regions.US_EAST_1)
                                        .build();

    static DynamoDB dynamoDB = new DynamoDB(dynamoDBClient);

    public static void main(String[] args) {
      final String tableName = "pruebaTablaDiego";
      //crear tabla Dynamodb
      //createtable(tableName);
      //Insertar en la tabla Dynamo
      loadData(tableName);

      //Eliminar taba dynamodb
      //deleteTable(tableName);
    }

    private static void deleteTable(String tableName) {
        Table table = dynamoDB.getTable(tableName);
        table.delete();
        System.out.println("table");
        try{
           table.waitForDelete();
        }catch(InterruptedException e){
            e.printStackTrace();
        }

    }

    private static void loadData(String tableName){
        Table table = dynamoDB.getTable(tableName);
        UUID uuid = UUID.randomUUID();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        Item item = new Item()
                    .withPrimaryKey("citaID",uuid.toString())
                    .withString("doctorName","Dr. Cervando")
                    .withString("speciality","Cardiologo")
                    .withNumber("patientID",7)
                    .withString("patientName","Cesar")
                    .withStringSet("date", timestamp.toString())
                    .withStringSet("roles",new HashSet<String>(Arrays.asList("ADMIN","USER")))
                    .withBoolean("active",true);

        table.putItem(item);
        System.out.println("item = "+item);

    }

    public static void createtable(String tableName) {
        ProvisionedThroughput pt = new ProvisionedThroughput()
                 .withReadCapacityUnits(5L)
                 .withWriteCapacityUnits(5L);

        KeySchemaElement kse = new KeySchemaElement()
                .withAttributeName("citaID")
                .withKeyType(KeyType.HASH);

        AttributeDefinition ad = new AttributeDefinition()
                .withAttributeName("citaID")
                .withAttributeType(ScalarAttributeType.S);

        CreateTableRequest request = new CreateTableRequest()
                .withTableName(tableName)
                .withKeySchema(kse)
                .withProvisionedThroughput(pt)
                .withAttributeDefinitions(ad);

        Table table = dynamoDB.createTable(request);
        System.out.println("Se ha creado la tabla "+table.getTableName());
        try {
            table.waitForActive();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }



}
