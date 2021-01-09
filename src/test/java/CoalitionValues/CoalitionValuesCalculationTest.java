package CoalitionValues;

import ReferenceData.Agent;
import ReferenceData.Coalition;
import org.junit.Test;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class CoalitionValuesCalculationTest {

    @Test
    public void coalitionValues_should_return_coalition_values_for_all_coalitions_of_agents_input(){

        Map<Coalition,Double> coalitionValues =
                new CoalitionValuesCalculation()
                        .coalitionValues(
                                Stream.of(agentOne, agentTwo, agentThree).collect(Collectors.toList()));

        assertEquals(
                coalitionValues.keySet().size(),
                7
        );
    }

    private Agent agentOne = new Agent("1");
    private Agent agentTwo = new Agent("2");
    private Agent agentThree = new Agent("3");
}
