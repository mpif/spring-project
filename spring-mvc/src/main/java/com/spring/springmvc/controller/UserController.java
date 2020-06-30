package com.spring.springmvc.controller;

import com.spring.springmvc.model.User;
import com.spring.springmvc.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: caishengzhi
 * @date: 2017-11-13 15:23
 **/
@Controller
@RequestMapping("user")
@Slf4j
public class UserController extends BaseController {

    @Resource
    private UserService userService;

    @RequestMapping(value = "/queryUser", method = {RequestMethod.GET, RequestMethod.POST})
    public String queryStore(Model model) {
        model.addAttribute("aaa", "aaa");
        log.info("跳转到查询门店页面!");
        return "o2o/selectLePar";
    }

    /**
     * 查询用户列表
     * @param currentPageNo
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/queryUserList", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ModelAndView queryStoreList(
            @RequestParam(value="currentPageNo", required = false) Integer currentPageNo,
            @RequestParam(value="pageSize", required = false) Integer pageSize) {

        log.info("查询门店列表信息, currentPageNo={}, pageSize={}", currentPageNo, pageSize);

        User queryUser = new User();
        queryUser.setUsername("张三");
        queryUser.setPassword("111");
        User resultUser = userService.queryBySelective(queryUser);
        System.out.println("查询到的用户为:[" + resultUser.toString() + "]");

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("user", resultUser);

        return new ModelAndView("o2o/selectLePar_list", model);

    }

    @RequestMapping(value = "/queryUserListByName", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> queryUserListByName(
            @RequestParam(value="userName", required = false) String userName) {


        log.info("根据用户名称查询用户列表信息，userName={}", userName);

        User queryUser = new User();
        queryUser.setUsername(userName);
        User resultUser = userService.queryBySelective(queryUser);
        System.out.println("根据用户名查询到的用户为:[" + resultUser.toString() + "]");

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("user", resultUser);

        return model;

    }

}
