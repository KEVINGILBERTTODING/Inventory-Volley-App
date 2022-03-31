package com.example.InventoryAPP;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class BrgAdapter extends BaseAdapter {
    Activity activity;
    List<Data> items;
    private LayoutInflater inflater;

    Locale localeID = new Locale("in", "ID");
    NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

    public BrgAdapter(Activity activity, List<Data> items) {
        this.activity = activity;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) convertView = inflater.inflate(R.layout.list, null);


        TextView kdbrg = (TextView) convertView.findViewById(R.id.kdbrg);
        TextView nmbrg = (TextView) convertView.findViewById(R.id.nmbrg);
        TextView hrgbeli = (TextView) convertView.findViewById(R.id.hrgbeli);
        TextView hrgjual = (TextView) convertView.findViewById(R.id.hrgjual);
        TextView stok = (TextView) convertView.findViewById(R.id.stok);

        Data data = items.get(position);

        // Convert ke format rupiah

        String fhrgBeli  = formatRupiah(Double.parseDouble(data.getHrgbeli()));
        String fhrgJual  = formatRupiah(Double.parseDouble(data.getHrgjual()));


        kdbrg.setText(data.getKdbrg());
        nmbrg.setText(data.getNmbrg());
        hrgbeli.setText(fhrgBeli);
        hrgjual.setText(fhrgJual);
        stok.setText(data.getStok());

        return convertView;
    }

    private String formatRupiah(Double number) {
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        return formatRupiah.format(number);

    }
}
