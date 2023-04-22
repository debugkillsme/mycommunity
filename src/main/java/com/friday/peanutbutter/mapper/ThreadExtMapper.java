package com.friday.peanutbutter.mapper;

import com.friday.peanutbutter.model.PostThread;

import java.util.List;

//使用ExtMapper制定sql操作，mybatis生成的update放在高并发情况下会出Bug
public interface ThreadExtMapper {
    int incView(PostThread record);
    int incCommentCount(PostThread record);
    List<PostThread> selectRelated(PostThread postThread);
    List<PostThread> selectBySearch(String search);

}
