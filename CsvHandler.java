/**
 * CsvHandler class for writing all experiment data into csv file.
 *
 * @author Shenglan Yu<shenglany1@student.unimelb.edu.au> - 808600
 * @author Haohua Wu<haohuaw@student.unimelb.edu.au> - 927081
 *
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

public class CsvHandler {
	
	// file writer
	private PrintWriter writer;
	
	// parameters header
	private List<String> paramHeaders;
	
	// reporters header
	private List<String> reporters;
	
	/**
	 * Constuctor of csvHandler.
	 * @param filePath
	 */
	public CsvHandler(String filePath) {
		try {
			this.writer = new PrintWriter(filePath);
			this.paramHeaders = Arrays.asList("START_WHITE", 
										"START_BLACK ",
										"START_RED",
										"ALBEDO_WHITE",
										"ALBEDO_BLACK",
										"ALBEDO_RED",
										"ALBEDO_GROUND",
										"LUMINOSITY",
										"SENCERIO");
			
			this.reporters = Arrays.asList("TICK",
											"WHITE_DAISY_POPULATION",
											"BLACK_DAISY_POPULATION",
											"RED_DAISY_POPULATION",
											"TOTAL_POPULATION",
											"GLOBAL_TEMPERATURE");
			this.writeHeaders();
			
		} catch (IOException e) {
		}
	}
	
	/**
	 * Write the first few headers into the csv file
	 */
	public void writeHeaders() {
		List<String> settings = Arrays.asList(String.valueOf(Parameters.START_WHITE),
											String.valueOf(Parameters.START_BLACK),
											String.valueOf(Parameters.START_RED),
											String.valueOf(Parameters.ALBEDO_WHITE),
											String.valueOf(Parameters.ALBEDO_BLACK),
											String.valueOf(Parameters.ALBEDO_RED),
											String.valueOf(Parameters.ALBEDO_GROUND),
											String.valueOf(Parameters.LUMINOSITY),
											String.valueOf(Parameters.SENCERIO));
		writeLine(this.paramHeaders);
		writeLine(settings);
		writeLine(this.reporters);
		
	}
	
	/**
	 * Write a single line into the csv file.
	 * @param newLine
	 */
	public void writeLine(List<String> newLine) {
		
		StringBuilder sb = new StringBuilder();
		boolean first = true;
		
		for(String element : newLine) {
			if (!first) {
				sb.append(',');
				sb.append(element);
			}else {
				sb.append(element);
				first = false;
			}
		}
		
		sb.append("\n");
		
		this.writer.append(sb.toString());
			
	}
	
	/**
	 * Save the file and close the handler.
	 */
	public void saveFile() {
		this.writer.flush();
		this.writer.close();	
	}

}
