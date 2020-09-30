/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yanti.music.ui;

import com.yanti.music.impl.KoneksiJdbc;
import com.yanti.music.model.DataTablesRequest;
import com.yanti.music.model.DataTablesResponse;
import com.yanti.music.model.Lagu;
import com.yanti.music.service.LaguService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author yanti
 */
@Controller
public class LaguAction {
    @Autowired
    private KoneksiJdbc koneksiJdbc;
    
    @Autowired
    private LaguService laguService;
    
    @PostMapping(path="/api/listlagudatajson")
    public ResponseEntity<DataTablesResponse<Lagu>> listLaguDataTable(@RequestBody DataTablesRequest dataRequest){
        return ResponseEntity.ok().body(laguService.listLaguDataTable(dataRequest));
    }
    
    @PostMapping("/api/savelagujson")
    public ResponseEntity <Map<String,Object>> savelagujson(@RequestBody Lagu lagu){
        Map<String, Object> status = new HashMap<>();
        koneksiJdbc.insertOrUpdateLagu(lagu);
        status.put("pesan", "Simpan Berhasil");
        return ResponseEntity.ok().body(status);
    }
    
    @PostMapping("/api/uploadlagu")
    public ResponseEntity<Map<String,Object>> uploadFileLagu(@RequestParam("file") MultipartFile file) {
        String message = null;
        Map<String,Object> pesan = new HashMap<>();
        try {
            String namaFile = laguService.save(file);
            
            pesan.put("pesan", "Uploaded the file succesfully: " + namaFile);
            return ResponseEntity.status(HttpStatus.OK).body(pesan);
        }catch (Exception e) {
            pesan.put("pesan", "Could not upload the file: " + file.getOriginalFilename() + "!");
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(pesan);
        }
    }
    
    @GetMapping(path= "/api/listlagujson")
    public ResponseEntity<List<Lagu>> listLaguCariJson(){
        return ResponseEntity.ok().body(koneksiJdbc.getLagu());
    }
    
    @DeleteMapping("/api/deletelagu/{id}")
    public ResponseEntity<?> deleteLagu(@PathVariable("id") Integer id){
        try {
            laguService.deleteById(id);
            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch (DataAccessException e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    
    @GetMapping(path = "/api/listlagujson/{id}")
    public ResponseEntity<List<Lagu>> findByAlbums(@PathVariable("id") Integer id){
        return ResponseEntity.ok().body(koneksiJdbc.getLaguByAlbums(id));
    }
    
    @GetMapping(path = "/api/listlagubygenrejson/{idg}")
    public ResponseEntity<List<Lagu>> findByGenre(@PathVariable("idg") Integer id){
        return ResponseEntity.ok().body(koneksiJdbc.getLaguByGenre(id));
    }
}
