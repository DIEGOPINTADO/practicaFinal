package isil.pe.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

public class Cita implements Serializable {
    private Integer citaID;
    private String doctorName;
    private String speciality;
    private Integer patientID;
    private String patientName;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'hh:mm")
    private LocalDate date;

    public Cita() {
    }

    public Cita(Integer citaID, String doctorName, String speciality, Integer patientID, String patientName, LocalDate date) {
        this.citaID = citaID;
        this.doctorName = doctorName;
        this.speciality = speciality;
        this.patientID = patientID;
        this.patientName = patientName;
        this.date = date;
    }

    public Integer getCitaID() {
        return citaID;
    }

    public void setCitaID(Integer citaID) {
        this.citaID = citaID;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public Integer getPatientID() {
        return patientID;
    }

    public void setPatientID(Integer patientID) {
        this.patientID = patientID;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Cita{" +
                "citaID=" + citaID +
                ", doctorName='" + doctorName + '\'' +
                ", speciality='" + speciality + '\'' +
                ", patientID=" + patientID +
                ", patientName='" + patientName + '\'' +
                ", date=" + date +
                '}';
    }
}
