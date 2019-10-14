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
		 //test if mapName is null
		 map.saveMap(g, " ");
		 String path = "src/main/resources/.map";
		 File file = new File(path);
		 boolean exists = file.exists();
		 assertTrue(exists == false);		 
	    }

	 @Test
	    public void saveMap2() throws IOException {
		 // test for overwriting the default map
		boolean check = map.saveMap(g, "map");
		 assertFalse(check); 
	    }

	 
	 
}
