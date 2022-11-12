package com.mirim.bokrim;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mirim.bokrim.Datas.Store;
import com.mirim.bokrim.Datas.StoreList;
import com.mirim.bokrim.ListView.ListViewMapAdapter;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

public class MapFragment extends Fragment implements  MapView.CurrentLocationEventListener, MapView.MapViewEventListener, MapView.POIItemEventListener{
    public MapFragment() {}

    // *변수 선언
    static SlidingUpPanelLayout slidingUpPanelLayout; // 슬라이딩 레이아웃

    public static ListView listData; // 가게 정보들 보이는 리스트뷰

    static LinearLayout linearResult; // 가게 상세 정보 페이지
    FrameLayout frameBtnBack; // 되돌아가기 버튼

    public static TextView textStoreName, textStoreAdd, textStoreOper; // 가게 상세 정보

    public static int storeId;

    public static boolean isSlidingDown = true;
    public static boolean isDragList = true;

    // 지도
    MapView mapView;
    ViewGroup mapViewContainer;
    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    String[] REQUIRED_PERMISSIONS  = {Manifest.permission.ACCESS_FINE_LOCATION};


    public static MapFragment newInstance() {
        return new MapFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_map, container, false);

        listData = v.findViewById(R.id.list_map);
        slidingUpPanelLayout = v.findViewById(R.id.sliding_layout);

        textStoreName = v.findViewById(R.id.text_store_name);
        textStoreAdd = v.findViewById(R.id.text_store_address);
        textStoreOper = v.findViewById(R.id.text_store_operation);

        linearResult = v.findViewById(R.id.linear_result);
        frameBtnBack = v.findViewById(R.id.frame_btn_back);

        EditText editSearch = v.findViewById(R.id.editTextFilter);

        Log.d("드래그뷰 슬라이딩", "MapFragment 입성!!!");
        // 슬라이딩 레이아웃
        Log.d("슬라이딩", "올라오라고 햇는디 그랫는디"+slidingUpPanelLayout.getPanelState());
        if(isSlidingDown) slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
        if(isDragList){
            listData.setVisibility(View.VISIBLE);
            linearResult.setVisibility(View.INVISIBLE);
            frameBtnBack.setVisibility(View.INVISIBLE);
        }

        //카카오맵
        // TODO : 프래그먼트 닫힐때마다 지도 지워주기
        //지도 띄우기
        mapView = new MapView(getActivity());
        mapViewContainer = (ViewGroup) v.findViewById(R.id.map_view);
        mapViewContainer.addView(mapView);

