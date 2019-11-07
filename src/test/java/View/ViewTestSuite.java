package View;

import Model.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * This is a Junit testsuite that is used to run the tests defined in:
 * {@link ContinentTest}, {@link CountryTest}, {@link PlayerTest}, {@link MapxTest}, {@link GamePlayTest}
 *
 * @author birjotsingh17
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({PhaseViewTest.class, WorldDominationViewTest.class})
public class ViewTestSuite {
}
