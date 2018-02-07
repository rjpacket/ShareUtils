package com.rjp.sharelib;

import android.graphics.Bitmap;
import android.net.Uri;
import android.text.TextUtils;

import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.MultiImageObject;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.VideoSourceObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.share.WbShareHandler;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *
 *
        基于微博sdk_4.1.4

        使用时需要在activity注册：
        ```
        WbSdk.install(this,new AuthInfo(this, Constants.APP_KEY, Constants.REDIRECT_URL, Constants.SCOPE));
        shareHandler = new WbShareHandler(this);
        shareHandler.registerApp();

        protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        shareHandler.doResultIntent(intent,this);
        }
        ```
        参数定义如下：
        ```
        当前 DEMO 应用的 APP_KEY，第三方应用应该使用自己的 APP_KEY 替换该 APP_KEY
        public static final String APP_KEY      = "2545760708";


        * 当前 DEMO 应用的回调页，第三方应用可以使用自己的回调页。
        *
        * <p>
        * 注：关于授权回调页对移动客户端应用来说对用户是不可见的，所以定义为何种形式都将不影响，
        * 但是没有定义将无法使用 SDK 认证登录。
        * 建议使用默认回调页：https://api.weibo.com/oauth2/default.html
        * </p>

        public static final String REDIRECT_URL = "http://www.sina.com";


        * Scope 是 OAuth2.0 授权机制中 authorize 接口的一个参数。通过 Scope，平台将开放更多的微博
        * 核心功能给开发者，同时也加强用户隐私保护，提升了用户体验，用户在新 OAuth2.0 授权页中有权利
        * 选择赋予应用的功能。
        *
        * 我们通过新浪微博开放平台--管理中心--我的应用--接口管理处，能看到我们目前已有哪些接口的
        * 使用权限，高级权限需要进行申请。
        *
        * 目前 Scope 支持传入多个 Scope 权限，用逗号分隔。
        *
        * 有关哪些 OpenAPI 需要权限申请，请查看：http://open.weibo.com/wiki/%E5%BE%AE%E5%8D%9AAPI
        * 关于 Scope 概念及注意事项，请查看：http://open.weibo.com/wiki/Scope

        public static final String SCOPE =
               "email,direct_messages_read,direct_messages_write,"
               + "friendships_groups_read,friendships_groups_write,statuses_to_me_read,"
               + "follow_app_official_microblog," + "invitation_write";
        ```
 * author : Gimpo create on 2018/2/7 15:12
 * email  : jimbo922@163.com
 */

public class WBShareUtils {

    /**
     * 分享 本地图片
     * @param shareHandler shareHandler
     * @param bitmap 图片地址
     * @param clientOnly 是否只分享客户端
     */
    public static void shareImage(WbShareHandler shareHandler, Bitmap bitmap, boolean clientOnly) {
        shareWeiBo(shareHandler, "", "", "", bitmap, null, "", clientOnly);
    }

    /**
     * 分享 文字
     * @param shareHandler shareHandler
     * @param title 标题
     * @param content 内容
     * @param targetUrl 链接
     * @param clientOnly 是否只分享客户端
     */
    public static void shareText(WbShareHandler shareHandler, String title, String content, String targetUrl, boolean clientOnly) {
        shareWeiBo(shareHandler, title, content, targetUrl, null, null, "", clientOnly);
    }

    /**
     * 分享 文字加图片
     * @param shareHandler shareHandler
     * @param title 标题
     * @param content 内容
     * @param targetUrl 链接
     * @param bitmap 图片
     * @param clientOnly 是否只分享客户端
     */
    public static void shareImageAndText(WbShareHandler shareHandler, String title, String content, String targetUrl, Bitmap bitmap, boolean clientOnly) {
        shareWeiBo(shareHandler, title, content, targetUrl, bitmap, null, "", clientOnly);
    }

    /**
     * 分享多图
     * @param shareHandler shareHandler
     * @param title 主题
     * @param content 内容
     * @param targetUrl 链接
     * @param imageUrls 多图
     * @param clientOnly 是否只分享客户端
     */
    public static void shareImages(WbShareHandler shareHandler, String title, String content, String targetUrl, List<String> imageUrls, boolean clientOnly) {
        shareWeiBo(shareHandler, title, content, targetUrl, null, imageUrls, "", clientOnly);
    }

    /**
     * 分享视频
     * @param shareHandler shareHandler
     * @param title 主题
     * @param content 内容
     * @param targetUrl 链接
     * @param videoUrl 视频地址
     * @param clientOnly 是否只分享客户端
     */
    public static void shareVideo(WbShareHandler shareHandler, String title, String content, String targetUrl, String videoUrl, boolean clientOnly) {
        shareWeiBo(shareHandler, title, content, targetUrl, null, null, videoUrl, clientOnly);
    }

    /**
     *
     * 分享
     * @param shareHandler shareHandler
     * @param title 主题
     * @param content 内容
     * @param targetUrl 链接
     * @param bitmap 图片
     * @param imageUrls 多图
     * @param videoUrl 视频
     * @param clientOnly 是否只分享客户端
     */
    private static void shareWeiBo(WbShareHandler shareHandler, String title, String content, String targetUrl, Bitmap bitmap, List<String> imageUrls, String videoUrl, boolean clientOnly) {
        WeiboMultiMessage weiboMessage = new WeiboMultiMessage();

        TextObject textObject = new TextObject();
        textObject.title = title;
        textObject.text = content;
        textObject.actionUrl = targetUrl;
        weiboMessage.textObject = textObject;

        if (bitmap != null) {
            // 设置 Bitmap 类型的图片到视频对象里        设置缩略图。 注意：最终压缩过的缩略图大小 不得超过 32kb。
            ImageObject imageObject = new ImageObject();
            imageObject.setImageObject(bitmap);
            weiboMessage.imageObject = imageObject;
        }

        if (imageUrls != null && imageUrls.size() > 0) {
            MultiImageObject multiImageObject = new MultiImageObject();
            //pathList设置的是本地本件的路径,并且是当前应用可以访问的路径，现在不支持网络路径（多图分享依靠微博最新版本的支持，所以当分享到低版本的微博应用时，多图分享失效
            // 可以通过WbSdk.hasSupportMultiImage 方法判断是否支持多图分享,h5分享微博暂时不支持多图）多图分享接入程序必须有文件读写权限，否则会造成分享失败
            ArrayList<Uri> pathList = new ArrayList<Uri>();
            for (String imageUrl : imageUrls) {
                pathList.add(Uri.fromFile(new File(imageUrl)));
            }
            multiImageObject.setImageList(pathList);
            weiboMessage.multiImageObject = multiImageObject;
        }

        //获取视频
        if (!TextUtils.isEmpty(videoUrl)) {
            VideoSourceObject videoSourceObject = new VideoSourceObject();
            videoSourceObject.videoPath = Uri.fromFile(new File(videoUrl));
            weiboMessage.videoSourceObject = videoSourceObject;
        }

        shareHandler.shareMessage(weiboMessage, clientOnly);
    }

}
