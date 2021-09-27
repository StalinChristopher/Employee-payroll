package com.yml.emloyeepayroll;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EmployeePayrollService {
	private List<EmployeePayroll> employeePayrollList;
	public static String PAYROLL_FILENAME = "data/payroll-file.txt";
	
	public EmployeePayrollService() {}
	
	public EmployeePayrollService(List<EmployeePayroll> employeePayrollList) {
		this.employeePayrollList = employeePayrollList;
	}

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		List<EmployeePayroll> employeePayrollList = new ArrayList<>();
		EmployeePayrollService employeePayrollService = new EmployeePayrollService(employeePayrollList);
		employeePayrollService.readEmployeePayrollData(in);
		employeePayrollService.writeEmployeePayrollData();
		
		

	}
	
	/**
	 * @param consoleInputReader
	 * Method to read the employee data from the console
	 */
	public void readEmployeePayrollData(Scanner consoleInputReader) {
		System.out.println("Enter employee id : ");
		int id = consoleInputReader.nextInt();
		consoleInputReader.nextLine();
		System.out.println("Enter employee name :");
		String name = consoleInputReader.nextLine();
		System.out.println("Enter employee salary : ");
		double salary = consoleInputReader.nextDouble();
		EmployeePayroll employeePayroll = new EmployeePayroll(id, name, salary);
		employeePayrollList.add(employeePayroll);
		
	}
	
	/**
	 * Method to print the list of employeePayrollData into the console
	 */
	public void writeEmployeePayrollData() {
		System.out.println("\nEmployee payroll data\n");
		for(EmployeePayroll item : employeePayrollList) {
			System.out.println(item);
		}
	}
	
	/**
	 * Method to write the list into a new file
	 */
	public void writeEmployeePayrollFile() {
		
		StringBuffer buffer  = new StringBuffer();
		employeePayrollList.forEach(employee -> {
			String employeeDataString = employee.toString().concat("\n");
			buffer.append(employeeDataString);
		});
		try {
			Files.write(Paths.get(PAYROLL_FILENAME), buffer.toString().getBytes());
		} catch( IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @return number of lines i.e count of entries
	 * Method to count the number of lines in the given file
	 */
	public long countLines() {
		long entries = 0;
		try {
			entries = Files.lines(new File(PAYROLL_FILENAME).toPath()).count();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(entries);
		return entries;
	}
	
	/**
	 * Method to read the data from the file and print it into the console
	 */
	public boolean printData() {
		try {
			Files.lines(new File(PAYROLL_FILENAME).toPath()).forEach(System.out::println);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

}
