package com.rjp.shareutils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;

import com.rjp.sharelib.QQShareUtils;
import com.sina.weibo.sdk.WbSdk;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.share.WbShareCallback;
import com.sina.weibo.sdk.share.WbShareHandler;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

public class MainActivity extends Activity implements View.OnClickListener, WbShareCallback {

    private Tencent mTencent;
    public static final String IMAGE_URL = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1517993752343&di=09e3d741dabab525852b5c36f068b9dc&imgtype=0&src=http%3A%2F%2Fpic.58pic.com%2F58pic%2F12%2F58%2F01%2F90f58PICTw2.jpg";
    public static final String TARGET_URL = "http://www.baidu.com";
    public static final String SONG_URL = "http://music.163.com/#/song?id=534540498";
    private WbShareHandler shareHandler;
    private IWXAPI api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_1).setOnClickListener(this);
        findViewById(R.id.btn_2).setOnClickListener(this);
        findViewById(R.id.btn_3).setOnClickListener(this);
        findViewById(R.id.btn_4).setOnClickListener(this);
        findViewById(R.id.btn_5).setOnClickListener(this);
        findViewById(R.id.btn_6).setOnClickListener(this);
        findViewById(R.id.btn_7).setOnClickListener(this);

        mTencent = Tencent.createInstance("1104943894", this);

        WbSdk.install(this,new AuthInfo(this, Constants.APP_KEY, Constants.REDIRECT_URL, Constants.SCOPE));
        shareHandler = new WbShareHandler(this);
        shareHandler.registerApp();

        api = WXAPIFactory.createWXAPI(this, "wx89f865d3974044b2");
    }

    private IUiListener iUiListener = new IUiListener() {
        @Override
        public void onComplete(Object o) {
            Toast.makeText(MainActivity.this, "btn1", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(UiError uiError) {

        }

        @Override
        public void onCancel() {

        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_1:
                String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/test.mp4";
                Bitmap bitmap = BitmapFactory.decodeFile(path);
                QQShareUtils.shareQZoneVideo(this, mTencent, "一些内容", path, iUiListener);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Tencent.onActivityResultData(requestCode, resultCode, data, iUiListener);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        shareHandler.doResultIntent(intent,this);
    }

    @Override
    public void onWbShareSuccess() {
        Toast.makeText(MainActivity.this, "success", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onWbShareCancel() {

    }

    @Override
    public void onWbShareFail() {

    }
}
