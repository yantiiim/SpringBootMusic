/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yanti.music.ui;

import com.yanti.music.impl.KoneksiJdbc;
import com.yanti.music.model.AkunAdmin;
import com.yanti.music.model.StatusLogin;
import com.yanti.music.model.UserAdmin;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 *
 * @author yanti
 */
@Controller
public class UserAdminAction {
    @Autowired
    private KoneksiJdbc koneksiJdbc;
    
//    @PostMapping("/api/login")
//    public ResponseEntity<StatusLogin> login(@RequestBody UserAdmin userAdmin) throws Exception {
//        System.out.println("masuk");
//        StatusLogin statusLogin = new StatusLogin();
//        if (userAdmin != null) {
//            String username = userAdmin.getUsername();
//           Optional<UserAdmin>useradmindb = koneksiJdbc.getUserAdminById(username);
//            if (useradmindb.isPresent() && Objects.equals(username, useradmindb.get().getUsername())) {
//                String password = userAdmin.getPassword();
//                if (Objects.equals(password,useradmindb.get().getPassword())) {
//                    System.out.println(useradmindb.get().getUsername());
//                    statusLogin.setIsValid(true);
//                    String token =UUID.randomUUID().toString();
//                    Map<String, Object> paramlogin = new HashMap<>();
//                    paramlogin.put("username", username);
//                    paramlogin.put("token", token);
//                } else {
//                    statusLogin.setIsValid(false);
//                    statusLogin.setToken(null);
//                }
//            }
//        } else {
//            statusLogin.setIsValid(false);
//            statusLogin.setToken(null);
//        }
//        return ResponseEntity.ok().body(statusLogin);
//    }
    
    @PostMapping("/api/login")
    public ResponseEntity<StatusLogin> login(@RequestBody UserAdmin userAdmin, HttpServletRequest request) throws Exception {
        StatusLogin statusLogin = new StatusLogin();
        if (userAdmin != null) {
            String username = userAdmin.getUsername();
            Optional<AkunAdmin> useradmindb = koneksiJdbc.getAkunAdminById(username);
            if (useradmindb.isPresent() && Objects.equals(username, useradmindb.get().getUsername())) {
                String password = userAdmin.getPassword();
                if (Objects.equals(password,useradmindb.get().getKeyword())) {
                    statusLogin.setIsValid(true);
                    String token = UUID.randomUUID().toString();
                    Map<String, Object> paramlogin = new HashMap<>();
                    paramlogin.put("username", username);
                    paramlogin.put("token", token);
                    koneksiJdbc.insertUserAdmin(paramlogin);
                    statusLogin.setToken(token);
                } else {
                    statusLogin.setIsValid(false);
                    statusLogin.setToken(null);
                }
            }
        } else {
            statusLogin.setIsValid(false);
            statusLogin.setToken(null);
        }
        return ResponseEntity.ok().body(statusLogin);
    }
    
    @PostMapping("/api/ceklogin")
    public ResponseEntity<StatusLogin> cekUserAdminValid(@RequestBody UserAdmin userAdmin) {
        System.out.println(userAdmin.getUsername());
        return ResponseEntity.ok().body(koneksiJdbc.cekUserAdminValid(userAdmin));
    }
}
