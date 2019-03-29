package com.app.controller;

import com.app.dto.UserDetailDto;
import com.app.dto.UserFullDetailDto;
import com.app.response.BaseResponse;
import com.app.service.KhanhService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    private KhanhService khanhService;

    @GetMapping("")
    @ResponseBody
    public BaseResponse<UserDetailDto> user() {

        return new BaseResponse<>(this.khanhService.findUserByUserName());
    }

    @PostMapping("")
    public BaseResponse userAdd(@RequestBody UserFullDetailDto userFullDetailDto) {

        return BaseResponse.createSuccessResponse(this.khanhService.addUser(userFullDetailDto));
    }

    @PutMapping("/{userId}")
    public BaseResponse userUpdateById(@RequestBody UserFullDetailDto userFullDetailDto,
                                       @PathVariable(name = "userId") Integer id) {

        return BaseResponse.createSuccessResponse(this.khanhService.updateUserById(userFullDetailDto, id));
    }

    @DeleteMapping("/{userId}")
    public BaseResponse userDelete(@PathVariable(name = "userId") Integer id) {

        return BaseResponse.createSuccessResponse(this.khanhService.deleteUserById(id));
    }
}

