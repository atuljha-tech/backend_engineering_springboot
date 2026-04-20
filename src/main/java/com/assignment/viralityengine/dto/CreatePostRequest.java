package com.assignment.viralityengine.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatePostRequest {

    @NotNull(message = "Author ID is required")
    private Long authorId;

    @NotBlank(message = "Author type is required")
    private String authorType;

    @NotBlank(message = "Content is required")
    private String content;

}
