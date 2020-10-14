package com.yanti.music.impl;

import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import com.yanti.music.model.DataTablesRequest;
import com.yanti.music.model.LablesRekaman;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcLables {

    @Autowired
    private DataSource dataSource;
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    //Label
    public List<LablesRekaman> getLablesRekaman(){
        
        String SQL = "SELECT id_label as idLabel, nama_labels as namaLabels, alamat, "
                   + "no_telp as noTelp, contact_person as contactPerson, url_website as urlWebsite FROM lables_rekaman";
        List<LablesRekaman> lbls = jdbcTemplate.query(SQL, BeanPropertyRowMapper.newInstance(LablesRekaman.class));
        return lbls;
    }
    
    public Optional<LablesRekaman> getLablesRekamanById(int id){
        String SQL = "SELECT nama_labels as namaLabels, alamat, no_telp as noTelp, "
                   + "contact_person as contactPerson, url_website as urlWebsite FROM lables_rekaman where id_label = ?";
        Object param[] = {id};
        try {
            return Optional.of (jdbcTemplate.queryForObject(SQL, param, BeanPropertyRowMapper.newInstance(LablesRekaman.class)));
        }catch (Exception e) {
            return Optional.empty();
        }
    }
    
    public void insertLables(LablesRekaman lablesRekaman){
        String sql = "insert into lables_rekaman (id_label,nama_labels,alamat,no_telp,contact_person,url_website) values (?,?,?,?,?,?)";
        Object param[] = {lablesRekaman.getIdLabel(),lablesRekaman.getNamaLabels(),lablesRekaman.getAlamat(),
                            lablesRekaman.getNoTelp(),lablesRekaman.getContactPerson(),lablesRekaman.getUrlWebsite()};
        jdbcTemplate.update(sql, param);
    }
    
    public void updateLables(LablesRekaman lablesRekaman){
        String sql = "UPDATE lables_rekaman SET nama_labels=?, alamat=?, no_telp=?, contact_person=?, url_website=? WHERE id_label=?";
        Object param[] = {lablesRekaman.getNamaLabels(),lablesRekaman.getAlamat(),lablesRekaman.getNoTelp(),
                            lablesRekaman.getContactPerson(),lablesRekaman.getUrlWebsite(),lablesRekaman.getIdLabel()};
        jdbcTemplate.update(sql, param);
    }
    
    public void insertOrUpdateLables (LablesRekaman lablesRekaman){
        Optional<LablesRekaman> data=getLablesRekamanById(lablesRekaman.getIdLabel());
        if(data.isPresent()){
            updateLables(lablesRekaman);
        }else{
            insertLables(lablesRekaman);
        }
    }
    
    public void deleteLables(Integer id){
        String SQL = "delete from lables_rekaman where id_label=?";
        Object parameters[] = {id};
        
        jdbcTemplate.update(SQL, parameters);
    }
    
    //Data Table Lables
    public Integer getBanyakLablesRekaman(DataTablesRequest req) {
        String query = "SELECT count(id_label) as banyak FROM lables_rekaman";
        if(!req.getExtraParam().isEmpty()){
            String nama_labels = (String) req.getExtraParam().get("namaLabels");
            query = " SELECT count(id_label) as banyak FROM lables_rekaman where nama_labels like concat('%',?,'%')";
            return jdbcTemplate.queryForObject(query, Integer.class, nama_labels);
        }else{
            return this.jdbcTemplate.queryForObject(query, null, Integer.class);
        }
        
    }
    
    public List<LablesRekaman> getListLablesRekaman(DataTablesRequest req) {
        String SQL = "SELECT id_label as idLabel, nama_labels as namaLabels, alamat, no_telp as noTelp, "
                + "contact_person as contactPerson, url_website as urlWebsite FROM lables_rekaman "
                + "order by "+(req.getSortCol()+1)+"  "+req.getSortDir() +" limit ? offset ?";
        if(!req.getExtraParam().isEmpty()){
            String namaLabels = (String) req.getExtraParam().get("namaLabels");
            SQL = "SELECT id_label as idLabel, nama_labels as namaLabels, alamat, no_telp as noTelp, "
                + "contact_person as contactPerson, url_website as urlWebsite FROM lables_rekaman "
                + "where nama_labels like concat('%',?,'%')"
                + " order by "+(req.getSortCol()+1)+"  "+req.getSortDir() +" limit ? offset ?";
            return jdbcTemplate.query(SQL, BeanPropertyRowMapper.newInstance(LablesRekaman.class), namaLabels, req.getLength(), req.getStart());
        }else{
            return jdbcTemplate.query(SQL, BeanPropertyRowMapper.newInstance(LablesRekaman.class), req.getLength(), req.getStart());
        }
        
    }

}
