package io.maxilog.service.dto;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class PageableImpl implements Pageable {

    @QueryParam("page")
    @DefaultValue("0")
    private int page;

    @DefaultValue("10")
    @QueryParam("size")

    private int size;
    @QueryParam("sort")
    private String sort;

    public PageableImpl() {
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
        return size;
    }

    @Override
    public boolean isPaged() {
        return false;
    }

    @Override
    public boolean isUnpaged() {
        return false;
    }

    public int getPageNumber() {
        return page;
    }

    public long getOffset() {
        return (long) page * (long) size;
    }

    @Override
    public Sort getSort() {
        if(sort!=null){
            List<String> sortList = Arrays.asList(sort.split(","));
            if(!sortList.isEmpty())
                return new Sort(sortList.size()>1? Sort.Direction.fromOptionalString(sortList.get(1)).orElse(Sort.DEFAULT_DIRECTION):Sort.DEFAULT_DIRECTION, sortList.get(0));
        }
        return Sort.unsorted();
    }

    @Override
    public Sort getSortOr(Sort sort) {
        return null;
    }

    public boolean hasPrevious() {
        return page > 0;
    }

    @Override
    public Optional<Pageable> toOptional() {
        return Optional.empty();
    }

    public Pageable previousOrFirst() {
        return hasPrevious() ? previous() : first();
    }

    public Pageable next() {
        return null;
    }

    public Pageable previous() {
        return null;
    }

    public Pageable first() {
        return null;
    }


}