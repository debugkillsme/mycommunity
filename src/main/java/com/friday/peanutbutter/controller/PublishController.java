package com.friday.peanutbutter.controller;

import com.friday.peanutbutter.cache.TagCache;
import com.friday.peanutbutter.dto.TagDTO;
import com.friday.peanutbutter.mapper.ThreadMapper;
import com.friday.peanutbutter.model.PostThread;
import com.friday.peanutbutter.model.User;
import com.friday.peanutbutter.service.ThreadService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {

    @Autowired
    private ThreadMapper threadMapper;

    @Autowired
    private ThreadService threadService;


    @GetMapping("/publish/{id}")
    public String edit(@PathVariable(name = "id")Long id,Model model){
        //使用唯一标识符"id"标记是重新编辑已有的页面
        model.addAttribute("id",id);
        //使用该唯一标识符获得对应的帖子
        //或者使用threadService方法返回ThreadDTO
        PostThread postThread = threadMapper.selectByPrimaryKey(id);

        model.addAttribute("title",postThread.getTitle());
        model.addAttribute("description",postThread.getDescription());
        model.addAttribute("tag",postThread.getTag());
        model.addAttribute("id",postThread.getId());
        model.addAttribute("tags",TagCache.get());

        return "/publish";
    }

    //如果是Get方法，则进行页面渲染
    @GetMapping("/publish")
    public String publish(Model model){
        model.addAttribute("tags", TagCache.get());
        return "publish";
    }

    //post方法更多用于页面交互
    @PostMapping("/publish")
    //路由接收表单中的参数,required=false表示不传值后台也不会报错
    public String doPublish(@RequestParam(value = "title",required = false)String title,
                            @RequestParam(value = "description",required = false)String description,
                            @RequestParam(value = "tag",required = false)String tag,
                            @RequestParam(value = "id",required = false)Long id,
                            //使用Model，后台从控制层直接返回前端所需要的数据
                            HttpServletRequest request, Model model){
        //为了回显到页面中去
        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);
        model.addAttribute("tags", TagCache.get());

        //前端同样可以验证
        if(title==null||title==""){
            model.addAttribute("error","标题不能为空");
            return "publish";
        }

        if(description==null||description==""){
            model.addAttribute("error","补充内容不能为空");
            return "publish";
        }

        if(tag == null||tag == ""){
            model.addAttribute("error","标签不能为空");

        }

        String invalid = TagCache.filterInvalid(tag);
        if(StringUtils.isNotBlank(invalid)){
            model.addAttribute("error","输入非法标签"+invalid);
            return "publish";
        }

        User user = (User)request.getSession().getAttribute("user");
        if(user == null){
            //需要给model add错误信息
            model.addAttribute("error","用户未登录");
            return "publish";

        }
        PostThread postThread = new PostThread();
        postThread.setTitle(title);
        postThread.setDescription(description);
        postThread.setTag(tag);
        //creator表示user的id
        postThread.setCreator(user.getId());
        //id是可空的
        postThread.setId(id);
        threadService.createOrUpdate(postThread);
        //如果没有异常则回到发布页面
        return "redirect:/";

    }

    //如果是Post方法，则进行请求
}
