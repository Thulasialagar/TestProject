import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLReaderOrWriterDynamicNode {
    private DocumentBuilderFactory docFactory;
    private DocumentBuilder docBuilder;
    private Document doc;
    private NodeList nodeList;
    private Element root;
    private List dynamicNodes;

    public XMLReaderOrWriterDynamicNode(String filePath) {
        try {
            docFactory = DocumentBuilderFactory.newInstance();
            docBuilder = docFactory.newDocumentBuilder();
            doc = docBuilder.parse(new File(filePath));
            root = doc.getDocumentElement();
            root.normalize();
            nodeList = root.getElementsByTagName("employee");
            dynamicNodes = new ArrayList<String>();
            dynamicNodes.add("address");
            // Add other dynamic nodes here
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String capitalizeFirstLetter(String propertyName){
        String s = Optional.ofNullable(propertyName).orElse("");
        if (!s.trim().isEmpty()) {
           s = s.substring(0,1).toUpperCase()+s.substring(1);
        }
        return s;

    }
    public void addEmployee(String name, int age, String designation, String newNodeName, Object... newNodeValues) {
        try {
            Element employee = doc.createElement("employee");
            Element empName = doc.createElement("name");
            empName.appendChild(doc.createTextNode(name));
            Element empAge = doc.createElement("age");
            empAge.appendChild(doc.createTextNode(String.valueOf(age)));
            Element empDesignation = doc.createElement("designation");
            empDesignation.appendChild(doc.createTextNode(designation));
            employee.appendChild(empName);
            employee.appendChild(empAge);
            employee.appendChild(empDesignation);

            if (dynamicNodes.contains(newNodeName)) {
                Element dynamicNode = doc.createElement(newNodeName);
                for (int i = 0; i < newNodeValues.length; i += 2) {
                    String propertyName = (String) newNodeValues[i];
                    Object propertyValue = newNodeValues[i + 1];
                    Method method = dynamicNode.getClass().getMethod("set" + capitalizeFirstLetter(propertyName), propertyValue.getClass());
                    method.invoke(dynamicNode, propertyValue);
                }
            }
        } catch (Exception e) {

        }
    }
}

