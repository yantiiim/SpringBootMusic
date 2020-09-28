/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yanti.music.model;

/**
 *
 * @author yanti
 */
public class Lagu {
    
    private int idLagu;
    private String judul;
    private String durasi;
    private int idGenre;
    private int idArtis;
    private int idAlbum;
    private String fileLagu;
    private Genre genre;
    private String namaGenre;
    private Artis artis;
    private String namaArtis;
    private Albums albums;
    private String namaAlbums;

    public int getIdLagu() {
        return idLagu;
    }

    public void setIdLagu(int idLagu) {
        this.idLagu = idLagu;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getDurasi() {
        return durasi;
    }

    public void setDurasi(String durasi) {
        this.durasi = durasi;
    }

    public int getIdGenre() {
        return idGenre;
    }

    public void setIdGenre(int idGenre) {
        this.idGenre = idGenre;
    }

    public int getIdArtis() {
        return idArtis;
    }

    public void setIdArtis(int idArtis) {
        this.idArtis = idArtis;
    }

    public int getIdAlbum() {
        return idAlbum;
    }

    public void setIdAlbum(int idAlbum) {
        this.idAlbum = idAlbum;
    }

    public String getFileLagu() {
        return fileLagu;
    }

    public void setFileLagu(String fileLagu) {
        this.fileLagu = fileLagu;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public String getNamaGenre() {
        return namaGenre;
    }

    public void setNamaGenre(String namaGenre) {
        this.namaGenre = namaGenre;
    }

    public Artis getArtis() {
        return artis;
    }

    public void setArtis(Artis artis) {
        this.artis = artis;
    }

    public String getNamaArtis() {
        return namaArtis;
    }

    public void setNamaArtis(String namaArtis) {
        this.namaArtis = namaArtis;
    }

    public Albums getAlbums() {
        return albums;
    }

    public void setAlbums(Albums albums) {
        this.albums = albums;
    }

    public String getNamaAlbums() {
        return namaAlbums;
    }

    public void setNamaAlbums(String namaAlbums) {
        this.namaAlbums = namaAlbums;
    }

    
    
    
}
