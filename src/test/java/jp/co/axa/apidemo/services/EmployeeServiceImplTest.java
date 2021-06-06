package jp.co.axa.apidemo.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.repositories.EmployeeRepository;

class EmployeeServiceImplTest {
	
	@InjectMocks
	EmployeeServiceImpl EmplyeeService;
	
	@Mock
	EmployeeRepository employeeRepository;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void retrieveEmployees() throws Exception {
		List<Employee> employees = new ArrayList<Employee>();
		employees.add(new Employee(1,"Sam", 6000, "IT"));
		employees.add(new Employee(2,"Ann", 3000, "Marketing"));
		employees.add(new Employee(3,"David", 2000, "Accounting"));
		when(employeeRepository.findAll()).thenReturn(employees);
		
		List<Employee> result = EmplyeeService.retrieveEmployees();
		assertEquals(3, result.size());
	}
	
	@Test
	public void testGetEmployee() throws Exception {
		Optional<Employee> optEmp = Optional.of(new Employee(1,"Sam", 6000, "IT"));
		when(employeeRepository.findById(1L)).thenReturn(optEmp);
		Employee result = EmplyeeService.getEmployee((long) 1);
		assertEquals(1, result.getId());
		assertEquals("Sam", result.getName());
		assertEquals(6000, result.getSalary());
		assertEquals("IT", result.getDepartment());
	}
	
	@Test
	public void saveEmployee() throws Exception {
		Employee emp = new Employee(5,"David", 7000, "Marketing");
		when(employeeRepository.save(emp)).thenReturn(emp);
		Employee result = EmplyeeService.saveEmployee(emp);
		assertEquals(5, result.getId());
		assertEquals("David", result.getName());
		assertEquals(7000, result.getSalary());
		assertEquals("Marketing", result.getDepartment());
	}
	
	@Test
	public void updateEmployee() throws Exception {
		Employee emp = new Employee(5,"David", 7000, "Marketing");
		emp.setName("Ann");
		when(employeeRepository.save(emp)).thenReturn(emp);
		Employee result = EmplyeeService.saveEmployee(emp);
		assertEquals("Ann", result.getName());
	}
	
	@Test
	public void deleteEmployee() throws Exception {
		Employee emp = new Employee(5,"David", 7000, "Marketing");
		EmplyeeService.deleteEmployee((long) 5);
		verify(employeeRepository, times(1)).deleteById((long) 5);
	}

}
