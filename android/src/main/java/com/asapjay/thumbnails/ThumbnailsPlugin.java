package com.asapjay.thumbnails;

import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

/**
 * ThumbnailsPlugin
 */
public class ThumbnailsPlugin implements MethodCallHandler {
    /**
     * Plugin registration.
     */
    public static void registerWith(Registrar registrar) {
        final MethodChannel channel = new MethodChannel(registrar.messenger(), "thumbnails");
        channel.setMethodCallHandler(new ThumbnailsPlugin());
    }

    @Override
    public void onMethodCall(MethodCall call, Result result) {
        Map<String, Object> arguments = call.arguments();
        if (call.method.equals("getThumbnail")) {

            String videoFile = (String) arguments.get("videoFilePath");
            String thumbOutputFile = (String) arguments.get("thumbFilePath");
            int imageType = (int) arguments.get("thumbnailFormat");
            int quality = (int) arguments.get("thumbnailQuality");
            try {
                buildThumbnail(videoFile, thumbOutputFile, imageType, quality);
                result.success("success");
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        } else {
            result.notImplemented();
        }
    }

    private void buildThumbnail(String vidPath, String thumbPath, int type, int quality) {

        if (vidPath == null || vidPath == "") {
            Log.println(Log.INFO, "WARNING", "Thumbnails: Video Path must not be null or empty");
            return;
        }
        if (thumbPath == null || thumbPath == "") {
            Log.println(Log.INFO, "WARNING", "Thumbnails: Thumbnail Path must not be null or empty");
            return;
        }

        Bitmap bitmap = ThumbnailUtils.createVideoThumbnail(vidPath, MediaStore.Video.Thumbnails.MINI_KIND);
        switch (type) {
            case 1:
                try {
                    FileOutputStream out = new FileOutputStream(new File(thumbPath));
                    bitmap.compress(Bitmap.CompressFormat.JPEG, quality, out);
                    out.flush();
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

            case 2:
                try {
                    FileOutputStream out = new FileOutputStream(new File(thumbPath));
                    bitmap.compress(Bitmap.CompressFormat.PNG, quality, out);
                    out.flush();
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case 3:
                try {
                    FileOutputStream out = new FileOutputStream(new File(thumbPath));
                    bitmap.compress(Bitmap.CompressFormat.WEBP, quality, out);
                    out.flush();
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

        }
    }
}
