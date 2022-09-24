package com.friday.peanutbutter.controller;

import com.friday.peanutbutter.dto.CommentCreateDTO;
import com.friday.peanutbutter.dto.CommentDTO;
import com.friday.peanutbutter.dto.ResultDTO;
import com.friday.peanutbutter.enums.CommentTypeEnum;
import com.friday.peanutbutter.exception.CustomizeErrorCode;
import com.friday.peanutbutter.model.Comment;
import com.friday.peanutbutter.model.User;
import com.friday.peanutbutter.service.CommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Controller
public class CommentController {
    @Autowired
    private CommentService commentService;

    //使用@ResponseBody返回JSON格式的对象
    @ResponseBody
    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    //使用@RequestBody注解接收json格式的对象
    public Object post(@RequestBody CommentCreateDTO commentCreateDTO,
                       HttpServletRequest request){
        User user = (User)request.getSession().getAttribute("user");
        if(user == null){
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        }
        //使用lang3包判断内容是否为空
        if(commentCreateDTO == null|| StringUtils.isBlank(commentCreateDTO.getContent())){
            return ResultDTO.errorOf(CustomizeErrorCode.CONTENT_IS_EMPTY);
        }
        Comment comment = new Comment();
        comment.setParentId(commentCreateDTO.getParentId());
        comment.setContent(commentCreateDTO.getContent());
        //type可能是帖子，也可能是评论
        comment.setType(commentCreateDTO.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(comment.getGmtCreate());
        comment.setCommentator(user.getId());
        comment.setLikeCount(0);
        comment.setCommentCount(0);
        commentService.insert(comment,user);
        return ResultDTO.okOf();
    }

    @ResponseBody
    @RequestMapping(value = "/comment/{id}",method = RequestMethod.GET)
    public ResultDTO<List<CommentDTO>> comments(@PathVariable(name = "id")Long id){
        List<CommentDTO> commentDTOList = commentService.listByParentId(id,CommentTypeEnum.COMMENT);
        return ResultDTO.okOf(commentDTOList);
    }
}
