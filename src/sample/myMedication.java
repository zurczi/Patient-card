package sample;

import org.hl7.fhir.dstu3.model.Medication;

public class myMedication {
    public String name;
    public String code;
    public String manufacturer;
    public String description;
    public myMedication(Medication m){
        name=m.getCode().getText(); ///TODO
        code=m.getCode().getCodingFirstRep().getCode();
        manufacturer=m.getManufacturer().getDisplay();
        description=m.getCode().getCoding().get(1).getDisplay();

    }

    @Override
    public String toString() {
        return "Name:"+ name + "\nCode:"+code+"\nManufacturer:"+manufacturer+"\nDescription:"+description;
    }
}
