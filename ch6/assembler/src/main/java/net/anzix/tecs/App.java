package net.anzix.tecs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Hello world!
 * 
 */
public class App {

	private Map<String, Integer> expressions = new HashMap();

	private Map<String, Integer> jumps = new HashMap();

	public App() {
		expressions.put("0", exp("101010"));
		expressions.put("1", exp("111111"));
		expressions.put("-1", exp("111010"));
		expressions.put("D", exp("001100"));
		expressions.put("A", exp("110000"));
		expressions.put("!D", exp("001101"));
		expressions.put("!A", exp("110001"));
		expressions.put("-D", exp("001111"));
		expressions.put("-A", exp("110011"));
		expressions.put("D+1", exp("011111"));
		expressions.put("A+1", exp("110111"));

		expressions.put("D-1", exp("001110"));
		expressions.put("A-1", exp("110010"));
		expressions.put("D+A", exp("000010"));
		expressions.put("D-A", exp("010011"));
		expressions.put("A-D", exp("000111"));
		expressions.put("D&A", exp("000000"));
		expressions.put("D|A", exp("010101"));

		jumps.put("JGT", jmp("001"));
		jumps.put("JEQ", jmp("010"));
		jumps.put("JGE", jmp("011"));
		jumps.put("JLT", jmp("100"));
		jumps.put("JNE", jmp("101"));
		jumps.put("JLE", jmp("110"));
		jumps.put("JMP", jmp("111"));
	}

	protected Integer exp(String string) {
		return Integer.parseInt(string, 2) << 6;
	}

	protected Integer jmp(String string) {
		return Integer.parseInt(string, 2);
	}

	public String assembler(File input) {
		StringBuilder result = new StringBuilder();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(input));

			String line;
			while ((line = reader.readLine()) != null) {
				line = normalizeLine(line);
				if (line.length() > 0) {
					result.append(createAssemblyCode(line));
					result.append("\n");
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);

		}
		return result.toString();
	}

	public String createAssemblyCode(String line) {
		return decToBin(assembler(line));
	}

	public String normalizeLine(String line) {
		int idx = line.indexOf("//");
		if (idx > -1) {
			line = line.substring(0, idx);
		}
		return line.trim();
	}

	public int assembler(String input) {
		int val = 0;
		if (input.startsWith("@")) {
			val = Integer.valueOf(input.substring(1));
		} else {
			String destination = null;
			String expression = null;
			String jump = null;
			if (input.contains("=")) {
				String[] t = input.split("=");
				destination = t[0].trim().toUpperCase();
				expression = t[1].trim().toUpperCase();
			} else if (input.contains(";")) {
				String t[] = input.split(";");
				expression = t[0];
				jump = t[1];
			} else {
				expression = input.trim().toUpperCase();
			}
			val = 0xE000;

			if (destination != null) {
				if (destination.contains("M")) {
					val = val | 0x8;
				}
				if (destination.contains("D")) {
					val = val | 0x10;
				}
				if (destination.contains("A")) {
					val = val | 0x20;
				}
			}
			if (expression != null) {
				if (expression.contains("M")) {
					val = val | 0x1000;
					expression = expression.replace('M', 'A');
				}
				if (!expressions.containsKey(expression)) {
					throw new UnsupportedOperationException(
							"Unknown operation: " + expression);
				}
				val = val | expressions.get(expression);
			}
			if (jump != null) {
				if (!jumps.containsKey(jump)) {
					throw new UnsupportedOperationException(
							"Unknown jump type: " + jump);
				}
				val = val | jumps.get(jump);
			}
		}
		return val;
	}

	public String decToBin(int val) {
		String ret = "";
		for (int i = 0; i < 16; i++) {
			if (val % 2 == 0)
				ret = "0" + ret;
			else
				ret = "1" + ret;
			val = val / 2;
		}
		return ret;
	}
}
