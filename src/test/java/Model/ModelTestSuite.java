package Model;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * This is a Junit testsuite that is used to run the tests defined in:
 * {@link ContinentTest}, {@link CountryTest}, {@link PlayerTest}, {@link MapxTest}, {@link GamePlayTest}
 *
 * @author birjotsingh17
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({ContinentTest.class, CountryTest.class, PlayerTest.class, MapxTest.class, GamePlayTest.class})
public class ModelTestSuite {
}
