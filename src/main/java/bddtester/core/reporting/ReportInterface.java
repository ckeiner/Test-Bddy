package bddtester.core.reporting;

import com.aventstack.extentreports.GherkinKeyword;

import bddtester.core.testdata.Testdata;

/**
 * The interface for reporting frameworks.
 * 
 * @author ckeiner
 *
 */
public interface ReportInterface
{
    /**
     * Reports a feature with the given description.
     * 
     * @param description
     *            The description of the feature.
     * @return A {@link ReportElement} depicting the feature.
     */
    public ReportElement feature(String description);

    /**
     * Reports a scenario with the given description.
     * 
     * @param description
     *            The description of the scenario.
     * @return A {@link ReportElement} depicting the scenario.
     */
    public ReportElement scenario(String description);

    /**
     * Reports a scenario outline with the given description and testdata.
     * 
     * @param description
     *            The description of the scenario outline.
     * @param testdata
     *            The {@link Testdata} for the scenario outline.
     * @return A {@link ReportElement} depicting the scenario outline.
     */
    public <T> ReportElement scenarioOutline(String description, T testdata);

    /**
     * Reports a scenario outline with the given description.
     * 
     * @param description
     *            The description of the scenario outline.
     * @return A {@link ReportElement} depicting the scenario outline.
     */
    public <T> ReportElement scenarioOutline(String description);

    /**
     * Reports a step with the given description.
     * 
     * @param description
     *            The description of the step.
     * @param keyword
     *            The {@link GherkinKeyword} for either Given, When, Then or And.
     * @return A {@link ReportElement} depicting the step.
     */
    public ReportElement step(GherkinKeyword keyword, String description);

    /**
     * Completes the report.
     */
    public void finishReport();

}