        //현재 위치로 중심점 변경
        mapView.setMapViewEventListener(this);
        mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading);
        if (!checkLocationServicesStatus()) {
            showDialogForLocationServiceSetting();
        }else {
            checkRunTimePermission();
        }

        //리스트뷰 연결
        ListViewMapAdapter adapter = new ListViewMapAdapter();
        listData.setAdapter(adapter);

        // TODO: 실제 가게 정보 입력
        for(int i = 0; i< StoreList.storeList.size(); i++)
            adapter.addItem(StoreList.storeList.get(i).title, StoreList.storeList.get(i).address, StoreList.storeList.get(i).id);

        // TODO: 리스트뷰 아이템 온클릭 xml 전환 전 ripple 나타나게 하기
        listData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                frameBtnBack.setVisibility(View.VISIBLE);
                changeDragView(true);
                Log.d("리스트뷰", "실행됏쮸"+Integer.toString(storeId)+Integer.toString(position));

                for(int i = 0; i< StoreList.storeList.size(); i++){
                    if(i==position){
                        Log.d("리스트뷰", "나는 안 되는 것이냐!!!"+StoreList.storeList.get(i).title);

                        textStoreName.setText(StoreList.storeList.get(i).title);
                        textStoreAdd.setText(StoreList.storeList.get(i).address);
                        textStoreOper.setText(StoreList.storeList.get(i).opertime);

                        Log.d("리스트뷰", "if문 안에 "+textStoreName.getText().toString());
                    }
                    Log.d("리스트뷰", "포문 안에 "+textStoreName.getText().toString());
                }
            }
        });


        Log.d("리스트뷰", "온클릭 밖에 "+textStoreName.getText().toString());


        Log.d("드래그뷰", Integer.toString(linearResult.getVisibility())+" "+Integer.toString(listData.getVisibility()));

        // 되돌아가기 버튼
        frameBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeDragView(false);
                frameBtnBack.setVisibility(View.INVISIBLE);
            }
        });

        //검색어 입력하기 전 자식 Fragment 이동
        editSearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                ((MainActivity)getActivity()).replaceFragment(MapParentFragment.newInstance());
            }
        });
        editSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).replaceFragment(MapParentFragment.newInstance());
            }
        });

        return v;
    }

    public static void changeDragView(boolean b){
        Log.d("드래그뷰", Integer.toString(linearResult.getVisibility())+" "+Integer.toString(listData.getVisibility()));
        if(b){ //가게 상세 정보: true
            linearResult.setVisibility(View.VISIBLE);
            listData.setVisibility(View.INVISIBLE);
        }else{ //리스트뷰: false
            linearResult.setVisibility(View.INVISIBLE);
            listData.setVisibility(View.VISIBLE);
        }
        Log.d("드래그뷰", Integer.toString(linearResult.getVisibility())+" "+Integer.toString(listData.getVisibility()));
    }

    public void displayMessage(String message){
        storeId = Integer.parseInt(message);
    }

    public static void setTextStore(){
        for(int i = 0; i< StoreList.storeList.size(); i++){
            if(i==storeId){
                textStoreName.setText(StoreList.storeList.get(i).title);
                textStoreAdd.setText(StoreList.storeList.get(i).address);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapViewContainer.removeAllViews();
    }

    @Override
    public void onCurrentLocationUpdate(MapView mapView, MapPoint mapPoint, float v) {
        MapPoint.GeoCoordinate mapPointGeo = mapPoint.getMapPointGeoCoord();
    }

    @Override
    public void onCurrentLocationDeviceHeadingUpdate(MapView mapView, float v) {

    }

    @Override
    public void onCurrentLocationUpdateFailed(MapView mapView) {

    }

    @Override
    public void onCurrentLocationUpdateCancelled(MapView mapView) {

    }

    // ActivityCompat.requestPermissions를 사용한 퍼미션 요청의 결과를 리턴받는 메소드
    @Override
    public void onRequestPermissionsResult(int permsRequestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grandResults) {

        super.onRequestPermissionsResult(permsRequestCode, permissions, grandResults);
        if (permsRequestCode == PERMISSIONS_REQUEST_CODE && grandResults.length == REQUIRED_PERMISSIONS.length) {

            // 요청 코드가 PERMISSIONS_REQUEST_CODE 이고, 요청한 퍼미션 개수만큼 수신되었다면
            boolean check_result = true;

            // 모든 퍼미션을 허용했는지 체크
            for (int result : grandResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    check_result = false;
                    break;
                }
            }

            if (check_result) {
                Log.d("@@@", "start");
                //위치 값을 가져올 수 있음

            } else {
                // 거부한 퍼미션이 있다면 앱을 사용할 수 없는 이유를 설명해주고 앱을 종료. 2가지 경우가 있다
                if (ActivityCompat.shouldShowRequestPermissionRationale(MapFragment.newInstance().getActivity(), REQUIRED_PERMISSIONS[0])) {
                    Toast.makeText(getContext(), "퍼미션이 거부되었습니다. 앱을 다시 실행하여 퍼미션을 허용해주세요.", Toast.LENGTH_LONG).show();

                    //앱 종료
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentManager.beginTransaction().remove(MapFragment.this).commit();
                    onDestroy();
                    onDetach();
                } else {
                    Toast.makeText(getContext(), "퍼미션이 거부되었습니다. 설정(앱 정보)에서 퍼미션을 허용해야 합니다. ", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    void checkRunTimePermission(){

        //런타임 퍼미션 처리
        // 1. 위치 퍼미션을 가지고 있는지 체크
        int hasFineLocationPermission = ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION);

        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED ) {
            // 2. 이미 퍼미션을 가지고 있다면
            // 3.  위치 값을 가져올 수 있음

        } else {  //2. 퍼미션 요청을 허용한 적이 없다면 퍼미션 요청이 필요
            // 사용자가 퍼미션 거부를 한 적이 있는 경우에는
            if (shouldShowRequestPermissionRationale(REQUIRED_PERMISSIONS[0])) {
                // 3-2. 요청을 진행하기 전에 사용자가에게 퍼미션이 필요한 이유 설명
                Toast.makeText(getContext(), "이 앱을 실행하려면 위치 접근 권한이 필요합니다.", Toast.LENGTH_LONG).show();
                // 3-3. 사용자게에 퍼미션 요청. 요청 결과는 onRequestPermissionResult에서 수신
                ActivityCompat.requestPermissions(getActivity(), REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);
            } else {
                // 4-1. 사용자가 퍼미션 거부를 한 적이 없는 경우에는 바로 퍼미션 요청.
                // 요청 결과는 onRequestPermissionResult에서 수신
                ActivityCompat.requestPermissions(getActivity(), REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);
            }
        }
    }

    //GPS 활성화
    private void showDialogForLocationServiceSetting() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("위치 서비스 비활성화");
        builder.setMessage("앱을 사용하기 위해서는 위치 서비스가 필요합니다.\n"
                + "위치 설정을 수정하시겠습니까?");
        builder.setCancelable(true);
        builder.setPositiveButton("설정", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Intent callGPSSettingIntent
                        = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE);
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        builder.create().show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case GPS_ENABLE_REQUEST_CODE:
                //사용자가 GPS 활성 시켰는지 검사
                if (checkLocationServicesStatus()) {
                    if (checkLocationServicesStatus()) {
                        Log.d("@@@", "onActivityResult : GPS 활성화 되있음");
                        checkRunTimePermission();
                        return;
                    }
                }
                break;
        }
    }

    public boolean checkLocationServicesStatus() {
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    @Override
    public void onMapViewInitialized(MapView mapView) {

    }

    @Override
    public void onMapViewCenterPointMoved(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewZoomLevelChanged(MapView mapView, int i) {

    }

    @Override
    public void onMapViewSingleTapped(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewDoubleTapped(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewLongPressed(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewDragStarted(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewDragEnded(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewMoveFinished(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onPOIItemSelected(MapView mapView, MapPOIItem mapPOIItem) {

    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem) {

    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem, MapPOIItem.CalloutBalloonButtonType calloutBalloonButtonType) {

    }

    @Override
    public void onDraggablePOIItemMoved(MapView mapView, MapPOIItem mapPOIItem, MapPoint mapPoint) {

    }
}