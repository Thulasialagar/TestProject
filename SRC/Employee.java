import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Employee {

    private String name;
    private String age;
    private String designation;

    private List<EmployeeDynamicNode> dynamicNodes;

    public Employee(String name, String age, String designation){
        this.name = name;
        this.age = age;
        this.designation = designation;
        this.dynamicNodes = new ArrayList<EmployeeDynamicNode>();

    }

    public Employee(String name){
        this.name = name;
    }
    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<EmployeeDynamicNode> getDynamicNodes() {
        return dynamicNodes;
    }

    public void addDynamicNode(EmployeeDynamicNode dynamicNode) {
        dynamicNodes.add(dynamicNode);
    }

    public void removeDynamicNode(EmployeeDynamicNode dynamicNode) {
        dynamicNodes.remove(dynamicNode);
    }
}
