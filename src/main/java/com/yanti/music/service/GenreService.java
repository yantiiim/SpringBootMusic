/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yanti.music.service;

import com.yanti.music.impl.KoneksiJdbc;
import com.yanti.music.model.DataTablesRequest;
import com.yanti.music.model.DataTablesResponse;
import com.yanti.music.model.Genre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 *
 * @author yanti
 */
@Service
public class GenreService {
    @Autowired
    private KoneksiJdbc koneksiJdbc;
    
    public DataTablesResponse<Genre> listGenreDataTable(DataTablesRequest req) {
            DataTablesResponse dataTableRespon  = new DataTablesResponse();
            dataTableRespon.setData(koneksiJdbc.getListGenre(req));
            Integer total = koneksiJdbc.getBanyakGenre(req);
            dataTableRespon.setRecordsFiltered(total);
            dataTableRespon.setRecordsTotal(total);
            dataTableRespon.setDraw(req.getDraw());
            return dataTableRespon;
        }
}
