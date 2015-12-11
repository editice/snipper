package org.editice.snipper.client.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tinglang (editice@gmail.com)
 * @since 2015/3/18  20:32
 */
public class PageBean {
    private int total;
    private int totalPage;
    private int pageNum;
    private int start;
    private int offset = 4;
    private List<Integer> pages = new ArrayList<Integer>();

    private int pre;
    private int next;

    public static final int SIZE = 10;

    public PageBean(int pageNum, int total) {
        this.pageNum = pageNum;
        this.total = total;

        getTotalPage();
        getStart();
        getPages();
        getPre();
        getNext();
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotalPage() {
        if(total%SIZE==0) {
            totalPage = total/SIZE;
        } else {
            totalPage = total/SIZE + 1;
        }
        return totalPage;
    }

    public static int getTotalPage(int total, int size) {
        int totalPage = 0;
        if(total%size==0) {
            totalPage = total/size;
        } else {
            totalPage = total/size + 1;
        }
        return totalPage;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getStart() {
        start = (pageNum-1) * SIZE;
        return start;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public List<Integer> getPages() {
        pages.clear();
        int s = (pageNum - offset)>0?(pageNum - offset):1;
        int e = (pageNum + offset)>totalPage?totalPage:(pageNum + offset);
        for(int i=s; i<=e; i++) {
            pages.add(i);
        }
        return pages;
    }

    public void setPages(List<Integer> pages) {
        this.pages = pages;
    }

    public int getPre() {
        pre = pageNum - 1;
        return pre;
    }

    public void setPre(int pre) {
        this.pre = pre;
    }

    public int getNext() {
        next = pageNum +1;
        return next;
    }

    public void setNext(int next) {
        this.next = next;
    }
}