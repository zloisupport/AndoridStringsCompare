package com.mrikso.stringscompare;

import java.io.*;
import org.dom4j.*;

public class Main
{
    public static void main(final String[] args) {
        String strXMLFileName1 = "xml1.xml";
        String strXMLFileName2 = "xml2.xml";
        String strComparisonResultsFile = "out.xml";
        System.out.println("Xml Merger Started...");
        if (args != null && args.length >= 3) {
            strXMLFileName1 = args[0];
            strXMLFileName2 = args[1];
            strComparisonResultsFile = args[2];
        }
        else {
            System.out.println("Error! Please enter valid arguments!");
            usageHelp();
            System.exit(0);
        }
        try {
            final XmlCompare xmlCompare = new XmlCompare(strXMLFileName1, strXMLFileName2, strComparisonResultsFile);
            xmlCompare.startAnalyze();
        }
        catch (IOException | DocumentException ex3) {
            final Exception ex2;
            final Exception ex = ex2;
            System.err.println("Error! Files not found!");
            ex.printStackTrace();
            System.exit(0);
        }
    }
    
    private static void usageHelp() {
        System.out.println("Usage: java -jar StringsXmlCompare.jar <original> <translated> <result>");
    }
}
