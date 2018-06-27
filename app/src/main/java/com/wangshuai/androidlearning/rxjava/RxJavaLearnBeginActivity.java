package com.wangshuai.androidlearning.rxjava;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.wangshuai.androidlearning.R;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.logging.Logger;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * RxJava入门学习
 */
public class RxJavaLearnBeginActivity extends AppCompatActivity {
    private static final String TAG = "RxJavaLearnBeginActivit";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java_learn_begin);

        usage1();
        usage2();
    }

    /**
     *  RxJava使用步骤详解:
     *  1、创建被观察者（Observable）并定义生产事件；
     *  2、创建观察者（Observer）并 定义响应事件的行为；
     *  3、通过订阅（Subscribe）连接观察者和被观察者。
     */
    private void usage1(){
        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("我要开始动手了");
                emitter.onNext("我正在行动中");
                emitter.onNext("我已经做完了");
                emitter.onComplete();
            }
        });

        Observer<String> observer = new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.e(TAG,"onSubscribe");
            }

            @Override
            public void onNext(@NonNull String s) {
                Log.e(TAG,"onNext--"+s);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.e(TAG,"onError");
            }

            @Override
            public void onComplete() {
                Log.e(TAG,"onComplete");
            }
        };

        observable.subscribe(observer);
    }

    /**
     * RxJava的链式调用，实际上拆解开步骤和 usage1 方式里的步骤是一样的
     */
    private void usage2(){
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("我要开始动手了");
                emitter.onNext("我正在行动中");
                emitter.onNext("我已经做完了");
                emitter.onComplete();
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.e(TAG,"onSubscribe");
            }

            @Override
            public void onNext(@NonNull String s) {
                Log.e(TAG,"onNext--"+s);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.e(TAG,"onError");
            }

            @Override
            public void onComplete() {
                Log.e(TAG,"onComplete");
            }
        });
    }
}
