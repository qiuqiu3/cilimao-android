package cn.sddman.bt.thread;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

import cn.sddman.bt.listener.MagnetSearchListener;
import cn.sddman.bt.mvp.e.MagnetInfo;
import cn.sddman.bt.mvp.e.MagnetRule;
import cn.sddman.bt.mvp.e.MagnetSearchBean;
import cn.sddman.bt.mvp.m.MagnetWServiceModel;
import cn.sddman.bt.mvp.m.MagnetWServiceModelImp;

public class MangetSearchTask extends AsyncTask<MagnetSearchBean, Void, List<MagnetInfo>> {
    private MagnetWServiceModel magnetWService;
    private MagnetSearchListener listener;

    public MangetSearchTask(MagnetSearchListener listener){
        this.listener=listener;
        magnetWService=new MagnetWServiceModelImp();

    }

    @Override
    protected List<MagnetInfo> doInBackground(MagnetSearchBean... parm) {
        List<MagnetInfo> infos = new ArrayList<MagnetInfo>();
        try {
            infos = magnetWService.parser(parm[0]);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return infos;
    }
    @Override
    protected void onPostExecute(List<MagnetInfo> info) {
        listener.success(info);
       // super.onPostExecute(info);
    }
}