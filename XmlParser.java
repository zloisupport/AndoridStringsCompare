package com.mrikso.stringscompare;

import org.dom4j.io.*;
import java.nio.charset.*;
import java.util.*;
import java.util.regex.*;
import org.dom4j.*;

public class XmlParser
{
    public static LinkedHashMap<String, StringItem> parseStrings(final String file) throws DocumentException {
        final SAXReader reader = new SAXReader();
        reader.setEncoding(StandardCharsets.UTF_8.toString());
        final Document document = reader.read(file);
        final Element root = document.getRootElement();
        final LinkedHashMap<String, StringItem> stringItemList = new LinkedHashMap<String, StringItem>();
        final Iterator<Element> it = root.elementIterator();
        while (it.hasNext()) {
            final Element element = it.next();
            boolean containsFormatted = false;
            boolean formatted = true;
            final Attribute nameAttr = element.attribute("name");
            final Attribute isFormattedAttr = element.attribute("formatted");
            if (isFormattedAttr != null) {
                containsFormatted = true;
                formatted = Boolean.parseBoolean(isFormattedAttr.getValue());
            }
            final String key = nameAttr.getStringValue();
            String value = null;
            String pattern;
            if (containsFormatted) {
                pattern = "<string name=\"(.*?)\" formatted=\"(true|false)\">(.*?)</string>";
            }
            else {
                pattern = "<string name=\"(.*?)\">(.*?)</string>";
            }
            final Pattern stringPattern = Pattern.compile(pattern, 32);
            final Matcher matcher = stringPattern.matcher(element.asXML());
            while (matcher.find()) {
                if (containsFormatted) {
                    value = matcher.group(3);
                }
                else {
                    value = matcher.group(2);
                }
            }
            if (containsFormatted) {
                stringItemList.put(key, new StringItem(key, value, containsFormatted, formatted));
            }
            else {
                stringItemList.put(key, new StringItem(key, value, containsFormatted));
            }
        }
        return stringItemList;
    }
}
