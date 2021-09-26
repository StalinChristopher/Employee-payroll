package com.yml.emloyeepayroll;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EmployeePayrollService {
	private List<EmployeePayroll> employeePayrollList;
	
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
	
	public void writeEmployeePayrollData() {
		System.out.println("\nEmployee payroll data\n");
		for(EmployeePayroll item : employeePayrollList) {
			System.out.println(item);
		}
	}

}
