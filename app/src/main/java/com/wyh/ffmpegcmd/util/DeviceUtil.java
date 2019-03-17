package com.wyh.ffmpegcmd.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;

import com.wyh.ffmpegcmd.common.App;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.lang.reflect.Field;

/**
 * Created by jieping on 2018/11/10.
 */

public class DeviceUtil {
    private static final String TAG = "DeviceUtil";

    /**
     * 密度转换像素
     *
     * @param pDipValue dp值
     * @return 像素
     */
    public static float dp2px(Context context, float pDipValue) {
        DisplayMetrics dm = context.getApplicationContext().getResources().getDisplayMetrics();
        return pDipValue * dm.density;
    }

    /**
     * 密度转换像素
     *
     * @param pDipValue dp值
     * @return 像素
     */
    public static int dp2px(float pDipValue) {
        return (int) dp2px(App.get(), pDipValue);
    }

    /**
     * 密度转换像素
     *
     * @param dipValue dp值
     * @return 像素
     */
    public static int dip2px(Context context, float dipValue) {
        DisplayMetrics dm = context.getApplicationContext().getResources().getDisplayMetrics();
        return (int) (dipValue * dm.density + 0.5f);
    }

    /**
     * 密度转换像素
     *
     * @param dipValue dp值
     * @return 像素
     */
    public static int dip2px(float dipValue) {
        return dip2px(App.get(), dipValue);
    }

    /**
     * 获取屏幕密度值
     *
     * @return 屏幕密度值
     */
    public static float getDensityValue() {
        return getDensityValue(App.get());
    }

    /**
     * 获取屏幕密度值
     *
     * @return 屏幕密度值
     */
    public static float getDensityValue(Context context) {
        DisplayMetrics dm = context.getApplicationContext().getResources().getDisplayMetrics();
        return dm.density;
    }

    /**
     * 获取手机品牌
     *
     * @return 手机品牌
     * @see Build#BRAND
     */
    public static String getDeviceBrand() {
        return Build.BRAND;
    }

    /**
     * 获取手机型号
     *
     * @return 手机型号
     * @see Build#MODEL
     */
    public static String getDeviceMode() {
        return Build.MODEL;
    }

    /**
     * 获取手机版本号
     *
     * @return 手机版本号
     * @see android.os.Build.VERSION#RELEASE
     */
    public static String getDeviceVersionoRelease() {
        return Build.VERSION.RELEASE;
    }

    /**
     * 获取屏幕密度值
     *
     * @return 屏幕密度值
     * @see #getDensityValue(Context)
     */
    public static float getDmDensity(Context context) {
        return getDensityValue(context);
    }

    // /**
    // * 当前屏幕的density因子
    // * @param dmDensityDpi
    // * @retrun DmDensity Setter
    // * */
    // public static void setDmDensityDpi(float dmDensityDpi) {
    // mDensityDpi = dmDensityDpi;
    // }

    /**
     * 获取当前屏幕的显示密度（dpi）
     *
     * @retrun 当前屏幕的显示密度
     */
    public static float getDmDensityDpi() {
        return getDmDensityDpi(App.get());
    }

    /**
     * 当前屏幕的显示密度
     *
     * @retrun 当前屏幕的显示密度
     */
    public static float getDmDensityDpi(Context context) {
        DisplayMetrics dm = context.getApplicationContext().getResources().getDisplayMetrics();
        return dm.densityDpi;
    }

