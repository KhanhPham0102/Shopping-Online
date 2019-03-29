package com.app.dto;

import com.app.response.BaseResponse;
import lombok.Data;

@Data
public class ProductDto {

    private Integer id;

    private String name;

    private Integer price;

    private Integer storeId;
}
