package sample;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.model.dstu2.resource.Bundle;
import ca.uhn.fhir.model.dstu2.resource.Patient;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.util.ArrayList;

public class Controller {

    private ArrayList<String> myList;
    public ArrayList<myPatient> patientArrayList;
    public Button search;
    public ListView patientList;
    public FhirContext ctx;
    public TextField patientName;
    public static String serverBase= "http://fhirtest.uhn.ca/baseDstu2";
    public IGenericClient client;
    public myPatient helpPatient;

    public void handleSearch(){

        helpPatient=new myPatient();

        if(patientName.getText().length()==0) {

            Bundle allPatient = client
                    .search()
                    .forResource(Patient.class)
                    .returnBundle(Bundle.class)
                    .execute();

            patientList.getItems().clear();

            patientArrayList= new ArrayList<>(); //all mypatient
            myList = new ArrayList<>(); //name list only
            for (int i=0 ; i<allPatient.getEntry().size(); i++){

                Patient thePatient = (Patient) allPatient.getEntry().get(i).getResource();
                String checkName=helpPatient.makeName(thePatient);
                if(checkName.length()>2) {
                    patientArrayList.add(new myPatient(thePatient,checkName,thePatient.getId().toString()));
                }
            }

            ObservableList<String> patients = FXCollections.observableArrayList ();
            for(myPatient patient : patientArrayList){
                patients.add(patient.getName());
                myList.add(patient.getId());}

            patientList.setItems(patients);


        }else{
            Bundle resultBundle = client.search()
                    .forResource(Patient.class)
                    .where(Patient.NAME.matches().value(patientName.getText()))
                    .returnBundle(Bundle.class)
                    .execute();
            System.out.println(resultBundle.getEntry().get(0).getResource());
        }/*
        Bundle results = client
                .search()
                .forResource(Patient.class)
                .where(Patient.FAMILY.matches().value("Anderson"))
                .returnBundle(ca.uhn.fhir.model.dstu2.resource.Bundle.class)
                .execute();

        System.out.println("Found " + results.getEntry().size() + " patients named 'duck'");*/
    }
    @FXML
    public void initialize() {
        ctx = FhirContext.forDstu2();
        client = ctx.newRestfulGenericClient(serverBase);
    }
}
