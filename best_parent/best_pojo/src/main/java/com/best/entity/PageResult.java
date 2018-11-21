package com.best.entity;

import java.io.Serializable;
import java.util.List;

public class PageResult implements Serializable {
    private Long total;
    private List rows;

    public PageResult(Long total, List rows) {
        super();
        this.rows = rows;
        this.total = total;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List getRows() {
        return rows;
    }

    public void setRows(List rows) {
        this.rows = rows;
    }
}
