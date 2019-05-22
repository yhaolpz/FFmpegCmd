package com.wyh.ffmpegcmd.util;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

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


    public static String getPath(final Context context, final Uri uri) {

        // DocumentProvider
        if (DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }



    /**
     * 将assets里的文件拷贝到指定目录
     *
     * @param context        上下文
     * @param assetsFilePath assets目录下的文件路径
     * @param targetFilePath 要拷贝的位置
     * @return 返回是否拷贝成功
     */
    public static boolean copyFromAssets(Context context, String assetsFilePath, String targetFilePath) {
        boolean result = false;
        InputStream in = null;
        FileOutputStream fs = null;
        File file = new File(targetFilePath);
        try {
            in = context.getAssets().open(assetsFilePath);
            if (file.exists()) {
                deleteFile(file);
            } else {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            fs = new FileOutputStream(targetFilePath);
            byte[] buffer = new byte[1024];
            int n;
            while ((n = in.read(buffer)) != -1) {
                fs.write(buffer, 0, n);
            }
            result = true;
        } catch (Throwable e) {
            result = false;
            try {
                deleteFile(file);
            } catch (Exception e1) {
            }
        } finally {
            if (null != fs) {
                try {
                    fs.close();
                } catch (Exception e) {
                }
            }
            if (null != in) {
                try {
                    in.close();
                } catch (Exception e) {
                }
            }
        }
        return result;
    }

    /**
     * 删除指定文件
     *
     * @param path 文件路径
     * @return true操作成功，false操作失败
     */
    public static boolean deleteFile(final String path) {
        if (!TextUtils.isEmpty(path)) {
            File file = new File(path);
            return deleteFile(file);
        }

        return false;
    }

    /**
     * 递归删除某个目录下的所有文件
     *
     * @param file 要删除的根目录
     * @return true操作成功，false操作失败
     */
    public static boolean deleteFile(File file) {
        if (file != null && file.exists()) {
            final File to = new File(file.getAbsolutePath() + System.currentTimeMillis());
            file.renameTo(to);
            return to.delete();
        }
        return false;
    }

    public static boolean isFileExist(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return false;
        }

        if (StorageUtils.isExternalStorageReadable()) {
            return new File(filePath).exists();
        } else {
            return false;
        }
    }
}
