package com.luv2code.springdemo.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.luv2code.springdemo.entity.Customer;
import com.luv2code.springdemo.util.SortUtils;

//dont forget repo so spring knows its a DAO
@Repository
public class CustomerDAOImpl implements CustomerDAO {
	
	//need inject session factory
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	//removed transactional which is now in the service layer. so it will start and end transaction.
	public List<Customer> getCustomers() {
		
		//get current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		//create query - now have query handle sort by last name
		Query<Customer> theQuery = currentSession.createQuery("from Customer order by lastName", Customer.class);
		//get resulting list from query
		List<Customer> customers = theQuery.getResultList();
		//return list of customers
		
		return customers;
	}

	@Override
	//transactional annotated in service which applies here
	public void saveCustomer(Customer theCustomer) {
		
		//get current hib sess
		Session session = sessionFactory.getCurrentSession();
		//save customer - now can be updated if customer already exists
		session.saveOrUpdate(theCustomer);
	}

	@Override
	public Customer getCustomer(int theId) {
		//current hibernate session
		Session session = sessionFactory.getCurrentSession();
		
		//query to find customer using id-- dont ahve to use query can just use
		//get, provide the class of object for which I'm searching and its passed
		//id
		Customer theCustomer = session.get(Customer.class, theId);
		
		return theCustomer;
	}

	@Override
	public void deleteCustomer(int theId) {
		
		//get hibernate session
		Session session = sessionFactory.getCurrentSession();
		//delete object with primary key
		Query theQuery = 
				session.createQuery("delete from Customer where id=:customerId");
		theQuery.setParameter("customerId", theId);
		
		theQuery.executeUpdate();
	}

	@Override
	public List<Customer> searchCustomers(String theSearchName) {
		
		Session session = sessionFactory.getCurrentSession();
		
		Query theQuery = null;
		//ensure not empty
		if(theSearchName != null && theSearchName.trim().length() > 0) {
			theQuery = session.createQuery("from Customer where lower(firstName) like :theName or lower(lastName) like :theName", Customer.class);
			theQuery.setParameter("theName", "%" + theSearchName + "%");
		}
		else {
			//empty
			theQuery = session.createQuery("from Customer", Customer.class);
		}
		
		//perform query
		List<Customer> customers = theQuery.getResultList();
		
		return customers;
	}

	@Override
	public List<Customer> getCustomers(int theSortField) {
		
		Session session = sessionFactory.getCurrentSession();
		
		String theFieldName = null;
		
		// which field?
		switch(theSortField) {
		case SortUtils.FIRST_NAME:
			theFieldName = "firstName";
			break;
		
		case SortUtils.LAST_NAME:
			theFieldName = "lastName";
			break;
		
		case SortUtils.EMAIL:
			theFieldName = "email";
			break;
		
		default:
			// no match
			theFieldName = "lastName";
		}
		
		//cleaner with  query string defined separately?
		String queryString = "from Customer order by " + theFieldName;
		
		Query<Customer> theQuery = session.createQuery(queryString, Customer.class);
		
		List<Customer> customers = theQuery.getResultList();
		return customers;
	}

}
