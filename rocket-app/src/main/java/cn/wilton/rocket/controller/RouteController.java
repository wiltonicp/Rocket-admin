package cn.wilton.rocket.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
/**
 * @Description
 * @Author: Ranger
 * @Date: 2021/1/19 16:38
 * @Email: wilton.icp@gmail.com
 */
@Controller
@RequestMapping
public class RouteController {

    @GetMapping(value = {"/login"})
    public String login(HttpServletRequest request,Model model) {
        return "login";
    }
    /**
     * 主页
     * @param model
     * @return
     */
    @GetMapping(value = {"/dashboard"})
    public String dashboard(HttpServletRequest request,Model model) {
        return "index";
    }

    /**
     * 系统首页
     * @param model
     * @return
     */
    @GetMapping(value = {"/console"})
    public String console(HttpServletRequest request,Model model) {
        return "view/console/console1";
    }

    @GetMapping(value = {"/logout"})
    public String logout(HttpServletRequest request,Model model) {
        return "login";
    }

}