package sample;

import ca.uhn.fhir.model.dstu2.resource.Bundle;
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
    //public FhirContext ctx;
    public TextField patientName;
    //public static String serverBase= "http://fhirtest.uhn.ca/baseDstu2";
    //public IGenericClient client;
    public FhirtestServer connection;
    public myPatient helpPatient;

    public void handleSearch(){

        helpPatient=new myPatient();
        patientArrayList= new ArrayList<>();
        myList = new ArrayList<>(); //name list only
        patientList.getItems().clear();
        Bundle myBundle;
        ObservableList<String> patients = FXCollections.observableArrayList ();

        if(patientName.getText().length()==0) {
        
            myBundle= connection.getAllName();

            patientArrayList=connection.getPatientName(myBundle,helpPatient);

        }else{
            myBundle= connection.getOneName(patientName.getText());
            patientArrayList=connection.getPatientName(myBundle,helpPatient);

        }
        for(myPatient patient : patientArrayList){
            patients.add(patient.getName());
            myList.add(patient.getId());}
        patientList.setItems(patients);
    }
    @FXML
    public void initialize() {
        connection=new FhirtestServer();
    }
}
