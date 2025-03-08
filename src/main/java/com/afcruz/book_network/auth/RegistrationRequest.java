package com.afcruz.book_network.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class RegistrationRequest {
    @NotEmpty(message = "User first name is mandatory")
    @NotBlank(message = "User first name is mandatory")
    private String firstname;
    @NotEmpty(message = "User last name is mandatory")
    @NotBlank(message = "User last name is mandatory")
    private String lastname;
    @Email
    @NotEmpty(message = "Email is mandatory")
    @NotBlank(message = "Email is mandatory")
    private String email;
    @NotEmpty(message = "Password is mandatory")
    @NotBlank(message = "Password is mandatory")
    @Size(min = 8, max = 32, message = "Password must between 8 and 32 chars")
    private String password;
    private LocalDate birthDate;
}
