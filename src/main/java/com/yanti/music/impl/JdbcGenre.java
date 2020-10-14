package com.yanti.music.impl;

import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import com.yanti.music.model.DataTablesRequest;
import com.yanti.music.model.Genre;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcGenre {
    
    @Autowired
    private DataSource dataSource;
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    //Genre
    public List<Genre> getGenre(){
        
        String SQL = "SELECT id_genre as idGenre, nama_genre as namaGenre FROM genre";
        List<Genre> gen = jdbcTemplate.query(SQL, BeanPropertyRowMapper.newInstance(Genre.class));
        return gen;
    }
    
    public Optional<Genre> getGenreById(int id){
        String SQL = "SELECT id_genre as idGenre, nama_genre as namaGenre FROM genre where id_genre = ?";
        Object param[] = {id};
        try {
            return Optional.of (jdbcTemplate.queryForObject(SQL, param, BeanPropertyRowMapper.newInstance(Genre.class)));
        }catch (Exception e) {
            return Optional.empty();
        }
    }
    
    public void insertGenre(Genre genre){
        String sql = "insert into genre (id_genre,nama_genre) values (?,?)";
        Object param[] = {genre.getIdGenre(),genre.getNamaGenre()};
        jdbcTemplate.update(sql, param);
    }
    
    public void updateGenre(Genre genre){
        String sql = "UPDATE genre SET nama_genre=? WHERE id_genre=?";
        Object param[] = {genre.getNamaGenre(),genre.getIdGenre()};
        jdbcTemplate.update(sql, param);
    }
    
    public void insertOrUpdateGenre (Genre genre){
        Optional<Genre> data=getGenreById(genre.getIdGenre());
        if(data.isPresent()){
            updateGenre(genre);
        }else{
            insertGenre(genre);
        }
    }
    
    public void deleteGenre(Integer id){
        String SQL = "delete from genre where id_genre=?";
        Object parameters[] = {id};
        
        jdbcTemplate.update(SQL, parameters);
    }
    
    //Data Table Genre
    public Integer getBanyakGenre(DataTablesRequest req) {
        String query = "SELECT count(id_genre) as banyak FROM genre";
        if(!req.getExtraParam().isEmpty()){
            String nama_genre = (String) req.getExtraParam().get("namaGenre");
            query = " SELECT count(id_genre) as banyak FROM genre where nama_genre like concat('%',?,'%')";
            return jdbcTemplate.queryForObject(query, Integer.class, nama_genre);
        }else{
            return this.jdbcTemplate.queryForObject(query, null, Integer.class);
        }
        
    }
    
    public List<Genre> getListGenre(DataTablesRequest req) {
        String SQL = "SELECT id_genre as idGenre, nama_genre as namaGenre FROM genre "
                + "order by "+(req.getSortCol()+1)+"  "+req.getSortDir() +" limit ? offset ?";
        if(!req.getExtraParam().isEmpty()){
            String namaGenre = (String) req.getExtraParam().get("namaGenre");
            SQL = "SELECT id_genre as idGenre, nama_genre as namaGenre FROM genre "
                + "where nama_genre like concat('%',?,'%')"
                + " order by "+(req.getSortCol()+1)+"  "+req.getSortDir() +" limit ? offset ?";
            return jdbcTemplate.query(SQL, BeanPropertyRowMapper.newInstance(Genre.class), namaGenre, req.getLength(), req.getStart());
        }else{
            return jdbcTemplate.query(SQL, BeanPropertyRowMapper.newInstance(Genre.class), req.getLength(), req.getStart());
        }
        
    }
}
