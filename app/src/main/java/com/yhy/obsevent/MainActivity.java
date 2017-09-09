package com.yhy.obsevent;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.yhy.obsevt.EvtManager;
import com.yhy.obsevt.EvtObserver;

public class MainActivity extends AppCompatActivity implements EvtObserver {

    private TextView tvPageFirst;
    private TextView tvNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 注册事件
        EvtManager.getInstance().regist(this);

        tvPageFirst = (TextView) findViewById(R.id.tv_page_first);
        tvNext = (TextView) findViewById(R.id.tv_next);

        tvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SecondActivity.class));
            }
        });
    }

    @Override
    public void notifyEvt(int evtCode, Bundle data) {
        // 其他页面触发事件后该方法就被调用
        if (evtCode == 200) {
            if (null == data) {
                tvPageFirst.setText("200-data为空");
            } else {
                tvPageFirst.setText("200-" + data.getString("data"));
            }
        } else if (evtCode == 201) {
            if (null == data) {
                tvPageFirst.setText("201-data为空");
            } else {
                tvPageFirst.setText("201-" + data.getString("data"));
            }
        }
    }

    @Override
    public int[] getEvtCode() {
        // 确定本页面的事件code
        return new int[]{200, 201};
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 注销事件
        EvtManager.getInstance().unRegist(this);
    }
}
