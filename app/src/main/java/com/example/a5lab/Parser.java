package com.example.a5lab;

import android.util.Log;
import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Parser {

    public List<String> parseXML(InputStream stream) {
        List<String> rates = new ArrayList<>();
        try {
            Log.d("Parser", "Parsing XML...");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(stream);
            document.getDocumentElement().normalize();

            NodeList nodes = document.getElementsByTagName("Cube");
            for (int i = 0; i < nodes.getLength(); i++) {
                Node node = nodes.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    if (element.hasAttribute("currency") && element.hasAttribute("rate")) {
                        String currency = element.getAttribute("currency");
                        String rate = element.getAttribute("rate");
                        rates.add(currency + " - " + rate);
                    }
                }
            }
        } catch (Exception e) {
            Log.e("Parser", "Error parsing XML", e);
        }
        return rates;
    }
}
