package com.yang.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class PageResult {

    private Integer totalCount;

    private Integer pageSize=10;

    private Integer currentPage=1;
    private Integer totalPage;

    private List result;

    public PageResult() {
    }

    public static PageResult empty(int pageSize) {
        return new PageResult(0, pageSize, 1, new ArrayList());
    }

    public PageResult(Integer totalCount, Integer pageSize,
                      Integer currentPage, List result) {
        super();
        this.totalCount = totalCount;
        this.pageSize = pageSize;
        this.currentPage = currentPage;
        this.result = result;
        this.totalPage=Math.max((totalCount + pageSize - 1) / pageSize, 1);
    }

    public Integer getTotalPage() {
        return this.totalPage;
    }

    public Integer getPrev() {
        return Math.max(currentPage - 1, 1);
    }

    public Integer getNext() {
        return Math.min(currentPage + 1, getTotalPage());
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public List getResult() {
        return result;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public String toString() {
        return "PageResult{" +
                "totalCount=" + totalCount +
                ", pageSize=" + pageSize +
                ", currentPage=" + currentPage +
                ", totalPage=" + totalPage +
                ", result=" + result +
                '}';
    }
}
