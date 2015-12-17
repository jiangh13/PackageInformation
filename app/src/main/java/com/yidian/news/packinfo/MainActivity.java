package com.yidian.news.packinfo;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity{
    private List<AppInfo> appList;
    private ListView listView;
    private EditText searchBox;
    private MyAdapter arrayAdapter;
    private InputMethodManager inputMethodManager;
    private ImageButton searchClear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inputMethodManager=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        listView = (ListView) findViewById(R.id.list);
        searchBox=(EditText)findViewById(R.id.searchbox);
        searchClear=(ImageButton)findViewById(R.id.searchclear);
        searchClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchBox.getText().clear();
                hideSoftKeyboard();
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                searchBox.clearFocus();
                hideSoftKeyboard();
            }
        });
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
                if(s.length()>0) searchClear.setVisibility(View.VISIBLE);
                else searchClear.setVisibility(View.GONE);
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

    private void hideSoftKeyboard(){
        if(getWindow().getAttributes().softInputMode!= WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN){
            if(getCurrentFocus()!=null){
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }
}