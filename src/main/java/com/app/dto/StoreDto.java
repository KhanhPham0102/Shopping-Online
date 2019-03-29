package com.app.dto;

import com.app.response.BaseResponse;
import lombok.Data;

@Data
public class StoreDto {

    private Integer id;

    private String name;

    private Integer userId;
}
