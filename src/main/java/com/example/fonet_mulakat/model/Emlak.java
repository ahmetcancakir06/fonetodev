package com.example.fonet_mulakat.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Emlak {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String mkare;
    @Column
    private String osayi;
    @Column
    private String bkati;
    @Column
    private String bbkat;
    @Column
    private String isinma;
    @Column
    private String ucret;
    @Column
    private String tur;
    @Column
    private String kiralik;
    @Column
    private String foto;
    @Column
    private String tamAdres;
    @Column
    private String sehir;
    @Column
    private String sokak;
    @Column
    private String zip;

    public Emlak(String mkare, String osayi, String bkati, String bbkat, String isinma, String ucret, String tur, String kiralik, String foto, String tamAdres, String sehir, String sokak, String zip) {
        this.mkare = mkare;
        this.osayi = osayi;
        this.bkati = bkati;
        this.bbkat = bbkat;
        this.isinma = isinma;
        this.ucret = ucret;
        this.tur = tur;
        this.kiralik = kiralik;
        this.foto = foto;
        this.tamAdres = tamAdres;
        this.sehir = sehir;
        this.sokak = sokak;
        this.zip = zip;
    }
    public Emlak(){}
}
