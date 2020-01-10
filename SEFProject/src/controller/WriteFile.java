package controller;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


public class WriteFile {
	
	public String path;
	public boolean appendToFile = true;
	
	public WriteFile(String filePath, boolean appendValue) {
		path = filePath;
		appendToFile = appendValue;
	}

	public void writeToFile(String textLine) throws IOException {
		
		FileWriter write = new FileWriter(path, appendToFile);
		PrintWriter printLine = new PrintWriter(write);
		
		printLine.printf(textLine);
		
		printLine.close();
				
		
	}
	
	
}
