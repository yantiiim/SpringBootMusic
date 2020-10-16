package com.yanti.music.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import javax.sql.DataSource;

import com.yanti.music.dto.AkunAdminDto;
import com.yanti.music.model.AkunAdmin;
import com.yanti.music.model.DataTablesRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcAkunAdmin {
    
    @Autowired
    private DataSource dataSource;
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    //Akun Admin

    public void registerAkun(AkunAdminDto.New akunAdminDto) throws SQLException {
        String sql = "insert into akun_admin (id,username,keyword) values (?,?,?)";
        Object param[] = {akunAdminDto.getId(), akunAdminDto.getKeyword(), akunAdminDto.getKeyword()};
        jdbcTemplate.update(sql, param);
    }

    public void registerAdmin(AkunAdminDto.New akunAdminDto) throws SQLException {
        String sql = "insert into akun_admin (id,username,keyword) values (?,?,?)";
        Object param[] = {akunAdminDto.getId(), akunAdminDto.getKeyword(), akunAdminDto.getKeyword()};
        jdbcTemplate.update(sql, param);
    }

    public void insertGroupUser(Map<String, Object> param) {
        String sql="insert into group_user (id,id_user,id_group) values (?,?,?)";
        Object parameter[] = {param.get("id"), param.get("idUser"), param.get("idGroup")};
        jdbcTemplate.update(sql, parameter);
    }

    public List<String> getRolesByUserName(String userName){
        String query = "select r.role_name from group_user g join roles r on (g.id_group = r.id_role) " +
                " join akun_admin a on (g.id_user = a.id) where a.username = ?";

        Object param[] = {userName};

        List<String> prop = jdbcTemplate.query(query, (rs, rownum) ->{
            return rs.getString("role_name");
        }, param);

        return prop;
    }

    public boolean tambahRoleAdmin(AkunAdmin akunAdmin){
        String baseQuery = "select id, username from akun_admin where username = ?";
        boolean isTambah = false;
        try{
            Optional<AkunAdmin> hasil = Optional.of(jdbcTemplate.queryForObject(baseQuery, (rs, rownum) ->{
                AkunAdmin akunAdmin1 = new AkunAdmin();
                akunAdmin1.setId(rs.getString("id"));
                akunAdmin1.setUsername(rs.getString("user_name"));
                return akunAdmin1;
            },akunAdmin.getUsername()));
            if(Objects.equals(akunAdmin.getUsername(),hasil.get().getUsername())){
                Map<String, Object> param = new HashMap<>();
                param.put("id", UUID.randomUUID().toString());
                param.put("idUser", hasil.get().getId());
                param.put("idGroup", 2);
                insertGroupUser(param);
                isTambah = true;
                return isTambah;
            } else {
                isTambah = false;
            }
        } catch (Exception e){
            e.printStackTrace();
            isTambah = false;
        }
        return isTambah;
    }

    public boolean tambahRoleUser(AkunAdmin akunAdmin){
        String baseQuery = "select id, username from akun_admin where username = ?";
        boolean isTambah = false;
        try{
            Optional<AkunAdmin> hasil = Optional.of(jdbcTemplate.queryForObject(baseQuery, (rs, rownum) ->{
                AkunAdmin akunAdmin1 = new AkunAdmin();
                akunAdmin1.setId(rs.getString("id"));
                akunAdmin1.setUsername(rs.getString("user_name"));
                return akunAdmin1;
            },akunAdmin.getUsername()));
            if(Objects.equals(akunAdmin.getUsername(),hasil.get().getUsername())){
                Map<String, Object> param = new HashMap<>();
                param.put("id", UUID.randomUUID().toString());
                param.put("idUser", hasil.get().getId());
                param.put("idGroup", 3);
                insertGroupUser(param);
                isTambah = true;
                return isTambah;
            } else {
                isTambah = false;
            }
        } catch (Exception e){
            e.printStackTrace();
            isTambah = false;
        }
        return isTambah;
    }

    public List<AkunAdmin> getAkun(){
        
        String SQL = "SELECT id,username FROM akun_admin";
        List<AkunAdmin> prop = jdbcTemplate.query(SQL, BeanPropertyRowMapper.newInstance(AkunAdmin.class));
        return prop;
    }

    public List<AkunAdmin> getAkunById(String id) throws EmptyResultDataAccessException {
        String baseQuery = "select id as id, username as username from akun_admin where id = ?";
        Object[] param = {id};

        return jdbcTemplate.query(baseQuery, param,  (rs, rowNUm) -> {
            AkunAdmin akunAdmin = new AkunAdmin();
            akunAdmin.setUsername(rs.getString("username"));
            akunAdmin.setId(rs.getString("id"));
            return akunAdmin;
        });
    }

    public List<AkunAdmin> getListAkun(DataTablesRequest req) {
        String SQL = "SELECT id,username FROM akun_admin "
                + "order by "+(req.getSortCol()+1)+"  "+req.getSortDir() +" limit ? offset ?";
        if(!req.getExtraParam().isEmpty()){
            String username = (String) req.getExtraParam().get("username");
            SQL = "SELECT id,username FROM akun_admin where username like concat('%',?,'%')"
                + " order by "+(req.getSortCol()+1)+"  "+req.getSortDir() +" limit ? offset ?";
            return jdbcTemplate.query(SQL, BeanPropertyRowMapper.newInstance(AkunAdmin.class), username, req.getLength(), req.getStart());
        }else{
            return jdbcTemplate.query(SQL, BeanPropertyRowMapper.newInstance(AkunAdmin.class), req.getLength(), req.getStart());
        }
        
    }

    public Integer getBanyakAkun(DataTablesRequest req) {
        String query = "SELECT count(id) as banyak FROM akun_admin";
        if(!req.getExtraParam().isEmpty()){
            String username = (String) req.getExtraParam().get("username");
            query = " SELECT count(id) as banyak FROM akun_admin where username like concat('%',?,'%')";
            return jdbcTemplate.queryForObject(query, Integer.class, username);
        }else{
            return this.jdbcTemplate.queryForObject(query, null, Integer.class);
        }
        
    }
}
