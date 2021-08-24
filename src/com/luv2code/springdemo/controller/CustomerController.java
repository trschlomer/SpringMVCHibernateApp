package com.luv2code.springdemo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.luv2code.springdemo.dao.CustomerDAO;
import com.luv2code.springdemo.entity.Customer;
import com.luv2code.springdemo.service.CustomerService;
import com.luv2code.springdemo.util.SortUtils;

@Controller
@RequestMapping("/customer")
public class CustomerController {
	
	//inject dao into controller - no longer use dao directly - now use service, inject
	@Autowired
	private CustomerService customerService;
	
	/*@GetMapping("/list")
	public String listCustomers(Model theModel) {
		
		//get customers from the dao-- now the service
		List<Customer> theCustomers = customerService.getCustomers();
		
		//add customers to spring mvc model - the name to give the attribute and
		//the data for attribute
		theModel.addAttribute("customers", theCustomers);
		
		return "list-customers";
	}*/
	
	@GetMapping("/showFormForAdd")
	public String showFormForAdd(Model theModel) {
		
		//create model attr to bind form data
		Customer theCustomer = new Customer();
		
		theModel.addAttribute("customer", theCustomer);
		return "customer-form";
	}
	
	@PostMapping("/saveCustomer")
	public String SaveCustomer(@ModelAttribute("customer") Customer theCustomer) {
		
		//save customer use service
		customerService.saveCustomer(theCustomer);
		
		return "redirect:/customer/list";
	}
	
	@GetMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("customerId") int theId, Model theModel) {
		// get customer from customerService
		
		Customer theCustomer = customerService.getCustomer(theId);
		//set customer as a model attribute to pre-populate the form
		theModel.addAttribute("customer", theCustomer);
		//send to form 
		
		return "customer-form";
	}
	
	@GetMapping("/delete")
	public String deleteCustomer(@RequestParam("customerId") int theId) {
		
		//delete
		customerService.deleteCustomer(theId);
		
		return "redirect:/customer/list";
	}
	
	@GetMapping("/search")
	public String searchCustomers(@RequestParam("theSearchName") String theSearchName,
									Model theModel) {
		// use service to search
		List<Customer> theCustomers = customerService.searchCustomer(theSearchName);
		//add the customers found to the model as an attribute
		theModel.addAttribute("customers", theCustomers);
		
		return "list-customers";
	}
	
	@GetMapping("/list") 
	public String listCustomers(Model theModel, @RequestParam(required=false) String sort){
		
		List<Customer> theCustomers = null;
		
		//check if sort null or not
		if (sort != null) {
			// provide the field by which to sort
			int theSortField = Integer.parseInt(sort);
			theCustomers = customerService.getCustomers(theSortField);
		}
		else {
			//default is sort by last name
			theCustomers = customerService.getCustomers(SortUtils.LAST_NAME);
		}
		
		//add to model as an attribute
		theModel.addAttribute("customers", theCustomers);
		
		return "list-customers";

	}
}
