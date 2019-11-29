package GameLogic;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
/**
 * This is a Junit testsuite that is used to run the tests defined in:
 * {@link GameLogic.ContinentTest}, {@link GameLogic.CountryTest},  {@link GameLogic.MapxTest}, {@link GameLogic.GamePlayTest}
 * 
 * @author birjotsingh17
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({PlayerTest.class,ContinentTest.class, CountryTest.class, MapxTest.class, GamePlayTest.class})
public class TestSuite {
}
