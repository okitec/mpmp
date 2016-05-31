package main;

import java.io.BufferedReader;
import java.io.PrintWriter;

public interface CmdFunc {
	public void exec(String line, BufferedReader in, PrintWriter out);
}
