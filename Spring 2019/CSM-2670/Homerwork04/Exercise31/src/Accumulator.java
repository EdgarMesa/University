public class Accumulator {
    /**Sumation that contains a unique attribute to storage the the
     * accumulation after a sum, subtraction, division, multiplication by a rational number.
     * the accumulation will start a 0, but set a new value, clear the current accumulation and negate it
     * can also be done
     */

    /** Attribute*/
    public double sum;


    public Accumulator(){this.sum = 0;}

    /** clear the current accumulation*/
    public void clear(){sum = 0;}

    /** set the accumulator to a desired value*/
    public void set(double number){sum = number;}

    /** negate the accomulator*/
    public void negate(){sum *= -1;}

    /** converts a rational number in a decimal
     * @param rational = rational number to convert in decimal
     * @return decimal number
     */
    public double decimal(RationalNumber rational)
    {
        double num = rational.GetNumerator();
        double deno = rational.GetDenominator();
        double decimal = num/deno;

        return decimal;
    }

    public String ToString(){return "Total accumulator: " + String.format("%-15.10f\n",sum);}

    /** add a rational number to the accumulator
     * @param rational = rational number to add
     * @return accumulator after the summation
     */
    public double add(RationalNumber rational)
    {
        double decimal = decimal(rational);

        return sum += decimal;
    }

    /** subtract a rational number to the accumulator
     * @param rational = rational number to add
     * @return accumulator after the subtraction
     */
    public double sub(RationalNumber rational)
    {
        double decimal = decimal(rational);

        return sum -= decimal;
    }
    /** multiply a rational number to the accumulator
     * @param rational = rational number to add
     * @return accumulator after the multiplication
     */
    public double mult(RationalNumber rational)
    {

        double decimal = decimal(rational);

        return sum *= decimal;
    }

    /** divides a rational number to the accumulator
     * @param rational = rational number to add
     * @return accumulator after the division
     */
    public double div(RationalNumber rational)
    {

        double decimal = decimal(rational);

        return sum /= decimal;
    }

}
