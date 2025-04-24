import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;

public class EmployeeParser {
    public static void main(String[] args) {
        try {
            // Create a DocumentBuilderFactory and DocumentBuilder to parse the XML
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
           
            // Parse the XML file and get the Document object
            Document document = builder.parse(new File("employees.xml"));
           
            // Get all the <employee> elements from the XML
            NodeList nodeList = document.getElementsByTagName("employee");
           
            // Loop through each <employee> node
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
               
                // Check if the current node is an element
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element employee = (Element) node;
                   
                    // Extract and print the values of <id>, <name>, and <role>
                    System.out.println("Employee ID: " + employee.getElementsByTagName("id").item(0).getTextContent());
                    System.out.println("Name: " + employee.getElementsByTagName("name").item(0).getTextContent());
                    System.out.println("Role: " + employee.getElementsByTagName("role").item(0).getTextContent());
                    System.out.println("-----------------------");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}