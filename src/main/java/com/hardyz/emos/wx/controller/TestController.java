package com.hardyz.emos.wx.controller;

import com.hardyz.emos.wx.common.util.R;
import com.hardyz.emos.wx.controller.form.TestSayHelloForm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/test")
@Api("测试Web接口")
public class TestController {
    @GetMapping("/sayHello")
    @ApiOperation("测试demo")
    public R sayHello() {
        return R.ok().put("message", "HelloWorld");
    }

    @PostMapping("/sayHelloForm")
    public R sayHello(@Valid @RequestBody TestSayHelloForm form) {
        return R.ok().put("message", "Hello," + form.getName());
    }
}
