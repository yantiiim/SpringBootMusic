/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yanti.music.service;

import com.yanti.music.impl.KoneksiJdbc;
import com.yanti.music.model.DataTablesRequest;
import com.yanti.music.model.DataTablesResponse;
import com.yanti.music.model.LablesRekaman;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author yanti
 */
@Service
public class LablesRekamanService {
    @Autowired
        private KoneksiJdbc koneksiJdbc;
    
//        @Transactional(readOnly = false)
        public DataTablesResponse<LablesRekaman> listLablesRekamanDataTable(DataTablesRequest req) {
            DataTablesResponse dataTableRespon  = new DataTablesResponse();
            dataTableRespon.setData(koneksiJdbc.getListLablesRekaman(req));
            Integer total = koneksiJdbc.getBanyakLablesRekaman(req);
            dataTableRespon.setRecordsFiltered(total);
            dataTableRespon.setRecordsTotal(total);
            dataTableRespon.setDraw(req.getDraw());
            return dataTableRespon;
        }
        
        public void deleteById(Integer id) throws DataAccessException{
        koneksiJdbc.deleteLables(id);
    }
}
