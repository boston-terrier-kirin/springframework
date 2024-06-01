package com.example.demo.model;

import com.example.demo.validation.CourseCode;
import jakarta.validation.constraints.*;

public class Customer {
    private String firstName;

    @Size(min=1, message="requires more then 1 characters")
    @NotNull(message="is required")
    private String lastName;

    @NotNull(message="is required")
    @Min(value=0, message="must be greater then or equal to zero")
    @Max(value=10, message="must be less then or equal to 10")
    private Integer freePasses;

    @Pattern(regexp="^[a-zA-Z0-9]{5}", message="only 5 chars/digits")
    private String postalCode;

    @CourseCode(value="LUV", message="must start with LUV")
    private String courseCode;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getFreePasses() {
        return freePasses;
    }

    public void setFreePasses(Integer freePasses) {
        this.freePasses = freePasses;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", freePasses=" + freePasses +
                ", postalCode='" + postalCode + '\'' +
                ", courseCode='" + courseCode + '\'' +
                '}';
    }
}
