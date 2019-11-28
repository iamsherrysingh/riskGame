package test;

import GameLogic.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * This is a Junit testsuite that is used to run the tests defined in:
 * {@link ContinentTest}, {@link CountryTest},  {@link MapxTest}, {@link GamePlayTest}
 *
 * @author birjotsingh17
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({PhaseViewTest.class})
public class ViewTestSuite {
}
