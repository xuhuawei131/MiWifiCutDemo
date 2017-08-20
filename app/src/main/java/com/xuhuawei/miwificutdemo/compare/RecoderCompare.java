package com.xuhuawei.miwificutdemo.compare;

import com.xuhuawei.miwificutdemo.bean.WifiInfoBean;

import java.util.Comparator;

/**
 * Created by Administrator on 2017/6/20 0020.
 */

public class RecoderCompare implements Comparator<WifiInfoBean> {
    @Override
    public int compare(WifiInfoBean o1, WifiInfoBean o2) {

        if(o1.date>o2.date){
            return -1;
        }else if(o1.date==o2.date){
            return o1.id>o2.id?1:0;
        }else{
            return 1;
        }
    }
}
