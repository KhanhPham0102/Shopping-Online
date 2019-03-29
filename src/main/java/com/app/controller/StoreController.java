package com.app.controller;

import com.app.dto.StoreFullDetailDto;
import com.app.response.BaseResponse;
import com.app.service.KhanhService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("stores")
public class StoreController {

    @Autowired
    private KhanhService khanhService;

    @GetMapping("")
    @ResponseBody
    public BaseResponse storeIdAndUserId(@RequestParam(name = "ID", required = false) String id,
                                         @RequestParam(name = "userID", required = false) String userId){

        return new BaseResponse<>(this.khanhService.findStoreByIdAndUserId(id, userId));
    }

    @PostMapping("")
    public BaseResponse storeAdd(@RequestBody StoreFullDetailDto storeFullDetailDto) {

        return BaseResponse.createSuccessResponse(this.khanhService.addStore(storeFullDetailDto));
    }
}
