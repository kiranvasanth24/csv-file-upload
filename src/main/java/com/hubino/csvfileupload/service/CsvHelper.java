package com.hubino.csvfileupload.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import com.hubino.csvfileupload.entity.TableFileUpload;
import com.hubino.csvfileupload.repository.FileUploadRepository;

public class CsvHelper {
	
	  public static String TYPE = "text/csv";
	  //static String[] HEADERs = {"Title", "Description", "Published" };
	  
	  @Autowired
	  private FileUploadRepository fileUploadRepository;

	  public static boolean hasCSVFormat(MultipartFile file) {

	    if (!TYPE.equals(file.getContentType())) {
	      return false;
	    }

	    return true;
	  }

	  public static List<TableFileUpload> csvToTableFileUpload(InputStream is) {
	    try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
	        CSVParser csvParser = new CSVParser(fileReader,
	            CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

	      List<TableFileUpload> tableFileUpload = new ArrayList<TableFileUpload>();

	      Iterable<CSVRecord> csvRecords = csvParser.getRecords();
	      //System.out.println(csvRecords);

	      for (CSVRecord csvRecord : csvRecords) {
	    	  TableFileUpload tableFileUploadData = new TableFileUpload(
	              0, csvRecord.get("Title"),
	              csvRecord.get("Description"),
	              Boolean.parseBoolean(csvRecord.get("Published"))
	            );
	    	  tableFileUpload.add(tableFileUploadData);
	      }

	      return tableFileUpload;
	    } catch (IOException e) {
	      throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
	    }
	  }

}
