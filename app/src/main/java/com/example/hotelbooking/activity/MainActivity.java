package com.example.hotelbooking.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.app.AlertDialog;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.widget.Toolbar;

import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hotelbooking.R;
import com.example.hotelbooking.adapter.KhachSanAdapter;
import com.example.hotelbooking.adapter.LoaiksAdapter;
import com.example.hotelbooking.model.KhachSan;
import com.example.hotelbooking.model.Loaiks;
import com.example.hotelbooking.ultil.CheckConnection;
import com.example.hotelbooking.ultil.Giohang;
import com.example.hotelbooking.ultil.Server;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    ViewFlipper viewFlipper;
    RecyclerView recyclerViewmanhinhchinh, recyclerViewkhachsandadat;
    NavigationView navigationView;
    ListView listViewmanhinhchinh;
    DrawerLayout drawerLayout;
    ArrayList<Loaiks> mangloaiks;
    LoaiksAdapter loaiksAdapter;
    int id = 0;
    String tenloaiks = "";
    String hinhanhloaiks = "";
    ArrayList<KhachSan> mangkhachsan;
    KhachSanAdapter khachSanAdapter;
    public static ArrayList<KhachSan> mangkhachsandadat;
    //khachsandadatAdapter khachsandadat_adapter;

    LoaiksAdapter diadiemadapter;
    ArrayList<Loaiks> arr_diadiem;
    TextView textView_diadiem;
    public static long[] date;

    public static ArrayList<Giohang> arr_giohang;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AnhXa();
        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
            ActionBar();
            ActionViewFlipper();
            GetDuLieuDiaDiem();
            GetDuLieuKsMoiNhat();
            //GetDuLieuKhachSanDaDat();
            CatchOnItemListView();
        } else {
            CheckConnection.ShowToast_Short(getApplicationContext(), "Kết nối không thành công. Kiểm tra lại!!!");
            finish();
        }
    }


//    @Override
//    public boolean onQueryTextSubmit(String query) {
//        return false;
//    }

    //    @Override
//    public boolean onQueryTextChange(String newText) {
//        String userInput = newText.toLowerCase();
//        ArrayList<KhachSan> newList = new ArrayList<>();
//        for (KhachSan item : mangkhachsan) {
//            if (item.getTenks().toLowerCase().contains(userInput)) {
//                newList.add(item);
//            }
//        }
//        khachSanAdapter.updateArrayList(newList);
//        return true;
//   }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_cart, menu);
        MenuItem searchItem = menu.findItem(R.id.menusearch);
