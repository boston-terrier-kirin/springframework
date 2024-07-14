package com.springboot.blog.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostDto {
    private Long id;

    @NotEmpty
    private String title;

    @NotEmpty(message="{errors.content.required}")
    private String content;

    @NotEmpty(message="{errors.shortDescription.required}")
    private String shortDescription;

    private String url;

    private LocalDateTime createdOn;

    private LocalDateTime updatedOn;

    private Set<CommentDto> comments;
}
