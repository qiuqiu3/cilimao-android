package cn.sddman.bt.mvp.p;

import android.os.AsyncTask;


import java.util.List;
import cn.sddman.bt.listener.MagnetSearchListener;
import cn.sddman.bt.mvp.e.MagnetInfo;
import cn.sddman.bt.mvp.e.MagnetRule;
import cn.sddman.bt.mvp.e.MagnetSearchBean;

import cn.sddman.bt.mvp.v.MagnetSearchView;
import cn.sddman.bt.thread.MangetSearchTask;

public class MagnetSearchPresenterImp implements MagnetSearchPresenter {
    private MagnetSearchView magnetSearchView;
    public MagnetSearchPresenterImp(MagnetSearchView magnetSearchView){
        this.magnetSearchView=magnetSearchView;
    }

    @Override
    public void searchMagnet(MagnetRule rule, String keyword,String sort, int page) {
        MangetSearchTask mangetSearchTask=new MangetSearchTask(new MagnetSearchListener() {
            @Override
            public void success(List<MagnetInfo> info) {
                magnetSearchView.refreshData(info);
            }

            @Override
            public void fail(String error) {

            }
        });
        MagnetSearchBean bean=new MagnetSearchBean();
        bean.setKeyword(keyword);
        bean.setPage(page);
        bean.setRule(rule);
        bean.setSort(sort);
        mangetSearchTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,bean);
    }
}
