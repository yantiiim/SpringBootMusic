package com.yanti.music.ui;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.yanti.music.dto.AkunAdminDto;
import com.yanti.music.impl.KoneksiJdbc;
import com.yanti.music.model.AkunAdmin;
import com.yanti.music.model.DataTablesRequest;
import com.yanti.music.model.DataTablesResponse;
import com.yanti.music.service.AkunAdminService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class AkunAdminAction {

    @Autowired
    private AkunAdminService akunAdminService;

    @Autowired KoneksiJdbc koneksiJdbc;

    @PostMapping("/api/registerakun")
    public ResponseEntity <?> registerAkun(@RequestBody AkunAdminDto.New akunAdminDto) throws SQLException{
        Map<String, Object> status = new HashMap<>();
        akunAdminService.saveUser(akunAdminDto);
        status.put("pesan", "Simpan Berhasil");
        return new ResponseEntity<>(akunAdminDto, HttpStatus.CREATED);
    }

    @PostMapping("/api/registeradmin")
    public ResponseEntity <?> registerAdmin(@RequestBody AkunAdminDto.New akunAdminDto) throws SQLException{
        Map<String, Object> status = new HashMap<>();
        akunAdminService.saveAdmin(akunAdminDto);
        status.put("pesan", "Simpan Berhasil");
        return new ResponseEntity<>(akunAdminDto, HttpStatus.CREATED);
    }

    @GetMapping(path="/api/listakunjson")
    public ResponseEntity<List<AkunAdmin>> listAkunCari( ){
        return ResponseEntity.ok().body(koneksiJdbc.getAkun());
    }

    @GetMapping(path="/api/listakunjson/{id}")
    public ResponseEntity<AkunAdmin> listAkunByIdJson(@PathVariable("id") int id ){
        Optional<AkunAdmin> hasil = koneksiJdbc.getAkunById(id);
        if(hasil.isPresent()){
            return ResponseEntity.ok().body(hasil.get());
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PostMapping(path="/api/listakundatajson")
    public ResponseEntity<DataTablesResponse<AkunAdmin>> listAkunDataTable(@RequestBody DataTablesRequest dataRequest){
        return ResponseEntity.ok().body(akunAdminService.listAkunDataTable(dataRequest));
    }
}
