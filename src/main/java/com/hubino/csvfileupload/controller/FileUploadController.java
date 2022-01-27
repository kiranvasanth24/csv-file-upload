package com.hubino.csvfileupload.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.hubino.csvfileupload.entity.TableFileUpload;
import com.hubino.csvfileupload.response.ErrorResponse;
import com.hubino.csvfileupload.service.CsvHelper;
import com.hubino.csvfileupload.service.FileUploadService;
import com.hubino.csvfileupload.util.Constants;

@RestController
@RequestMapping("/api")
public class FileUploadController {
	
	
	@Autowired
	private FileUploadService fileUploadService;
	
	private ErrorResponse errorResponse = null;
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * This method is to upload the file data.
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	@PostMapping("/csv/file/uploads")
	public ResponseEntity<?> fileUpload(@RequestParam(value = "file") MultipartFile file) throws IOException{
		if(CsvHelper.hasCSVFormat(file)) {
			try {
				fileUploadService.fileUpload(file);
				return ResponseEntity.ok(Constants.FILE_UPLOADED_SUCESS +file.getOriginalFilename());
			}catch(Exception e){
				logger.error("API: /api/csv/file/uploads ERROR: "+Constants.FILE_UPLOADED_FAIL);
				return getErrorResponseEntity(Constants.FILE_UPLOADED_FAIL, Constants.ERROR_CODE_400, HttpStatus.BAD_REQUEST);
			}
		}
		return getErrorResponseEntity(Constants.FILE_TYPE, Constants.ERROR_CODE_400, HttpStatus.BAD_REQUEST);	
	}
	
	/**
	 * This method is to receive the file data information by pagination.
	 * 
	 * @param offset
	 * @param pagesize
	 * @return
	 */
	@PostMapping("/csv/info")
	public ResponseEntity<?> getFileUploadInfo(@RequestParam(name = "offset") int offset,@RequestParam(name = "pagesize") int pagesize){
		
		Page<TableFileUpload> tableFileUpload = fileUploadService.getFileUploadInfo(offset, pagesize);
			
		if(tableFileUpload.isEmpty()) {
			return getErrorResponseEntity(Constants.LIST_EMPTY, Constants.ERROR_CODE_400, HttpStatus.BAD_REQUEST);
	}
		return ResponseEntity.ok(tableFileUpload);
		
	}
	
    public ResponseEntity<?> getErrorResponseEntity(String errorMessage, int errorCode, HttpStatus httpStatus) {

        errorResponse = new ErrorResponse();
        errorResponse.setErrorMessage(errorMessage);
        errorResponse.setErrorCode(errorCode);
        return new ResponseEntity<Object>(
                errorResponse, new HttpHeaders(), httpStatus);
    }
	

}