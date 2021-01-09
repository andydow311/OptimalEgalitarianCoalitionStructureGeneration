package CoalitionGeneration;

import ReferenceData.Agent;
import ReferenceData.Coalition;
import org.junit.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Collections.singletonList;
import static org.junit.Assert.assertEquals;


public class CoalitionGenerationTest {

    @Test
    public void generateListOfAgents_should_return_a_list_of_8_agents() {
        CoalitionGeneration coalitionGeneration = new CoalitionGeneration();

        assertEquals(
                coalitionGeneration.generateListOfAgents(8).size(),
                8
        );

    }

    @Test
    public void generateCoalitions_should_generate_7_coalitions_for_3_agents() {
        CoalitionGeneration coalitionGeneration = new CoalitionGeneration();
        assertEquals(coalitionGeneration.generateCoalitionsWithRandomValues(3).size(), 7);
    }

    @Test
    public void filterCoalitions_should_filter_7_coalitions_to_5_coalitions_for_3_agents() {
        CoalitionGeneration coalitionGeneration = new CoalitionGeneration();
        assertEquals(
                coalitionGeneration.filterCoalitions(
                        coalitionGeneration.generateCoalitionsWithRandomValues(3),
                        3).size(),
                5
        );
    }

    @Test
    public void updateCoalitionDisjointNeighbours_should_correctly_update_disjoint_colaitions() {

        List<Coalition> updatedCoalitions =
                new CoalitionGeneration().updateCoalitionDisjointNeighbours(
                        Stream.of(
                                coalitionOne,
                                coalitionTwo,
                                coalitionThree
                        ).collect(Collectors.toList())
                );


        assertEquals(
                coalitionOne.getDisjointNeighbours(),
                singletonList(coalitionThree)
        );

        assertEquals(
                coalitionTwo.getDisjointNeighbours().size(),
                0
        );


        assertEquals(
                coalitionThree.getDisjointNeighbours(),
                singletonList(coalitionOne)
        );


    }

    private Agent agentOne = new Agent("1");
    private Agent agentTwo = new Agent("2");
    private Agent agentThree = new Agent("3");


    private Coalition coalitionOne = new Coalition(singletonList(agentOne));
    private Coalition coalitionTwo = new Coalition(
            Stream.of(agentOne, agentThree).collect(Collectors.toList()));
    private Coalition coalitionThree = new Coalition(
            Stream.of(agentTwo, agentThree).collect(Collectors.toList()));
}
