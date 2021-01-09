package App;

import Algorithms.EgalitarianOptimalAlgorithms;
import CoalitionGeneration.CoalitionGeneration;
import CoalitionStructureGeneration.CoalitionStructureGeneration;
import CoalitionValues.CoalitionValuesCalculation;
import ReferenceData.Agent;
import ReferenceData.Coalition;
import ReferenceData.CoalitionStructure;
import com.sun.tools.javac.util.Pair;

import java.util.*;


public class Application {

    public static void main(String[] args) {

        int numberOfAgents = 9;

        long startCoalitionValue = System.currentTimeMillis();

        //get CoalitionValues
        List<Agent> agents = new CoalitionGeneration().generateListOfAgents(numberOfAgents);
        Map<Coalition, Double> coalitionValues = new CoalitionValuesCalculation().coalitionValues(agents);


        long endCoalitionValue = System.currentTimeMillis();

        long durationCoalitionValue = endCoalitionValue - startCoalitionValue;

        System.out.println(String.format("Coalition Values calculation took [%1S] milliseconds", durationCoalitionValue));

        //create CS searchSpace
        long startCSspaceGeneration = System.currentTimeMillis();

        Map<Integer, Set<CoalitionStructure>> coalitionStructureSpace =
                new CoalitionStructureGeneration().getCoalitionStructureSpace(agents);

        long endCSspaceGeneration = System.currentTimeMillis();

        long durationCSspaceGeneration = endCSspaceGeneration - startCSspaceGeneration;

        System.out.println(String.format("Coalition Structure Space Generation took [%1S] milliseconds", durationCSspaceGeneration));

        //search and Compute
        long startOptimalSearch = System.currentTimeMillis();
        Pair<CoalitionStructure, Double> egalitatianOptimalCoalitionStucture =
                new EgalitarianOptimalAlgorithms().egalitarianAlgorithmOverAllSpace(
                        coalitionStructureSpace,
                        numberOfAgents,
                        coalitionValues
                );

        long endOptimalSearch = System.currentTimeMillis();

        long durationOptimalSearch = endOptimalSearch - startOptimalSearch;

        System.out.println(String.format("Optimal Coalition Structure Search took [%1S] milliseconds", durationOptimalSearch));

        long totalDuarationForAll = durationCoalitionValue + durationCSspaceGeneration + durationOptimalSearch;

        System.out.println(
                String.format("Optimal Structure is [%1S] with value [%2S] in [%3S] milliseconds",
                        egalitatianOptimalCoalitionStucture.fst.toString(),
                        egalitatianOptimalCoalitionStucture.snd,
                        totalDuarationForAll
                )
        );


        //from coalitions

        List<Coalition> coalitions = new ArrayList<>();

        long sortingstart = System.currentTimeMillis();

        for (Map.Entry<Coalition, Double> entry : coalitionValues.entrySet()) {
            Coalition coalition = entry.getKey();
            double value = entry.getValue();
            coalition.setValue(value);
            coalitions.add(coalition);
        }

        Collections.sort(coalitions);

        List<Coalition> filteredCoalitions = new CoalitionGeneration().filterCoalitions(coalitions, numberOfAgents);


        Collections.sort(filteredCoalitions);

        long sortingEnd = System.currentTimeMillis();

        long sortingDuration = sortingEnd - sortingstart;

        System.out.println("sortingDuration took " + sortingDuration + " millisecs");

        long csGenStart = System.currentTimeMillis();

        List<Coalition> filteredCoalitionsWithNeighbours = new CoalitionGeneration().updateCoalitionDisjointNeighbours(filteredCoalitions);

        List<CoalitionStructure> coalitionStructures =
                new CoalitionStructureGeneration().getCoalitionStructuresFromCoalitions(filteredCoalitionsWithNeighbours, numberOfAgents);

        long csGenEnd = System.currentTimeMillis();

        long csgDur = csGenEnd - csGenStart;

        System.out.println("csgDur took " + csgDur + " millisecs");

        long optimalStart = System.currentTimeMillis();

        Pair<CoalitionStructure, Double> optimalFromCoalitions = new EgalitarianOptimalAlgorithms().egalitarianAlgorithmStructureFromCoalitions(coalitionStructures);

        long optimalEnd = System.currentTimeMillis();

        long optimalDuration = optimalEnd - optimalStart;

        System.out.println("optimalDuration took " + optimalDuration + " millisecs");

        long totalDuarationForCoalitions = sortingDuration + csgDur + optimalDuration;


        System.out.println(
                String.format("Optimal Structure from coalitions is [%1S] with value [%2S] in [%3S] milliseconds",
                        optimalFromCoalitions.fst.toString(),
                        optimalFromCoalitions.snd,
                        totalDuarationForCoalitions
                )
        );

    }

}
