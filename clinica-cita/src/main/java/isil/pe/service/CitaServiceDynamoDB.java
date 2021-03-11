package isil.pe.service;

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
import isil.pe.model.Cita;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class CitaServiceDynamoDB {
    private String tableName = "citasMedicasDP";
    private AWSCredentials credentials = new BasicAWSCredentials("AKIAQKS6V3RVGWHSMN7L","Cf3J5OyOvAKTsa6tdreRfc8r/tFaNg5hJu/ydpMa");
    private AmazonDynamoDB dynamoDBClient;

   private DynamoDB dynamoDB ;


    public CitaServiceDynamoDB() {
          dynamoDBClient = AmazonDynamoDBClientBuilder.standard()
                           .withCredentials(new AWSStaticCredentialsProvider(credentials))
                           .withRegion(Regions.US_EAST_1)
                           .build();
          dynamoDB = new DynamoDB(dynamoDBClient);
          createtable();
    }

    private void deleteTable(String tableName) {
        Table table = dynamoDB.getTable(tableName);
        table.delete();
        System.out.println("table");
        try{
            table.waitForDelete();
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    public void loadData(Cita cita){
        Table table = dynamoDB.getTable(tableName);
        UUID uuid = UUID.randomUUID();

        Item item = new Item()
                .withPrimaryKey("citaID",uuid.toString())
                .withString("doctorName",cita.getDoctorName())
                .withString("speciality",cita.getSpeciality())
                .withNumber("patientID",cita.getPatientID())
                .withString("patientName",cita.getPatientName())
                .withStringSet("date", cita.getDate().toString());

        table.putItem(item);
        System.out.println("item = "+item);

    }

    public void createtable() {
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
