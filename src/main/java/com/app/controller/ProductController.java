package com.app.controller;

import com.app.dto.ProductFullDetailDto;
import com.app.response.BaseResponse;
import com.app.service.KhanhService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("products")
public class ProductController {

    @Autowired
    private KhanhService khanhService;

    @GetMapping("")
    public BaseResponse product(@RequestParam(value = "storeID", required = false) String storeId) {

        if(storeId != null) {

            return new BaseResponse<>(this.khanhService.findProductByStoreId(Integer.parseInt(storeId)));
        }

        return new BaseResponse<>(this.khanhService.findAllProduct());
    }

    @GetMapping("/{productID}")
    public BaseResponse<ProductFullDetailDto> productId(@PathVariable(name = "productID") Integer id) {

        return new BaseResponse<>(this.khanhService.findProductById(id));
    }

    @PostMapping("")
    public BaseResponse productAdd(@RequestBody ProductFullDetailDto productFullDetailDto) {

        return BaseResponse.createSuccessResponse(this.khanhService.addProduct(productFullDetailDto));
    }

    @PutMapping("/{productID}")
    public BaseResponse productUpdate(@RequestBody ProductFullDetailDto productFullDetailDto,
                                      @PathVariable(name = "productID") Integer id) {

       return BaseResponse.createSuccessResponse(this.khanhService.updateProductById(productFullDetailDto, id));
    }

    @DeleteMapping("/{productID}")
    public BaseResponse productDelete(@PathVariable(name = "productID") Integer id) {

        return BaseResponse.createSuccessResponse(this.khanhService.deleteProductById(id));
    }
}
