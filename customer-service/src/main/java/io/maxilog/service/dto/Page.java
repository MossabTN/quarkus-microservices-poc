package io.maxilog.service.dto;

import java.io.Serializable;
import java.util.List;

public class Page<T> implements Serializable {

    private List<T> content;
    private int totalElements;
    public Page() {
    }

    public Page(List<T> content, int totalElements) {
        this.content = content;
        this.totalElements = totalElements;
    }

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

    public int getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(int totalElements) {
        this.totalElements = totalElements;
    }
}