package com.example.a5lab;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class MainActivityTest {

    private MainActivity mainActivity;
    private List<Currency> currencies;

    @Before
    public void setUp() {
        mainActivity = new MainActivity();
        currencies = new ArrayList<>();
        currencies.add(new Currency("USD", "1.234"));
        currencies.add(new Currency("EUR", "1.000"));
        currencies.add(new Currency("GBP", "0.857"));
    }

    @Test
    public void filterData_validFilter_shouldReturnFilteredList() {
        mainActivity.setCurrencyList(currencies);

        List<Currency> filtered = mainActivity.filterData("USD");

        assertEquals(1, filtered.size());
        assertEquals("USD", filtered.get(0).getCode());
    }

    @Test
    public void filterData_emptyFilter_shouldReturnFullList() {
        mainActivity.setCurrencyList(currencies);

        List<Currency> filtered = mainActivity.filterData("");

        assertEquals(3, filtered.size());
    }
}
