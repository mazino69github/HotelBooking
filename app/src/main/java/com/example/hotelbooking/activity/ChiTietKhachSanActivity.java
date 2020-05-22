package com.example.hotelbooking.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.hotelbooking.R;
import com.example.hotelbooking.model.KhachSan;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.crypto.KeyAgreement;


//public class ChiTietKhachSanActivity extends AppCompatActivity implements OnMapReadyCallback {
    public class ChiTietKhachSanActivity extends AppCompatActivity {
    Toolbar toolbarChitiet;
    ImageView imgChitiet;
    TextView txtTen, txtMota;
    Button btnChonphong;
    TextView txtDiachiKhachsan, txtDanhgia, txtMotaks;
    TextView txtToolbar;

    RelativeLayout diachilayout, relativelayout_main, ggmap;

    int id = 0;
    String tenchitiet = "";
    String motachitiet = "";
    String hinhanhchitiet = "";
    int idkhachsan = 0;
    String diachi = "";

    boolean isClickAdd = false;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_khach_san);
        Anhxa();
        ActionToolbar();
        GetInformation();
        EventButton();
        //EventAddress();
    }

//    private void EventAddress() {
//        diachilayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (isClickAdd) {
//                    ggmap.setVisibility(View.GONE);
//                    isClickAdd = false;
//                    return;
//                }
//                if (!isClickAdd) {
//                    ggmap.setVisibility(View.VISIBLE);
//                    isClickAdd = true;
//                    return;
//                }
//            }
//        });
//    }

    private void EventButton() {
        btnChonphong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KhachSan khachSan = new KhachSan(id, tenchitiet, 0, hinhanhchitiet, motachitiet, idkhachsan, diachi);
                Intent intent = new Intent(getApplicationContext(), PhongActivity.class);
                intent.putExtra("khachsan", khachSan);
                startActivity(intent);
            }
        });
        txtMotaks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txtMota.getVisibility() == View.GONE) {

                    txtMota.setVisibility(View.VISIBLE);

                } else {

                    txtMota.setVisibility(View.GONE);
                }
            }
        });
        txtDanhgia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DanhGia.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });


    }

    private void GetInformation() {


        KhachSan khachSan = (KhachSan) getIntent().getSerializableExtra("thongtinks");
        id = khachSan.getID();
        tenchitiet = khachSan.getTenks();
        motachitiet = khachSan.getMota();
        hinhanhchitiet = khachSan.getHinhanhks();
        idkhachsan = khachSan.getIDks();
        diachi = khachSan.getDiachi();

        txtTen.setText(tenchitiet);
        txtMota.setText(motachitiet);
        txtMota.setVisibility(View.GONE);
        txtDiachiKhachsan.setText(diachi);
        txtToolbar.setText(tenchitiet);

        Picasso.with(getApplicationContext()).load(hinhanhchitiet)
                .placeholder(R.drawable.noimage)
                .error(R.drawable.error)
                .into(imgChitiet);
    }

    private void ActionToolbar() {
        setSupportActionBar(toolbarChitiet);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarChitiet.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void Anhxa() {
        toolbarChitiet= (Toolbar) findViewById(R.id.toolbar_chitietks);
        imgChitiet = (ImageView) findViewById(R.id.imageview_chitietks);
        txtTen = (TextView) findViewById(R.id.textview_tenchitietks);
        txtMota = (TextView) findViewById(R.id.textview_motachitietks);
        btnChonphong = (Button) findViewById(R.id.buttonchonphong);
        txtDiachiKhachsan = (TextView) findViewById(R.id.textview_diachiks);
        txtToolbar = (TextView) findViewById(R.id.textview_toolbar);
        txtDanhgia = findViewById(R.id.danhgia);
        txtMotaks = findViewById(R.id.motakhachsan);

//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.mapkhachsan);
//        mapFragment.getMapAsync(this);
//        ggmap = (RelativeLayout) findViewById(R.id.ggmap);
//        diachilayout = (RelativeLayout) findViewById(R.id.diachilayout);
//        relativelayout_main = (RelativeLayout) findViewById(R.id.relativelayout_main);
    }
//
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_onlycart, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menucart:
                Intent intent = new Intent(getApplicationContext(), GioHangActivity.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
//
//    public LatLng getLocationFromAddress(Context context, String strAddress) {
//
//        Geocoder coder = new Geocoder(context);
//        List<Address> address;
//        LatLng p1 = null;
//
//        try {
//            //   May throw an IOException
//            address = coder.getFromLocationName(strAddress, 5);
//            if (address == null) {
//                return null;
//            }
//
//            Address location = address.get(0);
//            p1 = new LatLng(location.getLatitude(), location.getLongitude());
//
//        } catch (IOException ex) {
//
//            ex.printStackTrace();
//        }
//        return p1;
//    }
//
//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        mMap = googleMap;
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            //TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the username_icon grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
//        mMap.setMyLocationEnabled(true);
//        LatLng location_hotel = getLocationFromAddress(getApplicationContext(), txtten.getText().toString());
//        mMap.addMarker(new MarkerOptions().position(location_hotel).title(txtten.getText().toString()).snippet(txtdiachikhachsan.getText().toString()).icon(BitmapDescriptorFactory.defaultMarker()));
//        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
//        CameraPosition cameraPosition = new CameraPosition.Builder().target(location_hotel).zoom(18).build();
//        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
//    }
}
