package isil.pe.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;

import java.time.LocalDate;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Cita implements Serializable {
    private Integer citaID;
    private String doctorName;
    private String speciality;
    private Integer patientID;
    private String patientName;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

}
