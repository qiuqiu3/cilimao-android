package cn.sddman.bt.common;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.coorchice.library.SuperTextView;
import org.xutils.view.annotation.Event;
import org.xutils.x;

import cn.sddman.bt.R;

public class BaseActivity extends AppCompatActivity {
    private TextView topBarTitle=null;
    private SuperTextView closeView=null;
    private SuperTextView rightView=null;
    private boolean isClose=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getAppManager().addActivity(this);
        x.view().inject(this);
    }
    protected void setTopBarTitle(int title) {
        if(topBarTitle==null) {
            topBarTitle = findViewById(R.id.topBarTitle);
        }
        topBarTitle.setText(title);
    }
    protected void setTopBarTitle(String title) {
        if(topBarTitle==null) {
            topBarTitle = findViewById(R.id.topBarTitle);
        }
        topBarTitle.setText(title);
    }

    protected void setTopCloseText(String text) {
        if(closeView==null) {
            closeView = findViewById(R.id.close_view);
        }
        closeView.setText(text);
    }

    protected SuperTextView getRightView(){
        if(rightView==null) {
            rightView = findViewById(R.id.right_view);
        }
        return rightView;
    }

    protected void hideCloseView(){
        if(closeView==null){
            closeView=findViewById(R.id.close_view);
        }
        closeView.setVisibility(View.INVISIBLE);
    }
    protected void isClose(boolean b){
        isClose=b;
    }
    @Event(value = R.id.close_view)
    private void closeView(View view) {
        if(isClose)
            finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager().finishActivity(this);
    }
}
