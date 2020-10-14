package com.yanti.music.impl;

import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import com.yanti.music.model.GroupUser;
import com.yanti.music.model.Roles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcRoles {
    
    @Autowired
    private DataSource dataSource;
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    //roles

    public List<Roles> getRoles(){
        String sql = "select id_role as idRole, role_name as namaRole from roles";
        List<Roles> ro = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Roles.class));
        return ro;
    }

    public boolean checkingSuperAdmin(String idUser){
        String baseQuery="select id , id_group as idGroup, id_user as idUser from group_user " +
                "where id_user = ? and id_group = 1";
        boolean isCheck = false;
        try{
            Optional<GroupUser> hasil = Optional.of(jdbcTemplate.queryForObject(baseQuery, (rs, rownum) ->{
                GroupUser groupUser = new GroupUser();
                groupUser.setId(rs.getString("id"));
                groupUser.setIdGroup(rs.getInt("idGroup"));
                groupUser.setIdUser(rs.getString("idUser"));
                return groupUser;
            },idUser));
            if (hasil != null){
                isCheck = true;
                return isCheck;
            } else{
                isCheck = false;
            }
        } catch (Exception e){
            e.printStackTrace();
            isCheck = false;
        }
        System.out.println(isCheck);
        return isCheck;
    }
}
