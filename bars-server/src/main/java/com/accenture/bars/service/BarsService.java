package com.accenture.bars.service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.accenture.bars.domain.Record;
import com.accenture.bars.domain.Request;
import com.accenture.bars.exception.BarsException;
import com.accenture.bars.factory.InputFileFactory;
import com.accenture.bars.file.IInputFile;
import com.accenture.bars.file.IOutputFile;
import com.accenture.bars.file.XMLOutputFileImpl;
import com.accenture.bars.repository.AccountRepository;
import com.accenture.bars.repository.BillingRepository;
import com.accenture.bars.repository.CustomerRepository;
import com.accenture.bars.repository.RequestRepository;
import com.accenture.bars.domain.Account;
import com.accenture.bars.domain.Billing;
import com.accenture.bars.domain.Customer;

@Path("/bars")
public class BarsService {

	private static Logger log = LoggerFactory
			.getLogger(BarsService.class);

	@Autowired
	AccountRepository accountRepository;
	
	@Autowired
	BillingRepository billingRepository;
	
	@Autowired
	CustomerRepository customerRepository;
	
	
	@Autowired
	RequestRepository requestRepository;
	IInputFile inputFile;
	IOutputFile outputFile;

	// http://localhost:1998/bars/execute?file=FILE
	@GET
	@Path("/execute")
	@Produces(MediaType.APPLICATION_JSON)
	public Response execute(@QueryParam("file") String file)
			throws JSONException {
		log.info("Access /execute");

		JSONObject json = new JSONObject();

		inputFile = InputFileFactory.getInstance().getInputFile(new File(file));
                                                                                                                                                                                                                                                                                                                                                                  
		if ("".equals(file)) {
			json.put("ERROR", BarsException.PATH_DOES_NOT_EXIST);
			return Response.status(405).type(MediaType.APPLICATION_JSON)
					.entity(json.toString()).build();
		} else if (null == inputFile) {
			json.put("ERROR", BarsException.NO_SUPPORT_FILE);
			return Response.status(405).type(MediaType.APPLICATION_JSON)
					.entity(json.toString()).build();
		}

		inputFile.setFile(new File(file));

		try {
			List<Request> requests = inputFile.readFile();
			if (requests.isEmpty()) {
				json.put("ERROR", BarsException.NO_RECORDS_TO_READ);
				return Response.status(405).type(MediaType.APPLICATION_JSON)
						.entity(json.toString()).build();
			}

			for (Request request : requests) {
				requestRepository.save(request);
			}

			List<Record> records = fileProcessorRetrieveRecords();

			if (records.isEmpty()) {
				json.put("ERROR", BarsException.NO_RECORDS_TO_WRITE);
				return Response.status(405).type(MediaType.APPLICATION_JSON)
						.entity(json.toString()).build();
			}

			writeOutput(records);

			return Response.status(200).build();

		} catch (BarsException e) {
			json.put("ERROR", e.getMessage());
			return Response.status(405).type(MediaType.APPLICATION_JSON)
					.entity(json.toString()).build();
		}

	}
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getaccounts")
	public List<Account> getAccounts() {

		List<Account> accounts = accountRepository.findAll();
		List<Account> accountsActive = new ArrayList<>();
		for(int i=0; i<accounts.size();i++) {
			if(accounts.get(i).getIsActive().equals("Y")) {
				accountsActive.add(accounts.get(i));
			}
		}
		

		return accountsActive;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getInactiveAccounts")
	public List<Account> getInactiveAccounts() {

		List<Account> accounts = accountRepository.findAll();
		List<Account> accountsActive = new ArrayList<>();
		for(int i=0; i<accounts.size();i++) {
			if(accounts.get(i).getIsActive().equals("N")) {
				accountsActive.add(accounts.get(i));
			}
		}
		

		return accountsActive;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getbilling")
	public List<Billing> getBilling() {

//		List<Billing> billing = billingRepository.findAll();
//		
//
//		return billing;
		List<Account> account = accountRepository.findAll();
		List<Billing> billing = billingRepository.findAll();
		List<Billing> isActive = new ArrayList<>();
		
		for(int i = 0; i < billing.size(); i++) {
			
			for(int j = 0; j < account.size() ; j++) {
				
				if(billing.get(i).getAccount().getAccountId() == account.get(j).getAccountId() ) {
					String status = account.get(j).getIsActive();
					if(status.equalsIgnoreCase("y")) {
						isActive.add(billing.get(i));
					}
					
				}
				
			}
			
		}
		return isActive;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getbillingInactive")
	public List<Billing> getBillingInactive() {

//		List<Billing> billing = billingRepository.findAll();
//		
//
//		return billing;
		List<Account> account = accountRepository.findAll();
		List<Billing> billing = billingRepository.findAll();
		List<Billing> isActive = new ArrayList<>();
		
		for(int i = 0; i < billing.size(); i++) {
			
			for(int j = 0; j < account.size() ; j++) {
				
				if(billing.get(i).getAccount().getAccountId() == account.get(j).getAccountId() ) {
					String status = account.get(j).getIsActive();
					if(status.equalsIgnoreCase("n")) {
						isActive.add(billing.get(i));
					}
					
				}
				
			}
			
		}
		return isActive;
	}
	
	@GET
	@Path("/getcustomersActive")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Customer> getActiveCustomers(){
		List<Customer> customers = customerRepository.findAll();
		List<Customer> activeCustomers = new ArrayList<>();
		for(int i=0; i<customers.size();i++) {
			if(customers.get(i).getStatus().equals("Y")){
				activeCustomers.add(customers.get(i));
			}
		}
		return activeCustomers;
		
	}
	
	@GET
	@Path("/getcustomersInactive")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Customer> getCustomersInactive(){
		List<Customer> customers = customerRepository.findAll();
		List<Customer> activeCustomers = new ArrayList<>();
		for(int i=0; i<customers.size();i++) {
			if(customers.get(i).getStatus().equals("N")){
				activeCustomers.add(customers.get(i));
			}
		}
		return activeCustomers;
		
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getcustomers")
	public List<Customer> getCustomers() {

		List<Customer> customer = customerRepository.findAll();
		

		return customer;
	}
	

	// http://localhost:1998/bars/getrecords
	@GET
	@Path("/getrecords")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Record> getRecords() {
		log.info("Access /getrecords");
		List<Record> records = fileProcessorRetrieveRecords();
		requestRepository.deleteAll();
		return records;
	}
	public List<Record> fileProcessorRetrieveRecords() {
		log.info("Access /fileProcessorRetrieveRecords");
		List<Object[]> objectRecords = requestRepository.findRecords();
		List<Record> records = new ArrayList<>();
		for (Object[] object : objectRecords) {
			records.add(new Record((int) object[0],
					(java.sql.Date) object[1], (java.sql.Date) object[2],
					(String) object[3], (String) object[4],
					(double) object[5]));
		}
		return records;
	}
	
	
	
	@DELETE
	@Path("/deleteacc/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteAccountId(@PathParam("id") int id) throws JSONException {

		accountRepository.delete(accountRepository.findByAccountId(id));
		JSONObject response = new JSONObject();
		response.put("INFO", "Successfully Deleted Account");

		return Response.status(200).type(MediaType.APPLICATION_JSON)
				.entity(response.toString()).build();

	}
	
	@DELETE
	@Path("/deletebill/{billingid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteBill(@PathParam("billingid") int billingid) throws JSONException {

		billingRepository.delete(billingRepository.findByBillingId(billingid));
		JSONObject response = new JSONObject();
		response.put("INFO", "Successfully Deleted Account");

		return Response.status(200).type(MediaType.APPLICATION_JSON)
				.entity(response.toString()).build();

	}
	
	@DELETE
	@Path("/deletecustomer/{customerId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteCustomer(@PathParam("customerId") int customerId) throws JSONException {

		customerRepository.delete(customerRepository.findByCustomerId(customerId));
		JSONObject response = new JSONObject();
		response.put("INFO", "Successfully Deleted Account");

		return Response.status(200).type(MediaType.APPLICATION_JSON)
				.entity(response.toString()).build();

	}
	


//	// http://localhost:1997/crud/getaccount/username
//	@GET
//	@Produces(MediaType.APPLICATION_JSON)
//	@Path("/getbilling/{billingid}")
//	public Response getAccount(@PathParam("billingid") int billingid) throws JSONException {
//
//		//List<Billing> bill = billingRepository.findByBillingId(billingid);
//		
//		Billing billing = (Billing) billingRepository.findByBillingId(billingid);
//		JSONObject jsonObject = new JSONObject();
//		jsonObject.put("billingCycle", billing.getBillingCycle());
//		jsonObject.put("billingMonth", billing.getBillingMonth());
//		jsonObject.put("amount", billing.getAmount());
//		jsonObject.put("startDate", billing.getStartDate());
//		jsonObject.put("endDate", billing.getEndDate());
//		jsonObject.put("lastEdited", billing.getLastEdited());
//
//		return Response.status(200).entity(jsonObject.toString()).type(MediaType.APPLICATION_JSON).build();
//
//	}
	
	
	//////////////////////////////////////////////////////////////////
	///////////////////	EDIT	//////////////////////////////////////
	/////////////////////////////////////////////////////////////////
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getaccounts/{accountId}")
	public Response getAccount(@PathParam("accountId") int accountId) throws JSONException {
		Account account = accountRepository.findByaccountId(accountId);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("accountId", account.getAccountId());
		jsonObject.put("accountName", account.getAccountName());
		jsonObject.put("dateCreated", account.getDateCreated());
		jsonObject.put("isActive", account.getIsActive());
		jsonObject.put("lastEdited", account.getLastEdited());
 
		return Response.status(200).entity(jsonObject.toString()).type(MediaType.APPLICATION_JSON).build();
 
	}
	//////////////////////////////////////////////////////////
	@PUT
	@Path("/updatebarsaccount")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateAccount(Account account) throws JSONException {
		JSONObject response = new JSONObject();
		System.out.println("RAZER: " + account.getAccountId());
		Account tempAcct = accountRepository.findByaccountId(account.getAccountId());
		System.out.println(tempAcct.getAccountId() + "   " + tempAcct.getDateCreated());
		account.setDateCreated(tempAcct.getDateCreated());
		
			System.out.println("INITIAL");
			System.out.println("SDAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA: " + account.getIsActive());
			accountRepository.save(account);
			System.out.println("SDAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA: " + account.getIsActive());
			System.out.println("FINAL");
			response.put("INFO", "Successfully Updated Account!");
			return Response.status(200).entity(response.toString())
					.type(MediaType.APPLICATION_JSON).build();
		
 
	}
	/////////////////////////////////////////////////////////////////
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getcustomers/{customerId}")
	public Response getCustomer(@PathParam("customerId") int customerId) throws JSONException {
		Customer customer = customerRepository.findBycustomerId(customerId);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("customerId", customer.getCustomerId());
		jsonObject.put("firstName", customer.getFirstName());
		jsonObject.put("lastName", customer.getLastName());
		jsonObject.put("address", customer.getAddress());
		jsonObject.put("status", customer.getStatus());
		jsonObject.put("lastEdited", customer.getLastEdited());
 
		return Response.status(200).entity(jsonObject.toString()).type(MediaType.APPLICATION_JSON).build();
 
	}
	
	@PUT
	@Path("/updatecustomeraccount")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateCustomer(Customer customer) throws JSONException {
		JSONObject response = new JSONObject();
		System.out.println("RAZER: " + customer.getCustomerId());
		Customer tempAcct = customerRepository.findBycustomerId(customer.getCustomerId());
		System.out.println(tempAcct.getCustomerId() + "   " + tempAcct.getDateCreated());
		customer.setDateCreated(tempAcct.getDateCreated());
		
			System.out.println("INITIAL");
			System.out.println("SDAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA: " + customer.getStatus());
			customerRepository.save(customer);
			System.out.println("SDAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA: " + customer.getStatus());
			System.out.println("FINAL");
			response.put("INFO", "Successfully Updated Customer!");
			return Response.status(200).entity(response.toString())
					.type(MediaType.APPLICATION_JSON).build();
		
 
	}
	

	public void writeOutput(List<Record> records) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"MMddyyyy_HHmmss");
		String date = dateFormat.format(new Date());

		outputFile = new XMLOutputFileImpl();
		outputFile.setFile(new File("C:/BARS/Report/BARS_Report-" + date
				+ ".xml"));
		outputFile.writeFile(records);
	}
	
	
	
	

}
