package io.maxilog.service.dto;

import javax.ws.rs.QueryParam;

public class PageableImpl {

    @QueryParam("page")
    private int page;
    @QueryParam("size")
    private int size;
    @QueryParam("sort")
    private String sort;

    public PageableImpl() {
    }


    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size==0?10:size;
    }

    public void setSize(int size) {
        this.size = size;
    }


    public long getOffset() {
        return (long) page * (long) size;
    }
/*
    @Override
    public Sort getSort() {
        if(sort!=null){
            List<String> sortList = Arrays.asList(sort.split(","));
            if(!sortList.isEmpty())
                return new Sort(sortList.size()>1? Sort.Direction.fromOptionalString(sortList.get(1)).orElse(Sort.DEFAULT_DIRECTION):Sort.DEFAULT_DIRECTION, sortList.get(0));
        }
        return null;
    }*/


}