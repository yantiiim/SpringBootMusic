/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yanti.music.ui;

import com.yanti.music.impl.KoneksiJdbc;
import com.yanti.music.model.DataTablesRequest;
import com.yanti.music.model.DataTablesResponse;
import com.yanti.music.model.LablesRekaman;
import com.yanti.music.service.LablesRekamanService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 *
 * @author yanti
 */
@Controller
public class LablesRekamanAction {
    @Autowired
    private KoneksiJdbc koneksiJdbc;
    
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
     
}
