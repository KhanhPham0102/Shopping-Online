package com.app.dto;

import lombok.Data;

@Data
public class ProductFullDetailDto {

    private Integer id;

    private String name;

    private String description;

    private Integer price;

    private Integer storeId;
}
