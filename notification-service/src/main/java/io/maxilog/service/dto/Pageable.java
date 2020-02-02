package io.maxilog.service.dto;

import io.quarkus.panache.common.Page;

import javax.ws.rs.QueryParam;

public class Pageable extends Page {

    @QueryParam("page")
    private int page;
    @QueryParam("size")
    private int size;
    @QueryParam("sort")
    private String sort;

    public Pageable() {
        super(0,10);
    }

    public Pageable(int size) {
        super(size);
    }

    public Pageable(int index, int size) {
        super(index, size);
    }


    public void setPage(int page) {
        this.page = page;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setSort(String sort) {
    }

    public int getPageSize() {
        return size==0?10:size;
    }

    public int getPageNumber() {
        return page;
    }

    public long getOffset() {
        return (long) page * (long) size;
    }
/*

    public Sort getSort() {
        if(sort!=null){
            List<String> sortList = Arrays.asList(sort.split(","));
            if(!sortList.isEmpty())
                return new Sort(sortList.size()>1? Sort.(sortList.get(1)).orElse(Sort.DEFAULT_DIRECTION):Sort.DEFAULT_DIRECTION, sortList.get(0));
        }
        return null;
    }
*/


}