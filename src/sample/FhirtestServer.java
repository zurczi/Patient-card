package sample;


import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.model.primitive.DateDt;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import javafx.collections.ObservableList;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.exceptions.FHIRException;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class FhirtestServer {
    private FhirContext ctx;
    private static final String serverBase= "http://hapi.fhir.org/baseDstu3";
    private IGenericClient client;

    public FhirtestServer(){
        ctx = FhirContext.forDstu3();
        client = ctx.newRestfulGenericClient(serverBase);
    }

    public List<Bundle.BundleEntryComponent> getEveything(String patientID){
        Parameters outParams = client
                .operation()
                .onInstance(new IdType("Patient", patientID))
                .named("$everything")
                .withNoParameters(Parameters.class)
                .useHttpGet()
                .execute();

        List<Bundle.BundleEntryComponent> entries = new LinkedList<>();
        List<Parameters.ParametersParameterComponent> parameterComponents = outParams.getParameter();

        for (Parameters.ParametersParameterComponent parameterComponent : parameterComponents){
            Bundle bundle = (Bundle) parameterComponent.getResource();
            entries.addAll(bundle.getEntry());
            while (bundle.getLink(Bundle.LINK_NEXT) != null) {
                // load next page
                bundle = client.loadPage().next(bundle).execute();
                entries.addAll(bundle.getEntry());
            }
        }
        return entries;
    }
    public List<Bundle.BundleEntryComponent> getEveythingFromPeriod(String patientID, Date start, Date end){
        Parameters inParams = new Parameters();
        inParams.addParameter().setName("start").setValue(new DateType(start));
        inParams.addParameter().setName("end").setValue(new DateType(end));

// Invoke $everything on "Patient/1"
        Parameters outParams = client
                .operation()
                .onInstance(new IdType("Patient", patientID))
                .named("$everything")
                .withParameters(inParams)
                .useHttpGet() // Use HTTP GET instead of POST
                .execute();

        List<Bundle.BundleEntryComponent> entries = new LinkedList<>();
        List<Parameters.ParametersParameterComponent> parameterComponents = outParams.getParameter();

        for (Parameters.ParametersParameterComponent parameterComponent : parameterComponents){
            Bundle bundle = (Bundle) parameterComponent.getResource();
            entries.addAll(bundle.getEntry());
            while (bundle.getLink(Bundle.LINK_NEXT) != null) {
                bundle = client.loadPage().next(bundle).execute();
                entries.addAll(bundle.getEntry());
            }
        }
        return entries;
    }
    public List<Bundle.BundleEntryComponent> getAllName(){
        Bundle allPatient = this.getClient()
                .search()
                .forResource(Patient.class)
                .returnBundle(Bundle.class)
                .execute();
        List<Bundle.BundleEntryComponent> entries = new LinkedList<>();
        entries.addAll(allPatient.getEntry());
        while (allPatient.getLink(Bundle.LINK_NEXT) != null) {
            allPatient = client.loadPage().next(allPatient).execute();
            entries.addAll(allPatient.getEntry());
            System.out.println(entries.size());
        }
        return entries;
    }
    public List<Bundle.BundleEntryComponent> getOneName(String Name){
        Bundle oneName = this.getClient().search()
                .forResource(Patient.class)
                .where(Patient.NAME.matches().value(Name))
                .returnBundle(Bundle.class)
                .execute();
        List<Bundle.BundleEntryComponent> entries = new LinkedList<>();
        entries.addAll(oneName.getEntry());
        while (oneName.getLink(Bundle.LINK_NEXT) != null) {
            oneName = client.loadPage().next(oneName).execute();
            entries.addAll(oneName.getEntry());
        }
        return entries;
    }

    public ArrayList getPatientName(List<Bundle.BundleEntryComponent> allPatient, myPatient helpPatient){
        ArrayList<myPatient> patientArrayList = new ArrayList<>();
        for (int i=0 ; i < allPatient.size(); i++){
            Bundle.BundleEntryComponent thePatient =  allPatient.get(i);

            patientArrayList.add(new myPatient(thePatient));

        }
        return patientArrayList;
    }
    public List<Object> getObservation(List<Bundle.BundleEntryComponent> entries)  {
        List<Object> observationList = new LinkedList<>();
        for (Bundle.BundleEntryComponent e :entries){
            if(e.getResource() instanceof Observation){
                Observation o = (Observation)e.getResource();
                myObservation myObservation = new myObservation(o);
                System.out.println("Observation");
                observationList.add(myObservation);
            }else if(e.getResource() instanceof MedicationStatement){
                MedicationStatement ms = (MedicationStatement)e.getResource();
                myMedicationStatement myMedicationStatement = new myMedicationStatement(ms);
                System.out.println("MedicationStatement");
                observationList.add(myMedicationStatement);
            }else if(e.getResource() instanceof Medication){
                Medication m = (Medication)e.getResource();
                myMedication myMedication = new myMedication("M:"+m.getStatus().getDisplay());
                System.out.println("Medication");
                observationList.add(myMedication);
            }
        }
        return observationList;
    }

    public FhirContext getCtx() {
        return ctx;
    }

    public IGenericClient getClient() {
        return client;
    }
}
