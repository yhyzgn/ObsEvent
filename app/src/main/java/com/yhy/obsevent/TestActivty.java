package com.yhy.obsevent;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.yhy.obsevt.EvtManager;

/**
 * author : 颜洪毅
 * e-mail : yhyzgn@gmail.com
 * time   : 2017-09-09 11:10
 * version: 1.0.0
 * desc   :
 */
public class TestActivty extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        findViewById(R.id.tv_test_first).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle args = new Bundle();
                args.putString("data", "数据测试1");
                EvtManager.getInstance().notifyEvt(200, args);
                finish();
            }
        });

        findViewById(R.id.tv_test_second).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle args = new Bundle();
                args.putString("data", "数据测试2");
                EvtManager.getInstance().notifyEvt(201, args);
                finish();
            }
        });

        findViewById(R.id.tv_test_third).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle args = new Bundle();
                args.putString("data", "只改变的二页");
                EvtManager.getInstance().notifyEvt(202, args);
                finish();
            }
        });

        findViewById(R.id.tv_test_forth).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EvtManager.getInstance().notifyEvt(200);
                finish();
            }
        });
    }
}
