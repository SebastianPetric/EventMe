package thesis.hfu.eventme.functions;

public class Calculation {

    private static Calculation instance;

    public static Calculation getInstance(){
        if (Calculation.instance == null){
            Calculation.instance = new Calculation();
        }
        return Calculation.instance;
    }

    public double round(final double value) {
        double temp = value * 100;
        temp = Math.round(temp);
        temp = temp / 100;
        return temp;
    }
}
