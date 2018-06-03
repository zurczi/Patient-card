package sample;


import org.apache.commons.lang3.text.WordUtils;
import org.hl7.fhir.dstu3.model.Bundle;
import org.hl7.fhir.dstu3.model.Patient;

import java.util.Date;
import java.util.List;

public class myPatient {
    private String name;
    private String id;
    private Date birthDate;
    private String gender;

    public myPatient() {

    }

    @Override
    public String toString() {
        return getId() + " " + getName()+ " "+getBirthdate()+" "+ getGender();
    }

    public myPatient(Bundle.BundleEntryComponent p) {
        Patient patient = (Patient) p.getResource();
        this.id = p.getFullUrl().split("/")[p.getFullUrl().split("/").length - 1];
        this.name = patient.getName().isEmpty() ? "noname" : patient.getName().get(0).getFamily();
        this.gender = patient.getGender() == null ? "nogender" : patient.getGender().getDisplay();
        this.birthDate = patient.getBirthDate();
    }

    public String getName() {
        return name;
    }
    public String getId() {
        return id;
    }
    public Date getBirthdate() { return birthDate; }
    public String getGender() { return gender; }

}
