package net.anzix.tecs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.StringReader;

import junit.framework.Assert;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppIntTest {

	@Test
	public void testFiles() throws Exception{
		assertFiles(new File("../add/Add.asm"),new File("../add/Add.hack"));
		assertFiles(new File("../max/MaxL.asm"),new File("../max/MaxL.hack"));
		assertFiles(new File("../pong/PongL.asm"),new File("../pong/PongL.hack"));
	}
	
	public void assertFiles(File fasm, File fhack) throws Exception {
		App app = new App();
		String res = app.assembler(fasm);
		BufferedReader dest = new BufferedReader(new StringReader(res));
		BufferedReader orig = new BufferedReader(new FileReader(fhack));
		String ldest = dest.readLine();
		String lorig = orig.readLine();
		int l = 1;
		while (ldest != null && lorig != null) {
			Assert.assertEquals("Difference in line "+l,lorig, ldest);
			ldest = dest.readLine();
			lorig = orig.readLine();
			l++;
		}
		Assert.assertNull(ldest);
		Assert.assertNull(lorig);
	}
}
