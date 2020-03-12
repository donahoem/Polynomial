import java.util.*;
public class DensePolynomial extends Polynomial {

    private double[] coefficients; 
    
    public DensePolynomial(double[] coefficients, int[] exponents) {
        try {
            if (coefficients.length != exponents.length) throw new IllegalArgumentException();
            
            for (int i = 0; i < exponents.length ; i++) {
                if (exponents[i] < 0) throw new IllegalArgumentException();
            }
        }
        catch(IllegalArgumentException e) {
            System.out.println("coefficients and exponents must be of same length");
        }

        //find max exponent and set this.coefficients size
        int maxExponent = 0;
        for (int i = 0; i < exponents.length; i++) {
            if (exponents[i] > maxExponent) maxExponent = exponents[i];
        }
        this.coefficients = new double[maxExponent+1];

        //check exponents for repeats and set flag
        boolean repeatedExponents = false;
        for (int i = 0; i < exponents.length ; i++) {
            for (int j = 0; j < exponents.length; j++) {
                if (i != j) {
                    if (exponents[i] == exponents[j]) repeatedExponents = true;
                }
            }
        }

        //get array index reordering that occurs after calling Arrays.sort(exponents)
        int[] indexReordering = new int[exponents.length];
        int[] copiedExponents = new int[exponents.length];
        for (int i = 0; i < copiedExponents.length; i++) {
            copiedExponents[i] = exponents[i];
        }
        int[] sortedCopy = new int[exponents.length];
        for (int i = 0; i < copiedExponents.length; i++) {
            sortedCopy[i] = exponents[i];
        }
        Arrays.sort(sortedCopy);
        int sortedIndex = 0;
        while (sortedIndex < indexReordering.length) {
            for (int i = 0; i < copiedExponents.length; i = i + 1 % copiedExponents.length) {
                if (copiedExponents[i]  == sortedCopy[0]) {
                    indexReordering[sortedIndex] = i;
                    sortedCopy[0] = Integer.MAX_VALUE;
                    Arrays.sort(sortedCopy);
                    sortedIndex++;
                }
                if (sortedIndex == indexReordering.length) break;
            }
        }

        //use indexReordering to push coefficients into this.coefficients
        for (int i = 0; i < exponents.length; i++) {
            sortedCopy[i] = exponents[i];
        }
        Arrays.sort(sortedCopy);
        int counter = 0;
        for (int i = 0; i < this.coefficients.length; i++) {
            if (sortedCopy[counter] == i) {
                if (repeatedExponents) {
                    double sum = coefficients[indexReordering[counter]];
                    int j = 1;
                    boolean flag = false;
                    if (counter < sortedCopy.length - 1) {
                        while (sortedCopy[counter] == sortedCopy[counter + j]) {
                            flag = true;
                            sum += coefficients[indexReordering[counter + j]];
                            j++;
                            if (counter + j >= sortedCopy.length) break;
                        }
                    }
                    this.coefficients[i] = sum;
                    if (flag) counter += j;
                    else counter += 1;
                }
                else {
                    this.coefficients[i] = coefficients[indexReordering[counter]];
                    counter++;
                }
            }
            else {
                this.coefficients[i] = 0;
            }
        }
    }

    public int getDegree() {
        return this.coefficients.length - 1;
    }

    public double getCoeff(int d){
        return this.coefficients[d];
    }

    public boolean isZero(){
        for (double coefficient : coefficients) {
            if (coefficient != 0.0) 
                return false;
        }

        return true;
    }

    public Polynomial add(Polynomial q) {
        try {
            if (q == null) throw new NullPointerException();
        }
        catch (NullPointerException e){
            System.out.println("Passed polynomial cannot be null.");
        }
        double[] sumCoeff = new double[Integer.max(this.getDegree() + 1, q.getDegree() + 1)];
        int[] newExponents = new int[Integer.max(this.getDegree() + 1, q.getDegree() + 1)];
        for (int i = 0; i < sumCoeff.length; i++) {
            sumCoeff[i] = this.getCoeff(i) + q.getCoeff(i);
            newExponents[i] = i;
        }

        return new DensePolynomial(sumCoeff, newExponents);
    }

    public Polynomial multiply(Polynomial q) {
        try {
            if (q == null) throw new NullPointerException();
        }
        catch (NullPointerException e){
            System.out.println("Passed polynomial cannot be null.");
        }

        //get product matrix
        double[][] productMatrix = new double[ this.getDegree() + 1 ][ this.getDegree() + q.getDegree() + 1 ];
        for (int i = 0; i < this.getDegree() + 1; i++) {
            for (int j = 0; j < q.getDegree() + 1; j++) {
                productMatrix[i][j] = this.getCoeff(i) * q.getCoeff(j);
            }
        }

        //sum product matrix
        double[] finalCoeff = new double[ this.getDegree() + q.getDegree() + 1 ];
        for (int finalIndex = 0; finalIndex < finalCoeff.length; finalIndex++){
            int j = finalIndex;
            for (int i = 0; i < this.getDegree() + 1; i++) {
                finalCoeff[finalIndex] += productMatrix[i][j];
                j = ((j-1) % finalCoeff.length);
                if (j < 0) j += finalCoeff.length;
            }
        }

        //initialize new exponents array
        int[] newExponents = new int[this.getDegree() + q.getDegree() + 1];
        for (int i = 0; i < newExponents.length; i++) {
            newExponents[i] = i;
        }

        return new DensePolynomial(finalCoeff, newExponents);
    }

    public Polynomial subtract(Polynomial q){
        try {
            if (q == null) throw new NullPointerException();
        }
        catch (NullPointerException e){
            System.out.println("Passed polynomial cannot be null.");
        }
        double[] sumCoeff = new double[Integer.max(this.getDegree() + 1, q.getDegree() + 1)];
        int[] newExponents = new int[Integer.max(this.getDegree() + 1, q.getDegree() + 1)];
        for (int i = 0; i < sumCoeff.length; i++) {
            sumCoeff[i] = this.getCoeff(i) - q.getCoeff(i);
            newExponents[i] = i;
        }

        return new DensePolynomial(sumCoeff, newExponents);    
    }

    public Polynomial minus() {
        double[] newCoefficients = new double[this.coefficients.length];
        int[] newExponents = new int[this.coefficients.length];
        for (int i = 0; i < this.coefficients.length; i++){
            newCoefficients[i] = this.coefficients[i] * -1;
            newExponents[i] = i;
        }

        return new DensePolynomial(newCoefficients, newExponents);
    }


    public String toString() {
        String sum = "Polynomial: ";
        for (int i = this.coefficients.length - 1; i >= 0 ; i--){
            if (this.coefficients[i] != 0) {
                if ( !(coefficients[i] == 1 && i != this.coefficients.length - 2) ) {

                    if (coefficients[i] % 1 != 0) sum += coefficients[i];
                    else sum += (int)coefficients[i];
                }
            }
            if (this.coefficients[i] != 0) {
                if (i != 0)
                    sum += "x";
                if (i > 1)
                    sum += "^" + i;
            }
            if (i > 0 && this.coefficients[i-1] != 0) sum += " + ";
        }
        if (this.isZero()) sum = "Polynomial: 0";

        return sum;
    }

    @Override
    public boolean equals(Object o) {
        DensePolynomial denseP = (DensePolynomial)o;
        if ( denseP.toString().equals( this.toString() ) ) return true;
        else return false;
    }

}