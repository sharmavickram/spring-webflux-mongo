package com.example.sprinwebfluxmongo.controller;

import com.example.sprinwebfluxmongo.dto.EmployeeDto;
import com.example.sprinwebfluxmongo.servivce.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/employees")
@AllArgsConstructor
public class EmployeeController {

    private EmployeeService employeeService;

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public Mono<EmployeeDto> saveEmployee(@RequestBody EmployeeDto employeeDto){
        System.out.println("inside post() method for creating employee......");
        return employeeService.saveEmployee(employeeDto);
    }

    @GetMapping("{id}")
    public Mono<EmployeeDto> getEmployee(@PathVariable("id") String employeeId){
        System.out.println("inside getEmployee() method to get employee by Id......");
        return employeeService.getEmployee(employeeId);
    }

    @GetMapping
    public Flux<EmployeeDto> getAllEmployees(){
        System.out.println("inside List all employee...........");
        return employeeService.getAllEmployees();
    }

    @PutMapping("{id}")
    public Mono<EmployeeDto> updateEmployee(@RequestBody EmployeeDto employeeDto,
                                            @PathVariable("id") String employeeId){
        System.out.println("updating employee...........");
        return employeeService.updateEmployee(employeeDto, employeeId);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public Mono<Void> deleteEmployee(@PathVariable("id") String employeeId){
        System.out.println("deleting employee...........");
        return employeeService.deleteEmployee(employeeId);
    }
}