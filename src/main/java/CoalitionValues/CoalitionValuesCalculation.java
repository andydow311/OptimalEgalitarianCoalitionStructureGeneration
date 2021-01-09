package CoalitionValues;

import ReferenceData.Agent;
import ReferenceData.Coalition;

import java.util.*;

public class CoalitionValuesCalculation {

    public Map<Coalition,Double> coalitionValues(List<Agent> agents){

        Map<Coalition,Double> coalitionValues = new HashMap<>();

        int numberOfAgents = agents.size();

        Agent[] agentArray = new Agent[numberOfAgents];
        int index = 0;

        for(Agent agent : agents){
            agentArray[index] = agent;
            index++;
        }

        long numberOfCoalitions =  (long) Math.pow(2, numberOfAgents);

        int counter, j;

        for(counter = 0; counter < numberOfCoalitions; counter++) {
            Coalition coalition = new Coalition();
            for(j = 0; j < numberOfAgents; j++) {
                if((counter & (1 << j)) > 0)
                    coalition.getAgents().add(agentArray[j]);

            }

            if(coalition.getAgents().size() > 0){
                double value = randomCoaltionValue();
                coalition.setValue(value);
                Collections.sort(coalition.getAgents());
                coalitionValues.put(coalition,value);
            }

        }

        return coalitionValues;
    }

    private double randomCoaltionValue(){
        return Math.random()*100;
    }


}
