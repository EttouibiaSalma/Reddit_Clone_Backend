package com.reddit.reddit_clone.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogoutRequest {
    @NotBlank
    private String refreshToken;
}
