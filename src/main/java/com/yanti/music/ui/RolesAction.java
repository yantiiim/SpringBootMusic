package com.yanti.music.ui;

import java.util.List;

import com.yanti.music.dto.GroupUserDto;
import com.yanti.music.impl.JdbcRoles;
import com.yanti.music.model.GroupUser;
import com.yanti.music.model.Roles;
import com.yanti.music.service.GroupUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
public class RolesAction {
    
    @Autowired
    private JdbcRoles koneksiJdbc;

    @Autowired
    private GroupUserService groupUserService;

    @GetMapping(path= "/api/listrolesjson")
    public ResponseEntity<List<Roles>> listRolesJson() {
        return ResponseEntity.ok().body(koneksiJdbc.getRoles());
    }

    @PostMapping(path= "/api/checkingsuperadmin")
    public ResponseEntity<GroupUser>checkingSuperAdmin(@RequestBody String idUser){
        return ResponseEntity.ok().body(koneksiJdbc.checkingSuperAdmin(idUser));
    }

    @DeleteMapping("/api/deleterole")
    public ResponseEntity<?> delete(@RequestBody GroupUserDto.Information value){
        try{
            groupUserService.deleteById(value);
            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch (DataAccessException e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    
}
