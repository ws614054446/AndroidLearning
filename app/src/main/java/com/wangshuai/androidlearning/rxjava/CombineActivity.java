package com.wangshuai.androidlearning.rxjava;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.wangshuai.androidlearning.R;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.schedulers.Schedulers;

/**
 * 组合操作符
 */
public class CombineActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combine);

//        concat();
//        merge();
        zip();
    }

    /**
     * 组合多个被观察者
     * concat:最多四个
     * concatArray:可以组合四个以上
     */
    private void concat(){
        Observable.concat(Observable.just(1, 2),
                Observable.just(3, 4),
                Observable.just(5),
                Observable.just(6, 7))
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer value) {
                        Log.e("concat", "接收到了事件"+ value);
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                    }
                });

        Observable.concatArray(Observable.just(1, 2),
                Observable.just(3),
                Observable.just(4, 5),
                Observable.just(6),
                Observable.just(7))
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer value) {
                        Log.e("concatArray", "接收到了事件"+ value  );
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    /**
     *  组合多个被观察者一起发送数据，合并后并行执行
     *  mergeArray与concatArray类似
     */
    private void merge(){
        Observable.merge(
                Observable.intervalRange(0, 3, 1, 1, TimeUnit.SECONDS),
                Observable.intervalRange(2, 3, 1, 1, TimeUnit.SECONDS))
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Long value) {
                        Log.e("merge", "接收到了事件"+ value);
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    /**
     *  将多个Observables的事件结合到一起，它只发射与多个被观察者事件最少的那个Observable一样多的数据
     */
    private void zip(){
        Observable<Integer> observable1 = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                Log.d("observable1", "被观察者1发送了事件1");
                emitter.onNext(1);
                // 为了方便展示效果，所以在发送事件后加入2s的延迟
                Thread.sleep(1000);

                Log.d("observable1", "被观察者1发送了事件2");
                emitter.onNext(2);
                Thread.sleep(1000);

                Log.d("observable1", "被观察者1发送了事件3");
                emitter.onNext(3);
                Thread.sleep(1000);

                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io()); // 设置被观察者1在工作线程1中工作

                Observable<String> observable2 = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                Log.d("observable2", "被观察者2发送了事件A");
                emitter.onNext("A");
                Thread.sleep(1000);

                Log.d("observable2", "被观察者2发送了事件B");
                emitter.onNext("B");
                Thread.sleep(1000);

                Log.d("observable2", "被观察者2发送了事件C");
                emitter.onNext("C");
                Thread.sleep(1000);

                Log.d("observable2", "被观察者2发送了事件D");
                emitter.onNext("D");

                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.newThread());

        Observable.zip(observable1, observable2, new BiFunction<Integer, String, String>() {
            @Override
            public String apply(Integer integer, String string) throws Exception {
                return  integer + string;
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.e("zip", "onSubscribe");
            }

            @Override
            public void onNext(String value) {
                Log.e("zip", "最终接收到的事件 =  " + value);
            }

            @Override
            public void onError(Throwable e) {
                Log.e("zip", "onError");
            }

            @Override
            public void onComplete() {
                Log.e("zip", "onComplete");
            }
        });

    }

}
