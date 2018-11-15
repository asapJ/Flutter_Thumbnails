package com.asapjay.thumbnails;

import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.regex.Pattern;

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
    private Registrar mRegistrar;

    private ThumbnailsPlugin(Registrar mRegistrar) {
        this.mRegistrar = mRegistrar;
    }

    public static void registerWith(Registrar registrar) {
        final MethodChannel channel = new MethodChannel(registrar.messenger(), "thumbnails");
        channel.setMethodCallHandler(new ThumbnailsPlugin(registrar));
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
                String thumbnailPath = buildThumbnail(videoFile, thumbOutputFile, imageType, quality);
                result.success(thumbnailPath);
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        } else {
            result.notImplemented();
        }
    }

    private String buildThumbnail(String vidPath, String thumbPath, int type, int quality) {
        if (vidPath == null || vidPath.equals("")) {
            Log.println(Log.INFO, "WARNING", "Thumbnails: Video Path must not be null or empty!");
            return null;
        }
        return thumbPath == null ? cacheDirectory(vidPath, type, quality) : userDirectory(vidPath, thumbPath, type, quality);

    }

    private String cacheDirectory(String vidPath, int type, int quality) {
        Bitmap bitmap = ThumbnailUtils.createVideoThumbnail(vidPath, MediaStore.Video.Thumbnails.MINI_KIND);
        String sourceFileName = getFileName(Uri.parse(vidPath).getLastPathSegment());
        File tempDir = new File(mRegistrar.context().getExternalCacheDir() + File.separator + "ThumbFiles" + File.separator);
        if (tempDir.exists()) {
            clearThumbnails();
        } else {
            tempDir.mkdirs();
        }
        String tempFile = new File(tempDir + File.separator + sourceFileName).getPath();
        switch (type) {
            case 1:
                try {
                    FileOutputStream out = new FileOutputStream(new File(tempFile + ".jpg"));
                    bitmap.compress(Bitmap.CompressFormat.JPEG, quality, out);
                    out.flush();
                    out.close();
                    return tempFile + ".jpg";
                } catch (IOException e) {
                    e.printStackTrace();
                }
            case 2:
                try {
                    FileOutputStream out = new FileOutputStream(new File(tempFile + ".png"));
                    bitmap.compress(Bitmap.CompressFormat.PNG, quality, out);
                    out.flush();
                    out.close();
                    return tempFile + ".png";
                } catch (IOException e) {
                    e.printStackTrace();
                }
            case 3:
                try {
                    FileOutputStream out = new FileOutputStream(new File(tempFile + ".webp"));
                    bitmap.compress(Bitmap.CompressFormat.WEBP, quality, out);
                    out.flush();
                    out.close();
                    return tempFile + ".webp";
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        return "";
    }

    private String userDirectory(String vidPath, String thumbPath, int type, int quality) {
        File fileDir = null;
        Bitmap bitmap = ThumbnailUtils.createVideoThumbnail(vidPath, MediaStore.Video.Thumbnails.MINI_KIND);
        String sourceFileName = getFileName(Uri.parse(vidPath).getLastPathSegment());
        fileDir = new File(thumbPath + File.separator);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        String tempFile = new File(fileDir + File.separator + sourceFileName).getAbsolutePath();
        switch (type) {
            case 1:
                try {
                    FileOutputStream out = new FileOutputStream(new File(tempFile + ".jpg"));
                    bitmap.compress(Bitmap.CompressFormat.JPEG, quality, out);
                    out.flush();
                    out.close();
                    return tempFile + ".jpg";
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

            case 2:
                try {
                    FileOutputStream out = new FileOutputStream(new File(tempFile + ".png"));
                    bitmap.compress(Bitmap.CompressFormat.PNG, quality, out);
                    out.flush();
                    out.close();
                    return tempFile + ".png";
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case 3:
                try {
                    FileOutputStream out = new FileOutputStream(new File(tempFile + "webp"));
                    bitmap.compress(Bitmap.CompressFormat.WEBP, quality, out);
                    out.flush();
                    out.close();
                    return tempFile + ".webp";
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
        return "";
    }

    private void clearThumbnails() {
        String tempDirPath = mRegistrar.context().getExternalCacheDir()
                + File.separator + "TempFiles" + File.separator;
        File tempDir = new File(tempDirPath);
        if (tempDir.exists()) {
            String[] children = tempDir.list();
            for (String file : children) {
                new File(tempDir, file).delete();
            }
        }
    }

    private static final Pattern ext = Pattern.compile("(?<=.)\\.[^.]+$");

    private String getFileName(String s) {
        return ext.matcher(s).replaceAll("");
    }
}
