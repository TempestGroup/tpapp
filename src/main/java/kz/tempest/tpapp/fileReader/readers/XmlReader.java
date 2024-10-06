package kz.tempest.tpapp.fileReader.readers;

import kz.tempest.tpapp.fileReader.Reader;
import kz.tempest.tpapp.commons.utils.LogUtil;
import kz.tempest.tpapp.fileReader.dtos.XmlNodeItem;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class XmlReader extends Reader {

    @Override
    public Serializable read() {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(new ByteArrayInputStream(bytes));
            doc.getDocumentElement().normalize();
            return (Serializable) processNode(doc.getDocumentElement());
        } catch (Exception e) {
            LogUtil.write(e);
            return null;
        }
    }

    private List<XmlNodeItem> processNode(Node node) {
        List<XmlNodeItem> nodeList = new ArrayList<>();
        XmlNodeItem xmlNodeItem = new XmlNodeItem();
        xmlNodeItem.setName(node.getNodeName());
        if (node.hasAttributes()) {
            NamedNodeMap attributes = node.getAttributes();
            for (int j = 0; j < attributes.getLength(); j++) {
                Node attr = attributes.item(j);
                xmlNodeItem.getAttributes().put(attr.getNodeName(), attr.getNodeValue());
            }
        }
        NodeList childNodes = node.getChildNodes();
        boolean hasElementChildren = false;
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node currentNode = childNodes.item(i);
            if (currentNode.getNodeType() == Node.ELEMENT_NODE) {
                hasElementChildren = true;
                List<XmlNodeItem> children = processNode(currentNode);
                xmlNodeItem.getChildren().addAll(children);
            } else if (currentNode.getNodeType() == Node.TEXT_NODE) {
                String textContent = currentNode.getTextContent().trim();
                if (!textContent.isEmpty()) {
                    xmlNodeItem.setValue(textContent);
                }
            }
        }
        if (!hasElementChildren && xmlNodeItem.getValue() == null) {
            xmlNodeItem.setValue("");
        }
        nodeList.add(xmlNodeItem);
        return nodeList;
    }

//    @Override
//    public Serializable read() {
//        try {
//            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
//            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
//            Document doc = dBuilder.parse(new ByteArrayInputStream(bytes));
//            doc.getDocumentElement().normalize();
//            return processNode(doc.getDocumentElement());
//        } catch (Exception e) {
//            LogUtil.write(e);
//            return null;
//        }
//    }
//
//    private Serializable processNode(Node node) {
//        HashMap<String, Object> result = new HashMap<>();
//        NodeList nodeList = node.getChildNodes();
//        for (int i = 0; i < nodeList.getLength(); i++) {
//            Node currentNode = nodeList.item(i);
//
//            // Add attributes
//            if (node.hasAttributes()) {
//                NamedNodeMap attributes = node.getAttributes();
//                for (int j = 0; j < attributes.getLength(); j++) {
//                    Node attr = attributes.item(j);
//                    result.put("@" + attr.getNodeName(), attr.getNodeValue());
//                }
//            }
//
//            if (currentNode.getNodeType() == Node.ELEMENT_NODE) {
//                String nodeName = currentNode.getNodeName();
//                Object value = processNode(currentNode);
//
//                if (result.containsKey(nodeName)) {
//                    // If already a list, add to it
//                    if (result.get(nodeName) instanceof List) {
//                        ((List<Object>) result.get(nodeName)).add(value);
//                    } else {
//                        // Convert to list
//                        List<Object> nodeListValue = new ArrayList<>();
//                        nodeListValue.add(result.get(nodeName));
//                        nodeListValue.add(value);
//                        result.put(nodeName, nodeListValue);
//                    }
//                } else {
//                    result.put(nodeName, value);
//                }
//            } else if (currentNode.getNodeType() == Node.TEXT_NODE) {
//                String textContent = currentNode.getTextContent().trim();
//                if (!textContent.isEmpty()) {
//                    return textContent;
//                }
//            }
//        }
//        return result;
//    }
}
