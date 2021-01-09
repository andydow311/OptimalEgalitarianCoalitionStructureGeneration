package Algorithms;

import ReferenceData.Coalition;
import ReferenceData.CoalitionStructure;
import com.sun.tools.javac.util.Pair;

import java.util.*;

public class EgalitarianOptimalAlgorithms {

    public Pair<CoalitionStructure, Double> egalitarianAlgorithmOverAllSpace(
            Map<Integer, Set<CoalitionStructure>> CoalitionStructureSpace,
            int numberOfAgents,
            Map<Coalition, Double> coalitionValues) {

        CoalitionStructure grandCoalition = CoalitionStructureSpace.get(1).iterator().next();

        Pair<CoalitionStructure, Double> output = Pair.of(grandCoalition, computeEgalitarianCoalitionStructureValue(grandCoalition, coalitionValues));

        for (int i = 2; i <= numberOfAgents; i++) {
            Set<CoalitionStructure> csSet = CoalitionStructureSpace.get(i);
            for (CoalitionStructure cs : csSet) {
                double eqalitarianCoalitionStructureValue = computeEgalitarianCoalitionStructureValue(cs, coalitionValues);

                if (eqalitarianCoalitionStructureValue > output.snd) {
                    output = Pair.of(cs, eqalitarianCoalitionStructureValue);
                }
            }
        }

        return output;
    }

    public Pair<CoalitionStructure, Double> egalitarianAlgorithmStructureFromCoalitions(List<CoalitionStructure> coalitionStructures) {

        Collections.sort(coalitionStructures);

        Iterator<CoalitionStructure> iterator = coalitionStructures.iterator();

        CoalitionStructure coalitionStructure = iterator.next();

        return Pair.of(coalitionStructure, coalitionStructure.getValue());
    }

    public double computeEgalitarianCoalitionStructureValue(CoalitionStructure coalitionStructure, Map<Coalition,Double> coalitionValues){

        double output = 0;
        int counter = 1;

        for(Coalition coalition : coalitionStructure.getCoalitions()){
            if(coalitionValues.get(coalition) != null){
                if(counter == 1){
                    output = coalitionValues.get(coalition);
                }else{
                    if(output > coalitionValues.get(coalition)){
                        output = coalitionValues.get(coalition);
                    }
                }
                counter++;
            }else{
                if(counter == 1){
                    output = 0;
                }else{
                    if(output > 0){
                        output =0;
                    }
                }
            }

        }

        return output;
    }
}
