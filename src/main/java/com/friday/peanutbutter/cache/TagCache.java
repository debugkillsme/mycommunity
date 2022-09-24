package com.friday.peanutbutter.cache;

import com.friday.peanutbutter.dto.TagDTO;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TagCache {
    public static List<TagDTO> get(){
        List<TagDTO> tagDTOs =new ArrayList<>();
        //第一个标签类别
        TagDTO letter = new TagDTO();
        letter.setCategoryName("letter");
        List<String> letterList = new ArrayList<>();
        for(int i =0;i<26;i++){
            letterList.add(String.valueOf((char)('A'+i)));
        }
        letter.setTags(letterList);

        TagDTO stupidName = new TagDTO();
        stupidName.setCategoryName("typhoon");
        stupidName.setTags(Arrays.asList("海葵","玉兔","风神","杜鹃","海马","悟空","白鹿","海棠"));

        tagDTOs.add(letter);
        tagDTOs.add(stupidName);
        return tagDTOs;
    }

    //提供校验方法判断标签输入是否符合格式要求
    public static String filterInvalid(String tags){
        String[] split = StringUtils.split(tags,"/");
        List<TagDTO> tagDTOS =get();
        //把所有标签放到一个List中
        List<String> tagList = tagDTOS.stream().flatMap(tag -> tag.getTags().stream()).collect(Collectors.toList());
        //找到非法的标签
        String invalid = Arrays.stream(split).filter(t->!tagList.contains(t)).collect(Collectors.joining("/"));
        return invalid;

    }
}
