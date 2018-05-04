package com.zqt.controller;

import com.zqt.model.HeFeiBBS_Domain;
import com.zqt.services.HeFei_BBSServerce;
import com.zqt.services.HeFei_BBS_HFservices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

/**
 * created by zengqintao on 2018-04-26 14:47 .
 **/

@Controller
@RequestMapping("/main")
public class MainController {
    @Autowired
    HeFei_BBS_HFservices heFei_BBSHFservices;

    @Autowired
    private HeFei_BBSServerce heFei_bbsServerce;

    @RequestMapping(value = "/demo", method = RequestMethod.GET)
    public String welcome(Model model) {
        List<HeFeiBBS_Domain> Domainlist = new ArrayList<>();
        Domainlist = heFei_bbsServerce.QueryNews(1, 10);
        int pageNum = heFei_bbsServerce.QueryPageCount();
        pageNum = pageNum / 10 + 1;
        model.addAttribute("rows", Domainlist);
        model.addAttribute("page", 1);
        model.addAttribute("pageCount", pageNum);
        model.addAttribute("shoudao","列表信息传递完毕");
        return "demo";
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute("page", "5");
        model.addAttribute("pageCount", "15");
        return "index";
    }

    @RequestMapping(value = "/neirong", method = RequestMethod.GET)
    public String form(@RequestParam("username") String username, @RequestParam("age") String age, Model model) {
        System.out.println(username + age);
        model.addAttribute("shoudao", "信息接收完毕-姓名: " + username + "年龄:" + age);
        return "index";
    }

    @RequestMapping(value = "/getnews", method = RequestMethod.GET)
    public String getNews(@RequestParam("page") int page, @RequestParam("pageCount") int pageCount, ModelMap modelMap) {
        List<HeFeiBBS_Domain> Domainlist = new ArrayList<>();
        heFei_bbsServerce.hefei_BBs();
        Domainlist = heFei_bbsServerce.QueryNews(page, pageCount);
        int pageNum = heFei_bbsServerce.QueryPageCount();
        modelMap.addAttribute("Domainlist", Domainlist);
        modelMap.addAttribute("page", page);
        modelMap.addAttribute("pageCount", pageNum);
        return "xunlian";
    }

    @RequestMapping(value = "/pinglun", method = RequestMethod.GET)
    public String pinglun(String url, String title, ModelMap model) {
        Boolean boo = null;
        boo = heFei_BBSHFservices.pinglun(url, title);
        if (boo) {
            model.addAttribute("PLsucc", "评论成功");
        } else {
            model.addAttribute("PLfalse", "评论失败");
        }
        return "index";
    }

}
