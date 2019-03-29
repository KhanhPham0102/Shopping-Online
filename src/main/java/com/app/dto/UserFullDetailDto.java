package com.app.dto;

import lombok.Data;

@Data
public class UserFullDetailDto {

    private Integer id;

    private String userName;

    private String passWord;

    private String firstName;

    private String lastName;

    private String email;

    private String address;

    private Integer balance;
}
