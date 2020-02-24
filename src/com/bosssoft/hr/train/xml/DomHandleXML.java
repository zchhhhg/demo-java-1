/*
 * 采用Dom方式操作XML
 * author：张璐思
 * 2020-02-24
 * */
package com.bosssoft.hr.train.xml;

import org.w3c.dom.*;
import org.xml.sax.SAXException;
import javax.xml.parsers.*;
import java.io.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;


public class DomHandleXML {

    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
        DomHandleXML domHandleXML = new DomHandleXML();
        String filepath = "src\\com\\bosssoft\\hr\\train\\xml\\student.xml";
        domHandleXML.getAll(filepath);
    }
    //Dom对象的创建
    public Document createDom(String path) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new File(path));
        return document;
    }
    //将修改写入文件
    public void modifyFile(Document doc, String path) throws TransformerConfigurationException {
        TransformerFactory tff = TransformerFactory.newInstance();
        Transformer tf = tff.newTransformer();
        DOMSource ds = new DOMSource(doc);
        StreamResult sr = new StreamResult(new File(path));
    }
    //查询xml文件内容并用stringbuilder封装输出
    public void getAll(String path) throws IOException, SAXException, ParserConfigurationException {
        Document document = createDom(path);
        NodeList nodeList = document.getElementsByTagName("student");
        for (int i = 0; i < nodeList.getLength(); i++) {
            StringBuilder sb = new StringBuilder();
            sb.append("姓名：" + document.getElementsByTagName("name").item(i).getFirstChild().getNodeValue());
            sb.append(" , ");
            sb.append("年龄：" + document.getElementsByTagName("age").item(i).getFirstChild().getNodeValue());
            sb.append(" , ");
            sb.append("性别：" + document.getElementsByTagName("sex").item(i).getFirstChild().getNodeValue());
            sb.append(" , ");
            System.out.println(sb.toString());
        }
    }
    //添加实体元素
    public void addStudent(String name,String age,String sex,String path) throws ParserConfigurationException, SAXException, IOException {
        try{
            Document document = createDom(path);
            NodeList nodeList = document.getElementsByTagName("students");
            //创建新的节点
            Node studentNode = document.createElement("student");
            Node nameNode = document.createElement("name");
            nameNode.appendChild(document.createTextNode(name));
            Node ageNode = document.createElement("age");
            ageNode.appendChild(document.createTextNode(age));
            Node sexNode = document.createElement("sex");
            sexNode.appendChild(document.createTextNode(sex));
            // 添加节点
            studentNode.appendChild(nameNode);
            studentNode.appendChild(ageNode);
            studentNode.appendChild(sexNode);
            nodeList.item(0).appendChild(studentNode);
            // 此时真正的处理将新数据添加到文件中（磁盘）
            modifyFile(document, path);
        }catch(Exception e){
            e.printStackTrace();
        }
        getAll(path);
    }
    //删除实体元素
    public void deleteStudent(String name,String path) throws IOException, SAXException, ParserConfigurationException, TransformerConfigurationException {
        Document document = createDom(path);
        NodeList nodeList = document.getElementsByTagName("name");
        for(int i=0;i<nodeList.getLength();i++){
            String value = nodeList.item(i).getFirstChild().getTextContent();
            if(name!=null && name.equalsIgnoreCase(value)){
                Node parentNode = nodeList.item(i).getParentNode();
                document.getFirstChild().removeChild(parentNode);
            }
        }
        modifyFile(document,path);
        getAll(path);
    }
    //根据姓名属性修改实体内容
    public void updateNode(String name,String path) throws TransformerConfigurationException, IOException, SAXException, ParserConfigurationException {

        Document document = createDom(path);
        NodeList nodeList = document.getElementsByTagName("name");
        for(int i=0;i<nodeList.getLength();i++){
            String value = nodeList.item(i).getFirstChild().getTextContent();
            if(name!=null && name.equalsIgnoreCase(value)){
                Node parentNode = nodeList.item(i).getParentNode();
                NodeList nl = parentNode.getChildNodes();
                for(int j=0;j<nl.getLength();j++){
                    String modifyNode= nl.item(j).getNodeName();
                    if(modifyNode.equalsIgnoreCase("age")){
                        nl.item(j).getFirstChild().setTextContent("25");
                    }
                }
            }
        }
        modifyFile(document,"student.xml");
        getAll(path);
    }


}
