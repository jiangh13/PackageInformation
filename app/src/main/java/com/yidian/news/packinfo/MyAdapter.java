package com.yidian.news.packinfo;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by a on 15/12/17.
 */
public class MyAdapter extends BaseAdapter{
    //数据总集合
    private List<AppInfo> myAppInfoForeverList;
    private Context context;

    private List<AppInfo> mFilteredArrayList;
    private List<AppInfo> myAppInfoList;

    public MyAdapter(Context context,List<AppInfo> list){
        this.context=context;
        this.myAppInfoForeverList=list;
        mFilteredArrayList=new ArrayList<>();
        myAppInfoList=new ArrayList<>();
        myAppInfoList.addAll(myAppInfoForeverList);
    }
    @Override
    public int getCount(){
        return myAppInfoList.size();
    }

    @Override
    public AppInfo getItem(int position){
        return myAppInfoList.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int position,View convertView,ViewGroup parent){
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(context,R.layout.list_item,null);
            holder.image = (ImageView) convertView.findViewById(R.id.ItemImage);
            holder.title = (TextView) convertView.findViewById(R.id.ItemTitle);
            holder.text = (TextView) convertView.findViewById(R.id.ItemText);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.image.setImageDrawable(getItem(position).appIcon);
        holder.title.setText(getItem(position).appName);
        String details = "package name: " + getItem(position).packageName + "\nversion name: " + getItem(position).versionName + "\nversion code: " + getItem(position).versionCode;
        holder.text.setText(details);

        return convertView;
    }

    final class ViewHolder {
        ImageView image;
        TextView title;
        TextView text;
    }
    private NameFilter nameFilter;
    public NameFilter getFilter(){
        if(nameFilter==null){
            nameFilter=new NameFilter();
        }
        return nameFilter;
    }

    class NameFilter extends Filter{
        @Override
        protected FilterResults performFiltering(CharSequence charSequence){
            FilterResults filterResults=new FilterResults();

            if(TextUtils.isEmpty(charSequence)){
                mFilteredArrayList.clear();
                filterResults.values=myAppInfoForeverList;
            }else{
                mFilteredArrayList.clear();
                for(Iterator<AppInfo> iterator=myAppInfoForeverList.iterator();iterator.hasNext();){
                    AppInfo name=iterator.next();
                    if(name.appName.toLowerCase().contains(charSequence.toString().toLowerCase())){
                        mFilteredArrayList.add(name);
                    }
                }
                filterResults.values=mFilteredArrayList;
            }
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence arg0,FilterResults results){
            myAppInfoList=(ArrayList<AppInfo>)results.values;
            if(results.count>0){
                notifyDataSetChanged();
            }else{
                notifyDataSetInvalidated();
            }
        }
    }
}
