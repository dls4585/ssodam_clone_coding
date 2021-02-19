package ssodam.ssodam.controller;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class PasswordForm {
    @NotEmpty(message = "필수입니다.")
    private String password;
    @NotEmpty(message = "필수입니다.")
    private String newPassword;
    @NotEmpty(message = "필수입니다.")
    private String retype;
}
