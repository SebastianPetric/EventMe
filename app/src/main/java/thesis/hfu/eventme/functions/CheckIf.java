package thesis.hfu.eventme.functions;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckIf {

    private static CheckIf instance;

    public static CheckIf getInstance(){
        if (CheckIf.instance == null){
            CheckIf.instance = new CheckIf();
        }
        return CheckIf.instance;
    }

    public static boolean emailIsValid(String email) {

        boolean isValid=false;
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    public static boolean isNumeric(String str){

        try {
            double d = Double.parseDouble(str);
        }
        catch(NumberFormatException nfe){
            return false;
        }
        return true;
    }
}
