package com.yidian.news.packinfo;

import android.graphics.drawable.Drawable;
import android.util.Log;

/**
 * Created by a on 15/12/17.
 */
public class AppInfo {
    public String appName = "";
    public String packageName = "";
    public String versionName = "";
    public int versionCode = 0;
    public Drawable appIcon = null;

    public void print() {
        Log.v("app", "Name:" + appName + " Package:" + packageName);
        Log.v("app", "Name:" + appName + " versionName:" + versionName);
        Log.v("app", "Name:" + appName + " versionCode:" + versionCode);
    }
}
