/* Created by Adam Jost on 07/07/2021 */

package payroll.processing;

import java.util.Scanner;

public class ProcessPayrollTotals {
	
	public static void main (String[] args) {
		Scanner scanner = new Scanner(System.in);
		// Used to read in each input
		// entered by the user and reset
		// before the next entry.
		String input = "";
		// Employee details
		String name = "", social;
		double hourlyPay = -1, hoursWorked = -1;
		// Used to determine if doubles contain a decimal point.
		boolean containsPoint = false;
		int guiWidth = 34;
		// Repeatedly request employee name until a name is entered.
		while (input.equals("")) {
			System.out.println("Enter your first and last name: ");
			input = scanner.nextLine().trim();
			if (input.replaceAll("[\\s\\p{Z}]","").equals("")) {
				System.out.println("Error: Your name cannot be left blank.");
			}
		}
		
		// Capitalize the first letter in each word in the name.
		StringBuilder sb = new StringBuilder();
		String[] nameSections = input.split(" ");
		for (String section : nameSections) {
			sb.append(section.substring(0,1).toUpperCase() + section.substring(1).toLowerCase()).append(' '); 
		}
		name = sb.toString();
		// Reset the input String.
		input = "";
		// Request the users last four of their social until a valid entry 
		// has been entered. 
		while (input.equals("")) {
			System.out.println("Enter the last four digits of your social: ");
			input = scanner.nextLine().trim();
			if (input.replaceAll("[\\s\\p{Z}]","").equals("")) {
				System.out.println("Error: Your social cannot be left blank.");
				continue;
			}
			// If any character of the input string is not a digit
			// then the input must be invalid so we print an error 
			// message to the console.
			for (char c : input.toCharArray()) {
				if (!Character.isDigit(c)) {
					System.out.println("Error: Social can only contain numeric values.");
					input = "";
					break;
				}
			}
			// Input was found to be invalid so we must
			// prompt the user once more to enter the
			// last four of their social.
			if (input.equals("")) {
				continue;
			}
			// If the length of the user input is more or 
			// less than 4 then the input is invalid so we 
			// print an error message to the console and 
			// once again request them to enter their social.
			if (input.length() < 4 || input.length() > 4) {
				System.out.println("Error: Social must contain 4 numeric values.");
				input = "";
				continue;
			}
		}
		// Save the social entered 
		// by the user.
		social = input;
		// Reset the input once again. 
		input = "";
		// Repeatedly request the hourly pay rate until a valid one is entered.
		while (input.equals("")) {
			System.out.println("Enter your hourly pay rate (e.g. 15.50): ");
			input = scanner.nextLine().trim();
			if (input.replaceAll("[\\s\\p{Z}]","").equals("")) {
				System.out.println("Error: Your rate of pay cannot be left blank.");
				continue;
			}
			for (char c : input.toCharArray()) {
				// If the user used a $ symbol in their input
				// print an error message reiterating the correct
				// input format. 
				if (c == '$') {
					System.out.println("Error: Pay rate must follow the format \"15.50\".");
					input = "";
					break;
				}
				// If a decimal point is found
				if (c == '.') { 
					containsPoint = true;
					continue; 
				}
				// If any other character is found besides a digit
				// then the input is invalid so we print an error
				// message informing user of the acceptable 
				// format. 
				if (!Character.isDigit(c)) {
					System.out.println("Error: Invalid characters were entered.\n"
							+ "Pay rate must follow the format \"15.50\".");
					input = "";
					break;
				}
			}
			// Input was found to be invalid in the above checks
			// so we once again request the user for a valid
			// hourly pay rate.
			if (input == "") {
				containsPoint = false;
				continue;
			}
			// If a decimal point was not found then append one
			// to the end of the input. 
			if (!containsPoint) {
				input += ".";
			}
			// Attempt to parse the input string to a double.
			// If it fails then we output an error message and
			// once again prompt the user to enter a valid 
			// hourly rate of pay. 
			try {
				hourlyPay = Double.parseDouble(input);
			} catch (NumberFormatException e) {
				input = "";
				System.out.println("Error: Pay rate must follow the format \"15.50\".");
			}
		}
		// Reset
		input = "";
		containsPoint = false;
		//  Repeatedly request the number of hours worked until a valid entry has been 
		//  entered by the user.
		while (input.equals("")) {
			System.out.println("Enter the number of hours you worked: ");
			input = scanner.nextLine().trim();
			if (input.replaceAll("[\\s\\p{Z}]","").equals("")) {
				System.out.println("Error: Your number of hours worked cannot be left blank.");
				continue;
			}
			for (char c : input.toCharArray()) {
				if (c == '.') { 
					containsPoint = true;
					continue; 
				}
				if (!Character.isDigit(c)) {
					System.out.println("Error: Invalid characters were entered.\n"
							+ "Hours worked must follow the format \"45.67\"");
					input = "";
					break;
				}
			}
			// If input is an empty string then the input
			// was found to be invalid in one of the above 
			// checks therefore we once again prompt the 
			// user to enter a valid amount of hours
			// worked.
			if (input.equals("")) {
				containsPoint = false;
				continue;
			}
			// If the input did not contain a decimal point
			// then we append one to the end of the input
			// string. 
			if (!containsPoint) {
				input += ".";
			}
			// Attempt to parse the input string to a double.
			// If it fails then we output an error message and
			// once again prompt the user to enter a valid 
			// amount of hours worked. 
			try {
				hoursWorked = Double.parseDouble(input);
			} catch (NumberFormatException e) {
				input = "";
				System.out.println("Error: Hours worked must follow the format \"45.67\".");
			}
		}
		//Calculate Totals
		double grossPay = hourlyPay * hoursWorked;
		double fedTax = grossPay * 0.15;
		double stateTax = grossPay * 0.05;
		
		// Format and Output Payroll Information to Screen
        // Doubles are formatted to a precision of 2 decimal points
        // Totals in output are filled with preceding blank spaces to a width of 9.
		printSeperator('=', guiWidth);
		System.out.printf("\n%" + 10 + "s%s", " ", "Payroll Summary");
		printSeperator('=', guiWidth);
		System.out.printf("\nPayroll Summary for: %s\n"
				+ "SSN (Last 4 Digits): %s", name, social);
		printSeperator('-', guiWidth);
		System.out.printf("\nHours Worked:             %9.2f\n\n"
				+ "Pay Rate:                $%9.2f\n"
				+ "Gross Pay:               $%9.2f\n"
				+ "Federal Withholding:     $%9.2f\n"
				+ "State Withholding:       $%9.2f\n"
				+ "-----------------------------------\n"
				+ "Net Pay:                 $%9.2f", hoursWorked, hourlyPay, 
				grossPay, fedTax, stateTax, (grossPay - fedTax - stateTax));
		printSeperator('=', guiWidth);
		// Close the scanner
		scanner.close();
	}
	
	/**
	 * Prints a separator line consisting of a repeatedly printed
	 * argument character to the console.
	 * 
	 * @param c: the character repeatedly printed to create
	 *           the separator.
	 */
	private static void printSeperator(char c, int guiWidth) {
		System.out.println();
		for (int i=0; i<=guiWidth; i++) { System.out.print(c); }
	}
	
}

