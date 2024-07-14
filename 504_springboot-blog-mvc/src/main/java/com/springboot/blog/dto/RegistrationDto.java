package com.springboot.blog.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegistrationDto {
    private Long id;

    @NotEmpty(message = "{errors.firstName.required}")
    private String firstName;

    @NotEmpty(message = "{errors.lastName.required}")
    private String lastName;

    @NotEmpty(message = "{errors.email.required}")
    private String email;

    @NotEmpty(message = "{errors.password.required}")
    private String password;
}