//        SearchView searchView = (SearchView) searchItem.getActionView();
//        searchView.setOnQueryTextListener((SearchView.OnQueryTextListener) this);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menucart:
                Intent intent = new Intent(getApplicationContext(), GioHangActivity.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void CatchOnItemListView() {
        listViewmanhinhchinh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
                            Intent intent = new Intent(MainActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            CheckConnection.ShowToast_Short(getApplicationContext(), "Kiểm tra lại kết nôi");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 1:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
                            AlertDialog.Builder builderSingle = new AlertDialog.Builder(MainActivity.this);
                            builderSingle.setIcon(R.drawable.icon_diadiem);
                            builderSingle.setTitle("Chọn địa điểm của khách sạn");

                            builderSingle.setAdapter(diadiemadapter, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    switch (which) {
                                        case 0:
                                            if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
                                                Intent intent = new Intent(MainActivity.this, HoChiMinhActivity.class);
                                                intent.putExtra("idloaiks", 1);
                                                startActivity(intent);
                                            } else {
                                                CheckConnection.ShowToast_Short(getApplicationContext(), "Bạn hãy kiểm tra lại kết nối");
                                            }
                                            drawerLayout.closeDrawer(GravityCompat.START);
                                            break;
                                        case 1:
                                            if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
                                                Intent intent = new Intent(MainActivity.this, HaNoiActivity.class);
                                                intent.putExtra("idloaiks", 2);
                                                startActivity(intent);
                                            } else {
                                                CheckConnection.ShowToast_Short(getApplicationContext(), "Bạn hãy kiểm tra lại kết nối");
                                            }
                                            drawerLayout.closeDrawer(GravityCompat.START);
                                            break;
                                        case 2:
                                            if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
                                                Intent intent = new Intent(MainActivity.this, DaNangActivity.class);
                                                intent.putExtra("idloaiks", 3);
                                                startActivity(intent);
                                            } else {
                                                CheckConnection.ShowToast_Short(getApplicationContext(), "Bạn hãy kiểm tra lại kết nối");
                                            }
                                            drawerLayout.closeDrawer(GravityCompat.START);
                                            break;
                                    }
                                }

                            });
                            builderSingle.show();

                        } else {
                            CheckConnection.ShowToast_Short(getApplicationContext(), "Bạn hãy kiểm tra lại kết nối");
                        }
                        break;
                    case 2:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
                            Intent intent = new Intent(MainActivity.this, TaiKhoanActivity.class);
                            //intent.putExtra("idtaikhoan", idtaikhoan);
                            startActivity(intent);
                        } else {
                            CheckConnection.ShowToast_Short(getApplicationContext(), "Bạn hãy kiểm tra lại kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 3:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
                            Intent intent = new Intent(MainActivity.this, ThongTinActivity.class);
                            startActivity(intent);
                        } else {
                            CheckConnection.ShowToast_Short(getApplicationContext(), "Bạn hãy kiểm tra lại kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                }
            }
        });
    }

    private void GetDuLieuKsMoiNhat() {
        final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.duongdan_ksmoinhat, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null) {
                    int ID = 0;
                    String Tenks = "";
                    Integer Giasks = 0;
                    String Hinhanhks = "";
                    String Mota = "";
                    int IDks = 0;
                    String Diachi = "";
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            ID = jsonObject.getInt("id");
                            Tenks = jsonObject.getString("tenks");
                            Giasks = jsonObject.getInt("giaks");
                            Hinhanhks = jsonObject.getString("hinhanhks");
                            Mota = jsonObject.getString("mota");
                            IDks = jsonObject.getInt("idks");
                            Diachi = jsonObject.getString("diachi");
                            mangkhachsan.add(new KhachSan(ID, Tenks, Giasks, Hinhanhks, Mota, IDks, Diachi));
                            khachSanAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    /*private void GetDuLieuKhachSanDaDat() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.duongdan_khachsandadat, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    int ID = 0;
                    String Tensp = "";
                    Integer Gia = 0;
                    String Hinhanhsp = "";
                    String Mota = "";
                    int IDsp = 0;
                    String Diachi = "";
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            ID = jsonObject.getInt("id");
                            Tensp = jsonObject.getString("tensp");
                            Gia = jsonObject.getInt("giasp");
                            Hinhanhsp = jsonObject.getString("hinhanhsp");
                            Mota = jsonObject.getString("mota");
                            IDsp = jsonObject.getInt("idsp");
                            Diachi = jsonObject.getString("diachi");
                            manhkhachsandadat.add(new KhachSan(ID, Tensp, Gia, Hinhanhsp, Mota, IDsp, Diachi));
                            khachsandadat_adapter.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("id_user", String.valueOf(idtaikhoan));
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }*/

    private void GetDuLieuDiaDiem() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.duongdan_loaiks, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null) {
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            tenloaiks = jsonObject.getString("tenloaiks");
                            hinhanhloaiks = jsonObject.getString("hinhanhloaiks");
                            arr_diadiem.add(new Loaiks(id, tenloaiks, hinhanhloaiks));
                            diadiemadapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CheckConnection.ShowToast_Short(getApplicationContext(), error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void ActionViewFlipper() {
        ArrayList<String> arr_quangcao = new ArrayList<>();
        arr_quangcao.add("https://staticproxy.mytourcdn.com/0x0/resources/pictures/banners/advertising1589765373.jpg");
        arr_quangcao.add("https://staticproxy.mytourcdn.com/0x0/resources/pictures/banners/advertising1589366661.jpg");
        arr_quangcao.add("https://staticproxy.mytourcdn.com/0x0/resources/pictures/banners/advertising1578982831.jpg");
        arr_quangcao.add("https://staticproxy.mytourcdn.com/0x0/resources/pictures/banners/advertising1579055968.png");
        for (int i = 0; i < arr_quangcao.size(); i++) {
            ImageView imageview = new ImageView(getApplicationContext());
            Picasso.with(getApplicationContext()).load(arr_quangcao.get(i)).into(imageview);
            imageview.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageview);
        }
        viewFlipper.setFlipInterval(5000);
        viewFlipper.setAutoStart(true);
        Animation animation_slide_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_right);
        Animation animation_slide_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_right);
        viewFlipper.setInAnimation(animation_slide_in);
        viewFlipper.setOutAnimation(animation_slide_out);
    }

    private void ActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    private void AnhXa() {
        toolbar = (Toolbar) findViewById(R.id.toolbarManhinhchinh);
        viewFlipper = (ViewFlipper) findViewById(R.id.viewflipper);
        recyclerViewmanhinhchinh = (RecyclerView) findViewById(R.id.recyclerview_khachsanmoinhat);
        recyclerViewkhachsandadat = (RecyclerView) findViewById(R.id.recyclerview_khachsandadat);
        navigationView = (NavigationView) findViewById(R.id.navigationview);
        listViewmanhinhchinh = (ListView) findViewById(R.id.listview_manhinhchinh);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);

        mangloaiks = new ArrayList<>();
        mangloaiks.add(0, new Loaiks(0, "Trang Chính", "https://cdn1.iconfinder.com/data/icons/web-design-18/50/53-512.png"));
        mangloaiks.add(1, new Loaiks(0, "Địa điểm", "https://cdn2.iconfinder.com/data/icons/scenarium-vol-5/128/016_map_location_travel_navigation_city_street_road-512.png"));
        mangloaiks.add(2, new Loaiks(0, "Tài khoản", "https://cdn0.iconfinder.com/data/icons/ikooni-outline-seo-web/128/seo2-66-512.png"));
        mangloaiks.add(3, new Loaiks(0, "Thông tin", "https://cdn2.iconfinder.com/data/icons/seo-line-13/614/18_-_Intormation-512.png"));
        loaiksAdapter = new LoaiksAdapter(mangloaiks, getApplicationContext());
        listViewmanhinhchinh.setAdapter((loaiksAdapter));

        mangkhachsan = new ArrayList<>();
        khachSanAdapter = new KhachSanAdapter(getApplicationContext(), mangkhachsan);
        recyclerViewmanhinhchinh.setHasFixedSize(true);
        recyclerViewmanhinhchinh.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        recyclerViewmanhinhchinh.setAdapter(khachSanAdapter);

        //mangkhachsandadat = new ArrayList<>();
        //khachsandadatAdapter = new khachsandadatAdapter(getApplicationContext(), mangkhachsandadat);

        arr_diadiem = new ArrayList<>();
        diadiemadapter = new LoaiksAdapter(arr_diadiem, getApplicationContext());
        textView_diadiem = (TextView) findViewById(R.id.textview_diadiem);
        date = new long[60];
        Arrays.fill(date, 0);

        if (arr_giohang != null) {

        } else {
            arr_giohang = new ArrayList<>();
        }

    }
}

