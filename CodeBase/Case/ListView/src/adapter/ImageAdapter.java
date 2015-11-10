package com.example.luzhuo.listview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.luzhuo.listview.R;
import com.example.luzhuo.listview.utils.AsyncImageLoderUtil;

/**
 * =================================================
 * <p/>
 * 作者:卢卓
 * <p/>
 * 版本:1.0
 * <p/>
 * 创建日期:2015/11/10 14:54
 * <p/>
 * 描述:ListView的优化:<br>1.复用convertView;<br>2.异步加载网络图片;<br>3.快速滑动时不显示图片;
 * <p/>
 * 修订历史:
 * <p/>
 * <p/>
 * =================================================
 **/
public class ImageAdapter extends BaseAdapter{
    private Context context;
    private String[] listImage;
    private LayoutInflater inflater;

    public ImageAdapter(Context context, String[] listImage) {
        this.context = context;
        this.listImage = listImage;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listImage.length;
    }

    @Override
    public Object getItem(int position) {
        return listImage[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 1.复用 convertView
        ViewHolder viewHolder;
        if(convertView == null){
            convertView = inflater.inflate(R.layout.item_list,null);
            viewHolder = new ViewHolder();

            viewHolder.imageview = (ImageView)convertView.findViewById(R.id.imageview);
            viewHolder.textview = (TextView)convertView.findViewById(R.id.textview);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if(listImage.length != 0){
            // 2.异步加载图片
            AsyncImageLoderUtil.getInstance().displayListItemImage(listImage[position],viewHolder.imageview);

            viewHolder.textview.setText("第"+position+"张图片");
        }
        return  convertView;
    }

    static class ViewHolder{
        ImageView imageview;
        TextView textview;
    }

}
