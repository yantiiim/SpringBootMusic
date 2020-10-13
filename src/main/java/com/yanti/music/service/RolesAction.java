package com.yanti.music.service;

import java.util.List;

import com.yanti.music.impl.KoneksiJdbc;
import com.yanti.music.model.Roles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class RolesAction {
    
    @Autowired
    private KoneksiJdbc koneksiJdbc;

    @GetMapping(path= "/api/listrolesjson")
    public ResponseEntity<List<Roles>> listRolesJson() {
        return ResponseEntity.ok().body(koneksiJdbc.getRoles());
    }
    
}
