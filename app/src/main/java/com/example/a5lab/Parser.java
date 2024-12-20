package com.example.a5lab;

import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class Parser {

    public List<Currency> parseXML(InputStream inputStream) {
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
            Log.e("Parser", "Error parsing XML", e);
        }
        return currencies;
    }
}
