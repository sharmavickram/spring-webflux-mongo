package com.example.sprinwebfluxmongo;

import com.example.sprinwebfluxmongo.dto.EmployeeDto;
import com.example.sprinwebfluxmongo.repository.EmployeeRepository;
import com.example.sprinwebfluxmongo.servivce.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.Collections;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmployeeControllerIntegrationTests {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private EmployeeRepository employeeRepository;

    @BeforeEach
    public void before(){
        System.out.println("Before Each Test");
        employeeRepository.deleteAll().subscribe();
    }

    @Test
    public void testSaveEmployee(){

        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setFirstName("Vikram");
        employeeDto.setLastName("Sharma");
        employeeDto.setEmail("vikram@gmail.com");

        webTestClient.post().uri("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(employeeDto), EmployeeDto.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.firstName").isEqualTo(employeeDto.getFirstName())
                .jsonPath("$.lastName").isEqualTo(employeeDto.getLastName())
                .jsonPath("$.email").isEqualTo(employeeDto.getEmail());
    }

    @Test
    public void testGetSingleEmployee(){

        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setFirstName("Vikram");
        employeeDto.setLastName("Sharma");
        employeeDto.setEmail("vikram@gmail.com");

        EmployeeDto savedEmployee = employeeService.saveEmployee(employeeDto).block();

        webTestClient.get().uri("/api/employees/{id}", Collections.singletonMap("id",savedEmployee.getId()))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.id").isEqualTo(savedEmployee.getId())
                .jsonPath("$.firstName").isEqualTo(employeeDto.getFirstName())
                .jsonPath("$.lastName").isEqualTo(employeeDto.getLastName())
                .jsonPath("$.email").isEqualTo(employeeDto.getEmail());
    }

    @Test
    public void testGetAllEmployees(){

        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setFirstName("Vikram");
        employeeDto.setLastName("Sharma");
        employeeDto.setEmail("vikram@gmail.com");

        employeeService.saveEmployee(employeeDto).block();

        EmployeeDto employeeDto1 = new EmployeeDto();
        employeeDto1.setFirstName("Manish");
        employeeDto1.setLastName("Sharma");
        employeeDto1.setEmail("manish@gmail.com");

        employeeService.saveEmployee(employeeDto1).block();

        webTestClient.get().uri("/api/employees")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(EmployeeDto.class)
                .consumeWith(System.out::println);
    }

    @Test
    public void testUpdateEmployee(){

        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setFirstName("Manish");
        employeeDto.setLastName("singh");
        employeeDto.setEmail("manish@gmail.com");

        EmployeeDto savedEmployee = employeeService.saveEmployee(employeeDto).block();

        EmployeeDto updatedEmployee = new EmployeeDto();
        updatedEmployee.setFirstName("Manish");
        updatedEmployee.setLastName("Kumar Sharma");
        updatedEmployee.setEmail("manish@gmail.com");

        webTestClient.put().uri("/api/employees/{id}", Collections.singletonMap("id", savedEmployee.getId()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(updatedEmployee), EmployeeDto.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.firstName").isEqualTo(updatedEmployee.getFirstName())
                .jsonPath("$.lastName").isEqualTo(updatedEmployee.getLastName())
                .jsonPath("$.email").isEqualTo(updatedEmployee.getEmail());
    }

    @Test
    public void testDeleteEmployee(){

        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setFirstName("Manish");
        employeeDto.setLastName("Kumar Singh");
        employeeDto.setEmail("manish@gmail.com");

        EmployeeDto savedEmployee = employeeService.saveEmployee(employeeDto).block();

        webTestClient.delete().uri("/api/employees/{id}", Collections.singletonMap("id", savedEmployee.getId()))
                .exchange()
                .expectStatus().isNoContent()
                .expectBody()
                .consumeWith(System.out::println);

    }
}