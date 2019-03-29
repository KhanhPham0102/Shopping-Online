package com.app.controller;

import com.app.dto.UserDto;
import com.app.response.BaseResponse;
import com.app.security.JwtGenerator;
import com.app.service.KhanhService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.app.constants.MessageConstants.LOG_FALSE;
import static com.app.constants.MessageConstants.LOG_SUCCESS;

@RestController
@RequestMapping("")
public class LoginController {

    @Autowired
    private KhanhService khanhService;

    @PostMapping("login")
    public BaseResponse logIn(@RequestBody UserDto userDto) {

        if (!this.khanhService.login(userDto)) {

            return BaseResponse.createErrorResponse(500, LOG_FALSE);
        }

        JwtGenerator jwtGenerator = new JwtGenerator();

        String token = jwtGenerator.generate(userDto);

        this.khanhService.getUserNameByToken(token);

        return BaseResponse.createBaseResponse(token, LOG_SUCCESS);
    }

    @PostMapping("logout")
    public BaseResponse logOut() {

        return BaseResponse.createSuccessResponse(LOG_SUCCESS);
    }
}