    /***
     *  获取设备的IMEI值
     * @return 设备的IMEI值
     */
    @SuppressLint("MissingPermission")
    public static String getImeiValue() {
        String deviceId = null;
        try {
            deviceId = ((TelephonyManager) App.get().getSystemService(Context.TELEPHONY_SERVICE))
                    .getDeviceId();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return deviceId;
    }

    /***
     *  获得手机MAC值
     * @return 手机MAC值，不一定能获得到值，有可能返回null
     */
    public static String getMacValue() {

        String macValue = null;

        try {
            // WifiManager获得方式在开机后没有打开过wifi，就无法获得mac
            WifiManager wifi = (WifiManager) App.get().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            WifiInfo info = wifi.getConnectionInfo();
            macValue = info.getMacAddress();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (TextUtils.isEmpty(macValue)) {
            try {
                String str = "";
                // 该方式在MX、root的小米上均无法获得，在联想S720上可以
                Process pp = Runtime.getRuntime().exec("cat /sys/class/net/wlan0/address ");
                InputStreamReader ir = new InputStreamReader(pp.getInputStream());
                LineNumberReader input = new LineNumberReader(ir);

                for (; null != str; ) {
                    str = input.readLine();
                    if (str != null) {
                        macValue = str.trim();// 去空格
                        break;
                    }
                }
                Log.d(TAG, "Runtime mac=" + macValue);
            } catch (IOException ex) {
                // 赋予默认值
            }
        }
        return macValue;
    }

    /**
     * 获取唯一的设备ID，先获取Imei码，如果获取不到，<br>
     * 获取手机Mac值，如果再获取不到，获取AndroidId <br>
     * 如果都获取不到，返回null
     *
     * @return 唯一的设备ID
     */
    public static String getDeviceId() {
        String deviceId = null;
        // 先获取imei码
        deviceId = getImeiValue();
        if (TextUtils.isEmpty(deviceId)) {
            // 获取不到imei码，获取手机mac值
            deviceId = getMacValue();
            if (TextUtils.isEmpty(deviceId)) {
                // 获取不到手机mac值，获取android id
                deviceId = Settings.Secure.getString(App.get().getContentResolver(), Settings.Secure.ANDROID_ID);
            }
        }

        return deviceId;
    }

    /**
     * 获取屏幕高度
     *
     * @return 屏幕高度
     */
    public static int getScreenHeight() {
        return getScreenHeight(App.get());
    }

    /**
     * 获取屏幕高度
     *
     * @return 屏幕高度
     */
    public static int getScreenHeight(Context context) {
        DisplayMetrics dm = context.getApplicationContext().getResources().getDisplayMetrics();
        if (dm.widthPixels > dm.heightPixels) {
            return dm.widthPixels;
        } else {
            return dm.heightPixels;
        }
    }

    /**
     * 获取屏幕宽度
     *
     * @return 屏幕宽度
     */
    public static int getScreenWidth() {
        return getScreenWidth(App.get());
    }

    /**
     * 获取屏幕宽度
     *
     * @return 屏幕宽度
     */
    public static int getScreenWidth(Context context) {
        DisplayMetrics dm = context.getApplicationContext().getResources().getDisplayMetrics();
        if (dm.widthPixels > dm.heightPixels) {
            return dm.heightPixels;
        } else {
            return dm.widthPixels;
        }
    }

    /**
     * 获取系统状态栏高度
     *
     * @param context 上下文
     * @return 状态栏高度
     */
    public static int getStatusHeight(Context context) {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int sbar = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            int x = Integer.parseInt(field.get(obj).toString());
            sbar = context.getResources().getDimensionPixelSize(x);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sbar;
    }

    /**
     * 判断设备的API等级是否大于等于11
     *
     * @return true API等级大于等于11，false API等级小于11
     */
    public static boolean hasHoneycomb() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
    }

    /**
     * 判断SDK版本是否超过指定版本号
     *
     * @param sdkNum 指定的SDK版本号
     * @return true 大于指定版本号  false 反之，小于等于指定版本号
     */
    public static boolean isSDKVersionMoreThanSpecifiedNum(int sdkNum) {
        return Build.VERSION.SDK_INT > sdkNum ? true : false;
    }

    /**
     * 像素值转换为dp值
     *
     * @param context 上下文
     * @param pxValue 像素值
     * @return dp值
     */
    public static int px2dip(Context context, float pxValue) {
        DisplayMetrics dm = context.getApplicationContext().getResources().getDisplayMetrics();
        return (int) (pxValue / dm.density + 0.5f);
    }

    /**
     * 像素值转换为dp值
     *
     * @param pxValue 像素值
     * @return dp值
     */
    public static int px2dip(float pxValue) {
        return px2dip(App.get(), pxValue);
    }

}
