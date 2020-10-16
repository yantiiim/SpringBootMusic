package com.yanti.music.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import com.yanti.music.dto.GroupUserDto;
import com.yanti.music.model.GroupUser;
import com.yanti.music.model.Roles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcRoles {
    
    @Autowired
    private DataSource dataSource;
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate template;

    //roles

    public List<Roles> getRoles(){
        String sql = "select id_role as idRole, role_name as namaRole from roles";
        List<Roles> ro = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Roles.class));
        return ro;
    }

    public GroupUser checkingSuperAdmin(String idUser){
        String baseQuery="select id , id_group as idGroup, id_user as idUser from group_user " +
                "where id_user = ? and id_group = 1";
        GroupUser groupUsers = new GroupUser();
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
                groupUsers.setId(hasil.get().getId());
                groupUsers.setIdUser(hasil.get().getIdUser());
                groupUsers.setIsCheck(isCheck);
                return groupUsers;
            } else{
                isCheck = false;
                groupUsers.setIsCheck(isCheck);
            }
        } catch (Exception e){
            e.printStackTrace();
            isCheck = false;
            groupUsers.setIsCheck(isCheck);
        }
        return groupUsers;
    }

    public void deleteById(GroupUserDto.Information value) throws DataAccessException {
        String baseQuery = "delete from group_user where id_user = :idUser and id_group = :idGroup";

        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("idUser", value.getIdUser());
        parameterSource.addValue("idGroup", value.getIdGruop());

        template.update(baseQuery, parameterSource);
    }

    public List<GroupUser> getGroupUser() throws EmptyResultDataAccessException {
        String baseQuery= "select g.id as id, a.username as username, r.role_name as roleName " +
                "from group_user g join akun_admin a on (g.id_user = a.id) join roles r on (g.id_group = r.id_role)";

        MapSqlParameterSource params = new MapSqlParameterSource();

        return template.query(baseQuery, params, new RowMapper<GroupUser>() {
            @Override
            public GroupUser mapRow(ResultSet resultSet, int i) throws SQLException {
                GroupUser arsip = new GroupUser();
                arsip.setId(resultSet.getString("id"));
                arsip.setUserName(resultSet.getString("username"));
                arsip.setRoleName(resultSet.getString("roleName"));

                return arsip;
            }
        });
    }
}
