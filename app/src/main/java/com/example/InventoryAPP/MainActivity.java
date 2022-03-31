package com.example.InventoryAPP;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    public static final String URLTAMPIL = "http://192.168.11.19/crud/select.php";
    public static final String URLDELETE = "http://192.168.11.19/crud/delete.php";
    public static final String URLINSERT = "http://192.168.11.19/crud/insert.php";
    public static final String URLUBAH = "http://192.168.11.19/crud/edit.php";

    ListView list;
    AlertDialog.Builder dialog;
    SwipeRefreshLayout swipe;
    List<Data> itemList = new ArrayList<Data>();
    BrgAdapter adapter;

    LayoutInflater inflater;
    View dialogView;
    EditText tid,tkdbrg,tnmbrg,thrgbeli,thrgjual,tstok;
    String id, kdbrg, nmbrg, hrgbeli, hrgjual, stok;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        swipe = (SwipeRefreshLayout) findViewById(R.id.swipe);
        list = (ListView) findViewById(R.id.list);

        fab = (FloatingActionButton) findViewById(R.id.fabAdd);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogForm("","","","","","","SIMPAN");
            }
        });

        adapter = new BrgAdapter(MainActivity.this, itemList);
        list.setAdapter(adapter);


        swipe.setOnRefreshListener(this);

        swipe.post(new Runnable() {
                       @Override
                       public void run() {
                           swipe.setRefreshing(true);
                           itemList.clear();
                           adapter.notifyDataSetChanged();
                           callVolley();
                       }
                   }
        );
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final String idx = itemList.get(position).getId();
                final CharSequence[] dialogitem = {"Lihat", "Edit", "Delete"};
                dialog = new AlertDialog.Builder(MainActivity.this);
               // dialog.setCancelable(true);
                dialog.setItems(dialogitem, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        switch (which) {
                            case 0:
                                lihat(idx);
                                break;
                            case 1:
                                ubah(idx);
                                break;
                            case 2:
                                hapus(idx);
                                break;

                        }
                    }
                }).show();
                return false;
            }
        });



    }
    @Override
    public void onRefresh() {
      //  itemList.clear();
      //  adapter.notifyDataSetChanged();
        callVolley();
    }
    private void callVolley() {
        itemList.clear();
        adapter.notifyDataSetChanged();
        swipe.setRefreshing(true);

        // membuat request JSON
        JsonArrayRequest jArr = new JsonArrayRequest(URLTAMPIL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                // Parsing json
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);

                        Data item = new Data();

                        item.setId(obj.getString("id"));
                        item.setKdbrg(obj.getString("kdbrg"));
                        item.setNmbrg(obj.getString("nmbrg"));
                        item.setHrgbeli(obj.getString("hrgbeli"));
                        item.setHrgjual(obj.getString("hrgjual"));
                        item.setStok(obj.getString("stok"));

                        // menambah item ke array
                        itemList.add(item);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                // notifikasi adanya perubahan data pada adapter
                adapter.notifyDataSetChanged();

                swipe.setRefreshing(false);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "gagal koneksi ke server, cek setingan koneksi anda", Toast.LENGTH_LONG).show();
                swipe.setRefreshing(false);
            }
        });

        // menambah request ke request queue
        RequestQueue mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        mRequestQueue.add(jArr);

    }
    private void ubah(String id){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLUBAH,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jObj = new JSONObject(response);

                            String idx = jObj.getString("id");
                            String kdbrgx = jObj.getString("kdbrg");
                            String nmbrgx = jObj.getString("nmbrg");
                            String hrgbelix = jObj.getString("hrgbeli");
                            String hrgjualx = jObj.getString("hrgjual");
                            String stokx = jObj.getString("stok");

                            DialogForm(idx, kdbrgx, nmbrgx, hrgbelix, hrgjualx, stokx, "UPDATE");

                            adapter.notifyDataSetChanged();

                        }catch (JSONException e) {
                            // JSON error
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "gagal koneksi ke server, cek setingan koneksi anda", Toast.LENGTH_LONG).show();
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                // Posting parameters ke post url
                Map<String, String> params = new HashMap<String, String>();


                params.put("id", id );
                return params;
            }

        };
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(stringRequest);
    }
    private void hapus(String id){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLDELETE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        callVolley();
                        Toast.makeText(MainActivity.this, response, Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "gagal koneksi ke server, cek setingan koneksi anda", Toast.LENGTH_LONG).show();

            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                // Posting parameters ke post url
                Map<String, String> params = new HashMap<String, String>();


                params.put("id", id );
                return params;
            }

        };
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(stringRequest);
    }
    private void lihat(String id){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLUBAH,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jObj = new JSONObject(response);

                            String idx = jObj.getString("id");
                            String kdbrgx = jObj.getString("kdbrg");
                            String nmbrgx = jObj.getString("nmbrg");
                            String hrgbelix = jObj.getString("hrgbeli");
                            String hrgjualx = jObj.getString("hrgjual");
                            String stokx = jObj.getString("stok");

                            DialogFormLihat(idx, kdbrgx, nmbrgx, hrgbelix, hrgjualx, stokx);

                            adapter.notifyDataSetChanged();

                        }catch (JSONException e) {
                            // JSON error
                            e.printStackTrace();
                        }

                        tid.setEnabled(false);
                        tkdbrg.setEnabled(false);
                        tnmbrg.setEnabled(false);
                        thrgbeli.setEnabled(false);
                        thrgjual.setEnabled(false);
                        tstok.setEnabled(false);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "gagal koneksi ke server, cek setingan koneksi anda", Toast.LENGTH_LONG).show();
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                // Posting parameters ke post url
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", id );
                return params;
            }

        };
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(stringRequest);
    }

    void simpan(){


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLINSERT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        callVolley();
                        Toast.makeText(MainActivity.this, response, Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "gagal koneksi ke server, cek setingan koneksi anda", Toast.LENGTH_LONG).show();
            }
        })
        {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                // Posting parameters ke post url
                Map<String, String> params = new HashMap<String, String>();

                if (id.isEmpty()) {
                    params.put("kdbrg", kdbrg);
                    params.put("nmbrg", nmbrg);
                    params.put("hrgbeli", hrgbeli);
                    params.put("hrgjual", hrgjual);
                    params.put("stok", stok);
                    return params;
                }else{
                    params.put("id", id);
                    params.put("kdbrg", kdbrg);
                    params.put("nmbrg", nmbrg);
                    params.put("hrgbeli", hrgbeli);
                    params.put("hrgjual", hrgjual);
                    params.put("stok", stok);
                    return params;
                }
            }

        };
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(stringRequest);


    }
    private void DialogForm(String idx, String kdbrgx, String nmbrgx, String hrgbelix, String hrgjualx , String stokx, String button) {
        dialog = new AlertDialog.Builder(MainActivity.this);
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.form_barang, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);
        dialog.setIcon(R.drawable.delivery);
        dialog.setTitle("Data Barang");

        tid = (EditText) dialogView.findViewById(R.id.inId);
        tkdbrg = (EditText) dialogView.findViewById(R.id.inKdBrg);
        tnmbrg = (EditText) dialogView.findViewById(R.id.inNmBrg);
        thrgbeli = (EditText) dialogView.findViewById(R.id.inHrgBeli);
        thrgjual = (EditText) dialogView.findViewById(R.id.inHrgJual);
        tstok = (EditText) dialogView.findViewById(R.id.inStok);

        if (!idx.isEmpty()) {
            tid.setText(idx);
            tkdbrg.setText(kdbrgx);
            tnmbrg.setText(nmbrgx);
            thrgbeli.setText(hrgbelix);
            thrgjual.setText(hrgjualx);
            tstok.setText(stokx);
        } else {
            kosong();
        }

        dialog.setPositiveButton(button, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                id = tid.getText().toString();
                kdbrg = tkdbrg.getText().toString();
                nmbrg = tnmbrg.getText().toString();
                hrgbeli = thrgbeli.getText().toString();
                hrgjual = thrgjual.getText().toString();
                stok = tstok.getText().toString();
                simpan();

                dialog.dismiss();
            }
        });

        dialog.setNegativeButton("BATAL", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                kosong();
            }
        });

        dialog.show();

    }

    private void DialogFormLihat(String idx, String kdbrgx, String nmbrgx, String hrgbelix, String hrgjualx , String stokx) {
        dialog = new AlertDialog.Builder(MainActivity.this);
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.form_barang, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);
        dialog.setIcon(R.drawable.delivery);
        dialog.setTitle("Data Barang");

        tid = (EditText) dialogView.findViewById(R.id.inId);
        tkdbrg = (EditText) dialogView.findViewById(R.id.inKdBrg);
        tnmbrg = (EditText) dialogView.findViewById(R.id.inNmBrg);
        thrgbeli = (EditText) dialogView.findViewById(R.id.inHrgBeli);
        thrgjual = (EditText) dialogView.findViewById(R.id.inHrgJual);
        tstok = (EditText) dialogView.findViewById(R.id.inStok);

        if (!idx.isEmpty()) {
            tid.setText(idx);
            tkdbrg.setText(kdbrgx);
            tnmbrg.setText(nmbrgx);
            thrgbeli.setText(hrgbelix);
            thrgjual.setText(hrgjualx);
            tstok.setText(stokx);
        } else {
            kosong();
        }

        dialog.setNegativeButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                kosong();
            }
        });

        dialog.show();

    }
    private void kosong() {
        tid.setText(null);
        tkdbrg.setText(null);
        tnmbrg.setText(null);
        thrgbeli.setText(null);
        thrgjual.setText(null);
        tstok.setText(null);
    }
}