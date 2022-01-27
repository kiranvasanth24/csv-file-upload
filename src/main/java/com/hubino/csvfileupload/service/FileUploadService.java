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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hubino.csvfileupload.entity.TableFileUpload;
import com.hubino.csvfileupload.repository.FileUploadRepository;

@Service
public class FileUploadService {
	
	  //service
	
	  @Autowired
	  private FileUploadRepository fileUploadRepository;
	  
	  Logger logger = LoggerFactory.getLogger(getClass());
	  
		/**
		 * This method is to upload the file.
		 * 
		 * @param file
		 * @return
		 * @throws IOException
		 */
	  public void fileUpload(MultipartFile file) {
		logger.info("METHOD: fileUpload(MultipartFile file) Initiated");
	    try {
	      List<TableFileUpload> tutorials = CsvHelper.csvToTableFileUpload(file.getInputStream());
	      fileUploadRepository.saveAll(tutorials);
	    } catch (IOException e) {
	      throw new RuntimeException("fail to store csv data: " + e.getMessage());
	    }
	  }

		/**
		 * This method is to get the file info by pagination.
		 * 
		 * @param offset
		 * @param pagesize
		 * @return
		 */
	  public Page<TableFileUpload> getFileUploadInfo(int offset, int pagesize) {
		  logger.info("METHOD: getFileUploadInfo(int offset, int pagesize) Initiated");
		 return fileUploadRepository.findAll(PageRequest.of(offset, pagesize));
		}

	
}
