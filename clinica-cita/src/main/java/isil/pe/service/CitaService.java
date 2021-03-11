package isil.pe.service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import isil.pe.model.Cita;
import org.springframework.stereotype.Service;

@Service
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

    public void sendNotification(Cita cita) {

        try {

            String citaString = convertCitaToString(cita);
            if(sendMessage(citaString)){
                System.out.println("Se envio el mensaje!");

            }

        } catch (JsonProcessingException e) {
            System.err.println("Ocurrió un error al convertir a String");
        }

    }

    private String convertCitaToString(Cita cita) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(cita);
    }

    private boolean sendMessage(String message){

        SendMessageRequest request = new SendMessageRequest(CITA_QUEUE_URL, message);
        SendMessageResult result = sqsCita.sendMessage(request);

        if (result.getSdkHttpMetadata().getHttpStatusCode() != 200){
            return false;
        }

        return true;
    }


}
