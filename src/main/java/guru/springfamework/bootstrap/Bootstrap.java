package guru.springfamework.bootstrap;

import guru.springfamework.domain.Category;
import guru.springfamework.domain.Customer;
import guru.springfamework.domain.Vendor;
import guru.springfamework.repositories.CategoryRepository;
import guru.springfamework.repositories.CustomerRepository;
import guru.springfamework.repositories.VendorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Created by jt on 9/24/17.
 */
@Component
public class Bootstrap implements CommandLineRunner{

    private CategoryRepository categoryRespository;
    private CustomerRepository customerRepository;
    private VendorRepository vendorRepository;

    public Bootstrap(CategoryRepository categoryRespository, CustomerRepository customerRepository, VendorRepository vendorRepository) {
        this.categoryRespository = categoryRespository;
        this.customerRepository = customerRepository;
        this.vendorRepository = vendorRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        loadCategories();
        loadCustomers();
        loadVendors();
    }

    private void loadCustomers() {
        // Customers data bootstrap
        Customer susan = new Customer();
        susan.setId(1l);
        susan.setFirstname("Susan");
        susan.setLastname("Tanner");
        //susan.setCustomer_url("/shop/customer/624"); // done automaticaly in customerServiceImpl
        customerRepository.save(susan);

        Customer sergej = new Customer();
        sergej.setId(2l);
        sergej.setFirstname("Sergej");
        sergej.setLastname("Horvat");
        customerRepository.save(sergej);

        System.out.println("Customers loaded = " + customerRepository.count());
    }

    private void loadCategories() {
        Category fruits = new Category();
        fruits.setName("Fruits");

        Category dried = new Category();
        dried.setName("Dried");

        Category fresh = new Category();
        fresh.setName("Fresh");

        Category exotic = new Category();
        exotic.setName("Exotic");

        Category nuts = new Category();
        nuts.setName("Nuts");

        categoryRespository.save(fruits);
        categoryRespository.save(dried);
        categoryRespository.save(fresh);
        categoryRespository.save(exotic);
        categoryRespository.save(nuts);
        System.out.println("Data Loaded = " + categoryRespository.count() );
    }

    private void loadVendors(){
        Vendor pario = new Vendor();
        pario.setName("Pario");
        //pario.setId(1L);
        vendorRepository.save(pario);

        Vendor ats = new Vendor();
        ats.setName("Ante tata servis");
        //ats.setId(2L);
        vendorRepository.save(ats);

        System.out.println("Vendors loaded = " + vendorRepository.count());
    }
}
