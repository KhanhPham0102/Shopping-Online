package com.app.service;

import com.app.dto.*;
import com.app.model.User;
import com.app.response.BaseResponse;
import javassist.NotFoundException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface KhanhService {

    boolean login(UserDto userDto);

    void getUserNameByToken(String token);

    UserDetailDto findUserByUserName();

    String addUser(UserFullDetailDto userFullDetailDto);

    String updateUserById(UserFullDetailDto userFullDetailDto,
                                Integer id);

    String deleteUserById(Integer id);

    String findStoreByIdAndUserId(String id,
                                        String userId);

    String addStore(StoreFullDetailDto storeFullDetailDto);

    List<ProductDto> findAllProduct();

    ProductFullDetailDto findProductById(Integer id);

    List<ProductDto> findProductByStoreId(Integer storeId);

    String addProduct(ProductFullDetailDto productFullDetailDto);

    String updateProductById(ProductFullDetailDto productFullDetailDto,
                                   Integer id);

    String deleteProductById(Integer id);
}