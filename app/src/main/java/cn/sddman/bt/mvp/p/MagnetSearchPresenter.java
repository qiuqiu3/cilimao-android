package cn.sddman.bt.mvp.p;

import cn.sddman.bt.mvp.e.MagnetRule;

public interface MagnetSearchPresenter {
    void searchMagnet(MagnetRule rule, String keyword,String sort, int page );
}
