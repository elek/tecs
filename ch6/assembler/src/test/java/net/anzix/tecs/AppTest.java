package net.anzix.tecs;

import junit.framework.Assert;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest {

	
	@Test
	public void assembler() {
		App app = new App();
		Assert.assertEquals(2, app.assembler("@2"));
		Assert.assertEquals(34, app.assembler("@34"));
		Assert.assertEquals(Integer.parseInt("1110110000010000", 2),
				app.assembler("D=A"));

	}

	@Test
	public void exp() {
		App app = new App();

		Assert.assertEquals(Integer.valueOf(0x1C0), app.exp("111"));
	}

	@Test
	public void normalizeLine() {
		App app = new App();

		Assert.assertEquals("", app.normalizeLine("// anything"));
		Assert.assertEquals("@23", app.normalizeLine(" @23 // anything"));
	}

	@Test
	public void decToBin() {
		App app = new App();
		Assert.assertEquals("0000000000000001", app.decToBin(1));
		Assert.assertEquals("0000000000000101", app.decToBin(5));
		Assert.assertEquals("0000000000000111", app.decToBin(7));
	}

}
