package com.example.dell.fatiguedetection;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by guwei on 16-9-2.
 */
public class BleListAdapter extends BaseAdapter{

    public List<BluetoothDevice> mlist;
    private LayoutInflater mInflater;

    public BleListAdapter(Context context , List<BluetoothDevice> list){
        mlist = list;
        mInflater = LayoutInflater.from(context);
    }

    //获取传入的数组大小
    @Override
    public int getCount() {
        return mlist.size();
    }

    //获取第N条数据
    @Override
    public Object getItem(int i) {
        return mlist.get(i);
    }

    //获取item id
    @Override
    public long getItemId(int i) {
        return i;
    }

    //主要方法
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = new ViewHolder();
        if(view == null){
            //首先为view绑定布局
            view = mInflater.inflate(R.layout.bledevice_item , null);
            viewHolder.name = (TextView) view.findViewById(R.id.device_name);
            viewHolder.uuid = (TextView) view.findViewById(R.id.device_uuid);

            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }
        BluetoothDevice bd = mlist.get(i);
        viewHolder.name.setText(bd.getName());
        viewHolder.uuid.setText(bd.getAddress());

        //viewHolder.status.setText(R.string.noconnect);
        return view;
    }

    class ViewHolder{
        private TextView name , uuid;
    }
}

