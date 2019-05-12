package com.wyh.ffmpegcmd.common;

import android.media.MediaMetadataRetriever;
import android.media.MediaScannerConnection;

import com.wyh.ffmpegcmd.util.FileUtil;

/**l
 * Created by wyh on 2019/5/12.
 */
public class VideoUtil {

    public static String getVideoDuration(String path) {
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        mmr.setDataSource(path);
        return mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
    }

    /**
     * 扫描媒体文件
     *
     * @param filePath
     */
    public static void scanFile(String filePath) {
        if (FileUtil.isFileExist(filePath)) {
            MediaScannerConnection.scanFile(App.get(),
                    new String[]{filePath}, null, null);
        }
    }

}
