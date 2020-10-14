package com.yanti.music.impl;

import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import com.yanti.music.model.DataTablesRequest;
import com.yanti.music.model.Lagu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcLagu {
    
    @Autowired
    private DataSource dataSource;
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    //Lagu
    public List<Lagu> getLagu(){
        String SQL = "select la.id_lagu as idLagu, la.judul, la.durasi, la.id_genre as idGenre, \n" +
                     "la.id_artis as idArtis, la.id_album as idAlbum, la.file_lagu as fileLagu, g.id_genre as idGenre, \n" +
                     "ar.id_artis as idArtis, al.id_album as idAlbum from lagu la join genre g on la.id_genre = g.id_genre "
                   + "join artis ar on la.id_artis = ar.id_artis join albums al on la.id_album = al.id_album";
        List<Lagu> la = jdbcTemplate.query(SQL, BeanPropertyRowMapper.newInstance(Lagu.class));
        return la;
    }
    
    public Optional<Lagu> getLaguById(int id){
        String SQL = "select la.id_lagu as idLagu, la.judul, la.durasi, la.id_genre as idGenre, \n" +
                     "la.id_artis as idArtis, la.id_album as idAlbum, la.file_lagu as fileLagu, g.id_genre as idGenre, \n" +
                     "ar.id_artis as idArtis, al.id_album as idAlbum from lagu la join genre g on la.id_genre = g.id_genre "
                   + "join artis ar on la.id_artis = ar.id_artis join albums al on la.id_album = al.id_album where id_album = ?";
        Object param[] = {id};
        try {
            return Optional.of (jdbcTemplate.queryForObject(SQL, param, BeanPropertyRowMapper.newInstance(Lagu.class)));
        }catch (Exception e) {
            return Optional.empty();
        }
    }
    
    public void insertLagu(Lagu lagu){
        String sql = "insert into lagu (id_lagu,judul,durasi,id_genre,id_artis,id_album,file_lagu) values (?,?,?,?,?,?,?)";
        Object param[] = {lagu.getIdLagu(),lagu.getJudul(),lagu.getDurasi(),lagu.getIdGenre(),lagu.getIdArtis(),
                         lagu.getIdAlbum(),lagu.getFileLagu()};
        jdbcTemplate.update(sql, param);
    }
    
    public void updateLagu(Lagu lagu){
        String sql = "UPDATE lagu SET judul=?, durasi=?, id_genre=?, id_artis=?, id_album=? file_lagu=? WHERE id_lagu=?";
        Object param[] = {lagu.getJudul(),lagu.getDurasi(),lagu.getIdGenre(),lagu.getIdArtis(),
                         lagu.getIdAlbum(),lagu.getFileLagu(),lagu.getIdLagu()};
        jdbcTemplate.update(sql, param);
    }
    
    public void insertOrUpdateLagu (Lagu lagu){
        Optional<Lagu> data=getLaguById(lagu.getIdLagu());
        if(data.isPresent()){
            updateLagu(lagu);
        }else{
            insertLagu(lagu);
        }
    }
    
    public List<Lagu> getLaguByAlbums(int id) {

        String baseQuery = "select la.id_lagu as idLagu, la.judul, la.durasi, la.id_genre as idGenre, "
                + "la.id_artis as idArtis, la.id_album as idAlbum, la.file_lagu as fileLagu, "
                + "g.nama_genre as namaGenre, ar.nama_artis as namaArtis, al.nama_albums as namaAlbums "
                + "from lagu la join genre g on la.id_genre = g.id_genre join artis ar on la.id_artis = ar.id_artis "
                + "join albums al on la.id_album = al.id_album  where al.id_artis = ?";

        Object[] param = {id};

        return jdbcTemplate.query(baseQuery, param, (rs, rowNUm) -> {
            Lagu lagu = new Lagu();
            lagu.setIdLagu(rs.getInt("idLagu"));
            lagu.setJudul(rs.getString("judul"));
            lagu.setDurasi(rs.getString("durasi"));
            lagu.setIdGenre(rs.getInt("idGenre"));
            lagu.setIdArtis(rs.getInt("idArtis"));
            lagu.setIdAlbum(rs.getInt("idAlbum"));
            lagu.setFileLagu(rs.getString("fileLagu"));
            lagu.setNamaGenre(rs.getString("namaGenre"));
            lagu.setNamaArtis(rs.getString("namaArtis"));
            lagu.setNamaAlbums(rs.getString("namaAlbums"));
            return lagu;
        });
    }
    
    public List<Lagu> getLaguByGenre(int id) {

        String baseQuery = "select la.id_lagu as idLagu, la.judul, la.durasi, la.id_genre as idGenre, "
                         + "la.id_artis as idArtis, la.id_album as idAlbum, la.file_lagu as fileLagu, "
                         + "g.nama_genre as namaGenre, ar.nama_artis as namaArtis, al.nama_albums as namaAlbums "
                         + "from lagu la join genre g on la.id_genre = g.id_genre "
                         + "join artis ar on la.id_artis = ar.id_artis join albums al on la.id_album = al.id_album  "
                         + "where la.id_genre = ? ";

        Object[] param = {id};

        return jdbcTemplate.query(baseQuery, param, (rs, rowNUm) -> {
            Lagu lagu = new Lagu();
            lagu.setIdLagu(rs.getInt("idLagu"));
            lagu.setJudul(rs.getString("judul"));
            lagu.setDurasi(rs.getString("durasi"));
            lagu.setIdGenre(rs.getInt("idGenre"));
            lagu.setNamaGenre(rs.getString("namaGenre"));
            lagu.setIdArtis(rs.getInt("idArtis"));
            lagu.setNamaArtis(rs.getString("namaArtis"));
            lagu.setIdAlbum(rs.getInt("idAlbum"));
            lagu.setNamaAlbums(rs.getString("namaAlbums"));
            lagu.setFileLagu(rs.getString("fileLagu"));
            return lagu;
        });
    }
    
    public void deleteLagu(Integer id){
        String SQL = "delete from lagu where id_lagu=?";
        Object parameters[] = {id};
        
        jdbcTemplate.update(SQL, parameters);
    }
    
    //Data Table Lagu
    public Integer getBanyakLagu(DataTablesRequest req) {
        String query = "SELECT count(id_lagu) as banyak FROM lagu";
        if(!req.getExtraParam().isEmpty()){
            String judul = (String) req.getExtraParam().get("judul");
            query = " SELECT count(id_lagu) as banyak FROM lagu where judul like concat('%',?,'%')";
            return jdbcTemplate.queryForObject(query, Integer.class, judul);
        }else{
            return this.jdbcTemplate.queryForObject(query, null, Integer.class);
        }
        
    }
    
    public List<Lagu> getListLagu(DataTablesRequest req) {
        String SQL = "select la.id_lagu as idLagu, la.judul, la.durasi, la.id_genre as idGenre, \n" +
                     "la.id_artis as idArtis, la.id_album as idAlbum, la.file_lagu as fileLagu, g.id_genre as idGenre, \n" +
                     "ar.id_artis as idArtis, al.id_album as idAlbum from lagu la join genre g on la.id_genre = g.id_genre "
                   + "join artis ar on la.id_artis = ar.id_artis join albums al on la.id_album = al.id_album "
                    + "order by "+(req.getSortCol()+1)+"  "+req.getSortDir() +" limit ? offset ?";
        if(!req.getExtraParam().isEmpty()){
            String judul = (String) req.getExtraParam().get("judul");
            SQL = "select la.id_lagu as idLagu, la.judul, la.durasi, la.id_genre as idGenre, \n" +
                     "la.id_artis as idArtis, la.id_album as idAlbum, la.file_lagu as fileLagu, g.id_genre as idGenre, \n" +
                     "ar.id_artis as idArtis, al.id_album as idAlbum from lagu la join genre g on la.id_genre = g.id_genre "
                   + "join artis ar on la.id_artis = ar.id_artis join albums al on la.id_album = al.id_album "
                    + "where judul like concat('%',?,'%')"
                    + " order by "+(req.getSortCol()+1)+"  "+req.getSortDir() +" limit ? offset ?";
            return jdbcTemplate.query(SQL, BeanPropertyRowMapper.newInstance(Lagu.class), judul, req.getLength(), req.getStart());
        }else{
            return jdbcTemplate.query(SQL, BeanPropertyRowMapper.newInstance(Lagu.class), req.getLength(), req.getStart());
        }
        
    }
}
