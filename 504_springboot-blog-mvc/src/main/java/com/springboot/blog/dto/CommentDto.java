package com.springboot.blog.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentDto {
    private Long id;

    @NotEmpty(message = "{errors.name.required}")
    private String name;

    @NotEmpty(message = "{errors.email.required}")
    @Email(message = "{errors.email.invalid}")
    private String email;

    @NotEmpty(message = "{errors.content.required}")
    private String content;

    private LocalDateTime createdOn;

    private LocalDateTime updatedOn;
}
