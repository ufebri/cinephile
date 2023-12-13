package com.lagingoding.cinephile.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.lagingoding.cinephile.model.remote.TV;

import java.util.List;

public class DataTV {
    @SerializedName("page")
    @Expose
    private Integer page;
    @SerializedName("total_results")
    @Expose
    private Integer totalResults;
    @SerializedName("total_pages")
    @Expose
    private Integer totalPages;
    @SerializedName("results")
    @Expose
    private List<TV> tv = null;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public List<TV> getTv() {
        return tv;
    }

    public void setTv(List<TV> tv) {
        this.tv = tv;
    }
}
