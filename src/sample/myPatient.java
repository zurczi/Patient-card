package sample;


import org.hl7.fhir.dstu3.model.Bundle;
import org.hl7.fhir.dstu3.model.Patient;

import java.text.SimpleDateFormat;
import java.util.Date;

public class myPatient {
    private String name;
    private String id;
    private Date birthDate;
    private String birthDateString;
    private String gender;

    public myPatient() {

    }

    @Override
    public String toString() {
        return getName();
    }
    public String showDetails(){
        return "Patient Birth Date:"+getBirthdate()+"\nPatient gender: "+ getGender();
    }

    public String infoToString() {
        return "Patient ID: "+ getId() + "\tPatient Name: " + getName() + "\tBrithDate: " + getBirthdate() + "\tGender: " + getGender();
    }
    public myPatient(Bundle.BundleEntryComponent p) {
        Patient patient = (Patient) p.getResource();
        this.id = p.getFullUrl().split("/")[p.getFullUrl().split("/").length - 1];
        this.name = patient.getName().isEmpty() ? "noname" : patient.getName().get(0).getFamily();
        this.gender = patient.getGender() == null ? "nogender" : patient.getGender().getDisplay();
        this.birthDate = patient.getBirthDate();
        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
        this.birthDateString = dt.format(patient.getBirthDate());
    }

    /*
    public Boolean checkValidity() {
        if (this.getName() != null && this.getGender() != null && this.getBirthdate() != null
                && this.getName().length() > 2 && this.getGender() != "Unknown"  && this.getBirthdate().after(new Date(1925, 1, 1))) {
            return true;
        }
        else {
            return  false;
        }
    }
    */

    public String getName() {
        return name;
    }
    public String getId() {
        return id;
    }
    public Date getBirthdate() { return birthDate; }
    public String getBirthDateString() { return birthDateString; }
    public String getGender() { return gender; }

}
