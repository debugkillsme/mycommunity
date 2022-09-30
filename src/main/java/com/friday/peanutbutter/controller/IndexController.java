package com.friday.peanutbutter.controller;

import com.friday.peanutbutter.dto.ThreadDTO;
import com.friday.peanutbutter.service.ThreadService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
public class IndexController {
    //写一个方法把数据库中的帖子列表读出来
    @Autowired
    private ThreadService threadService;
    @GetMapping("/")
    public String index(@RequestParam(name = "page",defaultValue = "1")Integer page,
                        @RequestParam(name ="size", defaultValue = "2")Integer size ,
                        @RequestParam(name = "search", required = false)String search,
                        Model model){


        //根据search关键词展示页面
        PageInfo<ThreadDTO> pagination= threadService.list(search,page,size);
        model.addAttribute("pagination",pagination);
        //把"search传入在翻页时保持search关键词结果
        model.addAttribute("search",search);

        return "index";
    }
}
