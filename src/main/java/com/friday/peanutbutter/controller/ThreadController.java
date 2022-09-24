package com.friday.peanutbutter.controller;


import com.friday.peanutbutter.dto.CommentDTO;
import com.friday.peanutbutter.dto.ThreadDTO;
import com.friday.peanutbutter.enums.CommentTypeEnum;
import com.friday.peanutbutter.service.CommentService;
import com.friday.peanutbutter.service.ThreadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class ThreadController {
    @Autowired
    private ThreadService threadService;

    @Autowired
    private CommentService commentService;

    @GetMapping("/thread/{id}")
    public String threads(@PathVariable(name = "id") Long id, Model model){
        ThreadDTO threadDTO = threadService.getById(id);
        List<ThreadDTO>  relatedThreads = threadService.selectRelated(threadDTO);
        List<CommentDTO> comments = commentService.listByParentId(id, CommentTypeEnum.POST_THREAD);
        //累加评论
        threadService.incView(id);
        model.addAttribute("postthread",threadDTO);
        model.addAttribute("comments",comments);
        model.addAttribute("relatedThreads",relatedThreads);
        return "thread";
    }


}
