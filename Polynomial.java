import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;

public class Polynomial {

	double nonzero_coefficients[];
	int exponents[];


	// NO-ARGUMENT CONSTRUCTOR
	public Polynomial() {
	}

	// CONSTRUCTOR WITH ARRAYS
	public Polynomial(double nonzero_coefficients[], int exponents[]) {

		int [] final_exponents = new int[exponents.length];
		double [] final_nonzero_coefficients = new double[nonzero_coefficients.length];

		final_exponents = sort(exponents);
		for (int i = 0; i < final_nonzero_coefficients.length; i++) {
			final_nonzero_coefficients[i] = nonzero_coefficients[indexOf(exponents, final_exponents[i])];
		}

		this.exponents = final_exponents;
		this.nonzero_coefficients = final_nonzero_coefficients;
	}

	// CONSTRUCTOR WITH FILE
	public Polynomial(File file_input) throws IOException {

		char[] info = new char[100];
		FileReader input = new FileReader(file_input);
		input.read(info);

		int[] temp_exponents = new int[10000];
		double[] temp_nonzero_coefficients = new double[10000];


		int counter = -1;
		int e_counter = 0;
		int n_counter = 0;
		String type = "c";
		String info_s = "";
		String temp = "";

		while (counter < info.length - 1) {
			temp = "" + info[counter + 1];
			if (temp.equals("+") || temp.equals("-")) {
				if (type.equals("e")) {
					if (!(info_s.equals(""))) temp_exponents[e_counter] = Integer.parseInt(info_s);
					else temp_exponents[e_counter] = 1;
					e_counter++;
					info_s = "";
				}
				else {
					if (!(info_s.equals(""))) temp_nonzero_coefficients[n_counter] = Double.parseDouble(info_s);
					else temp_nonzero_coefficients[n_counter] = 1;
					info_s = "";
					n_counter++;
					temp_exponents[e_counter] = 0;
					e_counter++;
				}
				type = "c";
				if (temp.equals("-")) info_s = "-";
			}
			else if (temp.equals("x")) {
				if (!(info_s.equals(""))) temp_nonzero_coefficients[n_counter] = Double.parseDouble(info_s);
				else temp_nonzero_coefficients[n_counter] = 1;
				info_s = "";
				n_counter++;
				type="e";
			}

			else if (temp.equals("\0")) {
				if (type.equals("e")) {
					temp_exponents[e_counter] = Integer.parseInt(info_s);
				}
				else if (type.equals("c")) {
					temp_nonzero_coefficients[n_counter] = Double.parseDouble(info_s);
					temp_exponents[e_counter] = 0;
				}
				break;
			}

			else info_s = info_s + temp;
			counter++;
		}

		int len = 0;
		for (int i = 1; i < temp_exponents.length; i++)
		{
			if (temp_exponents[i] == 0) {
				len = i;
				break;
			}
		}

		int [] exponents = new int[len];
		double [] nonzero_coefficients = new double[len];
		for (int i = 0; i < len; i++) {
			exponents[i] = temp_exponents[i];
			nonzero_coefficients[i] = temp_nonzero_coefficients[i];
		}

		int [] final_exponents = new int[exponents.length];
		double [] final_nonzero_coefficients = new double[nonzero_coefficients.length];

		final_exponents = sort(exponents);
		for (int i = 0; i < final_nonzero_coefficients.length; i++) {
			final_nonzero_coefficients[i] = nonzero_coefficients[indexOf(exponents, final_exponents[i])];
		}

		this.exponents = final_exponents;
		this.nonzero_coefficients = final_nonzero_coefficients;
		input.close();
	}


	// HELPER FUNCTION: sort
	public int [] sort(int [] arr) {
		int [] new_arr = arr.clone();
		for (int i = 0; i < new_arr.length; i++) {
			for (int j = i + 1; j < new_arr.length; j++) {  
				int temp = 0;  
				if (new_arr[i] > new_arr[j]) {
					temp = new_arr[i];
					new_arr[i] = new_arr[j];
					new_arr[j] = temp;
				}
			}
		}
		return new_arr;
	}

	// HELPER FUNCTION: indexOf
	public int indexOf(int [] arr, int n) {

		for (int i = 0; i < arr.length; i++) {
			if (arr[i] == n) return i;
		}

		return -1;
	}

	// HELPER FUNCTION: contains
	public Boolean contains(int[] array, int num) {
		for (int i = 0; i < array.length; i++) {
			if (array[i] == num) return true;
		}
		return false;
	}


