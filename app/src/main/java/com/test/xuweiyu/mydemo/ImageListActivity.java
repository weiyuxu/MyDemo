package com.test.xuweiyu.mydemo;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.test.xuweiyu.mydemo.adapter.RecycleImageAdapter;
import com.test.xuweiyu.mydemo.bean.ImageBean;
import com.test.xuweiyu.mydemo.net.HttpUrlConnectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static android.support.v4.content.PermissionChecker.PERMISSION_DENIED;

/**
 * 实现读取手机文件夹下的所有图片并将其展示在列表中
 * Created by xuweiyu on 19-2-12.
 * Email:xuweiyu@xiaomi.com
 */
public class ImageListActivity extends Activity implements RecycleImageAdapter.ItemClickListener {
    private static final String TAG = "ImageListActivity";
    private RecyclerView mRecyclerView;
    private RecycleImageAdapter mRecycleViewAdapter;
    private List<ImageBean> mImagesBeans = new ArrayList<>();
    private static final int REQUEST_CODE = 1000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_list);
        initView();
//        initImageList();
        initPhoneInfo();
    }

    private void initPhoneInfo() {
        String[] phones = {"18202959982", "18612357511", "18578271900", "18943863886", "15531692314"
                , "18202959982", "18612357511", "18578271900", "18943863886", "15531692314"
                , "18202959982", "18612357511", "18578271900", "18943863886", "15531692314"};
        Observable.from(phones).map(new Func1<String, String>() {
            @Override
            public String call(String s) {
                String url = "https://tcc.taobao.com/cc/json/mobile_tel_segment.htm?tel=" + s;
                return url;
            }
        }).map(new Func1<String, String>() {
            @Override
            public String call(String url) {
                return HttpUrlConnectRequest.request(url, HttpUrlConnectRequest.GET);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                mRecycleViewAdapter.refresh(mImagesBeans);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                mImagesBeans.add(parseData(s));
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case mCode:
                initImages();
                break;
        }
    }

    private static final int mCode = 100;

    private void initImageList() {
        //Android 6.0之后使用动态权限后某些敏感权限必须在使用的时候得到用户的同意
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
            String permission = Manifest.permission.READ_EXTERNAL_STORAGE;
            int permissionCheck = ActivityCompat.checkSelfPermission(ImageListActivity.this, permission);
            if (permissionCheck == PERMISSION_DENIED) {
                ActivityCompat.requestPermissions(ImageListActivity.this, new String[]{permission}, mCode);
            } else {
                initImages();
            }
        }
    }

    private void initImages() {
        obtainFilePaths("/Download");
//        obtainAllFiles(Environment.getExternalStorageDirectory().listFiles(), 1);
        if (filePaths.size() > 0) {
            Subscriber<ImageBean> subscriber = new Subscriber<ImageBean>() {
                @Override
                public void onCompleted() {
//                    mAdapter.refreshData(mImages);
                    mRecycleViewAdapter.refresh(mImagesBeans);
                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onNext(ImageBean imageBean) {
                    mImagesBeans.add(imageBean);
                }
            };
            Observable.from(filePaths).map(new Func1<String, File>() {
                @Override
                public File call(String name) {
                    return getFileByName(name);
                }
            }).map(new Func1<File, ImageBean>() {
                @Override
                public ImageBean call(File file) {
                    return turnFileToBitmap(file);
                }
            }).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(subscriber);
        }
    }

    private File getFileByName(String name) {
        File file = new File(name);
        Log.d(TAG, "fileName = " + name);
        return file;
    }

    private int mNumber = 0;

    private ImageBean turnFileToBitmap(File file) {
        String filePath = file.getPath();
        Log.d(TAG, "filePath = " + filePath);
        Bitmap bitmap = BitmapFactory.decodeFile(filePath);
        ImageBean bean = new ImageBean(mNumber % 2 == 1 ? ImageBean.IMAGE : ImageBean.TEXT);
        if (mNumber % 2 == 1) {
            bean.url = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1550057402852&di=2b24c5826873cd092bad9ecbc95e38c8&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201701%2F15%2F20170115140708_5VRQc.gif";
        } else {
            bean.url = "http://img5.imgtn.bdimg.com/it/u=2907620548,2596885325&fm=26&gp=0.jpg";
        }
//        ImageBean bean = new ImageBean(ImageBean.IMAGE);
        mNumber++;
        bean.bitmap = bitmap;
        bean.name = file.getName();
        return bean;
    }

    List<String> filePaths = new ArrayList<>();

    private void obtainAllFiles(File[] rootFiles) {
        if (rootFiles != null && rootFiles.length >= 1) {
            for (File file : rootFiles) {
                if (file.isDirectory()) {
                    //不要
                } else if (file.isFile()) {
                    int idx = file.getPath().lastIndexOf(".");
                    if (idx > 0) {
                        String end = file.getPath().substring(idx).toLowerCase();
                        if (isImageFile(end)) {
                            filePaths.add(file.getPath());
                        }
                    }
                }
            }
        }

    }

    private void obtainFilePaths(String dir) {
        File file = new File(Environment.getExternalStorageDirectory().getPath() + dir);
        obtainAllFiles(file.listFiles());
    }

    private boolean isImageFile(String end) {
        String[] types = {".jpg", ".png", ".jpeg"};
        for (String type : types) {
            if (TextUtils.equals(end, type)) {
                return true;
            }
        }
        return false;
    }

    private void initView() {
        mRecyclerView = findViewById(R.id.re_image_list);
//        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecycleViewAdapter = new RecycleImageAdapter(ImageListActivity.this, mImagesBeans);
        mRecycleViewAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(mRecycleViewAdapter);
    }

    @Override
    public void onItemClick(int position) {
//        Intent intent = new Intent(ImageListActivity.this, RecreateActivity.class);
//        startActivityForResult(intent, REQUEST_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case REQUEST_CODE:
                if (resultCode == RESULT_OK){
                    recreate();
                }
                break;
        }
    }

    private ImageBean parseData(String result) {
        ImageBean bean = new ImageBean(mNumber % 2 == 1 ? ImageBean.IMAGE : ImageBean.TEXT);
        try {
            JSONObject object = new JSONObject(result.split("=")[1]);
            bean.province = object.optString("province");
            bean.url = getUrl();
            mNumber++;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return bean;
    }

    private String getUrl() {
        String urlStr = "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3625446637,1717567418&fm=26&gp=0.jpg";
        switch (mNumber) {
            case 0:
                urlStr = "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3126040423,2315953127&fm=26&gp=0.jpg";
                break;
            case 1:
                urlStr = "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=4137989149,1549503571&fm=200&gp=0.jpg";
                break;
            case 2:
                urlStr = "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=3621611262,3160781356&fm=26&gp=0.jpg";
                break;
            case 3:
                urlStr = "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=2109284028,930382938&fm=26&gp=0.jpg";
                break;
            case 4:
                urlStr = "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=4235015069,1262950045&fm=26&gp=0.jpg";
                break;
            case 5:
                urlStr = "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=2432096068,1431477362&fm=26&gp=0.jpg";
                break;
            case 6:
                urlStr = "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=1203245418,2545341523&fm=26&gp=0.jpg";
                break;
            case 7:
                urlStr = "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1592276246,4199737058&fm=26&gp=0.jpg";
                break;
            case 8:
                urlStr = "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1945556818,1944964321&fm=26&gp=0.jpg";
                break;
            case 9:
                urlStr = "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2907620548,2596885325&fm=26&gp=0.jpg";
                break;
            case 10:
                urlStr = "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=568508182,3692919244&fm=26&gp=0.jpg";
                break;
            case 11:
                urlStr = "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3625446637,1717567418&fm=26&gp=0.jpg";
                break;
            case 12:
                urlStr = "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=2181612442,667692692&fm=26&gp=0.jpg";
                break;
            case 13:
                urlStr = "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=2194421447,639907921&fm=26&gp=0.jpg";
                break;
            case 14:
                urlStr = "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3034118155,458921461&fm=26&gp=0.jpg";
                break;
            case 15:
                urlStr = "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=182102263,1629884356&fm=26&gp=0.jpg";
                break;
        }
        return urlStr;
    }
}
