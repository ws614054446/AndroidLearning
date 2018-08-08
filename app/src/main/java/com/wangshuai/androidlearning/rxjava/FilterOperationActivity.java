package com.wangshuai.androidlearning.rxjava;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.wangshuai.androidlearning.R;

import java.util.concurrent.TimeUnit;

import io.reactivex.CompletableObserver;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;

/**
 * 过滤操作符
 */
public class FilterOperationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_operation);
        filter();
        ofType();
        skip();
        distinct();
        take();
        throttle();
        elementAt();
//        elementAtOrError();
        ignoreElements();
    }

    /**
     * filter
     * 过滤特定条件的事件
     */
    private void filter() {
        Observable.just(1, 2, 3, 4, 5, 6).filter(new Predicate<Integer>() {
            @Override
            public boolean test(@NonNull Integer integer) throws Exception {
                return integer > 3;
            }
        }).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Integer integer) {
                Log.e("filter=", integer + "");
                /**
                 * 结果为：
                 * filter=: 4
                 * filter=: 5
                 * filter=: 6
                 */
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
     * ofType
     * 筛选出特定数据类型的数据
     */
    private void ofType() {
        Observable.just(1, "2", 3, "4").ofType(Integer.class).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Integer integer) {
                Log.e("ofType=", integer + "");
                /**
                 * ofType=: 1
                 * ofType=: 3
                 */
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
     * skip
     * 忽略前N项事件，只保留之后的事件
     * skipLast
     * 忽略后N项事件
     */
    private void skip() {
        Observable.just(1, 2, 3, 4, 5, 6)
                .skip(3)//跳过前3个事件
                .skipLast(2)//跳过后2两个事件
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Integer integer) {
                        Log.e("skip=", integer + "");
                        /**
                         * 结果为：
                         * skip=: 4
                         */
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
     * distinct：过滤重复事件
     * distinctUntilChanged：过滤连续重复的事件
     */
    private void distinct() {
        Observable.just(1, 2, 3, 3, 2, 5).distinct().subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Integer integer) {
                Log.e("distinct=", integer + "");
                /**
                 * 结果为：
                 * distinct=: 1
                 * distinct=: 2
                 * distinct=: 3
                 * distinct=: 5
                 */
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

        Observable.just(1, 2, 3, 3, 2, 5).distinctUntilChanged().subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Integer integer) {
                Log.e("distinctUntilChanged=", integer + "");
                /**
                 * 结果为：
                 * distinctUntilChanged=: 1
                 * distinctUntilChanged=: 2
                 * distinctUntilChanged=: 3
                 * distinctUntilChanged=: 2
                 * distinctUntilChanged=: 5
                 */
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
     * take：只发射前N项事件
     * takeLast：只发射后N项事件
     */
    private void take() {
        Observable.just(1, 2, 3, 4, 5).take(2)//只发射前两项事件
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Integer integer) {
                        Log.e("take=", integer + "");
                        /**
                         * 结果为：
                         * take=: 1
                         * take=: 2
                         */
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

        Observable.just(1, 2, 3, 4, 5).takeLast(2)//只发射后两项事件
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Integer integer) {
                        Log.e("takeLast=", integer + "");
                        /**
                         * 结果为：
                         * takeLast=: 4
                         * takeLast=: 5
                         */
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
     * throttleFirst：在某段时间内，只发送该段时间内第1次事件
     * throttleLast：在某段时间内，只发送该段时间内最后1次事件
     */
    private void throttle() {
        Observable
                .create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Exception {
                        emitter.onNext("1");
                        Thread.sleep(300);
                        emitter.onNext("2");
                        Thread.sleep(300);
                        emitter.onNext("3");
                        Thread.sleep(400);
                        emitter.onNext("4");
                        Thread.sleep(200);
                        emitter.onNext("5");
                    }
                })
//                .throttleFirst(1, TimeUnit.SECONDS)//每一秒发送第一个事件
                .throttleLast(1, TimeUnit.SECONDS)//每一秒发送最后一个事件
//                .sample(1L,TimeUnit.SECONDS)//功能与throttleLast一样
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull String s) {
//                        Log.e("throttleFirst=", s);
                        Log.e("throttleLast=", s);
                        /**
                         * throttleFirst结果为：
                         * throttleFirst=: 1
                         * throttleFirst=: 4
                         *
                         * throttleLast结果为：
                         * throttleLast=: 3
                         * throttleLast=: 5
                         */
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
     * 只发射第N项数据
     * 位置索引从0开始
     */
   private void elementAt(){
        Observable
                .just(0,1,2,3,4,5)
                .elementAt(2)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.e("elementAt=", integer+"");
                        /**
                         * 结果为：
                         * elementAt=: 2
                         */
                    }
                });
   }

    /**
     * 当出现越界情况（即获取的位置索引 ＞ 发送事件序列长度）时，即抛出异常
     */
   private void elementAtOrError(){
       Observable
               .just(0,1,2,3,4,5)
               .elementAtOrError(7)
               .subscribe(new Consumer<Integer>() {
                   @Override
                   public void accept(Integer integer) throws Exception {

                   }
               });

   }

    /**
     * 不发射任何数据，只发射Observable的终止通知
     */
    private void ignoreElements(){
        Observable
                .just(0,1,2,3,4,5)
                .ignoreElements()
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        Log.e("ignoreElements=", "onComplete");
                    }
                });

    }

}
