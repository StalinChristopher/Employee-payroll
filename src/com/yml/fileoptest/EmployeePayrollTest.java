package com.yml.fileoptest;
import com.yml.emloyeepayroll.*;

import java.io.IOException;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

public class EmployeePayrollTest {
	
	/**
	 * @throws IOException
	 * Test case to verify the count of employee entries in the files, if it is equals to 3 then asserts true
	 */
	@Test
	public void given3EmployeesWhenWrittenToFileShouldMatchEmployeeEntries() throws IOException {
		EmployeePayroll[]  arrayData = {
			new EmployeePayroll(1, "John Snow", 25000 ),
			new EmployeePayroll(2, "Vincent Kompany", 40000),
			new EmployeePayroll(3, "Kevin Hart", 35000)
		};
		EmployeePayrollService employeePayrollService = new EmployeePayrollService(Arrays.asList(arrayData));
		employeePayrollService.writeEmployeePayrollFile();
		long entryCount = employeePayrollService.countLines();
		Assert.assertEquals(3, entryCount);
		
	}
}
