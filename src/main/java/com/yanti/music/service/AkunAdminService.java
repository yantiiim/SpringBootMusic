package com.yanti.music.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.yanti.music.dto.AkunAdminDto;
import com.yanti.music.impl.KoneksiJdbc;
import com.yanti.music.model.AkunAdmin;
import com.yanti.music.model.DataTablesRequest;
import com.yanti.music.model.DataTablesResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AkunAdminService {
    
    @Autowired
    private KoneksiJdbc koneksiJdbc;

    public void saveAdmin(AkunAdminDto.New akunAdminDto) throws SQLException {
        akunAdminDto.setId(UUID.randomUUID().toString());
        Map<String, Object> paramRegAcc = new HashMap<>();
        paramRegAcc.put("id", UUID.randomUUID().toString());
        paramRegAcc.put("idUser", akunAdminDto.getId());
        paramRegAcc.put("idGroup", 2);
        koneksiJdbc.insertGroupUser(paramRegAcc);
        koneksiJdbc.registerAkun(akunAdminDto);
    }

    public void saveUser(AkunAdminDto.New akunAdminDto) throws SQLException {
        akunAdminDto.setId(UUID.randomUUID().toString());
        Map<String, Object> paramRegAcc = new HashMap<>();
        paramRegAcc.put("id", UUID.randomUUID().toString());
        paramRegAcc.put("idUser", akunAdminDto.getId());
        paramRegAcc.put("idGroup", 3);
        koneksiJdbc.insertGroupUser(paramRegAcc);
        koneksiJdbc.registerAkun(akunAdminDto);
    }

    public DataTablesResponse<AkunAdmin> listAkunDataTable (DataTablesRequest req) {
        DataTablesResponse dataTableRespon = new DataTablesResponse();
        dataTableRespon.setData(koneksiJdbc.getListAkun(req));
        Integer total = koneksiJdbc.getBanyakAkun(req);
        dataTableRespon.setRecordsFiltered(total);
        dataTableRespon.setRecordsTotal(total);
        dataTableRespon.setDraw(req.getDraw());
        return dataTableRespon;
    }
}
