package ReferenceData;

import java.util.Objects;

public class Agent implements Comparable<Agent> {

    private String label;

    public Agent(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Agent agent = (Agent) o;
        return label.equals(agent.label);
    }

    @Override
    public int hashCode() {
        return Objects.hash(label);
    }

    @Override
    public String toString() {
        return "[" + label + "]";
    }

    @Override
    public int compareTo(Agent o) {

        if (Integer.parseInt(o.getLabel()) < Integer.parseInt(label)) {
            return -1;
        }

        if (Integer.parseInt(o.getLabel()) > Integer.parseInt(label)) {
            return 1;
        }

        return 0;
    }
}