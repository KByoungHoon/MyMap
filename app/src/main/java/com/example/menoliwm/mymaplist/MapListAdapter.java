package com.example.menoliwm.mymaplist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Menoliwm on 2017-05-26.
 */

public class MapListAdapter<M> extends ArrayAdapter<MapInfo> {

    LayoutInflater layoutInflater = null;
    List<MapInfo> mapInfoList = new ArrayList<MapInfo>();
    HttpManager httpManager = new HttpManager();

    public MapListAdapter(Context context, int resource, List<MapInfo> objects){
        super(context, resource, objects);
        this.layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mapInfoList = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.map_info_list, parent, false);
        }

        final MapInfo mapInfo = (MapInfo)mapInfoList.get(position);
        TextView titleTv = ((TextView)convertView.findViewById(R.id.title));
        titleTv.setText(mapInfo.getTitle());
        TextView addressTv = ((TextView)convertView.findViewById(R.id.address));
        addressTv.setText(mapInfo.getAddress());
        TextView latitudeTv = ((TextView)convertView.findViewById(R.id.latitude));
        latitudeTv.setText(String.valueOf(mapInfo.getLatitude()));
        TextView longitudeTv = ((TextView)convertView.findViewById(R.id.longitude));
        longitudeTv.setText(String.valueOf(mapInfo.getLongitude()));
        final CheckBox checkBox = ((CheckBox) convertView.findViewById(R.id.checkBox_listView));
        checkBox.setFocusable(false);

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(checkBox.isChecked()){
                    mapInfo.setIsMapView(true);
                }else
                    mapInfo.setIsMapView(false);

                httpManager.editAndroidList(mapInfo);
            }
        });

        checkBox.setChecked(mapInfo.getIsMapView());


        return convertView;
    }
}
