package Model;

import org.junit.*;
import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;

import org.junit.Test;

import Model.Graph;
import Model.Mapx;

public class SaveMapTest {

	ByteArrayOutputStream baos = new ByteArrayOutputStream();
	PrintStream ps = new PrintStream(baos);
	// IMPORTANT: Save the old System.out!
	PrintStream old = System.out;
	
	
	static Mapx map;
	static Graph g;

	@Before
	public void before() {
		map = new Mapx();
		g = map.createGameGraph("src/main/resources/map.map");
	}

	@After
	public void aft() {
		map = null;
		g = null;
	}
	
	
	// this test is to check if there is no name passed in the string
	 @Test
	    public void saveMap() throws IOException {
		 
		 map.saveMap(g, " ");
		 String path = "src/main/resources/.map";
		 File file = new File(path);
		 boolean exists = file.exists();
		 assertTrue(exists == false);
		 
	    }

//	 @Test
//	    public void saveMap2() throws IOException {
//		
//		 String expected = "you cannot edit a default map";
//			
//			map.saveMap(g, "map");
//			//System.setOut(ps);
//			//System.out.flush();
//			//System.setOut(old);
//			System.out.println(baos.toString());
//			String a = baos.toString().trim();
//			//System.out.print(a + expected);
//			//ArrayList<String> ls = baos.toString().split(" ");
//			//System.out.println(expected);
//			boolean z;
//			if(expected.equalsIgnoreCase(a)) {
//				z=true;
//				System.out.println(z);
//			}else {
//				z=false;
//				System.out.println(z);
//			}
//			assertTrue(z);
//		// assertEquals(expected,a);
//		 
//	    }
	

}
