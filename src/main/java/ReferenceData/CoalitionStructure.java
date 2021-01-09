package ReferenceData;

import java.util.List;
import java.util.Objects;

public class CoalitionStructure implements Comparable<CoalitionStructure> {

    List<Coalition> coalitions;
    double value = 0;

    public CoalitionStructure(List<Coalition> coalitions, double value) {
        this.coalitions = coalitions;
        this.value = value;
    }

    public CoalitionStructure(List<Coalition> coalitions) {
        this.coalitions = coalitions;
    }

    public List<Coalition> getCoalitions() {
        return coalitions;
    }

    public void setCoalitions(List<Coalition> coalitions) {
        this.coalitions = coalitions;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    public int getSize(){
        int size = 0;

        for(Coalition coalition : coalitions){
            size = size + coalition.getAgents().size();
        }

        return size;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CoalitionStructure that = (CoalitionStructure) o;

        for(Coalition c : coalitions){
            if(!that.coalitions.contains(c)){
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(coalitions);
    }

    @Override
    public String toString() {

        String coalitionDetails = "";

        for(Coalition coalition: coalitions){
            coalitionDetails = coalitionDetails + " [" + coalition.toString() +"] ";

        }

        return "{" + coalitionDetails + "}";
    }

    @Override
    public int compareTo(CoalitionStructure o) {

        if(o.getValue() < value){
            return -1;
        }

        if(o.getValue() > value){
            return 1;
        }

        return 0;
    }
}

