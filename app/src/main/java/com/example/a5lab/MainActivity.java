package com.example.a5lab;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

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

        fetchData();

        edtFilter.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterData(s.toString());
            }
        });
    }

    private void fetchData() {
        Log.d("MainActivity", "Fetching currency data from ECB...");

        new Thread(() -> {
            try {
                URL url = new URL("https://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                InputStream inputStream = connection.getInputStream();
                List<Currency> currencies = parseXML(inputStream);

                runOnUiThread(() -> {
                    currencyList.clear();
                    currencyList.addAll(currencies);

                    filteredList.clear();
                    filteredList.addAll(currencyList);
                    adapter.notifyDataSetChanged();
                });
            } catch (Exception e) {
                Log.e("MainActivity", "Error fetching data", e);
                runOnUiThread(() -> Toast.makeText(MainActivity.this, "Error fetching data", Toast.LENGTH_SHORT).show());
            }
        }).start();
    }

    private List<Currency> parseXML(InputStream inputStream) {
        List<Currency> currencies = new ArrayList<>();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(inputStream);

            NodeList nodes = document.getElementsByTagName("Cube");

            for (int i = 0; i < nodes.getLength(); i++) {
                Node node = nodes.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    if (element.hasAttribute("currency") && element.hasAttribute("rate")) {
                        String currency = element.getAttribute("currency");
                        String rate = element.getAttribute("rate");
                        currencies.add(new Currency(currency, rate));
                    }
                }
            }
        } catch (Exception e) {
            Log.e("MainActivity", "Error parsing XML", e);
        }
        return currencies;
    }

    private void filterData(String query) {
        filteredList.clear();
        if (query.isEmpty()) {
            filteredList.addAll(currencyList);
        } else {
            for (Currency currency : currencyList) {
                if (currency.getCode().toLowerCase().contains(query.toLowerCase())) {
                    filteredList.add(currency);
                }
            }
        }
        adapter.notifyDataSetChanged();
    }
}
