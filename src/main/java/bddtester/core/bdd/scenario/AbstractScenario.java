package bddtester.core.bdd.scenario;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import bddtester.core.bdd.beforeAfter.After;
import bddtester.core.bdd.beforeAfter.Before;
import bddtester.core.bdd.status.Status;
import bddtester.core.bdd.status.Statusable;
import bddtester.core.reporting.ReportInterface;

/**
 * The abstract class for all scenarios.
 * 
 * @author ckeiner
 *
 */
public abstract class AbstractScenario implements Statusable
{
    /**
     * The reporter responsible for reporting
     */
    private ReportInterface reporter;

    /**
     * The description of the scenario.
     */
    final private String description;

    /**
     * The Status of the scenario.
     */
    private final Set<Status> status;

    public AbstractScenario(String description)
    {
        this.description = description;
        this.status = new LinkedHashSet<Status>();
    }

    /**
     * Executes the scenario.
     */
    public abstract void test();

    /**
     * Skips the scenario and reports it as such.
     */
    public abstract void skipScenario();

    /**
     * Adds all specified backgrounds to the scenario
     * 
     * @param befores
     *            The list of {@link Before}s.
     */
    public abstract void addBefores(List<Before> befores);

    /**
     * Adds all specified postSteps to the scenario
     * 
     * @param afters
     *            The list of {@link After}s.
     */
    public abstract void addAfters(List<After> afters);

    public String getDescription()
    {
        return description;
    }

    public ReportInterface getReporter()
    {
        return reporter;
    }

    public void setReporter(ReportInterface reporter)
    {
        this.reporter = reporter;
    }

    public Set<Status> getStatus()
    {
        return this.status;
    }

    @Override
    public AbstractScenario ignore()
    {
        getStatus().add(Status.IGNORE);
        return this;
    }

    @Override
    public AbstractScenario wip()
    {
        getStatus().add(Status.WIP);
        return this;
    }

    @Override
    public AbstractScenario skip()
    {
        getStatus().add(Status.SKIP);
        return this;
    }

}
