package com.rjp.sharelib;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;

import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzonePublish;
import com.tencent.connect.share.QzoneShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;

import java.util.ArrayList;

/**
 *
 *       基于腾讯android_sdk_3.3.1
 *
 *       需要在manifest配置
 *       ```
 *           activity
 *               android:name="com.tencent.tauth.AuthActivity"
 *               android:noHistory="true"
 *               android:launchMode="singleTask"
 *               intent-filter
 *                   action android:name="android.intent.action.VIEW" /
 *                   category android:name="android.intent.category.DEFAULT" /
 *                   category android:name="android.intent.category.BROWSABLE" /
 *                   data android:scheme="tencentXXX(appId)" /
 *               /intent-filter
 *           /activity
 *       ```
 *
 *       调用的地方如果想收到回调，需要增加代码：
 *       ```
 *       protected void onActivityResult(int requestCode, int resultCode, Intent data) {
 *           Tencent.onActivityResultData(requestCode, resultCode, data, iUiListener);
 *       }
 *       ```
 * author : Gimpo create on 2018/2/7 12:07
 * email  : jimbo922@163.com
 */
public class QQShareUtils {

    /**
     *
     * 分享图片和文字混合
     * @param activity activity
     * @param mTencent mTencent
     * @param title 标题 必填
     * @param targetUrl 点击跳转url 必填
     * @param summary 总结 可选
     * @param imageUrl 本地图片或者网络图片地址 可选
     * @param appName app名字 可选
     * @param shareQZone 是否显示分享到qzone
     * @param uiListener 回调
     */
    public static void shareImageAndText(Activity activity, Tencent mTencent, String title, String targetUrl, String summary, String imageUrl, String appName, boolean shareQZone, IUiListener uiListener) {
        Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
        params.putString(QQShare.SHARE_TO_QQ_TITLE, title);
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, targetUrl);
        if(!TextUtils.isEmpty(summary)) {
            params.putString(QQShare.SHARE_TO_QQ_SUMMARY, summary);
        }
        if(!TextUtils.isEmpty(imageUrl)) {
            params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, imageUrl);
        }
        if(!TextUtils.isEmpty(appName)) {
            params.putString(QQShare.SHARE_TO_QQ_APP_NAME, appName);
        }
        if (shareQZone) {
            params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN);
        } else {
            params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE);
        }
        mTencent.shareToQQ(activity, params, uiListener);
    }

    /**
     * 分享纯图片
     * @param activity activity
     * @param mTencent mTencent
     * @param imageUrl 注意  本地图片地址
     * @param appName app名字 可选
     * @param shareQZone 是否显示分享到qzone
     * @param uiListener 回调
     */
    public static void shareImage(Activity activity, Tencent mTencent, String imageUrl, String appName, boolean shareQZone, IUiListener uiListener) {
        Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_IMAGE);
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, imageUrl);
        if(!TextUtils.isEmpty(appName)) {
            params.putString(QQShare.SHARE_TO_QQ_APP_NAME, appName);
        }
        if (shareQZone) {
            params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN);
        } else {
            params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE);
        }
        mTencent.shareToQQ(activity, params, uiListener);
    }

    /**
     * 分享音乐
     * @param activity activity
     * @param mTencent mTencent
     * @param title 标题 必填
     * @param summary 总结 可选
     * @param targetUrl 点击链接 必填
     * @param imageUrl  图片地址或者网络地址  可选
     * @param musicUrl 远程音乐地址 不支持本地音乐 必填
     * @param appName app名字  可选
     * @param shareQZone 是否显示分享到qzone
     * @param uiListener 回调
     */
    public static void shareMusic(Activity activity, Tencent mTencent, String title, String summary, String targetUrl, String imageUrl, String musicUrl, String appName, boolean shareQZone, IUiListener uiListener) {
        Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_AUDIO);
        params.putString(QQShare.SHARE_TO_QQ_TITLE, title);
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, summary);
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, targetUrl);
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, imageUrl);
        params.putString(QQShare.SHARE_TO_QQ_AUDIO_URL, musicUrl);
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, appName);
        if (shareQZone) {
            params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN);
        } else {
            params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE);
        }
        mTencent.shareToQQ(activity, params, uiListener);
    }

    /**
     * 分享应用
     * @param activity activity
     * @param mTencent mTencent
     * @param title 标题 必填
     * @param summary 总结  可选
     * @param imageUrl 远程或本地图片地址 可选
     * @param appName app名字  可选
     * @param shareQZone 是否显示分享到qzone
     * @param uiListener 回调
     */
    public static void shareApp(Activity activity, Tencent mTencent, String title, String summary, String imageUrl, String appName, boolean shareQZone, IUiListener uiListener) {
        Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_APP);
        params.putString(QQShare.SHARE_TO_QQ_TITLE, title);
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, summary);
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, imageUrl);
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, appName);
        if (shareQZone) {
            params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN);
        } else {
            params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE);
        }
        mTencent.shareToQQ(activity, params, uiListener);
    }

    /**
     * 发表说说
     * @param activity activity
     * @param mTencent mTencent
     * @param summary 总结 选填 目前没有带过去
     * @param imageUrls 分享的图片 可以是集合 目前只支持上传一张，集合的第一个
     * @param uiListener 回调
     */
    public static void shareQZone(Activity activity, Tencent mTencent, String summary, ArrayList<String> imageUrls, IUiListener uiListener) {
        Bundle params = new Bundle();
        params.putInt(QzonePublish.PUBLISH_TO_QZONE_KEY_TYPE, QzonePublish.PUBLISH_TO_QZONE_TYPE_PUBLISHMOOD);
        params.putString(QzonePublish.PUBLISH_TO_QZONE_SUMMARY, summary);//选填
        params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, imageUrls);
        mTencent.publishToQzone(activity, params, uiListener);
    }

    /**
     * 发表qzone视频
     * @param activity activity
     * @param mTencent mTencent
     * @param summary 选填 一句话总结
     * @param videoUrl 视频地址
     * @param uiListener 回调接口
     */
    public static void shareQZoneVideo(Activity activity, Tencent mTencent, String summary, String videoUrl, IUiListener uiListener) {
        Bundle params = new Bundle();
        params.putInt(QzonePublish.PUBLISH_TO_QZONE_KEY_TYPE, QzonePublish.PUBLISH_TO_QZONE_TYPE_PUBLISHVIDEO);
        params.putString(QzonePublish.PUBLISH_TO_QZONE_SUMMARY, summary);
        params.putString(QzonePublish.PUBLISH_TO_QZONE_VIDEO_PATH, videoUrl);
        mTencent.publishToQzone(activity, params, uiListener);
    }
}
