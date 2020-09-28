/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yanti.music.ui;

import com.yanti.music.impl.KoneksiJdbc;
import com.yanti.music.model.Albums;
import com.yanti.music.model.Artis;
import com.yanti.music.model.DataTablesRequest;
import com.yanti.music.model.DataTablesResponse;
import com.yanti.music.model.Genre;
import com.yanti.music.model.LablesRekaman;
import com.yanti.music.model.Lagu;
import com.yanti.music.service.AlbumsService;
import com.yanti.music.service.ArtisService;
import com.yanti.music.service.GenreService;
import com.yanti.music.service.LablesRekamanService;
import com.yanti.music.service.LaguService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author yanti
 */
@Controller
public class BerandaAction {
    @Autowired
    private KoneksiJdbc koneksiJdbc;
    
    @Autowired
    private LablesRekamanService lablesRekamanService;
    
    @Autowired
    private ArtisService artisService;
    
    @Autowired
    private GenreService genreService;
    
    @Autowired
    private AlbumsService albumsService;
    
    @Autowired
    private LaguService laguService;
    
    //lables
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
    
    //artis
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
    
    //Genre
    @PostMapping(path="/api/listgenredatajson")
    public ResponseEntity<DataTablesResponse<Genre>> listGenreDataTable(@RequestBody DataTablesRequest dataRequest){
        return ResponseEntity.ok().body(genreService.listGenreDataTable(dataRequest));
    }
    
    @PostMapping("/api/savegenrejson")
    public ResponseEntity <Map<String,Object>> savegenrejson(@RequestBody Genre genre){
        Map<String, Object> status = new HashMap<>();
        koneksiJdbc.insertOrUpdateGenre(genre);
        status.put("pesan", "Simpan Berhasil");
        return ResponseEntity.ok().body(status);
    }
    
    @GetMapping(path= "/api/listgenrejson")
    public ResponseEntity<List<Genre>> listGenreCariJson(){
        return ResponseEntity.ok().body(koneksiJdbc.getGenre());
    }
    
    //Albums
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
    
    //Lagu
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
}
