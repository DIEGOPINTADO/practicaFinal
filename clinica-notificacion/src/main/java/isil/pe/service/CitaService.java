package isil.pe.service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.Message;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.amazonaws.services.sqs.model.ReceiveMessageResult;
import isil.pe.model.Cita;

import java.io.IOException;

public class CitaService {
    private static final String CITA_QUEUE_URL = "https://sqs.us-east-1.amazonaws.com/022747143274/citasMedicasQueue";
    private final AWSCredentials credentials = new BasicAWSCredentials("AKIAQKS6V3RVGWHSMN7L","Cf3J5OyOvAKTsa6tdreRfc8r/tFaNg5hJu/ydpMa");

    private AmazonSQS sqsCita;

    public CitaService() {

        sqsCita = AmazonSQSClientBuilder
                .standard()
                .withCredentials( new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.US_EAST_1)
                .build();

    }

    public Cita recieveNotification() {
        String citaString = receiveMessage();
        Cita cita = null;
        if(citaString != null){
            try {
                cita = convertStringToCita(citaString);
            } catch (IOException e) {
                System.err.println("Ocurrió un error al convertir a Cita object");
            }
        }

        return cita;
    }

    private Cita convertStringToCita(String citaString) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(citaString, Cita.class);
    }

    private String receiveMessage(){

        ReceiveMessageRequest request = new ReceiveMessageRequest()
                .withQueueUrl(CITA_QUEUE_URL)
                .withMaxNumberOfMessages(1)
                .withWaitTimeSeconds(3);

        ReceiveMessageResult result = sqsCita.receiveMessage(request);

        if(result.getSdkHttpMetadata().getHttpStatusCode() !=200){
            return null;
        }

        Message message = result.getMessages().stream().findFirst().orElse(null);
        if(message == null){
            return null;
        }

        return message.getBody();
    }

    public void recieveAllNotification() {

        ReceiveMessageRequest request = new ReceiveMessageRequest()
                .withQueueUrl(CITA_QUEUE_URL)
                .withMaxNumberOfMessages(1)
                .withWaitTimeSeconds(3);

        while (true){

            ReceiveMessageResult result = sqsCita.receiveMessage(request);

            if(result.getSdkHttpMetadata().getHttpStatusCode() !=200){
                System.out.println("Ocurrió un error en la cola "+CITA_QUEUE_URL+" ->" + result.getSdkHttpMetadata());
                return;
            }

            System.out.println("Obteniendo "+result.getMessages().size()+" mensajes desde la cola "+CITA_QUEUE_URL);

            result.getMessages().forEach( m -> {
                System.out.println("message = " + m.getBody());
                sqsCita.deleteMessage(CITA_QUEUE_URL, m.getReceiptHandle());
            });

        }



    }
}
