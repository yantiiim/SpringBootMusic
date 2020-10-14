package com.yanti.music.impl;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import javax.sql.DataSource;

import com.yanti.music.model.AkunAdmin;
import com.yanti.music.model.StatusLogin;
import com.yanti.music.model.UserAdmin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcLogin {
    
    @Autowired
    private DataSource dataSource;
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private JdbcAkunAdmin koneksiJdbc;

    //Login
    
    public Optional<AkunAdmin> getAkunAdminById(String akunAdmin) {
        String SQL = "select username, keyword from akun_admin where username = ? ";
        try {
            return Optional.of(jdbcTemplate.queryForObject(SQL, (rs, rownum) -> {
                AkunAdmin kab = new AkunAdmin();
                kab.setUsername(rs.getString("username"));
                kab.setKeyword(rs.getString("keyword"));
                return kab;
            }, akunAdmin));
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }
    
    public StatusLogin cekUserAdminValid(UserAdmin userAdmin) {
        String baseQuery = "select a.user_name from user_admin a inner join akun_admin b on b.username = a.user_name where tokenkey = ? ";
        StatusLogin sLogin = new StatusLogin();
        try {
            boolean isValid = false;
            Optional<UserAdmin> hasil = Optional.of(jdbcTemplate.queryForObject(baseQuery, (rs, rownum) -> {
                UserAdmin kab = new UserAdmin();
                kab.setUsername(rs.getString("user_name"));
                return kab;
            }, userAdmin.getToken()));
            if(hasil.isPresent()){
                if(Objects.equals(userAdmin.getUsername(), hasil.get().getUsername())) {
                    List<String>rolesName= koneksiJdbc.getRolesByUserName(hasil.get().getUsername());
                    sLogin.setIsValid(true);
                    sLogin.setRoles(rolesName);
                    sLogin.setToken(userAdmin.getToken());
                    System.out.println(hasil.get().getUsername());
                } else {
                    sLogin.setIsValid(false);
                }
            }
        } catch (Exception e) {
            sLogin.setIsValid(false);
            e.printStackTrace();
        }
        return sLogin;
    }

    public List<String> getRolesById(Integer id){
        String query = "select role_name from roles where role_id = ?";

        Object param[] = {id};

        List<String> prop = jdbcTemplate.query(query, (rs, rownum) ->{
            return rs.getString("role_name");
        }, param);

        return prop;
    }
    
    public void insertUserAdmin(Map<String, Object> param) {
        String sql = "insert into user_admin (user_name, tokenkey) values (?,?);";
        
        Object parameter[] = {param.get("username"),param.get("token")};
        jdbcTemplate.update(sql, parameter);
    }
}
