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
public class Albums {
    
    private int idAlbum;
    private String namaAlbums;
    private int idLabel;
    private int idArtis;
    private String fotoCover;
    private String keterangan;
    private LablesRekaman lablesRekaman;
    private String namaLabels;
    private Artis artis;
    private String namaArtis;
    

    public int getIdAlbum() {
        return idAlbum;
    }

    public void setIdAlbum(int idAlbum) {
        this.idAlbum = idAlbum;
    }

    public String getNamaAlbums() {
        return namaAlbums;
    }

    public void setNamaAlbums(String namaAlbums) {
        this.namaAlbums = namaAlbums;
    }

    public int getIdLabel() {
        return idLabel;
    }

    public void setIdLabel(int idLabel) {
        this.idLabel = idLabel;
    }

    public int getIdArtis() {
        return idArtis;
    }

    public void setIdArtis(int idArtis) {
        this.idArtis = idArtis;
    }

    public String getFotoCover() {
        return fotoCover;
    }

    public void setFotoCover(String fotoCover) {
        this.fotoCover = fotoCover;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public LablesRekaman getLablesRekaman() {
        return lablesRekaman;
    }

    public void setLablesRekaman(LablesRekaman lablesRekaman) {
        this.lablesRekaman = lablesRekaman;
    }

    public String getNamaLabels() {
        return namaLabels;
    }

    public void setNamaLabels(String namaLabels) {
        this.namaLabels = namaLabels;
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

    
    
    
}
