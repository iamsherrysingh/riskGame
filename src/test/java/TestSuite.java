import Model.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
/**
 * This is a Junit testsuite that is used to run the tests defined in:
 * {@link Model.ContinentTest}, {@link Model.CountryTest}, {@link Model.PlayerTest}, {@link Model.MapxTest}, {@link Model.GamePlayTest}
 * 
 * @author birjotsingh17
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({View.ViewTestSuite.class, Model.ModelTestSuite.class})
public class TestSuite {
}
