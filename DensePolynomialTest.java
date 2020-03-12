
import org.junit.*;
import static org.junit.Assert.*;

public class DensePolynomialTest {
    @Test
    public static void getDegreeTest() {
        double[] coefficients = {2,21,3,2};
        int[] exponents = {1,0,3,5};

        Polynomial test = new DensePolynomial(coefficients, exponents);
        int result = test.getDegree();
        assertEquals("getDegree() " + test, 5, result);
    }

    @Test
    public static void getCoeffTest() {
        double[] coefficients = {2,21,3,2};
        int[] exponents = {1,0,3,5};

        Polynomial test = new DensePolynomial(coefficients, exponents);
        double result = test.getCoeff(3);
        assertEquals("getCoeff(3) " + test, 3, result);
    }

    @Test
    public static void isZeroTest() {
        double[] coefficients = {5,21,3,2};
        int[] exponents = {0,0,0,0};

        Polynomial test = new DensePolynomial(coefficients, exponents);
        boolean result = test.isZero();
        assertEquals("isZero() " + test, false, result);
    }

    @Test
    public static void addTest() {
        double[] coefficients = {2,21,3,2};
        int[] exponents = {1,0,3,5};
        double[] coefficients2 = {1,18,4,2,8};
        int[] exponents2 = {2,1,2,5,0};

        Polynomial test = new DensePolynomial(coefficients, exponents);
        Polynomial test2 = new DensePolynomial(coefficients2, exponents2);

        double[] expectedCoeff = {29, 20, 5, 3, 4};
        int[] expectedExp = {0, 1, 2, 3, 5};

        Polynomial result = test.add(test2);
        assertEquals("add(test2)  test: " + test + " test2: " + test2, new DensePolynomial(expectedCoeff, expectedExp), result);
    }

    @Test
    public static void subtractTest() {
        double[] coefficients = {0,21,3,2};
        int[] exponents = {1,0,2,5};
        double[] coefficients2 = {1,8,4,2,8};
        int[] exponents2 = {2,1,2,6,0};

        Polynomial test = new DensePolynomial(coefficients, exponents);
        Polynomial test2 = new DensePolynomial(coefficients2, exponents2);

        double[] expectedCoeff = {13, -16, -5, 3};
        int[] expectedExp = {0, 1, 2, 3};

        Polynomial result = test.subtract(test2);
        assertEquals("subtract(test2)  test: " + test + " test2: " + test2, new DensePolynomial(expectedCoeff, expectedExp), result);
    }

    @Test
    public static void multiplyTest() {
        double[] coefficients = {0,21,3,2};
        int[] exponents = {1,0,2,5};
        double[] coefficients2 = {1,8,4,2,8};
        int[] exponents2 = {2,1,2,6,0};

        Polynomial test = new DensePolynomial(coefficients, exponents);
        Polynomial test2 = new DensePolynomial(coefficients2, exponents2);

        double[] expectedCoeff = {168, 168, 129, 24, 15, 16, 58, 10, 6, 4};
        int[] expectedExp = {0, 1, 2, 3, 4, 5, 6, 7, 8, 11};

        Polynomial result = test.multiply(test2);
        assertEquals("multiply(test2)  test: " + test + " test2: " + test2, new DensePolynomial(expectedCoeff, expectedExp), result);
    }

    @Test
    public static void minusTest() {
        double[] coefficients = {2,21,3,2};
        int[] exponents = {1,0,3,5};

        Polynomial test = new DensePolynomial(coefficients, exponents);

        double[] expectedCoeff = {-2,-21,-3,-2};
        int[] expectedExp = {1,0,3,5};

        Polynomial result = test.minus();
        assertEquals("minus() " + test, new DensePolynomial(expectedCoeff, expectedExp), result);
    }

    public static void main(String[] args){
        getDegreeTest();
        getCoeffTest();
        isZeroTest();
        addTest();
        subtractTest();
        multiplyTest();
        minusTest();
    }
}
