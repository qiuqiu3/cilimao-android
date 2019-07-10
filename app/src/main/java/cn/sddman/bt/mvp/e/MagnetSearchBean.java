package cn.sddman.bt.mvp.e;

public class MagnetSearchBean {
    private String keyword;
    private String sort;
    private  int page;
    private MagnetRule rule;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public MagnetRule getRule() {
        return rule;
    }

    public void setRule(MagnetRule rule) {
        this.rule = rule;
    }
}
