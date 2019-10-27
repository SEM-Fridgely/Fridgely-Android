package cmu.sem.fridgely.util;

public class Formatter {

    // Convert calorie value and keep only two decimals
    // TODO: Create rounding method for decimals
    public static String castCaloriesToTwoDecimals(String calories) {
        String[] strAry = calories.split("\\.");
        if(strAry.length>1){
            return strAry[0] + "." + strAry[1].substring(0,2);
        }else{
            return strAry[0];
        }

    }
}
