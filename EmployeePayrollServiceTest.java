package com.jdbc;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Assert;
import org.junit.jupiter.api.Test;


//import static EmployeePayrollService.IOService.FILE_IO;

class EmployeePayrollServiceTest {

	@Test
	public void given3Employee() { 
		EmployeePayrollData[] arrayOfEmps = {
				new EmployeePayrollData(1, "Bill", 100000.0),
				new EmployeePayrollData(2, "Terisa", 300000.0),
				new EmployeePayrollData(3, "Charlie", 300000.0)
		};	
		EmployeePayrollService employeePayrollService;
		employeePayrollService = new EmployeePayrollService();
		employeePayrollService.writeEmployeePayrollData(EmployeePayrollService.IOService.FILE_IO);
		employeePayrollService.printData(EmployeePayrollService.IOService.FILE_IO);
		long entries = employeePayrollService.countEntries(EmployeePayrollService.IOService.FILE_IO);
		Assert.assertEquals(3, entries);
	}
	
	@Test
	public void givenEmployeePayrollInDB_WhenRetrived_ShouldMatchEmployeeCount() { 
		EmployeePayrollService employeePayrollService = new EmployeePayrollService();
		List<EmployeePayrollData> employeePayrollData = employeePayrollService.readEmployeePayrollData(EmployeePayrollService.IOService.DB_IO);
		Assert.assertEquals(3,employeePayrollData.size());
	}

	@Test
	public void givenNewSalaryForEmployee_ShouldUpdated_ShouldSyncWithDB() { 
		EmployeePayrollService employeePayrollService = new EmployeePayrollService();
		List<EmployeePayrollData> employeePayrollData = employeePayrollService.readEmployeePayrollData(EmployeePayrollService.IOService.DB_IO);	
		employeePayrollService.updateEmployeeSalary("Terisa",3000000.00);
		boolean result = employeePayrollService.checkEmployeePayrollInSyncWithDB("Terisa");
		Assert.assertTrue(result);
	}
}
