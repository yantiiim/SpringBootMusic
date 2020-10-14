package com.yanti.music.ui;

import java.util.List;

import com.yanti.music.impl.JdbcRoles;
import com.yanti.music.model.Roles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
public class RolesAction {
    
    @Autowired
    private JdbcRoles koneksiJdbc;

    @GetMapping(path= "/api/listrolesjson")
    public ResponseEntity<List<Roles>> listRolesJson() {
        return ResponseEntity.ok().body(koneksiJdbc.getRoles());
    }

    @PostMapping("/api/checkingsuperadmin")
    public ResponseEntity<Boolean>cekUserLoginValid(@RequestBody String idUser){
        return ResponseEntity.ok().body(koneksiJdbc.checkingSuperAdmin(idUser));
    }
    
}
