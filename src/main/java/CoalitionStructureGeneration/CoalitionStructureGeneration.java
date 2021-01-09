package CoalitionStructureGeneration;

import ReferenceData.Agent;
import ReferenceData.Coalition;
import ReferenceData.CoalitionStructure;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Collections.singleton;
import static java.util.Collections.singletonList;

public class CoalitionStructureGeneration {

    public Coalition mergeCoalitions(Coalition coalitionOne, Coalition coalitionTwo){

        List<Agent> agents = new ArrayList<>();

        agents.addAll(coalitionOne.getAgents());

        agents.addAll(coalitionTwo.getAgents());

        return new Coalition(agents);
    }


    public Map<Integer, Set<CoalitionStructure>> getCoalitionStructureSpace(
            List<Agent> agents){

        Map<Integer,Set<CoalitionStructure>> output = new HashMap<>();

        output.put(agents.size(),singleton(getBaseLevelCoalitionStructure(agents)));

        for(int previousLevel = agents.size(); previousLevel >1; previousLevel--){
            if(previousLevel == 2){
                output.put(1,singleton(getTopLevelCoalitionStructure(agents)));
            }else {
                updateNextLevel(previousLevel, output);
            }
        }

        return output;
    }

    public  double getEgalitarianCoalitionStructureValue(List<Coalition> coalitions){
        double output = 0;
        int counter = 1;

        for(Coalition coalition : coalitions){

            if(counter == 1){
                output = coalition.getValue();
            }else{
                if(output > coalition.getValue()){
                    output = coalition.getValue();
                }
            }
            counter++;

        }

        return output;
    }

    public List<CoalitionStructure> getCoalitionStructuresFromCoalitions(List<Coalition> coalitions, int numberOfAgents) {

        List<CoalitionStructure> outputCS = new ArrayList<>();
        for (Coalition c : coalitions) {
            List<Coalition> neighbours = c.getDisjointNeighbours();
            Collections.sort(neighbours);
            Set<CoalitionStructure> coalitionStructureSpace = new HashSet<>();
            for (Coalition neighbour : neighbours) {
                if (coalitionStructureSpace.isEmpty()) {
                    CoalitionStructure cs = new CoalitionStructure(Stream.of(c, neighbour).collect(Collectors.toList()));
                    coalitionStructureSpace.add(cs);
                    if (cs.getSize() == numberOfAgents) {
                        double value = getEgalitarianCoalitionStructureValue(cs.getCoalitions());
                        cs.setValue(value);
                        if(!outputCS.contains(cs)) {
                            outputCS.add(cs);
                        }
                        break;
                    }
                } else {
                    for (CoalitionStructure coalitionStructure : coalitionStructureSpace) {
                        if (shouldCoalitionBeAddedtoStructure(coalitionStructure, neighbour)) {
                            coalitionStructure.getCoalitions().add(neighbour);
                            if (coalitionStructure.getSize() == numberOfAgents) {
                                double value = getEgalitarianCoalitionStructureValue(coalitionStructure.getCoalitions());
                                coalitionStructure.setValue(value);
                                if(!outputCS.contains(coalitionStructure)) {
                                    outputCS.add(coalitionStructure);
                                }

                                break;
                            }
                        }

                    }

                    CoalitionStructure cs = new CoalitionStructure(Stream.of(c, neighbour).collect(Collectors.toList()));
                    coalitionStructureSpace.add(cs);

                    if (cs.getSize() == numberOfAgents) {
                        double value = getEgalitarianCoalitionStructureValue(cs.getCoalitions());
                        cs.setValue(value);
                        if(!outputCS.contains(cs)) {
                            outputCS.add(cs);
                        }
                        break;
                    }


                }
            }
    //no disjoint neighbours so it could be be grand coalition
            if(c.getAgents().size() == numberOfAgents){
                CoalitionStructure cs = new CoalitionStructure(Stream.of(c).collect(Collectors.toList()));
                double value = getEgalitarianCoalitionStructureValue(cs.getCoalitions());
                cs.setValue(value);
                if(!outputCS.contains(cs)) {
                    outputCS.add(cs);
                }
            }

        }

        return outputCS;

    }

    private void updateNextLevel(int previousLevel,
                                       Map<Integer,Set<CoalitionStructure>> structureSpace){

        int nextLevel = previousLevel  -1;
        structureSpace.put(nextLevel, new HashSet<>());

        Set<CoalitionStructure> previousStructures = structureSpace.get(previousLevel);

        for(CoalitionStructure coalitionStructure : previousStructures){

            Coalition[] coalitionArray = new Coalition[coalitionStructure.getCoalitions().size()];
            coalitionArray = coalitionStructure.getCoalitions().toArray(coalitionArray);

            for(int i =0; i< coalitionArray.length; i++){
                Coalition outerCoalition = coalitionArray[i];
                for(int j = i+1; j< coalitionArray.length; j++){
                    List<Coalition> nextLevelCoalitions = new ArrayList<>();
                    Coalition innerCoalition = coalitionArray[j];
                    Coalition mergedCoalitions = mergeCoalitions(outerCoalition, innerCoalition);
                    nextLevelCoalitions.add(mergedCoalitions);

                    if(i > 0){
                        for(int k =0; k < i; k++){
                            nextLevelCoalitions.add(coalitionArray[k]);
                        }
                    }

                    if(j - i> 1){
                        int x = i;
                        int y = j;
                        for(int k =x+1; k < j; k++){
                            nextLevelCoalitions.add(coalitionArray[k]);
                        }
                    }

                    if(coalitionArray.length - i> 1){
                        int y = j;
                        for(int k =y+1; k < coalitionArray.length; k++){
                            nextLevelCoalitions.add(coalitionArray[k]);
                        }
                    }

                    CoalitionStructure nextLevelCoalitionStructure = new CoalitionStructure(nextLevelCoalitions);

                    if(validateStructureHasNotalreadyBeenConsidered(structureSpace.get(nextLevel),nextLevelCoalitionStructure)){
                        structureSpace.get(nextLevel).add(nextLevelCoalitionStructure);
                    }

                }

            }
        }

    }

    private boolean shouldCoalitionBeAddedtoStructure(CoalitionStructure coalitionStructure, Coalition coalition) {

        for(Coalition coalitionInStructure : coalitionStructure.getCoalitions()) {
            if(!coalitionInStructure.isDisjoint(coalition)) {
                return false;
            }
        }

        return true;
    }


    private boolean validateStructureHasNotalreadyBeenConsidered(Set<CoalitionStructure> structureSpace,
                                                                       CoalitionStructure nextLevelCoalitionStructure){

        for(CoalitionStructure cs : structureSpace) {
            if(cs.equals(nextLevelCoalitionStructure)){
                return false;
            }
        }
        return true;
    }


    private CoalitionStructure getTopLevelCoalitionStructure(List<Agent> agents){

        return new CoalitionStructure(singletonList(new Coalition(agents)));

    }


    private CoalitionStructure getBaseLevelCoalitionStructure(List<Agent> agents){

        List<Coalition> coalitions = new ArrayList<>();

        for(Agent agent : agents) {
            Coalition coalition = new Coalition(
                    Stream.of(agent).collect(Collectors.toList()));
            coalitions.add(coalition);
        }


        return new CoalitionStructure(coalitions);


    }

}
