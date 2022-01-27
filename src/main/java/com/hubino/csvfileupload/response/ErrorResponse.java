package com.hubino.csvfileupload.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ErrorResponse {
	
	private String errorMessage="";
	private int errorCode=0;

}
