
package com.genzeon.demo.domparser;
import java.io.File;
import java.io.IOException;

 

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

 


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

 


public class GenzeonSterlingModifyXMLDOM {

 

public static void main(String[] args) {
        String filePath = "C:\\Users\\AShinde\\Documents\\workspace-sts-3.9.5.RELEASE\\com.genzeon.demo.domparser\\src\\StandardXML.xml";
        File xmlFile = new File(filePath);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;
        Document doc1;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();
            
            //update attribute value
            updateAttributeValue(doc);
            
            doc1=copyElementNode(doc);
TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc1);
            StreamResult result = new StreamResult(new File("C:\\Users\\AShinde\\Documents\\workspace-sts-3.9.5.RELEASE\\com.genzeon.demo.domparser\\src\\StandardModifiedXML.xml"));
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(source, result);
            System.out.println("XML file updated successfully");
           
        } catch ( Exception e1) {
            e1.printStackTrace();
        }
    }
private static void updateAttributeValue(Document doc) {
    NodeList orderLines = doc.getElementsByTagName("OrderLine");
    Element orderLine = null;
    for(int i=0; i<orderLines.getLength();i++){
        orderLine = (Element) orderLines.item(i);
        String scac = orderLine.getAttribute("SCAC");
       
        Node itemNode=orderLine.getElementsByTagName("Item").item(0);
        NamedNodeMap attr=itemNode.getAttributes();
        Node nodeAttrMap=attr.getNamedItem("ItemShortDesc");
        if(scac.equalsIgnoreCase("PICKUP")){
        	nodeAttrMap.setTextContent("BOPIS");
        }
        
        else{
            nodeAttrMap.setTextContent("NON-BOPIS");
            }
    }
}

private static Document copyElementNode(Document doc) {
    NodeList orderLines = doc.getElementsByTagName("OrderLine");
    Node orderLineNode = null;
    //loop for each orderLine
    for(int i=0; i<orderLines.getLength();i++){
        orderLineNode = (Node) orderLines.item(i);
        NamedNodeMap attr = orderLineNode.getAttributes();
        Node nodeAttrMap=attr.getNamedItem("CarrierServiceCode");
        Node newNode;
        
        if(nodeAttrMap.getTextContent().equalsIgnoreCase("FURNITURE_REGULAR1")){
        	newNode=orderLineNode.cloneNode(true);
        	doc.getElementsByTagName("OrderLines").item(0).appendChild(newNode);
            System.out.println("After Copying the node");
        	return doc;
            
        }
       
    else{
        System.out.println("FURNITURE_REGULAR1 NOT FOUND");
        }
}
return doc;
}
}
        

