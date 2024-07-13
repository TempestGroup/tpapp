package kz.tempest.tpapp.commons.fileReader.readers;

import kz.tempest.tpapp.commons.fileReader.Reader;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class XmlReader extends Reader {
    @Override
    public Serializable read(byte[] fileBytes) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(new ByteArrayInputStream(fileBytes));
            doc.getDocumentElement().normalize();
            return processNode(doc.getDocumentElement());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private Serializable processNode(Node node) {
        HashMap<String, Object> result = new HashMap<>();
        NodeList nodeList = node.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node currentNode = nodeList.item(i);
            if (currentNode.getNodeType() == Node.ELEMENT_NODE) {
                String nodeName = currentNode.getNodeName();
                Object value = processNode(currentNode);

                if (result.containsKey(nodeName)) {
                    // If already a list, add to it
                    if (result.get(nodeName) instanceof List) {
                        ((List<Object>) result.get(nodeName)).add(value);
                    } else {
                        // Convert to list
                        List<Object> nodeListValue = new ArrayList<>();
                        nodeListValue.add(result.get(nodeName));
                        nodeListValue.add(value);
                        result.put(nodeName, nodeListValue);
                    }
                } else {
                    result.put(nodeName, value);
                }
            } else if (currentNode.getNodeType() == Node.TEXT_NODE) {
                String textContent = currentNode.getTextContent().trim();
                if (!textContent.isEmpty()) {
                    return textContent;
                }
            }

            // Add attributes
            if (node.hasAttributes()) {
                NamedNodeMap attributes = node.getAttributes();
                for (int j = 0; j < attributes.getLength(); j++) {
                    Node attr = attributes.item(j);
                    result.put("@" + attr.getNodeName(), attr.getNodeValue());
                }
            }
        }
        return result;
    }
}
