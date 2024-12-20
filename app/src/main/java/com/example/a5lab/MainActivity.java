package com.example.a5lab;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText edtFilter;
    private ListView listView;
    private CurrencyAdapter adapter;
    private List<Currency> currencyList = new ArrayList<>();
    private List<Currency> filteredList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtFilter = findViewById(R.id.edtFilter);
        listView = findViewById(R.id.listView);
        adapter = new CurrencyAdapter(this, filteredList);
        listView.setAdapter(adapter);
    }

    public void setCurrencyList(List<Currency> currencies) {
        this.currencyList.clear();
        this.currencyList.addAll(currencies);
    }

    public List<Currency> filterData(String query) {
        List<Currency> filtered = new ArrayList<>();
        if (query.isEmpty()) {
            filtered.addAll(currencyList);
        } else {
            for (Currency currency : currencyList) {
                if (currency.getCode().toLowerCase().contains(query.toLowerCase())) {
                    filtered.add(currency);
                }
            }
        }
        return filtered;
    }

    public CurrencyAdapter getAdapter() {
        return adapter;
    }

    public void setFilteredList(List<Currency> list) {
        filteredList.clear();
        filteredList.addAll(list);
        adapter.notifyDataSetChanged();
    }
}
