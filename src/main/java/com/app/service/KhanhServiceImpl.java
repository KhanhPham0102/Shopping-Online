package com.app.service;

import com.app.dto.*;
import com.app.exception.MyException;
import com.app.model.Product;
import com.app.model.Store;
import com.app.model.User;
import com.app.repository.ProductRepository;
import com.app.repository.StoreRepository;
import com.app.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.NotAcceptableStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.app.constants.MessageConstants.*;

@Service
public class KhanhServiceImpl implements KhanhService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private ProductRepository productRepository;

    private static Integer getUserIdFromToken;

    private boolean checkPassword(String password) {

        // A password must have at least eight characters
        if (password.length() < 8) return false;

        int numCount = 0;

        for (int i = 0; i < password.length(); i++) {

            // A password consists of only letters and digits
            if (!Character.isLetterOrDigit(password.charAt(i))) return false;
            else {
                if (Character.isDigit((int)password.charAt(i))) {

                    numCount++;
                }
            }
        }

        // A password must contain at least two digits
        return (numCount >= 2);
    }

    private void compareId(Integer id, Integer ID) {

        if(!id.equals(ID)) {

            throw new AuthorizationServiceException(USER_UNAUTHORIZED);
        }
    }

    private void comparePassword(String password) {

        if(!checkPassword(password)) {

            throw new MyException("PassWord is wrong format");
        }
    }

    private void checkAuthorized(Integer id) {

        Integer ID = Objects.requireNonNull(this.storeRepository.findById(Objects.requireNonNull(
                this.productRepository.findById(id).orElse(null))
                .getStoreId()).orElse(null))
                .getUserId();

        compareId(ID, getUserIdFromToken);
    }

    private void checkFoundAndAuthored(Integer id) {

        if (this.productRepository.findById(id).orElse(null) == null) {

            throw new MyException(PRODUCT_NOT_FOUND);
        }

        checkAuthorized(id);
    }

    private void checkUsername(String username) {

        if (this.userRepository.findByUserName(username).orElse(null) != null) {

            throw new MyException(NAME_USER_AVAILABLE);
        }
    }

    private void checkEmail(String email) {

        if (this.userRepository.findByEmail(email).orElse(null) != null) {

            throw new MyException(EMAIL_USER_AVAILABLE);
        }
    }

    @Override
    public void getUserNameByToken(String token) {

        Claims claims = Jwts.parser()
                .setSigningKey("youtube")
                .parseClaimsJws(token)
                .getBody();

        getUserIdFromToken = Objects.requireNonNull(this.userRepository.findByUserName(claims.getSubject())
                .orElse(null))
                .getId();
    }

    @Override
    public boolean login(UserDto userDto) {

        return this.userRepository.countByUserNameAndPassWord(userDto.getUserName(), userDto.getPassWord()) != 0;
    }

    @Override
    public UserDetailDto findUserByUserName() {

        try {
            return new ModelMapper().map(Objects.requireNonNull(this.userRepository.findById(getUserIdFromToken).orElse(null)),
                    UserDetailDto.class);
        } catch (Exception e) {

            throw new IllegalArgumentException(USER_NOT_FOUND);
        }
    }

    @Override
    public String addUser(UserFullDetailDto userFullDetailDto) {

        comparePassword(userFullDetailDto.getPassWord());

        checkUsername(userFullDetailDto.getUserName());

        checkEmail(userFullDetailDto.getEmail());

        try {

            this.userRepository.save(new ModelMapper().map(userFullDetailDto, User.class));
        } catch (Exception e) {

                throw new MyException(DATA_NOT_NULL);
        }

        return "Add Success";
    }

    @Override
    public String updateUserById(UserFullDetailDto userFullDetailDto,
                                       Integer id) {

        compareId(id, getUserIdFromToken);

        if (userFullDetailDto.getUserName() != null) {

            throw new NotAcceptableStatusException(USERNAME_CHANGE);
        }

        if(!Objects.requireNonNull(this.userRepository
                .findById(id)
                .orElse(null))
                .getEmail()
                .equals(userFullDetailDto
                        .getEmail())) {

            checkEmail(userFullDetailDto.getEmail());
        }

        userFullDetailDto.setUserName(Objects.requireNonNull(this.userRepository.findById(id)
                .orElse(null))
                .getUserName());

        userFullDetailDto.setId(id);

        try {

            comparePassword(userFullDetailDto.getPassWord());

            this.userRepository.save(new ModelMapper().map(userFullDetailDto, User.class));
        } catch (Exception e) {

            throw new MyException(DATA_NOT_NULL);
        }

        return "Update Success";
    }

    @Override
    public String deleteUserById(Integer id) {

        compareId(id,getUserIdFromToken);

        try {

            this.userRepository.deleteById(id);
        } catch (Exception e) {

            throw new MyException(USER_NOT_FOUND);
        }

        return DELETE_SUCCESS;
    }

    @Override
    public String findStoreByIdAndUserId(String id,
                                               String userId) {

        List<Store> storeList = new ArrayList<>();

        if (id != null && userId != null) {

            storeList.add(this.storeRepository.findByIdAndUserId(Integer.parseInt(id), Integer.parseInt(userId)).orElse(null));
        }

        if (id != null && userId == null) {

            storeList.add(this.storeRepository.findById(Integer.parseInt(id)).orElse(null));
        }

        if (id == null && userId != null) {

            storeList = this.storeRepository.findByUserId(Integer.parseInt(userId));
        }

        if (id == null && userId == null) {

            storeList = this.storeRepository.findAll();
        }

        if (id == null) {

            return new ModelMapper().map(storeList,
                    new TypeToken<List<StoreDto>>() {}.getType());
        }

        return new ModelMapper().map(storeList,
                new TypeToken<List<StoreFullDetailDto>>() {}.getType());
    }

    @Override
    public String addStore(StoreFullDetailDto storeFullDetailDto) {

        Store store = this.storeRepository.findByName(storeFullDetailDto.getName()).orElse(null);

        if (store != null) {

            throw new MyException(NAME_STORE_AVAILABLE);
        }

        storeFullDetailDto.setUserId(getUserIdFromToken);

        if (storeFullDetailDto.getUserId() == null) {

            throw new AuthorizationServiceException(USER_UNAUTHORIZED);
        }

        try {

            store = new ModelMapper().map(storeFullDetailDto, Store.class);

            this.storeRepository.save(store);
        } catch (Exception e){

            throw new MyException(DATA_NOT_NULL);
        }

        return "Add Success";
    }

    @Override
    public List<ProductDto> findAllProduct() {

        return new ModelMapper().map(this.productRepository.findAll(),
                new TypeToken<List<ProductDto>>() {}.getType());
    }

    @Override
    public ProductFullDetailDto findProductById(Integer id) {

        return new ModelMapper().map(this.productRepository.findById(id),
                ProductFullDetailDto.class);
    }

    @Override
    public List<ProductDto> findProductByStoreId(Integer storeId) {

        return new ModelMapper().map(this.productRepository.findByStoreId(storeId),
                new TypeToken<List<ProductDto>>() {}.getType());
    }

    @Override
    public String addProduct(ProductFullDetailDto productFullDetailDto) {

        Store store = this.storeRepository.findById(productFullDetailDto
                .getStoreId()).orElse(null);

        Integer userID;

        if (store != null) {

            userID = store.getUserId();
        } else {

            throw new MyException("Store is not available");
        }

        compareId(userID, getUserIdFromToken);

        try {

            Product product = this.productRepository.findByName(productFullDetailDto.getName()).orElse(null);

            if (product != null) {

                throw new MyException(NAME_PRODUCT_AVAILABLE);
            }

            product = new ModelMapper().map(productFullDetailDto, Product.class);

            this.productRepository.save(product);
        } catch (NullPointerException e) {

            throw new MyException(DATA_NOT_NULL);
        }

        return "Add Success";
    }

    @Override
    public String updateProductById(ProductFullDetailDto productFullDetailDto, Integer id) {

        checkFoundAndAuthored(id);

        if(!Objects.requireNonNull(this.productRepository
                .findById(id)
                .orElse(null))
                .getName()
                .equals(productFullDetailDto
                        .getName())) {

            if (this.productRepository.findByName(productFullDetailDto.getName()).orElse(null) != null) {

                throw new MyException(NAME_PRODUCT_AVAILABLE);
            }
        }

        try {

            productFullDetailDto.setStoreId(Objects.requireNonNull(this.productRepository.findById(id).orElse(null)).getStoreId());

            productFullDetailDto.setId(id);

            this.productRepository.save(new ModelMapper().map(productFullDetailDto, Product.class));
        } catch (Exception e) {

            throw new MyException(DATA_NOT_NULL);
        }

        return "Update Success";
    }

    @Override
    public String deleteProductById(Integer id) {

        checkFoundAndAuthored(id);

        try {

            this.productRepository.deleteById(id);
        } catch (RuntimeException e) {

            throw new MyException(PRODUCT_NOT_FOUND);
        }

        return DELETE_SUCCESS;
    }
}
