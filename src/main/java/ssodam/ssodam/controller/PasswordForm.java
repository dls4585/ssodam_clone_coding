package ssodam.ssodam.controller;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class PasswordForm {

    @NotEmpty
    private String originPassword;
    @NotEmpty
    private String newPassword;
    @NotEmpty
    private String repeat;
}
