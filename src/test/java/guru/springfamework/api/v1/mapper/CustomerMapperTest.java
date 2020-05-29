package guru.springfamework.api.v1.mapper;


import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.domain.Category;
import guru.springfamework.domain.Customer;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CustomerMapperTest {

    public static final String NAME="Susan";
    public static final long ID = 1L;

    CustomerMapper customerMapper = CustomerMapper.INSTANCE;

    @Test
    public void customerToCustomerDTO() throws Exception{

        // given
        Customer customer = new Customer();
        customer.setFirstname(NAME);
        customer.setId(ID);

        // when
        CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);

        // then
        assertEquals(Long.valueOf(ID),customerDTO.getId());
        assertEquals(NAME, customerDTO.getFirstname());

    }
}
