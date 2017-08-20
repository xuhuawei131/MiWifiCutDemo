package com.xuhuawei.miwificutdemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xuhuawei.miwificutdemo.R;
import com.xuhuawei.miwificutdemo.bean.WifiInfoBean;
import com.xuhuawei.miwificutdemo.utils.DateUtils;

import java.util.List;

/**
 * wifi适配器
 * Created by xuhuawei on 2017/6/20 0020.
 */

public class WifiRecoderAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<WifiInfoBean> arrayList;

    public WifiRecoderAdapter(Context context, List<WifiInfoBean> arrayList) {
        inflater = LayoutInflater.from(context);
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView=inflater.inflate(R.layout.adapter_wifi_layout,parent,false);

        }
        TextView text_index=(TextView)convertView.findViewById(R.id.text_index);
        TextView text_name=(TextView)convertView.findViewById(R.id.text_name);
        TextView text_status=(TextView)convertView.findViewById(R.id.text_status);
        TextView text_date=(TextView)convertView.findViewById(R.id.text_date);

        WifiInfoBean bean=arrayList.get(position);
        text_index.setText(""+bean.id);
        text_name.setText(bean.wifiName);
        text_status.setText(bean.status==1?"连接":"断开");
        text_date.setText(DateUtils.getDateFormat(bean.date));
        return convertView;
    }
}
