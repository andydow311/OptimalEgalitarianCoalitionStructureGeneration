package ReferenceData;

import java.util.*;

public class Coalition implements Comparable<Coalition> {

    List<Agent> agents;
    List<Coalition> disjointNeighbours;
    double value;

    public Coalition(List<Agent> agents) {
        Collections.sort(agents);
        this.agents = agents;
        disjointNeighbours = new ArrayList<>();
    }

    public Coalition() {
        this.agents = new ArrayList<>();
        disjointNeighbours = new ArrayList<>();
    }

    public List<Agent> getAgents() {
        return agents;
    }

    public void setAgents(List<Agent> agents) {
        Collections.sort(agents);
        this.agents = agents;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public void addDisjointNeighbours(Coalition coalition) {
        disjointNeighbours.add(coalition);
    }

    public List<Coalition> getDisjointNeighbours() {
        return disjointNeighbours;
    }

    public void setDisjointNeighbours(List<Coalition> disjointNeighbours) {
        this.disjointNeighbours = disjointNeighbours;
    }

    @Override
    public int compareTo(Coalition o) {

        if (o.getValue() < value) {
            return -1;
        }

        if (o.getValue() > value) {
            return 1;
        }

        return 0;
    }

    public boolean isDisjoint(Coalition coalition) {

        for (Agent thisAgent : agents) {
            for (Agent thatAgent : coalition.getAgents()) {
                if (thisAgent.equals(thatAgent)) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coalition coalition = (Coalition) o;

        for(Agent agent : agents)
            if(!coalition.getAgents().contains(agent)){
                return false;
            }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(agents);
    }

    @Override
    public String toString() {

        String agentString = "";

        for (Agent agent : agents) {
            agentString = agentString + agent.toString();
        }
        return "<(" + agentString + ")>";
    }
}
