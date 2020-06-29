package com.company;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class IOManager {

    public static void saveXML(File file, List<Todo> todos) {
        try {
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = documentBuilder.newDocument();
            Element root = document.createElement("todos");
            for (Todo todo :
                    todos) {
                Node todoNode = document.createElement("todo");
                Element name = document.createElement("name");
                name.setTextContent(todo.getName());
                todoNode.appendChild(name);
                Element description = document.createElement("description");
                description.setTextContent(todo.getDescription());
                todoNode.appendChild(description);
                Element isFinished = document.createElement("isFinished");
                isFinished.setTextContent(String.valueOf(todo.isFinished()));
                todoNode.appendChild(isFinished);

                root.appendChild(todoNode);
            }
            document.appendChild(root);

            Source source = new DOMSource(document);
            FileWriter fw = new FileWriter(file);
            StreamResult sr = new StreamResult(fw);
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            transformer.transform(source, sr);
            fw.close();
        } catch (ParserConfigurationException | IOException | TransformerException e) {
            e.printStackTrace();
        }
    }

    public static List<Todo> loadXML(File file) {
        if (checkFile(file))
            return new ArrayList<Todo>();
        List<Todo> todos = new ArrayList<Todo>();
        try {
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = documentBuilder.parse(file);
            document.getDocumentElement().normalize();

            NodeList todoNodes = document.getDocumentElement().getElementsByTagName("todo");

            for (int i = 0; i < todoNodes.getLength(); i++) {
                NodeList attrNodes = todoNodes.item(i).getChildNodes();
                String name = "", description = "";
                boolean isFinished = false;

                for (int j = 0; j < attrNodes.getLength(); j++) {
                    Node attrNode = attrNodes.item(j);
                    if (attrNodes.item(j).getNodeType() == Node.ELEMENT_NODE) {
                        if (attrNode.getNodeName().equals("name"))
                            name = attrNode.getTextContent();
                        if (attrNode.getNodeName().equals("description"))
                            description = attrNode.getTextContent();
                        if (attrNode.getNodeName().equals("isFinished"))
                            isFinished = Boolean.parseBoolean(attrNode.getTextContent());
                    }
                }
                todos.add(new Todo(name, description, isFinished));
            }
        } catch (Exception e) {
            Console.error(e.getMessage());
        }
        return todos;
    }

    private static boolean checkFile(File file) {
        try {
            if (file.createNewFile()) {
                Console.warning("File does not exist. Creating " + file.getName());
//                FileWriter fw = new FileWriter(file);
//                fw.write("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<todos>\n</todos>");
//                fw.close();
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
