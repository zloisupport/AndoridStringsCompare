package com.mrikso.stringscompare;

import org.dom4j.*;
import java.util.*;
import java.nio.charset.*;
import java.io.*;

public class XmlCompare
{
    private final String firstFile;
    private final String twoFile;
    private final String resultFile;
    
    public XmlCompare(final String first, final String two, final String result) {
        this.firstFile = first;
        this.twoFile = two;
        this.resultFile = result;
    }
    
    public void startAnalyze() throws DocumentException, IOException {
        final LinkedHashMap<String, StringItem> firstMap = XmlParser.parseStrings(this.firstFile);
        final LinkedHashMap<String, StringItem> twoMap = XmlParser.parseStrings(this.twoFile);
        this.findDifferences(firstMap, twoMap);
    }
    
    private void findDifferences(final LinkedHashMap<String, StringItem> firstMap, final LinkedHashMap<String, StringItem> twoMap) throws IOException {
        if (!firstMap.isEmpty() && !twoMap.isEmpty()) {
            int added = 0;
            int skipped = 0;
            final HashMap<String, StringItem> resultMap = new HashMap<String, StringItem>();
            for (final Map.Entry<String, StringItem> original : firstMap.entrySet()) {
                final StringItem stringItem = original.getValue();
                if (!twoMap.containsKey(original.getKey())) {
                    if (stringItem.getValueString() != null && !stringItem.getValueString().isEmpty()) {
                        ++added;
                        if (stringItem.isContainsFormatted()) {
                            resultMap.put(original.getKey(), new StringItem(stringItem.getNameString(), stringItem.getValueString(), stringItem.isContainsFormatted(), stringItem.isFormatted()));
                        }
                        else {
                            resultMap.put(original.getKey(), new StringItem(stringItem.getNameString(), stringItem.getValueString(), stringItem.isContainsFormatted()));
                        }
                    }
                    else {
                        ++skipped;
                    }
                }
            }
            System.out.printf("%d new lines added, %d empty lines skipped%n", added, skipped);
            final LinkedHashMap<String, StringItem> sortedMap = new LinkedHashMap<String, StringItem>();
            resultMap.entrySet().stream().sorted((Comparator<? super Object>)Map.Entry.comparingByKey()).forEachOrdered(x -> sortedMap.put(x.getKey(), (StringItem)x.getValue()));
            twoMap.putAll((Map<?, ?>)sortedMap);
            this.saveNewStringInFile(twoMap, this.resultFile);
        }
    }
    
    private void saveNewStringInFile(final Map<String, StringItem> resultMap, final String fileName) throws IOException {
        final File stringsXml = new File(fileName);
        if (stringsXml.exists()) {
            stringsXml.delete();
        }
        final Writer out = new OutputStreamWriter(new FileOutputStream(stringsXml), StandardCharsets.UTF_8);
        out.write("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
        out.write("<resources>\n");
        for (final Map.Entry<String, StringItem> item : resultMap.entrySet()) {
            final StringItem stringItem = item.getValue();
            String value = stringItem.getValueString();
            if (value != null) {
                value = this.convertValueString(stringItem.getValueString());
            }
            final String key = stringItem.getNameString();
            final boolean containsFormatted = stringItem.isContainsFormatted();
            final boolean formatted = stringItem.isFormatted();
            String line;
            if (containsFormatted) {
                line = String.format("    <string name=\"%s\" formatted=\"%s\">%s</string>\n", key, formatted, value);
            }
            else if (value != null) {
                line = String.format("    <string name=\"%s\">%s</string>\n", key, value);
            }
            else {
                line = String.format("    <string name=\"%s\"/>\n", key);
            }
            out.write(line);
        }
        out.write("</resources>");
        out.flush();
        System.out.printf("Please check the generated file: %s%n", stringsXml.getAbsolutePath());
    }
    
    private String convertValueString(final String value) {
        return value.replace("&gt;", ">");
    }
}
