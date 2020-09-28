/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yanti.music.service;

import com.yanti.music.impl.KoneksiJdbc;
import com.yanti.music.model.DataTablesRequest;
import com.yanti.music.model.DataTablesResponse;
import com.yanti.music.model.Lagu;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author yanti
 */
@Service
public class LaguService {
    @Autowired
    private KoneksiJdbc koneksiJdbc;
    
    @Value("${upload.lagu}")
    private String pathFile;
    
    public DataTablesResponse<Lagu> listLaguDataTable(DataTablesRequest req) {
            DataTablesResponse dataTableRespon  = new DataTablesResponse();
            dataTableRespon.setData(koneksiJdbc.getListLagu(req));
            Integer total = koneksiJdbc.getBanyakLagu(req);
            dataTableRespon.setRecordsFiltered(total);
            dataTableRespon.setRecordsTotal(total);
            dataTableRespon.setDraw(req.getDraw());
            return dataTableRespon;
        }
        
    public String save(MultipartFile file) {
        try {
            Path root = Paths.get(pathFile);
            String[] fileFrags = file.getOriginalFilename().split("\\.");
            String extension = fileFrags[fileFrags.length - 1];
            String uuid = UUID.randomUUID().toString() +"." +extension;
            Files.copy(file.getInputStream(), root.resolve(uuid));
            return uuid;
        } catch (IOException e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
    }
}
