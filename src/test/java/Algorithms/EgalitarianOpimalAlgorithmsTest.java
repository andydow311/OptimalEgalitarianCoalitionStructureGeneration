package Algorithms;

import ReferenceData.Agent;
import ReferenceData.Coalition;
import ReferenceData.CoalitionStructure;
import com.sun.tools.javac.util.Pair;
import org.junit.Test;
import static org.junit.Assert.assertEquals;


import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Collections.singletonList;
import static org.junit.Assert.assertTrue;

public class EgalitarianOpimalAlgorithmsTest {

    @Test
    public void EgalitarianOptimalAlgorithms_should_return_the_correct_optimal_value(){

        Pair<CoalitionStructure, Double> output =
                new EgalitarianOptimalAlgorithms()
                        .egalitarianAlgorithmOverAllSpace(csSpace,3, coalitionValues);

        assertEquals(
                java.util.Optional.ofNullable(output.snd),
               Optional.of(4.0)
        );

        assertEquals(
                java.util.Optional.ofNullable(output.fst),
                Optional.of(coalitionStructureThree)
        );

    }

    @Test
    public void egalitarianAlgorithmStructureFromCoalitions_should_return_the_correct_optimal_value(){

        Pair<CoalitionStructure, Double> output =
                new EgalitarianOptimalAlgorithms().egalitarianAlgorithmStructureFromCoalitions(
                        Stream.of(
                                coalitionStructureOne,
                                coalitionStructureThree
                        ).collect(Collectors.toList())
                );

        assertEquals(
                java.util.Optional.ofNullable(output.snd),
                Optional.of(4.0)
        );

        assertEquals(
                java.util.Optional.ofNullable(output.fst),
                Optional.of(coalitionStructureThree)
        );

    }

    @Test
    public void computeEgalitarianCoalitionStructureValue_should_return_the_correct_value(){

        assertTrue(
                new EgalitarianOptimalAlgorithms()
                        .computeEgalitarianCoalitionStructureValue(
                                coalitionStructurefour, coalitionValues
                        ) == 1
        );
    }

    private Agent agentOne = new Agent("1");
    private Agent agentTwo = new Agent("2");
    private Agent agentThree = new Agent("3");;

    private Coalition coalitionOne = new Coalition(singletonList(agentOne));
    private Coalition coalitionTwo = new Coalition(singletonList(agentTwo));
    private Coalition coalitionThree = new Coalition(singletonList(agentThree));

    private Coalition coalitionFour = new Coalition(
            Stream.of(agentOne,agentTwo).collect(Collectors.toList()));
    private Coalition coalitionFive = new Coalition(
            Stream.of(agentOne,agentThree).collect(Collectors.toList()));
    private Coalition coalitionSix = new Coalition(
            Stream.of(agentTwo,agentThree).collect(Collectors.toList()));

    private Coalition coalitionSeven = new Coalition(
            Stream.of(agentOne, agentTwo,agentThree).collect(Collectors.toList()));

    private CoalitionStructure coalitionStructureOne
            = new CoalitionStructure(
                    Stream.of(coalitionOne, coalitionTwo, coalitionThree).collect(Collectors.toList()),
            1);

    private CoalitionStructure coalitionStructureTwo
            = new CoalitionStructure(
            Stream.of(coalitionOne, coalitionSix).collect(Collectors.toList()),
            1);

    private CoalitionStructure coalitionStructureThree
            = new CoalitionStructure(
            Stream.of(coalitionTwo, coalitionFive).collect(Collectors.toList()),
            4);

    private CoalitionStructure coalitionStructurefour
            = new CoalitionStructure(
            Stream.of(coalitionThree, coalitionFour).collect(Collectors.toList()),
            1);

    private CoalitionStructure coalitionStructureFive
            = new CoalitionStructure(
            Stream.of(coalitionSeven).collect(Collectors.toList()),
            3);

    Map<Coalition, Double> coalitionValues = new HashMap<>(); {

        coalitionValues.put(coalitionOne, 1.0);
        coalitionValues.put(coalitionTwo, 4.0);
        coalitionValues.put(coalitionThree, 1.0);
        coalitionValues.put(coalitionFour, 1.0);
        coalitionValues.put(coalitionFive, 4.0);
        coalitionValues.put(coalitionSix, 1.0);
        coalitionValues.put(coalitionSeven, 2.0);

    }

    Map<Integer, Set<CoalitionStructure>> csSpace = new HashMap<>();{

        csSpace.put(1,Stream.of(coalitionStructureFive).collect(Collectors.toSet()));
        csSpace.put(2,Stream.of(coalitionStructureTwo, coalitionStructureThree, coalitionStructurefour).collect(Collectors.toSet()));
        csSpace.put(3,Stream.of(coalitionStructureOne).collect(Collectors.toSet()));

    }


}
