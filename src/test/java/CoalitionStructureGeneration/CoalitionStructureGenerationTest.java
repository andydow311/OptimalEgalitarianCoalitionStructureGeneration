package CoalitionStructureGeneration;

import ReferenceData.Agent;
import ReferenceData.Coalition;
import ReferenceData.CoalitionStructure;
import org.junit.*;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Collections.singletonList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class CoalitionStructureGenerationTest {

    @Test
    public void mergeCoalitions_should_merge_multiple_coalitions_into_a_single_one() {

        CoalitionStructureGeneration coalitionStructureGeneration = new CoalitionStructureGeneration();
        Coalition mergedCoalition = coalitionStructureGeneration.mergeCoalitions(coalitionOne, coalitionTwo);

        assertEquals(
                mergedCoalition.getAgents().size(),
                2
        );

    }

    @Test
    public void getCoalitionStructureSpace_should_return_5_levels_of_structures_for_5_agents() {

        Map<Integer, Set<CoalitionStructure>> csSapceForFive = new CoalitionStructureGeneration().getCoalitionStructureSpace(
                Stream.of(
                        agentOne,
                        agentTwo,
                        agentThree,
                        agentFour,
                        agentFive
                ).collect(Collectors.toList())

        );


        assertEquals(
                csSapceForFive.keySet().size(),
                5
        );

    }

    @Test
    public void getCoalitionStructureSpace_should_return_5_structures_for_3_agents() {

        Map<Integer, Set<CoalitionStructure>> csSapceForThree = new CoalitionStructureGeneration().getCoalitionStructureSpace(
                Stream.of(
                        agentOne,
                        agentTwo,
                        agentThree
                ).collect(Collectors.toList())

        );

        int numberOfStructures = 0;

        for (int key : csSapceForThree.keySet()) {
            numberOfStructures += csSapceForThree.get(key).size();
        }

        assertEquals(numberOfStructures, 5);

    }

    @Test
    public void getEgalitarianCoalitionStructureValue_should_correctly_compute_egalitarian_value_for_structure() {

        coalitionThree.setValue(3);
        coalitionFour.setValue(4);
        coalitionFive.setValue(5);

        assertTrue(
                new CoalitionStructureGeneration().getEgalitarianCoalitionStructureValue(
                        Stream.of(
                                coalitionThree,
                                coalitionFour,
                                coalitionFive
                        ).collect(Collectors.toList())
                ) == 3);
    }

    @Test
    public void getCoalitionStructuresFromCoalitions_should_return_list_of_coalition_structures(){

        coalitionOne.setDisjointNeighbours(
                Stream.of(
                        coalitionTwo,
                        coalitionThree
                ).collect(Collectors.toList())
        );

        coalitionTwo.setDisjointNeighbours(
                Stream.of(
                        coalitionOne,
                        coalitionThree
                ).collect(Collectors.toList())
        );

        coalitionThree.setDisjointNeighbours(
                Stream.of(
                        coalitionOne,
                        coalitionTwo
                ).collect(Collectors.toList())
        );

        assertEquals(
            new CoalitionStructureGeneration().getCoalitionStructuresFromCoalitions(
                    Stream.of(
                            coalitionOne,
                            coalitionTwo,
                            coalitionThree
                    ).collect(Collectors.toList()),
                    3).size(),
        1);

    }

    private Agent agentOne = new Agent("1");
    private Agent agentTwo = new Agent("2");
    private Agent agentThree = new Agent("3");
    private Agent agentFour = new Agent("4");
    private Agent agentFive = new Agent("5");

    private Coalition coalitionOne = new Coalition(singletonList(agentOne));
    private Coalition coalitionTwo = new Coalition(singletonList(agentTwo));
    private Coalition coalitionThree = new Coalition(singletonList(agentThree));
    private Coalition coalitionFour = new Coalition(singletonList(agentFour));
    private Coalition coalitionFive = new Coalition(singletonList(agentFive));

}
