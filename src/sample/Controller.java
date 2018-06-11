package sample;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import org.hl7.fhir.dstu3.model.Bundle;

import java.util.ArrayList;
import java.util.List;

public class Controller {

    //GUI elements
    public ListView details;
    public Button search;
    public ListView patientList;
    public TextField patientName;
    public Button showDetails;
    public TextArea medicationShow;
    public ListView timeList;
    public DatePicker timeStart;
    public DatePicker timeEnd;
    public Label labelID;
    public Label labelName;
    public Label labelGender;
    public Label labelDateOfBirth;
    public Label labelPosition;

    public ArrayList<myPatient> patientArrayList;
    public FhirtestServer connection;
    public myPatient helpPatient;
    public List<Object> observations;

    public void handleChoosePatient(MouseEvent arg0) {
        myPatient choosenPatient= (myPatient)patientList.getSelectionModel().getSelectedItem();
        labelPosition.setText(patientList.getSelectionModel().getSelectedIndex() + 1 + " / " + patientList.getItems().size());
        labelID.setText(choosenPatient.getId());
        labelGender.setText(choosenPatient.getGender());
        labelName.setText(choosenPatient.getName());
        labelDateOfBirth.setText(choosenPatient.getBirthDateString());

        System.out.println("clicked on " + patientList.getSelectionModel().getSelectedItem());
        /*
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Details about selected Patient");
        alert.setHeaderText(choosenPatient.toString());
        alert.setContentText(choosenPatient.showDetails());

        alert.showAndWait();
        */
    }

    public void handleShowDetails() {
        List<Bundle.BundleEntryComponent> everythingFromChoosenPatient;
        myPatient choosenPatient =(myPatient)patientList.getSelectionModel().getSelectedItem();
        everythingFromChoosenPatient= connection.getEveything(choosenPatient.getId());

        ObservableList<Object> observationsList = FXCollections.observableArrayList();
        ObservableList<Object> medicationsList = FXCollections.observableArrayList();

        observations = connection.getObservation(everythingFromChoosenPatient);
        List<Object> medications = connection.getMedicationRequest(everythingFromChoosenPatient);
        List<myMedication> myMedicationsList = connection.getMedication(everythingFromChoosenPatient);

        for (Object o : observations) {
            myObservation mo = (myObservation)o;
            if (mo.getName() != null && mo.getMeasure() != null) {
                observationsList.add(o);
            }
            /*if(o instanceof myMedicationRequest){
                myMedicationRequest ms = (myMedicationRequest)o;
                timeObservableList.add(ms.getStartDate().toString()+"\n ");
                //System.out.println("MEDICATIONSTATEMANT");

            }else {

                timeObservableList.add(mo.getStartDate().toString()+"\n ");}
                mo.getStartDate()
                //System.out.println("Observation");
            */
        }
        medicationShow.clear();
        for (Object o : medications) {
            myMedicationRequest mmr = (myMedicationRequest)o;
            medicationsList.add(mmr);
            medicationShow.appendText(mmr.getName() + "\n");
        }

        System.out.println("Observations size:" + observations.size());
        observationsList = observationsList.sorted();
        details.setItems(observationsList);
        timeStart.getEditor().clear();
        timeEnd.getEditor().clear();
    }
    public void handleSearch(){
        System.out.println("Button 'Szukaj' clicked");
        helpPatient = new myPatient();
        patientArrayList = new ArrayList<>();
        patientList.getItems().clear();
        List<Bundle.BundleEntryComponent> myBundle;
        ObservableList<myPatient> patients = FXCollections.observableArrayList();

        if(patientName.getText() != "") {
            myBundle= connection.getOneName(patientName.getText());
            patientArrayList = connection.getPatientName(myBundle, helpPatient);
        }
        for (myPatient patient : patientArrayList) {
            patients.add(patient);
        }

        patientList.setItems(patients);
        System.out.println("Button 'Szukaj' clicked EXIT");
    }

    public void changeTimeStart() {
        ObservableList<Object> observationsList = FXCollections.observableArrayList();

        for (Object o : observations) {
            myObservation mo = (myObservation) o;
            if (timeEnd.getValue() != null && mo.getName() != null && mo.getMeasure() != null && !mo.getStartDate().before(java.sql.Date.valueOf(timeStart.getValue())) &&
                    !mo.getStartDate().after(java.sql.Date.valueOf(timeEnd.getValue().plusDays(1)))) {
                observationsList.add(o);
            }
            if (timeEnd.getValue() == null && mo.getName() != null && mo.getMeasure() != null && !mo.getStartDate().before(java.sql.Date.valueOf(timeStart.getValue()))) {
                observationsList.add(o);
            }
        }
        observationsList = observationsList.sorted();
        details.setItems(observationsList);
    }

    public void changeTimeEnd() {
        ObservableList<Object> observationsList = FXCollections.observableArrayList();

        for (Object o : observations) {
            myObservation mo = (myObservation) o;
            if (timeStart.getValue() != null && mo.getName() != null && mo.getMeasure() != null && timeEnd.getValue() != null && !mo.getStartDate().after(java.sql.Date.valueOf(timeEnd.getValue().plusDays(1))) &&
                    !mo.getStartDate().before(java.sql.Date.valueOf(timeStart.getValue()))) {
                observationsList.add(o);
            }

            if (timeStart.getValue() == null && mo.getName() != null && mo.getMeasure() != null && timeEnd.getValue() != null && !mo.getStartDate().after(java.sql.Date.valueOf(timeEnd.getValue().plusDays(1)))) {
                observationsList.add(o);
            }
        }
        observationsList = observationsList.sorted();
        details.setItems(observationsList);
    }


    @FXML
    public void initialize() {
        connection=new FhirtestServer();

        helpPatient = new myPatient();
        patientArrayList= new ArrayList<>();
        patientList.getItems().clear();
        List<Bundle.BundleEntryComponent> myBundle;
        ObservableList<myPatient> patients = FXCollections.observableArrayList();
        myBundle = connection.getAllName();
        patientArrayList = connection.getPatientName(myBundle, helpPatient);
        for (myPatient patient : patientArrayList) {
            patients.add(patient);
        }
        patientList.setItems(patients);
    }

}
