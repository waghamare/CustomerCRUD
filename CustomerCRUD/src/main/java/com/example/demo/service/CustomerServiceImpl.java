package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.exception.InvalidMobileNumber;
import com.example.demo.model.Customer;
import com.example.demo.repository.CustomerRepository;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerRepository cr;
	
	
	@Override
	public void add(Customer customer) {
		// TODO Auto-generated method stub
		
		String mob = customer.getMob();
		System.out.println(mob);
		mob.trim();
		if(mob.startsWith("+91")) {
			mob=mob.substring(3);
		}
		if(mob.startsWith("91"))
		{
			mob = mob.substring(3); 
		}
		
		
		if(mob.length() == 10) {
			if(mob.charAt(0) == '0' || mob.charAt(0) == '1' || mob.charAt(0) == '2' || mob.charAt(0) == '3' 
					||mob.charAt(0) == '4' ||mob.charAt(0) == '5')
				throw new InvalidMobileNumber("Numbar must be start from 6,7,8,9");
			if(!mob.startsWith("+91")) {
				throw new InvalidMobileNumber("Mobile Number must start with +91");
			}
			for(int i=0 ; i < mob.length(); i++) {
				if(!Character.isDigit(mob.charAt(i)))
					throw new InvalidMobileNumber("Number can't start from #, $, & , etc..");
			}
			if(cr.existsById(customer.getId())) {
				throw new RuntimeException("Customer ID already exists");
			}
			if(!mob.startsWith("91")) {
				throw new InvalidMobileNumber("After +91 Number should start with 91");
			}
		}
		else
			throw new InvalidMobileNumber("After +91 Number should be 10 Digit");
		cr.save(customer); //Insert
		
	}

	@Override
	public List<Customer> display() {
		// TODO Auto-generated method stub
		return cr.findAll();//select*from Customer
	}

	@Override
	public Customer delete(Integer id) {
		// TODO Auto-generated method stub
		
		//search
		
		if(cr.findById(id).isPresent()) {
			Customer temp = cr.findById(id).get();
			cr.deleteById(id);
			return temp;
		}
		
		return null;
	}

	@Override
	public void update(Customer customer, Integer id) {
		// TODO Auto-generated method stub
		customer.setId(id);
		cr.save(customer);
	}

	@Override
	public Customer search(Integer id) {
		// TODO Auto-generated method stub
		if(cr.findById(id).isPresent()) {
			Customer temp = cr.findById(id).get();
			return temp;
		}
		
		return null;
	}

	@Override
	public void addAll(List<Customer> list) {
		// TODO Auto-generated method stub
		cr.saveAll(list);
	}

	@Override
	public Customer findByMob(String mob) {
		return cr.findByMob(mob);
	}

}
