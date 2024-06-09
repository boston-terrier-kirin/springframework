package com.example.demo.controller;

import com.example.demo.entity.Employee;
import com.example.demo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/list")
    public String listEmployees(Model model) {
        List<Employee> employees = this.employeeService.findAll();
        model.addAttribute("employees", employees);

        return "employees/list-employees";
    }

    @GetMapping("/showFormForAdd")
    public String showFormForAdd(Model model) {
        Employee employee = new Employee();
        model.addAttribute("employee", employee);

        return "employees/employee-form";
    }

    @GetMapping("/showFormForUpdate")
    public String showFormForUpdate(@RequestParam("employeeId") int employeeId, Model model) {
        Employee employee = this.employeeService.findById(employeeId);
        model.addAttribute("employee", employee);

        return "employees/employee-form";
    }

    @PostMapping("/save")
    public String saveEmployee(@ModelAttribute("employee") Employee employee) {
        this.employeeService.save(employee);

        return "redirect:/employees/list";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("employeeId") int employeeId, Model model) {
       this.employeeService.deleteById(employeeId);

        return "redirect:/employees/list";
    }
}
