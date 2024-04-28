package Customer.customerdetails.Service;

import Customer.customerdetails.Model.Customer;

import java.util.List;

public interface CustomerServiceInterface {

    List<Customer> getAllCustomers();
    Customer getCustomerById(int customerId);

    boolean deleteCustomerById(int customerId);

     Customer updatebyid(int customerId, Customer customer);

    Customer savecustomer(Customer c);

}
