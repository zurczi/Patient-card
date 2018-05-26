package sample;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.model.dstu2.resource.Bundle;
import ca.uhn.fhir.model.dstu2.resource.Patient;
import ca.uhn.fhir.rest.client.api.IGenericClient;

import java.util.ArrayList;

public class FhirtestServer {
    public FhirContext ctx;
    public static String serverBase= "http://fhirtest.uhn.ca/baseDstu2";
    public IGenericClient client;

    public FhirtestServer(){
        ctx = FhirContext.forDstu2();
        client = ctx.newRestfulGenericClient(serverBase);
    }
    public Bundle getAllName(){
        Bundle allPatient = this.getClient()
                .search()
                .forResource(Patient.class)
                .returnBundle(Bundle.class)
                .execute();
        return allPatient;
    }
    public Bundle getOneName(String Name){
        Bundle oneName = this.getClient().search()
                .forResource(Patient.class)
                .where(Patient.NAME.matches().value(Name))
                .returnBundle(Bundle.class)
                .execute();
        return oneName;
    }

    public ArrayList getPatientName(Bundle allPatient, myPatient helpPatient){
        ArrayList<myPatient> patientArrayList= new ArrayList<>();
        for (int i=0 ; i<allPatient.getEntry().size(); i++){

            Patient thePatient = (Patient) allPatient.getEntry().get(i).getResource();
            String checkName=helpPatient.makeName(thePatient);
            if(checkName.length()>2) {
                patientArrayList.add(new myPatient(thePatient,checkName,thePatient.getId().toString()));
            }
        }
    return patientArrayList;
    }


    public FhirContext getCtx() {
        return ctx;
    }

    public IGenericClient getClient() {
        return client;
    }
}
