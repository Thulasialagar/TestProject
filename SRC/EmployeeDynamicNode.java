import java.util.ArrayList;
import java.util.List;

public class EmployeeDynamicNode {

        private String name;
        private String value;
        private List<EmployeeDynamicNode> children;

        public EmployeeDynamicNode(String name) {
            this.name = name;
            this.value = "";
            this.children = new ArrayList<EmployeeDynamicNode>();
        }

        public EmployeeDynamicNode(String name, String value) {
            this.name = name;
            this.value = value;
            this.children = new ArrayList<EmployeeDynamicNode>();
        }

        public String getName() {
            return name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public List<EmployeeDynamicNode> getChildren() {
            return children;
        }

        public void addChild(EmployeeDynamicNode child) {
            children.add(child);
        }

        public void removeChild(EmployeeDynamicNode child) {
            children.remove(child);
        }

        public boolean hasChildren() {
            return !children.isEmpty();
        }
    }

