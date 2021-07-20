package com.awei.controller;

import com.awei.entity.User;
import com.awei.service.UserService;
import com.awei.utils.VerifyCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Base64Utils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("user")
@CrossOrigin
@Slf4j
public class UserController {

    @Autowired
    UserService userService;

    /**
     * 用来处理用户登陆
     */

    @PostMapping("login")
    public Map<String,Object> login( @RequestBody User user){
        Map<String, Object> map = new HashMap<>();
        try {
            User userDB = userService.login(user);
            map.put("state",true);
            map.put("msg","登陆成功");
            map.put("user",userDB);
        }catch (Exception e){
            map.put("state",false);
            map.put("msg",e.getMessage());
        }

        return map;
    }

    /**
     * 用来处理用户注册方法
     *
     */
    @PostMapping("register")
    public Map<String,Object> register(@RequestBody User user,String code,HttpServletRequest request){

        log.info("用户信息:[{}]",user.toString());
        log.info("用户输入的验证码信息:[{}]",code);
        Map<String, Object> map = new HashMap<>();
            String key = (String)request.getServletContext().getAttribute("code");
        try {
            if(key.equalsIgnoreCase(code)){
                //1调用业务方法
                userService.register(user);
                map.put("state",true);
                map.put("msg","提示;注册成功");
            }else {
                throw new RuntimeException("验证码不正确");
            }
        }catch (Exception e){
                map.put("state",false);
                map.put("msg","提示："+e.getMessage());
        }
        return map;
    }

    /**
     *  生成验证码图片
     */
    @GetMapping("getImage")
    public String getImageCode(HttpServletRequest request) throws IOException {
        //使用工具类生成验证码
        String code = VerifyCodeUtils.generateVerifyCode(4);

        //前后端分离的是没有session的概念的，可以放入servletContext作用域或者redis
        //2. 将验证码放入servletContext作用域
        request.getServletContext().setAttribute("code",code);

        //3.将图片转为base64
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        VerifyCodeUtils.outputImage(120,30,byteArrayOutputStream,code);  //这个是把图片加载到byte输出流
        String s = Base64Utils.encodeToString(byteArrayOutputStream.toByteArray());
        return "data:image/png;base64,"+s;
    }
}
