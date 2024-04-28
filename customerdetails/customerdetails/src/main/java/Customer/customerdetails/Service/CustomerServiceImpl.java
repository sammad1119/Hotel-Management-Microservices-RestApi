package Customer.customerdetails.Service;

import Customer.customerdetails.Exception.ResourceNotFoundException;
import Customer.customerdetails.Model.Customer;
import Customer.customerdetails.Repository.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerServiceInterface {

    @Autowired
    private CustomerRepo customerRepository;

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Customer getCustomerById(int customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new ResourceNotFoundException("Customer not found with ID: " + customerId));
        return customer;
    }


    @Override
    public boolean deleteCustomerById(int customerId) {
        if (customerRepository.existsById(customerId)) {
            customerRepository.deleteById(customerId);
            return true;
        }
        return false;
    }

    @Override
    public Customer updatebyid(int customerId, Customer customer) {
        Customer existingCustomer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with ID: " + customerId));

        existingCustomer.setCustomerName(customer.getCustomerName());
        existingCustomer.setCustomerAddress(customer.getCustomerAddress());
        existingCustomer.setCustomerPhone(customer.getCustomerPhone());
        existingCustomer.setCustomerEmail(customer.getCustomerEmail());

        return customerRepository.save(existingCustomer);
    }

    @Override
    public Customer savecustomer(Customer c) {
        return customerRepository.save(c);
    }



}
