package com.rjp.sharelib;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXMusicObject;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.tencent.mm.opensdk.modelmsg.WXVideoObject;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;

import java.io.ByteArrayOutputStream;

/**
 *
            基于微信sdk_5.1.4:
            需要在调用的activity注册
            ```
            api = WXAPIFactory.createWXAPI(this, "wx89f865d3974044b2");
            ```
            然后在包名下创建文件夹 axapi - WXEntryActivity 监听分享是否成功。
 * author : Gimpo create on 2018/2/7 17:58
 * email  : jimbo922@163.com
 */

public class WXShareUtils {

    /**
     * 分享文字
     * @param api api
     * @param content 文字
     * @param shareZone 是否分享到朋友圈
     */
    public static void shareText(IWXAPI api, String content, boolean shareZone) {
        WXTextObject textObj = new WXTextObject();
        textObj.text = content;

        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = textObj;
        msg.description = content;

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("text");
        req.message = msg;
        if(shareZone){
            req.scene = SendMessageToWX.Req.WXSceneTimeline;
        }else {
            req.scene = SendMessageToWX.Req.WXSceneSession;
        }
        api.sendReq(req);
    }

    /**
     * 分享bitmap
     * @param api api
     * @param bitmap 图片
     * @param bitmapThumbSize 图片缩略尺寸
     * @param shareZone 是否分享到朋友圈
     */
    public static void shareBitmap(IWXAPI api, Bitmap bitmap, int bitmapThumbSize, boolean shareZone) {
        WXImageObject imgObj = new WXImageObject(bitmap);

        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = imgObj;

        Bitmap thumbBmp = Bitmap.createScaledBitmap(bitmap, bitmapThumbSize, bitmapThumbSize, true);
        bitmap.recycle();
        msg.thumbData = bmpToByteArray(thumbBmp, true);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("img");
        req.message = msg;
        if(shareZone){
            req.scene = SendMessageToWX.Req.WXSceneTimeline;
        }else {
            req.scene = SendMessageToWX.Req.WXSceneSession;
        }
        api.sendReq(req);
    }

    /**
     * 分享文件夹图片
     * @param api api
     * @param filePath 图片本地文件地址
     * @param bitmapThumbSize 图片缩略尺寸
     * @param shareZone 是否分享到朋友圈
     */
    public static void shareImage(IWXAPI api, String filePath, int bitmapThumbSize, boolean shareZone) {
        WXImageObject imgObj = new WXImageObject();
        imgObj.setImagePath(filePath);

        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = imgObj;

        Bitmap bmp = BitmapFactory.decodeFile(filePath);
        Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, bitmapThumbSize, bitmapThumbSize, true);
        bmp.recycle();
        msg.thumbData = bmpToByteArray(thumbBmp, true);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("img");
        req.message = msg;
        if(shareZone){
            req.scene = SendMessageToWX.Req.WXSceneTimeline;
        }else {
            req.scene = SendMessageToWX.Req.WXSceneSession;
        }
        api.sendReq(req);
    }

    /**
     * 分享音乐
     * @param api api
     * @param musicUrl 音乐远程地址
     * @param title 主题
     * @param description 描述
     * @param musicBitmap 音乐缩略图
     * @param bitmapThumbSize 图片缩略尺寸
     * @param shareZone 是否分享到朋友圈
     */
    public static void shareMusic(IWXAPI api, String musicUrl, String title, String description, Bitmap musicBitmap, int bitmapThumbSize, boolean shareZone) {
        WXMusicObject music = new WXMusicObject();
        music.musicUrl= musicUrl;

        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = music;
        msg.title = title;
        msg.description = description;

        Bitmap thumbBmp = Bitmap.createScaledBitmap(musicBitmap, bitmapThumbSize, bitmapThumbSize, true);
        musicBitmap.recycle();
        msg.thumbData = bmpToByteArray(thumbBmp, true);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("music");
        req.message = msg;
        if(shareZone){
            req.scene = SendMessageToWX.Req.WXSceneTimeline;
        }else {
            req.scene = SendMessageToWX.Req.WXSceneSession;
        }
        api.sendReq(req);
    }

    /**
     * 分享视频
     * @param api api
     * @param videoUrl 视频地址
     * @param title 主题
     * @param description 描述
     * @param videoBitmap 视频缩略图
     * @param bitmapThumbSize 缩略图尺寸
     * @param shareZone 是否分享到朋友圈
     */
    public static void shareVideo(IWXAPI api, String videoUrl, String title, String description, Bitmap videoBitmap, int bitmapThumbSize, boolean shareZone) {
        WXVideoObject video = new WXVideoObject();
        video.videoUrl = videoUrl;

        WXMediaMessage msg = new WXMediaMessage(video);
        msg.title = title;
        msg.description = description;
        Bitmap thumbBmp = Bitmap.createScaledBitmap(videoBitmap, bitmapThumbSize, bitmapThumbSize, true);
        videoBitmap.recycle();
        msg.thumbData = bmpToByteArray(thumbBmp, true);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("video");
        req.message = msg;
        if(shareZone){
            req.scene = SendMessageToWX.Req.WXSceneTimeline;
        }else {
            req.scene = SendMessageToWX.Req.WXSceneSession;
        }
        api.sendReq(req);
    }

    /**
     * 分享网页
     * @param api api
     * @param webUrl 网页地址
     * @param title 标题
     * @param description 描述
     * @param webBitmap 网页缩略图
     * @param bitmapThumbSize 缩略图尺寸
     * @param shareZone 是否分享到朋友圈
     */
    public static void shareWebPage(IWXAPI api, String webUrl, String title, String description, Bitmap webBitmap, int bitmapThumbSize, boolean shareZone) {
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = webUrl;
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = title;
        msg.description = description;
        Bitmap thumbBmp = Bitmap.createScaledBitmap(webBitmap, bitmapThumbSize, bitmapThumbSize, true);
        webBitmap.recycle();
        msg.thumbData = bmpToByteArray(thumbBmp, true);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("webpage");
        req.message = msg;
        if(shareZone){
            req.scene = SendMessageToWX.Req.WXSceneTimeline;
        }else {
            req.scene = SendMessageToWX.Req.WXSceneSession;
        }
        api.sendReq(req);
    }

    public static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, output);
        if (needRecycle) {
            bmp.recycle();
        }

        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    private static String buildTransaction(String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }
}
