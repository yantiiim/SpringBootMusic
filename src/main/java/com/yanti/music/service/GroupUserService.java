package com.yanti.music.service;

import java.util.List;

import com.yanti.music.dto.GroupUserDto;
import com.yanti.music.impl.JdbcRoles;
import com.yanti.music.model.GroupUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class GroupUserService {
    
    @Autowired
    private JdbcRoles koneksiJdbc;

    public List<GroupUser> findAll() throws EmptyResultDataAccessException{
        return koneksiJdbc.getGroupUser();
    }

    public void deleteById(GroupUserDto.Information value) throws DataAccessException {
        koneksiJdbc.deleteById(value);
    }
}
