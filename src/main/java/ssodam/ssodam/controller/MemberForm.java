package ssodam.ssodam.controller;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class MemberForm {

    @NotEmpty(message = "닉네임은 필수입니다.")
    private String name;
    @NotEmpty(message = "이메일은 필수입니다.")
    private String email;
}
