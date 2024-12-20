package com.example.a5lab;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.allOf;

@RunWith(AndroidJUnit4.class)
public class MainActivityUITest {

    @Rule
    public ActivityScenarioRule<MainActivity> rule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void setUp() {
        rule.getScenario().onActivity(activity -> {
            List<Currency> currencies = new ArrayList<>();
            currencies.add(new Currency("USD", "United States Dollar"));
            currencies.add(new Currency("EUR", "Euro"));
            currencies.add(new Currency("GBP", "British Pound"));
            activity.setCurrencyList(currencies);
            activity.setFilteredList(activity.filterData(""));
        });
    }

    @Test
    public void filterCurrency_validCode_shouldDisplayFilteredItem() {
        onView(withId(R.id.edtFilter)).perform(typeText("USD"), closeSoftKeyboard());
        try { Thread.sleep(500); } catch (InterruptedException ignored) {}

        onData(allOf(instanceOf(Currency.class), withCurrencyCode("USD")))
                .inAdapterView(withId(R.id.listView))
                .check(matches(isDisplayed()));
    }

    @Test
    public void filterCurrency_emptyFilter_shouldDisplayAllItems() {
        onView(withId(R.id.edtFilter)).perform(clearText(), closeSoftKeyboard());
        try { Thread.sleep(500); } catch (InterruptedException ignored) {}

        onData(allOf(instanceOf(Currency.class), withCurrencyCode("USD")))
                .inAdapterView(withId(R.id.listView))
                .check(matches(isDisplayed()));
    }

    private Matcher<Object> withCurrencyCode(String code) {
        return new TypeSafeMatcher<Object>() {
            @Override
            protected boolean matchesSafely(Object item) {
                if (item instanceof Currency) {
                    return ((Currency) item).getCode().equals(code);
                }
                return false;
            }

            @Override
            public void describeTo(org.hamcrest.Description description) {
                description.appendText("Currency with code: " + code);
            }
        };
    }
}
