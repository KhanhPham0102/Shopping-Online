package com.app.dto;

import com.app.model.Store;
import com.app.response.BaseResponse;
import lombok.Data;

import java.util.List;

@Data
public class UserDetailDto {

    private Integer id;

    private String userName;

    private String firstName;

    private String lastName;

    private String email;

    private String address;

    private Integer balance;
}
