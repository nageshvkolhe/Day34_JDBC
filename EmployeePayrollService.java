package com.jdbc;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.practice.jdbc.EmployeePayrollDBService;
import com.practice.jdbc.EmployeePayrollData;

public class EmployeePayrollService {
	
	public enum IOService {FILE_IO, DB_IO, CONSOLE_IO, REST_IO}
	
	private static List<EmployeePayrollData> employeePayrollList;
	
	private EmployeePayrollDBService employeePayrollDBService;
	
	public void EmployeePayrollDBService() {
		employeePayrollDBService = EmployeePayrollDBService.getInstance();
	}
	public EmployeePayrollService(List<EmployeePayrollData>EmployeePayrollList) {
		this();
		this.employeePayrollList = employeePayrollList;
		
	}
	
	public EmployeePayrollService() {
		// TODO Auto-generated constructor stub
	}
	public static void main(String[] args) {
		ArrayList<EmployeePayrollData> EmployeePayrollList = new ArrayList<>();
		EmployeePayrollService employeePayrollService = new EmployeePayrollService(employeePayrollList);
		Scanner consoleInputReader = new Scanner(System.in);
		employeePayrollService.readEmployePayrollData(consoleInputReader);
		employeePayrollService.writeEmployeePayrollData(IOService.CONSOLE_IO);
	}
	
	private void readEmployePayrollData(Scanner consoleInputReader) {
		System.out.println("Enter Employe ID : ");
		int id = consoleInputReader.nextInt();
		System.out.println("Enter Employee Name : ");
		String name = consoleInputReader.next();
		System.out.println("Enter Employee Salary : ");
		double salary = consoleInputReader.nextDouble();
		employeePayrollList.add(new EmployeePayrollData(id, name, salary));
	}
	public List<EmployeePayrollData> readEmployeePayrollData(IOService dbIo){
		if(dbIo.equals(IOService.DB_IO))
			this.employeePayrollList = new EmployeePayrollDBService().readData();
		return this.employeePayrollList;
	}
	
	public void writeEmployeePayrollData(IOService ioService) {
		if(ioService.equals(IOService.CONSOLE_IO))
			System.out.println("\nWriting Employee Payroll Roaster to Console\n" + employeePayrollList );
		else if(ioService.equals(IOService.FILE_IO))
				new EmployeePayrollFileIOService().writeData(employeePayrollList);
	}
	
	public void printData(IOService ioService) {
		if(ioService.equals(IOService.FILE_IO))
			new EmployeePayrollFileIOService().printData();	
	}

	public long countEntries(IOService ioService) {	
		if(ioService.equals(IOService.FILE_IO))
			return new EmployeePayrollFileIOService().countEntries();
		return 0;
	}

	
	public boolean checkEmployeePayrollInSyncWithDB(String name) {
		List<EmployeePayrollData> employeePayrollDataList =  new EmployeePayrollDBService().getEmployeePayrollData(name);
		return employeePayrollDataList.get(0).equals(getEmployeePayrollData(name));
	}
	
	public void updateEmployeeSalary(String name, double salary) {
		int result = new EmployeePayrollDBService().updateEmployeeData(name,salary);
		if (result == 0) return;
		EmployeePayrollData employeePayrollData = this.getEmployeePayrollData(name);
		if (employeePayrollData != null) employeePayrollData.salary = salary;	
	}

	private EmployeePayrollData getEmployeePayrollData(String name) {
		return this.employeePayrollList.stream()
				.filter(employeePayrollDataItem -> employeePayrollDataItem.name.equals(name))
				.findFirst()
				.orElse(null);
	}


}
