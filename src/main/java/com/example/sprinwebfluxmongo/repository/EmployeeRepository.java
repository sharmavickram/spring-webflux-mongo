package com.example.sprinwebfluxmongo.repository;

import com.example.sprinwebfluxmongo.domain.Employee;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface EmployeeRepository extends ReactiveCrudRepository<Employee, String> {
}