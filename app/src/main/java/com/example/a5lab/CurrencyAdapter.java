package com.example.a5lab;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.BaseAdapter;

import java.util.List;

public class CurrencyAdapter extends BaseAdapter {
    private final Context context;
    private final List<Currency> currencyList;

    public CurrencyAdapter(Context context, List<Currency> currencyList) {
        this.context = context;
        this.currencyList = currencyList;
    }

    @Override
    public int getCount() {
        return currencyList.size();
    }

    @Override
    public Object getItem(int position) {
        return currencyList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.currency_item, parent, false);
        }

        Currency currency = currencyList.get(position);

        TextView txtCode = convertView.findViewById(R.id.txtCode);
        TextView txtRate = convertView.findViewById(R.id.txtRate);

        txtCode.setText(currency.getCode());
        txtRate.setText(currency.getRate());

        return convertView;
    }
}

