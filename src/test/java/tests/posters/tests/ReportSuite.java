package tests.posters.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import tests.posters.tests.complex.GuestCheckout;
import tests.posters.tests.reportFatal.ExceptionThrowingTest;
import tests.posters.tests.simple.FeatureTest;
import tests.posters.tests.simple.ReuseTest;
import tests.posters.tests.simple.ReuseTestdata;
import tests.posters.tests.simple.ScenarioTest;

@RunWith(Suite.class)
@SuiteClasses({ GuestCheckout.class, FeatureTest.class, ReuseTest.class, ReuseTestdata.class,
        ScenarioTest.class, ExceptionThrowingTest.class })

public class ReportSuite
{

}
