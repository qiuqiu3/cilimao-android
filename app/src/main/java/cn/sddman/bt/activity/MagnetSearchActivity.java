package cn.sddman.bt.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.cocosw.bottomsheet.BottomSheet;
import com.coorchice.library.SuperTextView;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.header.progresslayout.ProgressLayout;
import com.yarolegovich.lovelydialog.LovelyChoiceDialog;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.sddman.bt.R;
import cn.sddman.bt.adapter.MagnetSearchListAdapter;
import cn.sddman.bt.common.BaseActivity;
import cn.sddman.bt.common.Const;
import cn.sddman.bt.mvp.e.MagnetInfo;
import cn.sddman.bt.mvp.e.MagnetRule;
import cn.sddman.bt.mvp.p.MagnetSearchPresenter;
import cn.sddman.bt.mvp.p.MagnetSearchPresenterImp;

import cn.sddman.bt.mvp.v.MagnetSearchView;

import cn.sddman.bt.util.AlertUtil;

import cn.sddman.bt.util.GsonUtil;
import cn.sddman.bt.util.StringUtil;
import cn.sddman.bt.util.Util;

@ContentView(R.layout.activity_magnet_search)
public class MagnetSearchActivity extends BaseActivity implements MagnetSearchView {
    @ViewInject(R.id.input_search) private EditText searchText;
    @ViewInject(R.id.recyclerview) private RecyclerView recyclerView;
    @ViewInject(R.id.search_source) private TextView searchSourceText;
    @ViewInject(R.id.search_sort) private TextView searchSortText;
    @ViewInject(R.id.refresh)private TwinklingRefreshLayout refreshLayout;

    private MagnetSearchPresenter magnetSearchPresenter;
    private MagnetSearchListAdapter searchListAdapter;
    private LovelyChoiceDialog sourceDialog=null,sortDialog=null;
    private List<MagnetInfo> list=new ArrayList<>();
    private List<MagnetRule> rules;
    private MagnetRule rule=new MagnetRule();
    private  List<String> btSources=new ArrayList<>();
    private String searchKeyWord;
    private int searchPage=1;
    private String searchSort= Const.SEARCH_SORT_DATE;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.hideCloseView();
        magnetSearchPresenter=new MagnetSearchPresenterImp(this);
        initData();
        initView();
    }
    private void initData(){
        rules= GsonUtil.getRule(this,"rule.json");
        if(rules==null){
            AlertUtil.errorToast("获取种子来源网站失败，请重新打开本页面或者重启APP");
            return;
        }
        for(MagnetRule rule:rules){
            btSources.add(rule.getSite());
        }
        rule=GsonUtil.getMagnetRule(rules).get(rules.get(0).getSite());
    }
    private void initView(){
        searchSourceText.setText(String.format(getString(R.string.search_source),rule.getSite()));
        searchSortText.setText(String.format(getString(R.string.search_sort), getString(R.string.date)));
        //recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        searchListAdapter=new MagnetSearchListAdapter(this,this,list);
        recyclerView.setAdapter(searchListAdapter);
        //refreshLayout
        ProgressLayout header = new ProgressLayout(this);
        header.setProgressBackgroundColorSchemeColor(getResources().getColor(R.color.colorMain));
        header.setColorSchemeResources(R.color.white);
        refreshLayout.setHeaderView(header);
        refreshLayout.setFloatRefresh(true);
        refreshLayout.setOverScrollRefreshShow(false);
        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter(){
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                searchPage=1;
                list.clear();
                searchListAdapter.notifyDataSetChanged();
                search();
            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                searchPage+=1;
                search();
            }
        });
        //
        searchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || (event != null && KeyEvent.KEYCODE_ENTER == event.getKeyCode() && KeyEvent.ACTION_DOWN == event.getAction())) {
                    refreshLayout.startRefresh();
                }
                return false;
            }
        });
    }

    @Override
    public void refreshData(List<MagnetInfo> info) {
        refreshLayout.finishRefreshing();
        refreshLayout.finishLoadmore();
        if(null==info){
            AlertUtil.errorToast("网络超时，请重试");
        }else if(info.size()==0){
            AlertUtil.errorToast("没有更多了");
        }else {
            list.addAll(info);
            searchListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void moreOption(final MagnetInfo magnet) {
        Intent intent;
         new BottomSheet.Builder(this)
                    .title(R.string.slest_option)
                    .sheet(R.menu.magnet_option)
                    .listener(new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case R.id.copy:
                                    Util.putTextIntoClip(magnet.getMagnet());
                                    AlertUtil.successToast("复制成功");
                                    break;
                                case R.id.xl:
                                    openXL(magnet);
                                    break;

                            }
                        }
                    }).show();
    }

    private void search(){
        searchKeyWord=searchText.getText().toString().trim();
        if(!StringUtil.isEmpty(searchKeyWord)) {
            magnetSearchPresenter.searchMagnet(rule, searchKeyWord,searchSort, searchPage);
            hintKeyBoard();
        }else{
            refreshLayout.finishRefreshing();
            refreshLayout.finishLoadmore();
        }
    }
    private void openXL(MagnetInfo magnet){
        if(Util.checkApkExist(getString(R.string.xl_package_name))){
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(magnet.getMagnet()));
            intent.addCategory("android.intent.category.DEFAULT");
            startActivity(intent);
        }else{
            AlertUtil.errorToast("未安装迅雷");
        }
    }
    private void hintKeyBoard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive() && getCurrentFocus() != null) {
            if (getCurrentFocus().getWindowToken() != null) {
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }
    @Event(value = R.id.btn_search)
    private void magnetSearchClick(View view) {
        refreshLayout.startRefresh();
    }
    @Event(value = R.id.bt_source)
    private void btSourceClick(View view) {
        if(sourceDialog==null) {
            sourceDialog=new LovelyChoiceDialog(this)
                    .setTopColorRes(R.color.colorMain)
                    .setIcon(R.drawable.ic_source)
                    .setItems(btSources, new LovelyChoiceDialog.OnItemSelectedListener<String>() {
                        @Override
                        public void onItemSelected(int position, String item) {
                            rule = GsonUtil.getMagnetRule(rules).get(item);
                            searchSourceText.setText(String.format(getString(R.string.search_source), item));
                            refreshLayout.startRefresh();
                        }
                    });
        }
        sourceDialog.show();
    }

    @Event(value = R.id.bt_sort)
    private void btSortClick(View view) {
        if(sortDialog==null) {
            sortDialog= new LovelyChoiceDialog(this)
                    .setTopColorRes(R.color.colorMain)
                    .setIcon(R.drawable.ic_sort)
                    .setItems(Arrays.asList(getString(R.string.date),getString(R.string.hot),getString(R.string.size)), new LovelyChoiceDialog.OnItemSelectedListener<String>() {
                        @Override
                        public void onItemSelected(int position, String item) {
                            if(item.equals(getString(R.string.date))){
                                searchSort=Const.SEARCH_SORT_DATE;
                            }else if(item.equals(getString(R.string.hot))){
                                searchSort=Const.SEARCH_SORT_HOT;
                            } else {
                                searchSort=Const.SEARCH_SORT_SIZE;
                            }
                            searchSortText.setText(String.format(getString(R.string.search_sort), item));
                            refreshLayout.startRefresh();
                        }
                    });
        }
        sortDialog.show();
    }

}
