package sample;

import org.hl7.fhir.dstu3.model.Observation;
import org.hl7.fhir.exceptions.FHIRException;

import java.text.SimpleDateFormat;
import java.util.Date;

public class myObservation {
    private String name;
    private Date startDate;
    private String startDateString;
    private String measure;


    public myObservation(Observation o) {
        try{
            this.name = o.getCode().getText();
            this.startDate = o.getIssued();
            SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            this.startDateString = dt.format(this.startDate);

            if (o.hasValueQuantity()) {
                measure = String.format("%.2f ", o.getValueQuantity().getValue()) + o.getValueQuantity().getUnit();
            }
        }
        catch (FHIRException e){
            e.toString();
        }
    }

    @Override
    public String toString() {
        return startDateString + "\n" + name + "\n" + measure;
    }

    public Date getStartDate() {
        return startDate;
    }
    public String getName() { return name; }
    public String getStartDateString() { return startDateString; }
    public String getMeasure() { return measure; }
}
