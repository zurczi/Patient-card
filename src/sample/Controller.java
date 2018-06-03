package sample;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.hl7.fhir.dstu3.model.Bundle;
import org.hl7.fhir.exceptions.FHIRException;

import java.util.ArrayList;
import java.util.List;

public class Controller {

    //GUI elements
    public ListView details;
    public Button search;
    public ListView patientList;
    public TextField patientName;
    public Button showDetails;

    public ArrayList<myPatient> patientArrayList;
    public FhirtestServer connection;
    public myPatient helpPatient;

    public void handleShowDetails() {
       //myPatient choosenPatient =(myPatient)patientList.getSelectionModel().getSelectedItem();
       List<Bundle.BundleEntryComponent> everythingFromChoosenPatient= connection.getEveything("4030829");
       ObservableList<Object> observationsList = FXCollections.observableArrayList ();
       List<Object> observations= connection.getObservation(everythingFromChoosenPatient);
       for (Object o : observations) {
            observationsList.add(o);
       }
       System.out.println("Observations size:"+observations.size());
       details.setItems(observationsList);
    }
    public void handleSearch(){
        helpPatient = new myPatient();
        patientArrayList= new ArrayList<>();
        patientList.getItems().clear();
        List<Bundle.BundleEntryComponent> myBundle;
        ObservableList<myPatient> patients = FXCollections.observableArrayList ();

        if(patientName.getText().length()==0) {
        
            myBundle = connection.getAllName();

            patientArrayList = connection.getPatientName(myBundle, helpPatient);

        } else {
            myBundle= connection.getOneName(patientName.getText());
            patientArrayList = connection.getPatientName(myBundle, helpPatient);

        }
        for (myPatient patient : patientArrayList) {
            patients.add(patient );
        }
        patientList.setItems(patients);
    }
    @FXML
    public void initialize() {
        connection=new FhirtestServer();
    }

}
