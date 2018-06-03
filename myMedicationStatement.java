package sample;

import org.hl7.fhir.dstu3.model.Medication;
import org.hl7.fhir.dstu3.model.MedicationStatement;
import org.hl7.fhir.exceptions.FHIRException;

import java.util.Date;

public class myMedicationStatement {
    private String name;
    private String taken;
    private String dosage;
    private Date startDate;
    private Date endDate;

    public myMedicationStatement(MedicationStatement ms)  {
        try{
            this.name ="Name " + ms.getMedicationCodeableConcept().getCodingFirstRep().getDisplay();
            this.taken = " Taken " + ms.getTaken().getDisplay();
            this.dosage=" Dosage DurationUnit: " + ms.getDosageFirstRep().getDoseSimpleQuantity().getValue().toString() + ms.getDosageFirstRep().getDoseSimpleQuantity().getUnit();
            this.startDate = ms.getEffectivePeriod().getStart();
            this.endDate =  ms.getEffectivePeriod().getEnd();

        } catch (FHIRException e) {
            e.printStackTrace();
        }catch (NullPointerException n){

        }
    }

    @Override
    public String toString() {
        return name+" "+taken+" "+dosage + startDate+endDate;
    }
}
