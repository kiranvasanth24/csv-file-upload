package com.hubino.csvfileupload.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hubino.csvfileupload.entity.TableFileUpload;



public interface FileUploadRepository extends JpaRepository<TableFileUpload, Long> {

}
