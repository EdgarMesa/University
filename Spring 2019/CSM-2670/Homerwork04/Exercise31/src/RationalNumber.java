import java.math.*;

public class RationalNumber {

    /** Attributes num = numerator, deno = denominator*/
    private final int num;
    private final int deno;

    /**constructor*/
    public RationalNumber(int num, int deno)
    {
        /** if denominator = 0 throw an exception*/
        if (deno == 0)
        {
            System.err.println("Can not divided by 0");
            throw new IllegalArgumentException();
        }

        /** if the denominator is negative, multiply both by -1*/
        if (deno < 0)
        {
            num *= -1;
            deno *= -1;
        }


        int gdc;
        /** getting the gdc to reduce the fraction*/
        gdc = gdc(num,deno);

        this.num = num/gdc;
        this.deno = deno/gdc;
    }

    /** second constructor for the radio 0/1*/
    public RationalNumber(){this(0,1);}

    /** get the numerator
     * @return numerator
     */
    public double GetNumerator(){return num;}

    /** get the numerator
     * @return denominator
     */
    public double GetDenominator(){return deno;}

    /** ToString method
     * @return value of numerator and denominator as a string
     */
    public String ToString()
    {
    if(deno == 1) return "Numerador: " + num;
    return "Numerador: " + num + "\t" + "Denominador: " + deno;
    }

    /**method to get the gdc using recursion
     *
     * @param num = numerator
     * @param deno = denominator
     * @return absolute value of gdc of bot numbers
     */
    public int gdc(int num, int deno)
    {
        if(deno == 0) return num;

        return Math.abs(gdc(deno,num%deno));
    }

    /** summation of two rationals numbers.
     * @param n = a rational number
     * @return the sum of two rational numbers as a another rational number using common denominator
     */
    public RationalNumber summation(RationalNumber n)
    {
        return new RationalNumber((num * n.deno) + (n.num * deno), n.deno * deno);
    }

    /** subtraction of two rationals numbers.
     * @param n = a rational number
     * @return the subtraction of two rational numbers as a another rational number using common denominator
     */
    public RationalNumber subtraction(RationalNumber n)
    {
        return new RationalNumber((num * n.deno) - (n.num * deno), deno * n.deno*(-1));
    }

    /** multiplication of two rationals numbers.
     * @param n = a rational number
     * @return the multiplication of two rational numbers as a another rational number
     */
    public RationalNumber multiplication(RationalNumber n)
    {
        return new RationalNumber(num * n.num,deno * n.deno);
    }

    /** division of two rationals numbers.
     * @param n = a rational number
     * @return the division of two rational numbers as a another rational number
     */
    public RationalNumber division(RationalNumber n)
    {
        return new RationalNumber(deno * n.num,num * n.deno);
    }


}
