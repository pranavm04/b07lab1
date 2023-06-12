import java.io.File;
import java.io.FileWriter;

public class Driver {
    public static void main(String [] args) throws Exception {
    Polynomial p = new Polynomial();
    System.out.println("p(3) = " + p.evaluate(3));
    double [] c1 = {6, 5};
    int [] c1e = {0, 3};
    Polynomial p1 = new Polynomial(c1, c1e);
    File input = new File("test_input.txt");
    Polynomial p2 = new Polynomial(input);
    Polynomial s = p1.add(p2);
    System.out.println("s(0.1) = " + s.evaluate(0.1));
    if(s.hasRoot(1)) System.out.println("1 is a root of s");
    else System.out.println("1 is not a root of s");
    p1.saveToFile("test_p1.txt");

    for (int i = 0; i < p2.exponents.length; i++) {
        System.out.println("p2c[" + i + "]=" + p2.nonzero_coefficients[i] + "; p2e[" + i + "]" + p2.exponents[i]);
    }
    for (int i = 0; i < s.exponents.length; i++) {
        System.out.println("sc[" + i + "]=" + s.nonzero_coefficients[i] + "; s[" + i + "]" + s.exponents[i]);
    }

    Polynomial m = p1.multiply(p2);
    p2.saveToFile("test_p2.txt");
    s.saveToFile("test_s.txt");
    m.saveToFile("test_m.txt");
    }

    }