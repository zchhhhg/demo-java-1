/*
 * 采用Sax方式操作XML
 * author：张璐思
 * 2020-02-24
 * */

package com.bosssoft.hr.train.xml;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Stack;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SaxHandleXML extends DefaultHandler {
    public Stack tags = new Stack();
    public Map contents = new LinkedHashMap();
    public int count = 0;
    @Override
    public void characters(char[] ch, int start, int length)throws SAXException {
        String tag = (String)tags.peek();

        if("name".equals(tag)){
            String name = new String(ch,start,length);
            contents.put("name"+count,name);
        }
        if("age".equals(tag)){
            contents.put("age"+count,new String(ch,start,length));
        }
        if("sex".equals(tag)){
            contents.put("sex"+count,new String(ch,start,length));
        }
        if("address".equals(tag)){
            contents.put("address"+count,new String(ch,start,length));
        }
    }

    @Override
    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {
        if("student".equals(qName)){
            count++;
        }
        tags.push(qName);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        tags.pop();
    }

    public Map getContents(){
        return contents;
    }
    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
        String filepath="src\\com\\bosssoft\\hr\\train\\xml\\student.xml";
        SaxHandleXML saxhandleXml = new SaxHandleXML();
        SAXParserFactory saxFactory = SAXParserFactory.newInstance();
        javax.xml.parsers.SAXParser sp = saxFactory.newSAXParser();
        sp.parse(new File(filepath), saxhandleXml);
        Map contents = saxhandleXml.getContents();
        Iterator keys = contents.keySet().iterator();
        while(keys.hasNext()){
            String key = (String)keys.next();
            System.out.println(key+":"+contents.get(key));
        }
    }


    }
