/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yanti.music.ui;

import com.yanti.music.impl.KoneksiJdbc;
import com.yanti.music.model.Artis;
import com.yanti.music.model.DataTablesRequest;
import com.yanti.music.model.DataTablesResponse;
import com.yanti.music.service.ArtisService;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author yanti
 */
@Controller
public class ArtisAction {
    @Autowired
    private KoneksiJdbc koneksiJdbc;
    
    @Autowired
    private ArtisService artisService;
    
    @PostMapping(path="/api/listartisdatajson")
    public ResponseEntity<DataTablesResponse<Artis>> listArtisDataTable(@RequestBody DataTablesRequest dataRequest){
        return ResponseEntity.ok().body(artisService.listArtisDataTable(dataRequest));
    }
    
    @PostMapping("/api/saveartisjson")
    public ResponseEntity <Map<String,Object>> saveartisjson(@RequestBody Artis artis){
        Map<String, Object> status = new HashMap<>();
        koneksiJdbc.insertOrUpdateArtis(artis);
        status.put("pesan", "Simpan Berhasil");
        return ResponseEntity.ok().body(status);
    }
    
    @PostMapping("/api/uploadartis")
    public ResponseEntity<Map<String,Object>> uploadFileArtis(@RequestParam("file") MultipartFile file) {
        String message = null;
        Map<String,Object> pesan = new HashMap<>();
        try {
            String namaFile = artisService.save(file);
            
            pesan.put("pesan", "Uploaded the file succesfully: " + namaFile);
            pesan.put("namaFile",  namaFile);
            return ResponseEntity.status(HttpStatus.OK).body(pesan);
        }catch (Exception e) {
            pesan.put("pesan", "Could not upload the file: " + file.getOriginalFilename() + "!");
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(pesan);
        }
    }
    
    @GetMapping(path= "/api/listartisjson")
    public ResponseEntity<List<Artis>> listArtisCariJson(){
        return ResponseEntity.ok().body(koneksiJdbc.getArtis());
    }
    
    @GetMapping(value = "/api/image/{id}")
    public ResponseEntity<InputStreamResource> getImage(@PathVariable("id") String id) {
        try {
            return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(
                    new InputStreamResource( artisService.load(id).getInputStream() ));
        } catch (IOException ex) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null);
        }
    }
}
