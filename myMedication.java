package sample;

public class myMedication {
    private String text;

    public String getText() {
        return text;
    }

    public myMedication(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
