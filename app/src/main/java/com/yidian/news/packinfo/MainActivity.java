package com.yidian.news.packinfo;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity{
    private List<AppInfo> appList;
    private ListView listView;
    private EditText searchBox;
    private MyAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.list);

        searchBox=(EditText)findViewById(R.id.searchbox);

        initListViewAdapter();
        setSearchBoxTextChanged();
    }

    private void setSearchBoxTextChanged(){
        searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                arrayAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void initListViewAdapter(){
        try {
            appList = new ArrayList<>();
            List<PackageInfo> packages = getPackageManager().getInstalledPackages(0);
            for (int i = 0; i < packages.size(); ++i) {
                PackageInfo packageInfo = packages.get(i);
                AppInfo tmpInfo = new AppInfo();
                tmpInfo.appName = packageInfo.applicationInfo.loadLabel(getPackageManager()).toString();
                tmpInfo.packageName = packageInfo.packageName;
                tmpInfo.versionName = packageInfo.versionName;
                tmpInfo.versionCode = packageInfo.versionCode;
                tmpInfo.appIcon = packageInfo.applicationInfo.loadIcon(getPackageManager());

                //获取非系统应用
//                if((packageInfo.applicationInfo.flags& ApplicationInfo.FLAG_SYSTEM)==0){
//                    appList.add(tmpInfo);
//                }

                appList.add(tmpInfo);
                arrayAdapter = new MyAdapter(getApplicationContext(), appList);
                listView.setAdapter(arrayAdapter);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}