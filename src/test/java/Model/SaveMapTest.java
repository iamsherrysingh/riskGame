package Model;

import org.junit.*;
import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

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
	
	 @Test
	    public void saveMap() throws IOException {
		 String path = "src/main/resources/.map";
		 File file = new File(path);
		 boolean exists = file.exists();
		 map.saveMap(g, " ");
		 assertTrue(exists == false);
		 
	    }

	

}
