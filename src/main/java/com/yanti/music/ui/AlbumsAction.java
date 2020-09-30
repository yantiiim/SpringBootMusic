/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yanti.music.ui;

import com.yanti.music.impl.KoneksiJdbc;
import com.yanti.music.model.Albums;
import com.yanti.music.model.DataTablesRequest;
import com.yanti.music.model.DataTablesResponse;
import com.yanti.music.service.AlbumsService;
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
public class AlbumsAction {
    @Autowired
    private KoneksiJdbc koneksiJdbc;
    
    @Autowired
    private AlbumsService albumsService;
    
    @PostMapping(path="/api/listalbumsdatajson")
    public ResponseEntity<DataTablesResponse<Albums>> listAlbumsDataTable(@RequestBody DataTablesRequest dataRequest){
        return ResponseEntity.ok().body(albumsService.listAlbumsDataTable(dataRequest));
    }
    
    @PostMapping("/api/savealbumsjson")
    public ResponseEntity <Map<String,Object>> savealbumsjson(@RequestBody Albums albums){
        Map<String, Object> status = new HashMap<>();
        koneksiJdbc.insertOrUpdateAlbums(albums);
        status.put("pesan", "Simpan Berhasil");
        return ResponseEntity.ok().body(status);
    }
    
    @PostMapping("/api/uploadalbums")
    public ResponseEntity<Map<String,Object>> uploadFileAlbums(@RequestParam("file") MultipartFile file) {
        String message = null;
        Map<String,Object> pesan = new HashMap<>();
        try {
            String namaFile = albumsService.save(file);
            
            pesan.put("pesan", "Uploaded the file succesfully: " + namaFile);
            return ResponseEntity.status(HttpStatus.OK).body(pesan);
        }catch (Exception e) {
            pesan.put("pesan", "Could not upload the file: " + file.getOriginalFilename() + "!");
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(pesan);
        }
    }
    
    @GetMapping(path= "/api/listalbumsjson")
    public ResponseEntity<List<Albums>> listAlbumsCariJson(){
        return ResponseEntity.ok().body(koneksiJdbc.getAlbums());
    }
    
    @DeleteMapping("/api/deletealbums/{id}")
    public ResponseEntity<?> deleteAlbums(@PathVariable("id") Integer id){
        try {
            albumsService.deleteById(id);
            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch (DataAccessException e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    
    @GetMapping(path = "/api/listalbumsjson/{id}")
    public ResponseEntity<List<Albums>> findByArtis(@PathVariable("id") Integer id){
        return ResponseEntity.ok().body(koneksiJdbc.getAlbumsByArtis(id));
    }
}
