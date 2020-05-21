package com.example.hotelbooking.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hotelbooking.R;
import com.example.hotelbooking.adapter.hanoiAdapter;
import com.example.hotelbooking.model.KhachSan;
import com.example.hotelbooking.ultil.CheckConnection;
import com.example.hotelbooking.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DaNangActivity extends AppCompatActivity {
    Toolbar toolbarDN;
    ListView lvDN;
    hanoiAdapter dnAdapter;
    ArrayList<KhachSan> arr_dn;
    int iddn = 0;
    int page = 1;

    View footerview;
    boolean isLoading = false;
    myHandler myHandler;
    boolean limitData = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_da_nang);
        Anhxa();
        if (CheckConnection.haveNetworkConnection(getApplicationContext()))
        {
            GetIdloaisp();
            ActionToolbar();
            GetData(page);
            LoadMoreData();
        }
        else
        {
            CheckConnection.ShowToast_Short(getApplicationContext(),"Bạn hãy kiểm tra lại kết nối Internet");
            finish();
        }
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_cart, menu);
        MenuItem searchItem = menu.findItem(R.id.menusearch);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String userInput = newText.toLowerCase();
        ArrayList<KhachSan> newList = new ArrayList<>();
        for (KhachSan item : arr_hcm){
            if (item.getTenks().toLowerCase().contains(userInput)){
                newList.add(item);
            }
        }
        hochiminhAdapter.updateArrayList(newList);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.menucart:
                Intent intent = new Intent(getApplicationContext(), GioHangActivity.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }*/

    private void LoadMoreData() {
        lvDN.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), ChiTietKhachSanActivity.class);
                intent.putExtra("thongtinks",arr_dn.get(position));
                startActivity(intent);
            }
        });

        lvDN.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstItem, int visibleItem, int totalItem) {
                if(firstItem + visibleItem  == totalItem && totalItem != 0 && isLoading == false && limitData == false) {
                    isLoading =  true;
                    ThreadData threadData = new ThreadData();
                    threadData.start();
                }
            }
        });
    }

    private void GetData(int Page) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String duongdan = Server.duongdan_khachsan + String.valueOf(Page);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, duongdan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int Id = 0;
                String Tendn = "";
                int Giadn = 0;
                String Hinhanhdn = "";
                String Mota = "";
                int Idksdn = 0;
                String Diachi = "";
                if (response != null && response.length() != 2) {
                    lvDN.removeFooterView(footerview);
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            Id = jsonObject.getInt("id");
                            Tendn = jsonObject.getString("tenks");
                            Giadn = jsonObject.getInt("giaks");
                            Hinhanhdn = jsonObject.getString("hinhanhks");
                            Mota = jsonObject.getString("mota");
                            Idksdn = jsonObject.getInt("idks");
                            Diachi = jsonObject.getString("diachi");
                            arr_dn.add(new KhachSan(Id, Tendn, Giadn, Hinhanhdn, Mota, Idksdn,Diachi));
                            dnAdapter.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    limitData = true;
                    lvDN.removeFooterView(footerview);
                    CheckConnection.ShowToast_Short(getApplicationContext(),"Đã hết dữ liệu");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<String, String>();
                param.put("idks", String.valueOf(iddn));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void ActionToolbar() {
        setSupportActionBar(toolbarDN);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarDN.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void GetIdloaisp() {
        iddn = getIntent().getIntExtra("idloaiks", -1);
    }

    private void Anhxa() {
        toolbarDN = (Toolbar) findViewById(R.id.toolbardanang);
        lvDN = (ListView) findViewById(R.id.listviewdanang);
        arr_dn = new ArrayList<>();
        dnAdapter = new hanoiAdapter(getApplicationContext(), arr_dn);
        lvDN.setAdapter(dnAdapter);

        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footerview = inflater.inflate(R.layout.progressbar, null);
        myHandler = new myHandler();
    }

    public class myHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 0:
                    lvDN.addFooterView(footerview);
                    break;
                case 1:
                    page++;
                    GetData(page);
                    isLoading = false;
                    break;
            }
            super.handleMessage(msg);
        }
    }

    public class ThreadData extends Thread{
        @Override
        public void run() {
            myHandler.sendEmptyMessage(0);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message message = myHandler.obtainMessage(1);
            myHandler.sendMessage(message);
            super.run();
        }
    }
}
