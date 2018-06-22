package com.wangshuai.androidlearning.rxjava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;

import com.wangshuai.androidlearning.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RxJavaLearnActivity extends AppCompatActivity {

    @BindView(R.id.btn_learn_rxjava_begin)
    AppCompatButton btnLearnRxjavaBegin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java_learn);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_learn_rxjava_begin)
    public void onViewClicked() {

    }
}
