package jw.spring.training.securityexample.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExampleController {

//    @GetMapping("/user/hello")
//    public String helloUser(){
//        return "hello user";
//    }
//
//    @GetMapping("/admin/hello")
//    public String helloAdmin(){
//        return "hello admin";
//    }
//
//    @GetMapping("/hello")
//    public String helloAnyone(){
//        return "hello!";
//    }

    @GetMapping("/user/hello")
    public String helloUser(){
        return "hello user";
    }

    @GetMapping("/admin/hello")
    public String helloAdmin(){
        return "hello admin";
    }

    @GetMapping("/hello")
    public String helloAnyone(){
        return "hello!";
    }

    @GetMapping("/vip/hello")
    public String helloVIP(){
        return "hello VIP";
    }
}
