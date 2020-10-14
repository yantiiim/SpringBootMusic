package com.yanti.music.impl;

import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import com.yanti.music.model.Albums;
import com.yanti.music.model.DataTablesRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcAlbums {
    
    @Autowired
    private DataSource dataSource;
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    //Albums
    public List<Albums> getAlbums(){
        String SQL = "select al.id_album as idAlbum, al.nama_albums as namaAlbums, "
                + "al.id_labels as idLabel, al.id_artis as idArtis, al.foto_cover as fotoCover, "
                + "al.keterangan, ar.nama_artis as namaArtis, la.nama_labels as namaLabels "
                + "from Albums al join Artis ar on al.id_artis = ar.id_artis "
                + "join lables_rekaman la on al.id_labels = la.id_label";
        List<Albums> al = jdbcTemplate.query(SQL, BeanPropertyRowMapper.newInstance(Albums.class));
        return al;
    }
    
    public Optional<Albums> getAlbumsById(int id){
        String SQL = "select al.id_album as idAlbum, al.nama_albums as namaAlbums, "
                + "al.id_labels as idLabel, al.id_artis as idArtis, al.foto_cover as fotoCover, "
                + "al.keterangan, ar.nama_artis as namaArtis, la.nama_labels as namaLabels "
                + "from Albums al join Artis ar on al.id_artis = ar.id_artis "
                + "join lables_rekaman la on al.id_labels = la.id_label where id_album = ?";
        Object param[] = {id};
        try {
            return Optional.of (jdbcTemplate.queryForObject(SQL, param, BeanPropertyRowMapper.newInstance(Albums.class)));
        }catch (Exception e) {
            return Optional.empty();
        }
    }
    
    public void insertAlbums(Albums albums){
        String sql = "insert into albums (id_album,nama_albums,id_labels,id_artis,foto_cover,keterangan) values (?,?,?,?,?,?)";
        Object param[] = {albums.getIdAlbum(),albums.getNamaAlbums(),albums.getIdLabel(),albums.getIdArtis(),
                          albums.getFotoCover(),albums.getKeterangan()};
        jdbcTemplate.update(sql, param);
    }
    
    public void updateAlbums(Albums albums){
        String sql = "UPDATE albums SET nama_albums=?, id_labels=?, id_artis=?, foto_cover=?, keterangan=? WHERE id_album=?";
        Object param[] = {albums.getNamaAlbums(),albums.getIdLabel(),albums.getIdArtis(),
                          albums.getFotoCover(),albums.getKeterangan(),albums.getIdAlbum()};
        jdbcTemplate.update(sql, param);
    }
    
    public void insertOrUpdateAlbums (Albums albums){
        Optional<Albums> data=getAlbumsById(albums.getIdAlbum());
        if(data.isPresent()){
            updateAlbums(albums);
        }else{
            insertAlbums(albums);
        }
    }
    
    public List<Albums> getAlbumsByArtis(int id) {

        String baseQuery = "select al.id_album as idAlbum, al.nama_albums as namaAlbums," +
                " al.id_labels as idLabel, al.id_artis as idArtis, al.foto_cover as fotoCover, al.keterangan, ar.nama_artis as namaArtis from Albums al join Artis ar on " +
                "al.id_artis = ar.id_artis where al.id_artis = ? ";

        Object[] param = {id};

        return jdbcTemplate.query(baseQuery, param, (rs, rowNUm) -> {
            Albums albums = new Albums();
            albums.setIdAlbum(rs.getInt("idAlbum"));
            albums.setNamaAlbums(rs.getString("namaAlbums"));
            albums.setIdLabel(rs.getInt("idLabel"));
            albums.setIdArtis(rs.getInt("idArtis"));
            albums.setFotoCover(rs.getString("fotoCover"));
            albums.setKeterangan(rs.getString("keterangan"));
            albums.setNamaArtis(rs.getString("namaArtis"));
            return albums;
        });
    }
    
    public void deleteAlbums(Integer id){
        String SQL = "delete from albums where id_album=?";
        Object parameters[] = {id};
        
        jdbcTemplate.update(SQL, parameters);
    }
    
    //Data Table Albums
    public Integer getBanyakAlbums(DataTablesRequest req) {
        String query = "SELECT count(id_album) as banyak FROM albums";
        if(!req.getExtraParam().isEmpty()){
            String nama_albums = (String) req.getExtraParam().get("namaAlbums");
            query = " SELECT count(id_album) as banyak FROM albums where nama_albums like concat('%',?,'%')";
            return jdbcTemplate.queryForObject(query, Integer.class, nama_albums);
        }else{
            return this.jdbcTemplate.queryForObject(query, null, Integer.class);
        }
        
    }
    
    public List<Albums> getListAlbums(DataTablesRequest req) {
        String SQL = "select al.id_album as idAlbum, al.nama_albums as namaAlbums, "
                + "al.id_labels as idLabel, al.id_artis as idArtis, al.foto_cover as fotoCover, "
                + "al.keterangan, ar.nama_artis as namaArtis, la.nama_labels as namaLabels "
                + "from Albums al join Artis ar on al.id_artis = ar.id_artis "
                + "join lables_rekaman la on al.id_labels = la.id_label "
                    + "order by "+(req.getSortCol()+1)+"  "+req.getSortDir() +" limit ? offset ?";
        if(!req.getExtraParam().isEmpty()){
            String namaAlbums = (String) req.getExtraParam().get("namaAlbums");
            SQL = "select al.id_album as idAlbum, al.nama_albums as namaAlbums, "
                + "al.id_labels as idLabel, al.id_artis as idArtis, al.foto_cover as fotoCover, "
                + "al.keterangan, ar.nama_artis as namaArtis, la.nama_labels as namaLabels "
                + "from Albums al join Artis ar on al.id_artis = ar.id_artis "
                + "join lables_rekaman la on al.id_labels = la.id_label "
                    + "where nama_albums like concat('%',?,'%')"
                    + " order by "+(req.getSortCol()+1)+"  "+req.getSortDir() +" limit ? offset ?";
            return jdbcTemplate.query(SQL, BeanPropertyRowMapper.newInstance(Albums.class), namaAlbums, req.getLength(), req.getStart());
        }else{
            return jdbcTemplate.query(SQL, BeanPropertyRowMapper.newInstance(Albums.class), req.getLength(), req.getStart());
        }
        
    }
}
