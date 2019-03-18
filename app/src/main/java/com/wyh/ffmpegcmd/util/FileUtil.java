package com.wyh.ffmpegcmd.util;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;

import java.io.File;
import java.net.URISyntaxException;

/**
 * Created by wyh on 2019/3/17.
 */
public class FileUtil {
    public static final String ROOT_DIR = Environment.getExternalStorageDirectory().getPath() +
            File.separator + "FFmpegCmd";
    public static final String OUTPUT_DIR = ROOT_DIR + File.separator + "Output";
    public static final String OUTPUT_AUDIO_DIR = OUTPUT_DIR + File.separator + "audio";
    public static final String OUTPUT_VIDEO_DIR = OUTPUT_DIR + File.separator + "video";
    public static final String CACHE_DIR = ROOT_DIR + File.separator + "cache";

    static {
        mkDirs(ROOT_DIR);
        mkDirs(OUTPUT_DIR);
        mkDirs(OUTPUT_AUDIO_DIR);
        mkDirs(OUTPUT_VIDEO_DIR);
        mkDirs(CACHE_DIR);
    }

    public static void mkDirs(String dirPath) {
        File file = new File(dirPath);
        if (!file.exists()) {
            file.mkdirs();
        }
    }


    public static String getPath(Context context, Uri uri) {
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = {"_data"};
            Cursor cursor = null;
            try {
                cursor = context.getContentResolver().query(uri, projection, null, null, null);
                int column_index = cursor.getColumnIndexOrThrow("_data");
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index);
                }
            } catch (Exception e) {
                // Eat it  Or Log it.
            }
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }
}
