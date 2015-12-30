package blackcrystal.model;

import org.springframework.data.domain.Page;

import java.util.List;


public class PageAssembler<T> {

    public List<T> content;
    public Integer totalPages;
    public Long totalElements;
    public Integer currentPage;

    public PageAssembler(Page<T> page) {
        this(page.getContent(), page.getTotalPages(), page.getTotalElements(), page.getNumber());
    }
    public PageAssembler(List<T> content, Integer totalPages, Long totalElements, Integer currentPage) {
        this.content = content;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
        this.currentPage = currentPage;
    }



}
