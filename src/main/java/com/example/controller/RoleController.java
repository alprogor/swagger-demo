package com.example.controller;

import com.example.domain.LoginForm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;



@Api(tags = "角色管理")
@RestController
public class RoleController {

    // 返回被@ApiModel标注的类对象
    @ApiOperation(value = "实体类响应",notes = "返回数据为实体类的接口")
    @PostMapping("/role1")
    public LoginForm role1(@RequestBody LoginForm loginForm){
        return loginForm;
    }

    // 其他类型的,此时不能增加字段注释，所以其实swagger推荐使用实体类
    @ApiOperation(value = "非实体类",notes = "非实体类")
    @ApiResponses({
            @ApiResponse(code=200,message = "调用成功"),
            @ApiResponse(code=401,message = "无权限" )
    }
    )
    @PostMapping("/role2")
    public String role2(){
        return " {\n" +
                " name:\"广东\",\n" +
                "     citys:{\n" +
                "         city:[\"广州\",\"深圳\",\"珠海\"]\n" +
                "     }\n" +
                " }";
    }

}