	// add FUNCTION
	public Polynomial add(Polynomial px) {

		if (this.exponents == null || this.exponents.length == 0) return px;
		if (px.exponents == null || px.exponents.length == 0) return this;

		int num_diff = px.exponents.length + this.exponents.length;
		for (int i = 0; i < px.exponents.length; i++) {
			if (contains(this.exponents, px.exponents[i])) num_diff--;
		}

		int [] temp_exponents = new int[num_diff];
		double [] temp_nonzero_coefficients = new double[num_diff];
		for (int i=0; i<num_diff; i++) {
			temp_exponents[i] = -1;
			temp_nonzero_coefficients[i] = 0;
		}
		int counter = 0;
		for (int i = 0; i < temp_exponents.length; i++) {
			if (i < this.exponents.length) {
				if (!(contains(temp_exponents, this.exponents[i]))) {
					temp_exponents[counter] = this.exponents[i];
					counter++;
				}
			}
			if (i < px.exponents.length) {
				if (!(contains(temp_exponents, px.exponents[i]))) {
					temp_exponents[counter] = px.exponents[i];
					counter++;
				}
			}
			temp_nonzero_coefficients[i] = 0;
		}

		for (int i = 0; i < num_diff; i++) {
			if (contains(this.exponents, temp_exponents[i])) {
				temp_nonzero_coefficients[i] += this.nonzero_coefficients[indexOf(this.exponents, temp_exponents[i])];
			}
			if (contains(px.exponents, temp_exponents[i])) {
				temp_nonzero_coefficients[i] += px.nonzero_coefficients[indexOf(px.exponents, temp_exponents[i])];
			}
		}

		int final_counter = num_diff;
		for (int i = 0; i < num_diff; i++) {
			if (temp_nonzero_coefficients[i] == 0.0) final_counter--;
		}

		int [] final_exponents = new int[final_counter];
		double [] final_nonzero_coefficients = new double[final_counter];
		counter=0;
		for (int i = 0; i < num_diff; i++) {
			if (temp_nonzero_coefficients[i] != 0) {
				final_exponents[counter] = temp_exponents[i];
				final_nonzero_coefficients[counter] = temp_nonzero_coefficients[i];
				counter++;
			}
		}

		Polynomial p_final = new Polynomial(final_nonzero_coefficients, final_exponents);
		return p_final;

	}


	// evaluate FUNCTION
	public double evaluate(double x) {
		// Returns the value the Polynomial evaluates to when x is set to be equal to the argument.

		double p = 0.0;
		if (this.nonzero_coefficients == null) return 0;
		for (int i = 0; i < nonzero_coefficients.length; i++) {
			p += (nonzero_coefficients[i] * Math.pow(x, exponents[i])); 
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


	// multiply FUNCTION
	public Polynomial multiply(Polynomial px) {

		if (px.exponents == null || px.exponents.length == 0) {
			Polynomial return_p = new Polynomial();
			return return_p;
		}
		else if (px.exponents.length == 1) {
			if (px.nonzero_coefficients[0] == 0) {
				Polynomial return_p = new Polynomial();
				return return_p;
			}

			int [] final_exponents = this.exponents.clone();
			double [] final_nonzero_coefficients = this.nonzero_coefficients.clone();
			for (int i = 0; i < this.exponents.length; i++) {
				final_exponents[i] += px.exponents[0];
				final_nonzero_coefficients[i] *= px.nonzero_coefficients[0];
			}

			Polynomial return_p = new Polynomial(final_nonzero_coefficients, final_exponents);
			return return_p;
		}
		else {
			int [] exp1 = new int[1];
			exp1[0] = px.exponents[0];
			double [] nonz1 = new double[1];
			nonz1[0] = px.nonzero_coefficients[0];

			int [] exp2 = new int[px.exponents.length - 1];
			double [] nonz2 = new double[px.nonzero_coefficients.length - 1];
			for (int i = 1; i < px.exponents.length; i++) {
				exp2[i-1] = px.exponents[i];
				nonz2[i-1] = px.nonzero_coefficients[i];
			}

			Polynomial px1 = new Polynomial(nonz1, exp1);
			Polynomial px2 = new Polynomial(nonz2, exp2);

			Polynomial pxn = this.multiply(px2); 
			return pxn.add(this.multiply(px1));
		}
	}


	// saveToFile FUNCTION
	public void saveToFile(String filename) throws IOException {

		FileWriter file = new FileWriter(filename, false);

		if (exponents.length != 0) {
			for (int i = 0; i < exponents.length; i++) {
				if (i!= 0 && nonzero_coefficients[i] > 0) file.write("+");
				String temp = "";
				if (nonzero_coefficients[i] % 1 == 0) {
					temp = "" + (int)nonzero_coefficients[i];
				}
				else {
					temp = "" + nonzero_coefficients[i];
				}
				file.write(temp);
				if (exponents[i] != 0) {
					file.write("x" + exponents[i]);
				}
			}
		}

		file.close();

	}
}