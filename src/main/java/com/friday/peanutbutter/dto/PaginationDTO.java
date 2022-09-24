package com.friday.peanutbutter.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PaginationDTO<T> {
    //包括分页页面的元素
    private List<T> data;
    private boolean showPrevious;
    private boolean showFirstPage;
    private boolean showNext;
    private boolean showEndPage;

    private Integer page;
    private Integer totalPage;
    private List<Integer> pages = new ArrayList<Integer>();

    public void setPagination(Integer page) {
        this.page=page;
        pages.add(page);
        for(int i=1 ;i<=3;i++){
            //页码头部插入
            if(page-i>0){
                pages.add(0,page-i);
            }
            //页码尾部插入
            if(page+i<=totalPage){
                pages.add(page+i);
            }
        }

        if(page==1){
            showPrevious = false;
        }else {
            showPrevious = true;
        }

        if(page ==  totalPage ){
            showNext = false;
        }else{
            showNext = true;
        }

        if(pages.contains(1)){
            showFirstPage = false;
        }else {
            showFirstPage = true;
        }

        if(pages.contains(totalPage)){
            showEndPage = false;
        }else {
            showEndPage = true;
        }
    }
}
