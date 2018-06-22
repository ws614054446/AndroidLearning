package com.wangshuai.androidlearning;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;

import com.wangshuai.androidlearning.rxjava.RxJavaLearnActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.btn_learn_rxjava)
    AppCompatButton btnLearnRxjava;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_learn_rxjava)
    public void onViewClicked() {
        startActivity(new Intent(MainActivity.this, RxJavaLearnActivity.class));
    }
}
