package com.example.controller;

import com.example.domain.LoginForm;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


/**
 * 这个接口的代码，与接口定义，接口入参声明有关，接口数据返回请参考
 * {@link com.example.controller.RoleController}
 */

@Api(tags = "用户管理") //  tags：你可以当作是这个组的名字。
@RestController
public class UserController {
    // 注意，对于swagger，不要使用@RequestMapping，
    // 因为@RequestMapping支持任意请求方式，swagger会为你生成7个接口文档
    @GetMapping("/info")
    public String info(String id){
        return "aaa";
    }
    @ApiOperation(value = "用户测试",notes = "用户测试notes",tags = "角色管理")
    @GetMapping("/test")
    public String test(String id){
        return "test";
    }


    // 获取实体类对象参数
    @ApiOperation(value = "登录接口",notes = "登录接口的说明")
    @PostMapping("/login")
    public LoginForm login(@RequestBody LoginForm loginForm){
        return loginForm;
    }

    // 使用URL query参数
    @ApiOperation(value = "登录接口2",notes = "登录接口的说明2")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username",//参数名字
                    value = "用户名",//参数的描述
                    required = true,//是否必须传入
                    //paramType定义参数传递类型：有path,query,body,form,header
                    paramType = "query",
                    defaultValue = "root" // 设置example Value默认值
                    )
            ,
            @ApiImplicitParam(name = "password",//参数名字
                    value = "密码",//参数的描述
                    required = true,//是否必须传入
                    paramType = "query"
                    )
    })
    @PostMapping(value = "/login2")
    public LoginForm login2(String username,String password){
        System.out.println(username+":"+password);
        LoginForm loginForm = new LoginForm();
        loginForm.setUsername(username);
        loginForm.setPassword(password);
        return loginForm;
    }


    // 使用路径参数
    @PostMapping("/login3/{id1}/{id2}")
    @ApiOperation(value = "登录接口3",notes = "登录接口的说明3")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id1",//参数名字
                    value = "用户名",//参数的描述
                    required = true,//是否必须传入
                    //paramType定义参数传递类型：有path,query,body,form,header
                    paramType = "path"
            )
            ,
            @ApiImplicitParam(name = "id2",//参数名字
                    value = "密码",//参数的描述
                    required = true,//是否必须传入
                    paramType = "path"
            )
    })
    public String login3(@PathVariable Integer id1,@PathVariable Integer id2){
        return id1+":"+id2;
    }

    // 用header传递参数
    @PostMapping("/login4")
    @ApiOperation(value = "登录接口4",notes = "登录接口的说明4")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username",//参数名字
                    value = "用户名",//参数的描述
                    required = true,//是否必须传入
                    //paramType定义参数传递类型：有path,query,body,form,header
                    paramType = "header"
            )
            ,
            @ApiImplicitParam(name = "password",//参数名字
                    value = "密码",//参数的描述
                    required = true,//是否必须传入
                    paramType = "header"
            )
    })
    public String login4( @RequestHeader String username,
                          @RequestHeader String password){
        return username+":"+password;
    }

    // 对于这个接口的在线测试，请基于启用了swagger-bootstrap-ui之后，默认情况下，swagger无法选择发送数据的编码方式
    @PostMapping("/login5")
    @ApiOperation(value = "登录接口5",notes = "登录接口的说明5")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username",//参数名字
                    value = "用户名",//参数的描述
                    required = true,//是否必须传入
                    //paramType定义参数传递类型：有path,query,body,form,header
                    paramType = "form"
            )
            ,
            @ApiImplicitParam(name = "password",//参数名字
                    value = "密码",//参数的描述
                    required = true,//是否必须传入
                    paramType = "form"
            )
    })
    public String login5(String username,
                          String password){
        return username+":"+password;
    }

    // 有文件上传时要用@ApiParam，用法基本与@ApiImplicitParam一样，不过@ApiParam用在参数上
    // 或者你也可以不注解，swagger会自动生成说明
    @ApiOperation(value = "上传文件",notes = "上传文件")
    @PostMapping(value = "/upload")
    public String upload(@ApiParam(value = "图片文件", required = true)MultipartFile uploadFile){
        return uploadFile.getOriginalFilename();
    }
    // 多个文件上传时，**swagger只能测试单文件上传**
    @ApiOperation(value = "上传多个文件",notes = "上传多个文件")
    @PostMapping(value = "/upload2",consumes = "multipart/*", headers = "content-type=multipart/form-data")
    public String upload2(@ApiParam(value = "图片文件", required = true,allowMultiple = true)MultipartFile[] uploadFile){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < uploadFile.length; i++) {
            System.out.println(uploadFile[i].getOriginalFilename());
            sb.append(uploadFile[i].getOriginalFilename());
            sb.append(",");
        }
        return sb.toString();
    }

    // 既有文件，又有参数
    @ApiOperation(value = "既有文件，又有参数",notes = "既有文件，又有参数")
    @PostMapping(value = "/upload3")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name",
                    value = "图片新名字",
                    required = true
            )
    })
    public String upload3(@ApiParam(value = "图片文件", required = true)MultipartFile uploadFile,
                          String name){
        String originalFilename = uploadFile.getOriginalFilename();

        return originalFilename+":"+name;
    }

    // 如果需要额外的参数，非本方法用到，但过滤器要用,类似于权限token
    @PostMapping("/login6")
    @ApiOperation(value = "带token的接口",notes = "带token的接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization",//参数名字
                    value = "授权token",//参数的描述
                    required = true,//是否必须传入
                    paramType = "header"
            )
            ,
            @ApiImplicitParam(name = "username",//参数名字
                    value = "用户名",//参数的描述
                    required = true,//是否必须传入
                    paramType = "query"
            )
    })
    public String login6(String username){
        return username;
    }
}
