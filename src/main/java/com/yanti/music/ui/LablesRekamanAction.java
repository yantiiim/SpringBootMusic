/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yanti.music.ui;

import com.yanti.music.impl.JdbcLables;
import com.yanti.music.model.DataTablesRequest;
import com.yanti.music.model.DataTablesResponse;
import com.yanti.music.model.LablesRekaman;
import com.yanti.music.service.LablesRekamanService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 *
 * @author yanti
 */
@Controller
public class LablesRekamanAction {
    @Autowired
    private JdbcLables koneksiJdbc;
    
    @Autowired
    private LablesRekamanService lablesRekamanService;

    @PostMapping(path="/api/listlablesdatajson")
    public ResponseEntity<DataTablesResponse<LablesRekaman>> listLablesRekamanDataTable(@RequestBody DataTablesRequest dataRequest){
        return ResponseEntity.ok().body(lablesRekamanService.listLablesRekamanDataTable(dataRequest));
    }
    
    @PostMapping("/api/savelablesjson")
    public ResponseEntity <Map<String,Object>> savelablesjson(@RequestBody LablesRekaman lablesRekaman){
        Map<String, Object> status = new HashMap<>();
        koneksiJdbc.insertOrUpdateLables(lablesRekaman);
        status.put("pesan", "Simpan Berhasil");
        return ResponseEntity.ok().body(status);
    }
    
    @GetMapping(path= "/api/listlablesjson")
    public ResponseEntity<List<LablesRekaman>> listLablesCariJson(){
        return ResponseEntity.ok().body(koneksiJdbc.getLablesRekaman());
    }
    
    @DeleteMapping("/api/deletelables/{id}")
    public ResponseEntity<?> deleteLables(@PathVariable("id") Integer id){
        try {
            lablesRekamanService.deleteById(id);
            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch (DataAccessException e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
     
}
