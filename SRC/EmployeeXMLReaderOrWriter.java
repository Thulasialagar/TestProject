
import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
public class EmployeeXMLReaderOrWriter {

    private DocumentBuilderFactory docFactory;
    private DocumentBuilder docBuilder;
    private Document doc;
    private NodeList nodeList;
    private Element root;

    public EmployeeXMLReaderOrWriter(String filePath) {
        try {
            docFactory = DocumentBuilderFactory.newInstance();
            docBuilder = docFactory.newDocumentBuilder();
            doc = docBuilder.parse(new File(filePath));
            root = doc.getDocumentElement();
            root.normalize();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addEmployee(Employee employee) {
        try {
            Element emplNode = doc.createElement("employee");
            Element empName = doc.createElement("name");
            empName.appendChild(doc.createTextNode(employee.getName()));
            Element empAge = doc.createElement("age");
            empAge.appendChild(doc.createTextNode(employee.getAge()));
            Element empDesignation = doc.createElement("designation");
            empDesignation.appendChild(doc.createTextNode(employee.getDesignation()));

            emplNode.appendChild(empName);
            emplNode.appendChild(empAge);
            emplNode.appendChild(empDesignation);
            for(EmployeeDynamicNode dynamicNode: employee.getDynamicNodes()) {
                addDynamicNode(dynamicNode, emplNode);
            }
            root.appendChild(emplNode);
        } catch (DOMException e) {
            e.printStackTrace();
        }
    }

    public void addDynamicNode(EmployeeDynamicNode dynamicNode,Element parent ){
        Element dynamicChildNode = doc.createElement(dynamicNode.getName());
        if(dynamicNode.getValue()!=null && !dynamicNode.getValue().isEmpty()) {
            dynamicChildNode.appendChild(doc.createTextNode(dynamicNode.getValue()));
        }
        if(dynamicNode.hasChildren()){
            for(EmployeeDynamicNode childNode : dynamicNode.getChildren()){
                addDynamicNode(childNode, dynamicChildNode);
            }
        }
        parent.appendChild(dynamicChildNode);

    }

    public void deleteEmployee(Employee employee) {
        if(employee!= null && employee.getName() !=null)
        if (root.hasChildNodes()) {
            NodeList empNodeList = root.getChildNodes();
            for (int i = 0; i < empNodeList.getLength(); i++) {
                Node node = empNodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) node;
                    if (eElement.getElementsByTagName("name").item(0).getTextContent().equalsIgnoreCase(employee.getName())) {
                        root.removeChild(node);
                    }
                }
            }
        }
    }

    public void writeToXMLFile(String filePath) {
        try {
            javax.xml.transform.TransformerFactory transformerFactory = javax.xml.transform.TransformerFactory.newInstance();
            javax.xml.transform.Transformer transformer = transformerFactory.newTransformer(new StreamSource(new File("XmlFormat.xsl")));
            // For formatting the complete file
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION,  "yes");
            javax.xml.transform.dom.DOMSource source = new javax.xml.transform.dom.DOMSource(doc);
            javax.xml.transform.stream.StreamResult result = new javax.xml.transform.stream.StreamResult(new File(filePath));
            transformer.transform(source, result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String filePath = "employees.xml";
        EmployeeXMLReaderOrWriter xmlFileRW = new EmployeeXMLReaderOrWriter(filePath);
        Employee employee1 = new Employee("Tulasi");
        Employee employee2 = new Employee("Tulasi", "38", "Developer");
        EmployeeDynamicNode addressNode = new EmployeeDynamicNode("Address");
        EmployeeDynamicNode cityNode = new EmployeeDynamicNode("city", "Texas");
        EmployeeDynamicNode countryNode = new EmployeeDynamicNode("country", "United Sates");
        addressNode.addChild(cityNode);
        addressNode.addChild(countryNode);
        employee2.addDynamicNode(addressNode);
        xmlFileRW.deleteEmployee(employee1);
        xmlFileRW.addEmployee(employee2);
        xmlFileRW.writeToXMLFile(filePath);
    }

}
