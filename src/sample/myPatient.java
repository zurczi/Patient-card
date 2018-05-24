package sample;

import ca.uhn.fhir.model.dstu2.resource.Patient;

public class myPatient {

    private Patient patient;
    private String name;
    private String id;
    public myPatient(){

    }

    public myPatient(Patient p, String n, String i){
        this.patient=p;
        this.name = n;
        this.id = i;

    }
    public String getName() {
        return name;
    }
    public String getId() {
        return id;
    }

    public String makeName(Patient patient){
        String name = "";
/*
        for (int i = 0; i < patient.getName().size(); i++) {
            if (patient.getName().get(i).getFamily() !=null)
                name = name + " " + patient.getName().get(i).getFamily();
        }*/

        for (int i = 0; i < patient.getName().size(); i++) {
            if (patient.getName().get(i).getGivenAsSingleString()!=null)
                name = name + " " + patient.getName().get(i).getFamilyAsSingleString();


        }

        return name;
    }
}
