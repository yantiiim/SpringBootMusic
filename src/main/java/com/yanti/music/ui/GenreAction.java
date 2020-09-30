/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yanti.music.ui;

import com.yanti.music.impl.KoneksiJdbc;
import com.yanti.music.model.DataTablesRequest;
import com.yanti.music.model.DataTablesResponse;
import com.yanti.music.model.Genre;
import com.yanti.music.service.GenreService;
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
public class GenreAction {
    @Autowired
    private KoneksiJdbc koneksiJdbc;
    
    @Autowired
    private GenreService genreService;
    
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
    
    @DeleteMapping("/api/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id){
        try {
            genreService.deleteById(id);
            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch (DataAccessException e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
