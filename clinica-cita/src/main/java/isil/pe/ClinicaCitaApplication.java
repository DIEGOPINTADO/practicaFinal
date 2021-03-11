package isil.pe;

import isil.pe.model.Cita;
import isil.pe.service.CitaService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ClinicaCitaApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClinicaCitaApplication.class, args);
     //   Cita cita = new Cita(1,"Dr. Medina", "Odontologo",1,"Diego",null);
       // CitaService citaService = new CitaService();
      //  citaService.sendNotification(cita);
    }

}
