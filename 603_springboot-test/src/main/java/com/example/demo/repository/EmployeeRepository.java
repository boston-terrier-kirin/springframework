package com.example.demo.repository;

import com.example.demo.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    public Optional<Employee> findByEmail(String email);

    @Query("""
            select e from Employee e where e.firstName = ?1 and e.lastName = ?2
            """)
    public Employee findByFullName(String firstName, String lastName);

    @Query("""
            select e from Employee e where e.lastName = :familyName
            """)
    public Employee findByFamilyName(@Param("familyName") String familyName);

    @Query(nativeQuery = true,
            value = """
                select * from employees e where e.email = :email and e.first_name = :firstName and e.last_name = :lastName
            """)
    public Employee findByEmailAndFullName(@Param("email") String email, @Param("firstName") String firstName, @Param("lastName") String lastName);
}
