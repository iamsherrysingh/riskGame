import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.RunWith;
import org.junit.runner.notification.Failure;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import Model.ContinentTest;
import Model.CountryTest;
import Model.MapTest;


@RunWith(Suite.class)
@SuiteClasses({MapTest.class,ContinentTest.class,CountryTest.class})
public class TestSuite {
	
	
	

	public static void main(String[] args) {
		Result r =JUnitCore.runClasses(TestSuite.class);
		for(Failure f : r.getFailures()) {
			System.out.println(f.toString());
		}
	
		System.out.println(r.wasSuccessful());
	
	}
	


}
