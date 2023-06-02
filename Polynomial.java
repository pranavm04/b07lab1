public class Polynomial {

	double coefficients[];
	
	// NO-ARGUMENT CONSTRUCTOR
	public Polynomial() {
	}

	// CONSTRUCTOR
	public Polynomial(double coeff[]) {
		this.coefficients = coeff;
	}


	// add FUNCTION
	public Polynomial add(Polynomial px) {
		// Takes a Polynomial argument and returns the sum of the calling object and the argument.

		int new_length = Math.max(px.coefficients.length, this.coefficients.length);
		
		double [] new_coefficients = new double[new_length];
		for (int i = 0; i < new_length; i++) {
			new_coefficients[i] = 0;
			if (i < this.coefficients.length) {
				new_coefficients[i] += this.coefficients[i];
			}
			if (i < px.coefficients.length) {
				new_coefficients[i] += px.coefficients[i];
			}

		}

		Polynomial new_coef = new Polynomial(new_coefficients);
		return new_coef;

	}

	// evaluate FUNCTION
	public double evaluate(double x) {
		// Returns the value the Polynomial evaluates to when x is set to be equal to the argument.

		double p = 0.0;
		if (this.coefficients == null) return 0;
		for (int i = 0; i < coefficients.length; i++) {
			p += (coefficients[i] * Math.pow(x, i)); 
		}
		return p;
	}

	// hasRoot FUNCTION
	public Boolean hasRoot(double x) {
		// Returns whether or not the specified value of x is a root of the polynomial.

		double p = 0.0;
		p = evaluate(x);
		if (p == 0) return true;
		else return false;

	}

}