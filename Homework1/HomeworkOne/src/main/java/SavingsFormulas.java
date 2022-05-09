public class SavingsFormulas {
    public static double futureValueLumpSum(double cash, double interest, int years) {
        return cash * Math.pow((1 + interest), years);
    }

    private static double calcTotal(double values[], double sum, int index) {
        if(index == 0) {
            return sum * (1 + values[0]);
        } else {
            return calcTotal(values, sum, index-1) * (1 + values[index]);
        }
    }

    public static double futureValueLS_VariableInterest(double cash, double values[]) {
        return calcTotal(values, cash, values.length-1);
    }

    public static double compoundSavingsConstant(double cash, double interest, int years) {
        return cash * ((Math.pow(1 + interest, years) - 1) / interest);
    }

    private static double calcCompound(double values[], double interest, int index) {
        if(index == 0) {
            return values[0];
        } else {
            return (calcCompound(values, interest, index-1) * (1 + interest)) + values[index];
        }
    }

    public static double compoundSavingsVariable(double values[], double interest) {
        return calcCompound(values, interest, values.length-1);
    }
}