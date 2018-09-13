package bddtester.core.reporting.allurereports;

import java.util.ArrayList;
import java.util.List;

public class AllureFeature
{
    List<AllureScenario> feature;
    String description;

    public AllureFeature(String description)
    {
        this.feature = new ArrayList<AllureScenario>();
        this.description = description;
    }

    public void add(String scenario)
    {
        feature.add(new AllureScenario(scenario));
    }

    public AllureScenario getLastScenario()
    {
        return feature.get(feature.size() - 1);
    }
}
