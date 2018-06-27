package com.wangshuai.androidlearning.rxjava;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.wangshuai.androidlearning.R;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.observables.GroupedObservable;
import io.reactivex.schedulers.Schedulers;

/**
 * 变换操作符
 */
public class TransFormActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trans_form);

//        map();
//        flatMap();
//        switchMap();
//        buffer();
//        scan();
        groupBy();
    }

    /**
     * map操作符
     * 将被观察者发送的事件转换为任意的类型事件
     * 一般用于数据类型转换
     */
    private void map(){
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onComplete();
            }
        }).map(new Function<Integer, String>() {
            @Override
            public String apply(@NonNull Integer integer) throws Exception {
                return "整型参数"+integer+"变成字符串";
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull String s) {
                Log.e("map onNext= ",s);
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
     * flatMap
     * 将被观察者发送的事件序列进行拆分和单独转换，再合并成一个新的事件序列，最后再进行发送
     * 新合并生成的事件序列顺序是无序的，即 与旧序列发送事件的顺序无关
     */
    private void flatMap(){
        Observable.just(1,2,3).flatMap(new Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(@NonNull Integer integer) throws Exception {
                final List<String> list = new ArrayList<>();
                for (int i = 0; i < 3; i++) {
                    list.add("我是事件 " + integer + "拆分后的子事件" + i);
                    // 通过flatMap中将被观察者生产的事件序列先进行拆分，再将每个事件转换为一个新的发送三个String事件
                    // 最终合并，再发送给被观察者
                }
                return Observable.fromIterable(list);
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull String s) {
                Log.e("flatMap onNext=",s);
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
     * 作用和flatMap类似，只不过区别在于：拆分 & 重新合并生成的事件序列的顺序 = 被观察者旧序列生产的顺序
     */
    private void concatMap(){
        Observable.just(1,2,3).concatMap(new Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(@NonNull Integer integer) throws Exception {
                final List<String> list = new ArrayList<>();
                for (int i = 0; i < 3; i++) {
                    list.add("我是事件 " + integer + "拆分后的子事件" + i);
                    // 通过flatMap中将被观察者生产的事件序列先进行拆分，再将每个事件转换为一个新的发送三个String事件
                    // 最终合并，再发送给被观察者
                }
                return Observable.fromIterable(list);
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull String s) {
                Log.e("flatMap onNext=",s);
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
     * switchMap
     * switchMap和flatMap功能类似，区别在于：switchMap每当源Observable发射一个新的数据项（Observable）时，它将取消订阅并停止监视之前那个数据项产生的Observable，并开始监视当前发射的这一个。
     */
    private void switchMap(){
        Observable.just(1,2,3,4,5,6).switchMap(new Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(@NonNull Integer integer) throws Exception {
                return Observable.just(integer+"--").subscribeOn(Schedulers.newThread());
            }
        }).subscribeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull String s) {
                Log.e("switchMap onNext=",s);
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
     * buffer(int count)  count:每个缓冲区在发射前的最大数目
     * 将源Observable变换一个新的Observable，这个新的Observable每次发射一组列表值而不是一个一个发射。
     *  放到缓存区中，最终发送
     *  buffer(3)结果：
     *  E/buffer onNext=: 缓冲区事件数量=3
        E/buffer onNext=: 事件为1
        E/buffer onNext=: 事件为2
        E/buffer onNext=: 事件为3
        E/buffer onNext=: 缓冲区事件数量=3
        E/buffer onNext=: 事件为4
        E/buffer onNext=: 事件为5
        E/buffer onNext=: 事件为6
     *
     *
     *  buffer(int count, int skip):此后每skip项数据，然后又用count项数据填充缓冲区
     *  buffer(3,1)结果：
     *  E/buffer onNext=: 缓冲区事件数量=3
        E/buffer onNext=: 事件为1
        E/buffer onNext=: 事件为2
        E/buffer onNext=: 事件为3
        E/buffer onNext=: 缓冲区事件数量=3
        E/buffer onNext=: 事件为2
        E/buffer onNext=: 事件为3
        E/buffer onNext=: 事件为4
        E/buffer onNext=: 缓冲区事件数量=3
        E/buffer onNext=: 事件为3
        E/buffer onNext=: 事件为4
        E/buffer onNext=: 事件为5
        E/buffer onNext=: 缓冲区事件数量=3
        E/buffer onNext=: 事件为4
        E/buffer onNext=: 事件为5
        E/buffer onNext=: 事件为6
        E/buffer onNext=: 缓冲区事件数量=2
        E/buffer onNext=: 事件为5
        E/buffer onNext=: 事件为6
        E/buffer onNext=: 缓冲区事件数量=1
        E/buffer onNext=: 事件为6
     */
    private void buffer(){
        Observable.just(1,2,3,4,5,6).buffer(3).subscribe(new Observer<List<Integer>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull List<Integer> integers) {
                if (integers != null){
                    Log.e("buffer onNext=","缓冲区事件数量="+integers.size());
                    for (Integer value: integers) {
                        Log.e("buffer onNext=","事件为"+value);
                    }
                }
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
     * window
     * window非常类似buffer操作符，区别在于buffer操作符产生的结果是一个List，而window操作符产生的结果是一个Observable，
     * 订阅者可以对这个结果Observable重新进行订阅处理
     */
    private void window(){
        Observable.just(1,2,3).window(2,1).subscribe(new Observer<Observable<Integer>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Observable<Integer> integerObservable) {

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
     * scan
     * 对一个序列的数据应用一个函数，并将这个函数的结果发射出去作为下个数据应用合格函数时的第一个参数使用
     */
    private void scan(){
        Observable.just(1,2,3,4,5).scan(new BiFunction<Integer, Integer, Integer>() {
            @Override
            public Integer apply(@NonNull Integer integer, @NonNull Integer integer2) throws Exception {
                return integer+integer2;
            }
        }).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Integer integer) {
                Log.e("scan onNext=",integer+"");
                /**
                 * 结果为：
                 * E/scan onNext=: 1
                 * E/scan onNext=: 3
                 * E/scan onNext=: 6
                 * E/scan onNext=: 10
                 * E/scan onNext=: 15
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
     * groupBy
     * 将原始Observable发射的数据按照key来拆分成一些小的Observable，然后这些小Observable分别发射其所包含的的数据.
     * 返回GroupedObservable的结果集，GroupedObservable中存在一个方法为getKey()，可以通过该方法获取结果集的Key值
     */
    private void groupBy(){
        Observable.just(1,2,3,4,5,6).groupBy(new Function<Integer, Integer>() {
            @Override
            public Integer apply(@NonNull Integer integer) throws Exception {
                return integer%2;
            }
        }).subscribe(new Observer<GroupedObservable<Integer, Integer>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull final GroupedObservable<Integer, Integer> integerIntegerGroupedObservable) {
                integerIntegerGroupedObservable.subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.e("groupBy onNext","key="+integerIntegerGroupedObservable.getKey()+"--value="+integer);
                        /**
                         * 结果为：
                         * E/groupBy onNext: key=1--value=1
                         * E/groupBy onNext: key=0--value=2
                         * E/groupBy onNext: key=1--value=3
                         * E/groupBy onNext: key=0--value=4
                         * E/groupBy onNext: key=1--value=5
                         * E/groupBy onNext: key=0--value=6
                         */
                    }
                });

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
