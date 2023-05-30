package com.example.mongodbapi.response;

import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiResponse<T> {
    private String message = "Process successfully";
    private LocalDateTime localDateTime;
    private T data;

}
