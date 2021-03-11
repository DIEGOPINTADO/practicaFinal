package isil.pe;

import isil.pe.service.CitaService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ClinicaNotificacionApplication {

    public static void main(String[] args) {

        SpringApplication.run(ClinicaNotificacionApplication.class, args);
        CitaService citaService = new CitaService();
        citaService.recieveAllNotification();
    }


}
