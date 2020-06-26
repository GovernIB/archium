package es.caib.archium.ejb.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class CSVReader {
	
	private ArrayList<String[]> data = new ArrayList<String[]>();

	public CSVReader(String filePath, String separator) throws IOException, FileNotFoundException {
		
		BufferedReader br = null;
		String line = "";

		try {
			InputStream input = CSVReader.class.getResourceAsStream(filePath);
		    br = new BufferedReader(new InputStreamReader(input));
		    while ((line = br.readLine()) != null) {                
		        String[] lineData = line.split(separator);
		        data.add(lineData);
		    }
		} catch (FileNotFoundException e) {
			throw e;
		} catch (IOException e) {
			throw e;
		} finally {
		    if (br != null) {
		        try {
		            br.close();
		        } catch (IOException e) {
		        	throw e;
		        }
		    }
		}
	}

	public ArrayList<String[]> getData() {
		return data;
	}	
	
	
	
}
