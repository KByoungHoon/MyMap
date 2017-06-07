package com.example.menoliwm.mymaplist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.support.v4.app.FragmentActivity;
import android.widget.FrameLayout;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    static Boolean DBAccesser = true;

    private GoogleMap mMap;
    LatLng MapInfoList;

    HttpManager httpManager = new HttpManager();
    MapInfoManager mapInfoManager = new MapInfoManager();
    ListView mapListView;

    DBAccess dbAccess = new DBAccess(MapsActivity.this, 1);

    List<MapInfo> mapList;
    MapListAdapter<MapInfo> adapter;

    static final private int DELETE = Menu.FIRST;
    static final private int EDIT = Menu.FIRST+1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        final LinearLayout listView_LinearLayout = (LinearLayout) findViewById(R.id.listView_FrameLayout);
        final LinearLayout map_LinearLayout = (LinearLayout) findViewById(R.id.map_FrameLayout);

        Button listVisible = (Button) findViewById(R.id.btn_visible_invisible);
        listVisible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listView_LinearLayout.getVisibility() == View.INVISIBLE) {
                    listView_LinearLayout.setVisibility(View.VISIBLE);
                    map_LinearLayout.setVisibility(View.INVISIBLE);
                }else{
                    listView_LinearLayout.setVisibility(View.INVISIBLE);
                    map_LinearLayout.setVisibility(View.VISIBLE);
                }
            }
        });


        Button add = (Button) findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MapsActivity.this, AddMapsActivity.class);
                startActivityForResult(intent, 0);
            }
        });
    }

    @Override
    public void onResume() {

        mapListView = (ListView) findViewById(R.id.view_List);

        httpManager.getMyMapInfoList();

        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<MapInfo>>() {
        }.getType();

        if(DBAccesser) {
            mapList = gson.fromJson(httpManager.str, listType);
        }else
           mapList = dbAccess.getMapListDB();


        adapter = new MapListAdapter<MapInfo>(this, 0, mapList);

        mapListView.setAdapter(adapter);
        registerForContextMenu(mapListView);

        super.onResume();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        menu.setHeaderTitle("Menu");
        menu.add(0,EDIT, Menu.NONE, "修正");
        menu.add(0,DELETE, Menu.NONE, "削除");
    }

    public boolean onContextItemSelected(MenuItem item) {
        super.onContextItemSelected(item);
        AdapterView.AdapterContextMenuInfo menuInfo =
                (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        MapInfo mapInfo = (MapInfo) mapListView.getAdapter().getItem(menuInfo.position);

        int id = mapInfo.getId();

        int index = menuInfo.position;

        switch(item.getItemId()){
            case(EDIT):{
                mapList.set(index, mapInfo);
                Intent intent = new Intent(MapsActivity.this, EditMapsActivity.class);

                intent.putExtra("id", String.valueOf(id));
                intent.putExtra("userid", mapInfo.getUserid());
                intent.putExtra("title", mapInfo.getTitle());
                intent.putExtra("address", mapInfo.getAddress());
                intent.putExtra("latitude", String.valueOf(mapInfo.getLatitude()));
                intent.putExtra("longitude", String.valueOf(mapInfo.getLongitude()));
                intent.putExtra("isMapView", String.valueOf(mapInfo.getIsMapView()));

                startActivityForResult(intent, 1);
                break;
            }
            case(DELETE):{
                mapList.remove(index);
                httpManager.deleteAndroidList(id);
                adapter.notifyDataSetChanged();
                break;
            }
        }
        return false;
    }

    /**z
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng Tokyo = new LatLng(35.681586, 139.765089);

        mMap.addMarker(new MarkerOptions().position(Tokyo).title("Marker in Tokyo"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Tokyo, 16));

        mMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {
                LatLng nearRight = mMap.getProjection().getVisibleRegion().nearRight;
                LatLng farLeft = mMap.getProjection().getVisibleRegion().farLeft;

                mapInfoManager.getMapInfoList(farLeft.latitude, nearRight.latitude, nearRight.longitude, farLeft.longitude);

                double latitude =  mapInfoManager.getMapInfoList(farLeft.latitude, nearRight.latitude, nearRight.longitude, farLeft.longitude).getLatitude();
                double longitude =  mapInfoManager.getMapInfoList(farLeft.latitude, nearRight.latitude, nearRight.longitude, farLeft.longitude).getLongitude();

                MapInfoList = new LatLng(latitude, longitude);

                mMap.addMarker(new MarkerOptions().position(MapInfoList));
            }
        });

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

            }
        });

        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {

            }
        });
        //拡大、縮小Control
        mMap.getUiSettings().setZoomControlsEnabled(true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent){
        MapInfo mapInfo = new MapInfo();
        switch (requestCode){
            case 0:

                int id = 0;
                String addUserid = "Meno";
                String addTitle = intent.getStringExtra("title");
                String addAddress = intent.getStringExtra("address");
                double addLatitude = Double.parseDouble(intent.getStringExtra("latitude"));
                double addLongitude = Double.parseDouble(intent.getStringExtra("longitude"));

                mapInfo.setId(id);
                mapInfo.setUserid(addUserid);
                mapInfo.setTitle(addTitle);
                mapInfo.setAddress(addAddress);
                mapInfo.setLatitude(addLatitude);
                mapInfo.setLongitude(addLongitude);

                httpManager.addAndroidList(mapInfo);
                break;

            case 1:
                mapInfo.setId(Integer.parseInt(intent.getStringExtra("id")));
                mapInfo.setUserid(intent.getStringExtra("userid"));
                mapInfo.setTitle(intent.getStringExtra("title"));
                mapInfo.setAddress(intent.getStringExtra("address"));
                mapInfo.setLatitude(Double.parseDouble(intent.getStringExtra("latitude")));
                mapInfo.setLongitude(Double.parseDouble(intent.getStringExtra("longitude")));
                mapInfo.setIsMapView(Boolean.parseBoolean(intent.getStringExtra("isMapView")));

                httpManager.editAndroidList(mapInfo);
                break;
        }
        adapter.notifyDataSetChanged();
    }
}
