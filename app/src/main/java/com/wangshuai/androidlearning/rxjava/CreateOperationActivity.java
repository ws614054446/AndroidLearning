package com.wangshuai.androidlearning.rxjava;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.wangshuai.androidlearning.R;

import org.reactivestreams.Subscriber;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

/**
 * RxJava 创建操作符
 */
public class CreateOperationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_operation);

    }


    /**
     * 调用Observable的just方法创建一个被观察者对象（Observable）
     * just方法传入需要传递的参数，最多传递10个
     * just(1,2,3)相当于执行了 onNext(1)、onNext(2)、onNext(3)
     */
    private void justOperation() {
        Observable
                .just("1", "2", "3")
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull String s) {

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * fromArray 传入一个数组
     * fromIterable 传入一个集合
     */
    private void fromOperation() {
        Observable
                .fromArray(new String[]{"1", "2", "3"})
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull String s) {

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        Observable.fromIterable(list).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull String s) {

            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    /**
     * 仅发送Complete事件，直接通知完成
     * 类似的方法还有 error()：仅发送Error事件，直接通知异常
     * never()：不发送任何事件
     */
    private void empty() {
        Observable.empty().subscribe(new Observer<Object>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Object o) {

            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    /**
     * defer()
     * 直到有观察者（Observer）订阅时，才动态创建被观察者对象（Observable） & 发送事件
     */
    private void deferOperation() {

        // 此时被观察者对象还没创建
        Observable<String> observable = Observable
                .defer(new Callable<ObservableSource<? extends String>>() {
                    @Override
                    public ObservableSource<? extends String> call() throws Exception {
                        return Observable.just("呵呵呵");
                    }
                });

        //此时，才会调用defer（）创建被观察者对象（Observable）
        observable.subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull String s) {

            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    /**
     * timer()
     * 延迟指定时间后，发送1个数值0（Long类型）
     */
    private void timerOperation(){
        Observable.timer(3, TimeUnit.SECONDS).subscribe(new Observer<Long>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Long aLong) {

            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    /**
     * interval()
     * 每隔指定时间就发送 事件
     */
    private void intervalOperation(){
        //initialDelay第1次延迟时间   period间隔时间
        Observable.interval(3,1,TimeUnit.SECONDS).subscribe(new Observer<Long>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Long aLong) {

            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

        /**
         * intervalRange()
         * 每隔指定时间就发送事件,可指定发送的数据的数量
         * 参数1-start 事件序列起始点
         * 参数2-count 事件数量
         * 参数3-initialDelay 第1次延迟时间
         * 参数4-period 间隔时间
         */
        Observable.intervalRange(2,10,3,2,TimeUnit.SECONDS).subscribe(new Observer<Long>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Long aLong) {

            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    /**
     * range()
     * rangeLong()
     * 连续发送 1个事件序列，可指定范围
     */
    private void range(){
        //range参数1-start：事件序列起始点
        //range参数2-count：事件数量
        Observable.range(1,5).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Integer integer) {

            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    /**
     * repeatWhen()
     * 创建一个发射特定数据重复多次的Observable，默认在trampoline调度器上执行。repeatWhen，完成的时候触发是否重试
     */
    private void repeat(){
        Observable.just(0, 1, 2).repeatWhen(new Function<Observable<Object>, ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(@NonNull Observable<Object> objectObservable) throws Exception {
                objectObservable = objectObservable.delay(2, TimeUnit.SECONDS); //2s后发射
                return objectObservable;
            }
        }).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Integer integer) {

            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }
}
