package team.mediasoft.education.tracker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.PostConstruct;

@Controller
public class AppController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String helloPage() {
        return "hello.html";
    }

    @PostConstruct
    void method() {
        System.out.println("AppContr constructed");
    }
}
