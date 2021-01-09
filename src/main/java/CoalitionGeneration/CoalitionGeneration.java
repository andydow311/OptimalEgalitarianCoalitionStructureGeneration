package CoalitionGeneration;

import ReferenceData.Agent;
import ReferenceData.Coalition;

import java.util.*;

public class CoalitionGeneration {


    public  List<Coalition> generateCoalitionsWithRandomValues(int numberOfAgents) {

        List<Coalition> output = new ArrayList<>();

        Agent[] agents = generateArrayOfAgents(numberOfAgents);

        long numberOfCoalitions =  (long) Math.pow(2, numberOfAgents);

        int counter, j;

        for(counter = 0; counter < numberOfCoalitions; counter++) {
            Coalition coalition = new Coalition();
            for(j = 0; j < numberOfAgents; j++) {
                if((counter & (1 << j)) > 0)
                    coalition.getAgents().add(agents[j]);

            }

            if(coalition.getAgents().size() > 0){
                double value = Math.random()*100;
                coalition.setValue(value);
                output.add(coalition);
            }

        }

        return output;
    }

    public List<Coalition> filterCoalitions(List<Coalition> coalitions, int numberOfAgents){

        double threshold = Math.pow(2,(numberOfAgents-1)) +1;
        double start = 1;
        List<Coalition> output = new ArrayList<>();

        for(Coalition coalition : coalitions){
            output.add(coalition);
            start++;
            if(start > threshold){
                break;
            }
        }

        return output;
    }

    public List<Agent> generateListOfAgents(int numberOfAgents){
        return Arrays.asList(generateArrayOfAgents(numberOfAgents));
    }

    public List<Coalition> updateCoalitionDisjointNeighbours(List<Coalition> coalitions) {

        for(int firstIndex =0; firstIndex < coalitions.size(); firstIndex++) {
            Coalition firstCoalition = coalitions.get(firstIndex);
            for (int secondIndex = firstIndex + 1; secondIndex < coalitions.size(); secondIndex++) {
                Coalition secondCoalition = coalitions.get(secondIndex);
                if (firstCoalition.isDisjoint(secondCoalition)) {
                    firstCoalition.addDisjointNeighbours(secondCoalition);
                    secondCoalition.addDisjointNeighbours(firstCoalition);
                }
            }
        }

        return coalitions;
    }

    private Agent[] generateArrayOfAgents(int numberOfAgents){

        Agent[] agentArray = new Agent[numberOfAgents];

        for(int i = 1; i <= numberOfAgents; i++){
            Agent agent = new Agent(String.valueOf(i));
            int index = i-1;
            agentArray[index] = agent;
        }


        return agentArray;
    }


}
