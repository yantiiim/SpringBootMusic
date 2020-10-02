/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yanti.music.impl;

import com.yanti.music.model.Albums;
import com.yanti.music.model.Artis;
import com.yanti.music.model.DataTablesRequest;
import com.yanti.music.model.Genre;
import com.yanti.music.model.LablesRekaman;
import com.yanti.music.model.Lagu;
import com.yanti.music.model.UserAdmin;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author yanti
 */
@Repository
public class KoneksiJdbc {

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
    
    //Admin
    public Optional<UserAdmin> getUserAdminById(String userAdmin) {
        String SQL = "select user_name, user_password from user_admin where user_name = ? ";
        try {
            return Optional.of(jdbcTemplate.queryForObject(SQL, (rs, rownum) -> {
                UserAdmin kab = new UserAdmin();
                kab.setUsername(rs.getString("user_name"));
                kab.setPassword(rs.getString("user_password"));
                return kab;
            }, userAdmin));
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }
    

}
