package com.example.a5lab;

import static org.junit.Assert.*;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

public class ParserTest {

    @Test
    public void parseXML_validData_shouldReturnCurrencies() {
        String xmlData = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                "<Cube>" +
                "<Cube currency=\"USD\" rate=\"1.234\"/>" +
                "<Cube currency=\"EUR\" rate=\"1.000\"/>" +
                "<Cube currency=\"GBP\" rate=\"0.857\"/>" +
                "</Cube>";
        InputStream inputStream = new ByteArrayInputStream(xmlData.getBytes());
        Parser parser = new Parser();

        List<Currency> currencies = parser.parseXML(inputStream);

        assertEquals(3, currencies.size());
        assertEquals("USD", currencies.get(0).getCode());
        assertEquals("1.234", currencies.get(0).getRate());
    }

    @Test
    public void parseXML_emptyData_shouldReturnEmptyList() {
        String xmlData = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Cube></Cube>";
        InputStream inputStream = new ByteArrayInputStream(xmlData.getBytes());
        Parser parser = new Parser();

        List<Currency> currencies = parser.parseXML(inputStream);

        assertTrue(currencies.isEmpty());
    }
}
