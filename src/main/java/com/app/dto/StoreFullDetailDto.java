package com.app.dto;

import com.app.model.Product;
import lombok.Data;

import java.util.List;

@Data
public class StoreFullDetailDto {

    private Integer id;

    private String name;

    private String description;

    private Integer userId;
}
