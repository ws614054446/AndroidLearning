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

public class RxJavaLearnAPIActivity extends AppCompatActivity {

    @BindView(R.id.btn_create_operation)
    AppCompatButton btnCreateOperation;
    @BindView(R.id.btn_transform)
    AppCompatButton btnTransform;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java_learn_api);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_create_operation, R.id.btn_transform})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_create_operation://创建操作
                startActivity(new Intent(RxJavaLearnAPIActivity.this,CreateOperationActivity.class));
                break;
            case R.id.btn_transform://变换操作
                startActivity(new Intent(RxJavaLearnAPIActivity.this,TransFormActivity.class));
                break;
        }
    }
}
