package com.wyh.ffmpegcmd.common;

import android.media.MediaMetadataRetriever;

/**
 * Created by wyh on 2019/5/12.
 */
public class VideoUtil {

    public static String getVideoDuration(String path) {
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        mmr.setDataSource(path);
        return mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
    }

}
