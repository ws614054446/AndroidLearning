package com.wangshuai.androidlearning.rxjava;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;

import com.wangshuai.androidlearning.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RxJavaLearnActivity extends AppCompatActivity {

    @BindView(R.id.btn_learn_rxjava_begin)
    AppCompatButton btnLearnRxjavaBegin;
    @BindView(R.id.btn_learn_rxjava_api)
    AppCompatButton btnLearnRxjavaApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java_learn);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_learn_rxjava_begin, R.id.btn_learn_rxjava_api})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_learn_rxjava_begin:
                startActivity(new Intent(RxJavaLearnActivity.this, RxJavaLearnBeginActivity.class));
                break;
            case R.id.btn_learn_rxjava_api:
                startActivity(new Intent(RxJavaLearnActivity.this,RxJavaLearnAPIActivity.class));
                break;
        }
    }
}
