package com.yanti.music.impl;

import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import com.yanti.music.model.Artis;
import com.yanti.music.model.DataTablesRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcArtis {
    
    @Autowired
    private DataSource dataSource;
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    //Artis
    public List<Artis> getArtis(){
        
        String SQL = "SELECT id_artis as idArtis, nama_artis as namaArtis, foto, "
                   + "url_website as urlWebsite, keterangan FROM artis";
        List<Artis> art = jdbcTemplate.query(SQL, BeanPropertyRowMapper.newInstance(Artis.class));
        return art;
    }
    
    public Optional<Artis> getArtisById(int id){
        String SQL = "SELECT nama_artis as namaArtis, foto, url_website as urlWebsite, "
                   + "keterangan FROM artis where id_artis = ?";
        Object param[] = {id};
        try {
            return Optional.of (jdbcTemplate.queryForObject(SQL, param, BeanPropertyRowMapper.newInstance(Artis.class)));
        }catch (Exception e) {
            return Optional.empty();
        }
    }
    
    public void insertArtis(Artis artis){
        String sql = "insert into artis (id_artis,nama_artis,foto,url_website,keterangan) values (?,?,?,?,?)";
        Object param[] = {artis.getIdArtis(),artis.getNamaArtis(),artis.getFoto(),
                            artis.getUrlWebsite(),artis.getKeterangan()};
        jdbcTemplate.update(sql, param);
    }
    
    public void updateArtis(Artis artis){
        String sql = "UPDATE artis SET nama_artis=?, foto=?, url_website=?, keterangan=? WHERE id_artis=?";
        Object param[] = {artis.getNamaArtis(),artis.getFoto(),artis.getUrlWebsite(),
                            artis.getKeterangan(),artis.getIdArtis()};
        jdbcTemplate.update(sql, param);
    }
    
    public void insertOrUpdateArtis (Artis artis){
        Optional<Artis> data=getArtisById(artis.getIdArtis());
        if(data.isPresent()){
            updateArtis(artis);
        }else{
            insertArtis(artis);
        }
    }
    
    public void deleteArtis(Integer id){
        String SQL = "delete from artis where id_artis=?";
        Object parameters[] = {id};
        
        jdbcTemplate.update(SQL, parameters);
    }
    
    //Data Table Artis
    public Integer getBanyakArtis(DataTablesRequest req) {
        String query = "SELECT count(id_artis) as banyak FROM artis";
        if(!req.getExtraParam().isEmpty()){
            String nama_artis = (String) req.getExtraParam().get("namaArtis");
            query = " SELECT count(id_artis) as banyak FROM artis where nama_artis like concat('%',?,'%')";
            return jdbcTemplate.queryForObject(query, Integer.class, nama_artis);
        }else{
            return this.jdbcTemplate.queryForObject(query, null, Integer.class);
        }
        
    }
    
    public List<Artis> getListArtis(DataTablesRequest req) {
        String SQL = "SELECT id_artis as idArtis, nama_artis as namaArtis, foto, url_website as urlWebsite, "
                + "keterangan FROM artis "
                + "order by "+(req.getSortCol()+1)+"  "+req.getSortDir() +" limit ? offset ?";
        if(!req.getExtraParam().isEmpty()){
            String namaArtis = (String) req.getExtraParam().get("namaArtis");
            SQL = "SELECT id_artis as idArtis, nama_artis as namaArtis, foto, url_website as urlWebsite, "
                + "keterangan FROM artis "
                + "where nama_artis like concat('%',?,'%')"
                + " order by "+(req.getSortCol()+1)+"  "+req.getSortDir() +" limit ? offset ?";
            return jdbcTemplate.query(SQL, BeanPropertyRowMapper.newInstance(Artis.class), namaArtis, req.getLength(), req.getStart());
        }else{
            return jdbcTemplate.query(SQL, BeanPropertyRowMapper.newInstance(Artis.class), req.getLength(), req.getStart());
        }
        
    }
}
