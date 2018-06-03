package sample;

import org.hl7.fhir.dstu3.model.Observation;
import org.hl7.fhir.exceptions.FHIRException;

import java.util.Date;

public class myObservation {
    private String name;
    private Date startDate;
    private String measure;


    public myObservation(Observation o) {
        try{
        this.name = o.getCode().getText();
        this.startDate =o.getIssued();
        if (o.hasValueQuantity()) {
            measure = o.getValueQuantity().getValue().toString() + o.getValueQuantity().getUnit();
    }}catch (FHIRException e){

        }
    }

    @Override
    public String toString() {
        return "Name: "+ name+"StartDate :"+startDate+"Pomiar: "+measure;
    }
}
