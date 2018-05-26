package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
   // FhirContext ctx;
    //IGenericClient client;
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Karta pacjenta");
        primaryStage.setScene(new Scene(root, 500, 500));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
       /*
        FhirContext ctx = FhirContext.forDstu2();
        String serverBase = "http://fhirtest.uhn.ca/baseDstu2";

        IGenericClient client = ctx.newRestfulGenericClient(serverBase);

// Perform a search
        Bundle results = client
                .search()
                .forResource(Patient.class)
                .where(Patient.FAMILY.matches().value("Anderson"))
                .returnBundle(ca.uhn.fhir.model.dstu2.resource.Bundle.class)
                .execute();

        System.out.println("Found " + results + " patients named 'duck'");*/
    }
}

