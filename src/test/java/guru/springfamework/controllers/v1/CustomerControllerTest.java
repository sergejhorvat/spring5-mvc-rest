package guru.springfamework.controllers.v1;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.controllers.RestResponseEntityExceptionHandler;
import guru.springfamework.repositories.CustomerRepository;
import guru.springfamework.services.CustomerService;

import guru.springfamework.services.ResourceNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CustomerControllerTest extends AbstractRestControllerTest{

    public static final String FIRSTNAME = "Susan";
    public static final String LASTNAME = "Tanner";
    public static final String BASE_URL = "/api/v1/customers/";


    @Mock
    CustomerService customerService;
    CustomerRepository customerRepository;

    @InjectMocks
    CustomerController customerController;
    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception{
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(customerController)
                .setControllerAdvice(new RestResponseEntityExceptionHandler())
                .build();
    }

    @Test
    public void testListCustomers() throws Exception{
        CustomerDTO customer1 = new CustomerDTO();
        customer1.setFirstname(FIRSTNAME);
        customer1.setLastname(LASTNAME);
        customer1.setId(1l);

        CustomerDTO customer2 = new CustomerDTO();
        customer2.setFirstname("Sergej");
        customer2.setLastname("Horvat");
        customer2.setId(2l);

        List<CustomerDTO> customers = Arrays.asList(customer1,customer2);

        when(customerService.getAllCustomers()).thenReturn(customers);

        mockMvc.perform(get(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customers",hasSize(2)));
    }

    @Test
    public void testGetCustomerById() throws Exception{

        //given
        CustomerDTO customer1 = new CustomerDTO();
        customer1.setFirstname("Sergej");
        customer1.setLastname("Horvat");
        customer1.setCustomerUrl(BASE_URL + "1");

        when(customerService.getCustomerById(anyLong())).thenReturn(customer1);

        // when
        mockMvc.perform(get(BASE_URL + "1")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname", equalTo("Sergej") ));
    }

    @Test
    public void createNewCustomer() throws Exception {
        //given
        CustomerDTO customer = new CustomerDTO();
        customer.setFirstname("Fred");
        customer.setLastname("Flintstone");

        CustomerDTO returnDTO = new CustomerDTO();
        returnDTO.setFirstname(customer.getFirstname());
        returnDTO.setLastname(customer.getLastname());
        returnDTO.setCustomerUrl(BASE_URL + "1");

        when(customerService.createNewCustomer(customer)).thenReturn(returnDTO);

        //when/then
        mockMvc.perform(post(BASE_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(asJsonString(customer)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstname", equalTo("Fred")))
                .andExpect(jsonPath("$.customer_url", equalTo(BASE_URL + "1")));

    }

    @Test
    public void testUpdateCustomer() throws Exception{

        //given
        CustomerDTO customer = new CustomerDTO();
        customer.setFirstname("Fredy");
        customer.setLastname("Kruger");

        CustomerDTO returnDto = new CustomerDTO();
        returnDto.setFirstname(customer.getFirstname());
        returnDto.setLastname(customer.getLastname());
        returnDto.setCustomerUrl(BASE_URL + "1");

        when(customerService.saveCustomerByDTO(anyLong(), any(CustomerDTO.class))).thenReturn(returnDto);

        // when /then
        mockMvc.perform(put(BASE_URL + "1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname", equalTo("Fredy")))
                .andExpect(jsonPath("$.lastname", equalTo("Kruger")))
                .andExpect(jsonPath("$.customer_url", equalTo(BASE_URL + "1")));
    }

    @Test
    public void testPatchCustomer() throws Exception{
        //given
        CustomerDTO customer = new CustomerDTO();
        customer.setFirstname("Fredy");

        CustomerDTO returnDto = new CustomerDTO();
        returnDto.setFirstname(customer.getFirstname());
        returnDto.setLastname("Kruger");
        returnDto.setCustomerUrl(BASE_URL + "1");

        when(customerService.patchCustomer(anyLong(), any(CustomerDTO.class))).thenReturn(returnDto);

        // when /then
        mockMvc.perform(patch(BASE_URL + "1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname", equalTo("Fredy")))
                .andExpect(jsonPath("$.lastname", equalTo("Kruger")))
                .andExpect(jsonPath("$.customer_url", equalTo(BASE_URL + "1")));
    }

    @Test
    public void testDeleteCustomer() throws Exception{

        mockMvc.perform(delete("/api/v1/customers/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(customerService).deleteCustomerById(anyLong());
    }

    @Test
    public void testNotFoundException() throws Exception{
        when(customerService.getCustomerById(anyLong())).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(get(BASE_URL + "222")
        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
