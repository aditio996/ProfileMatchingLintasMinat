package dev.as.m.profilematchinglintasminat;

import android.content.Context;

import java.util.ArrayList;

public class TotalAkhir {

    private String idSiswa;
    private ArrayList<Float> gapArray, bobotGapArray, profilIdealArray, profilSiswaArray, totalCFArray, totalSFArray, totalAspekArray, totalNilaiArray;
    private ArrayList<Ranking> rankingArray;
    private ArrayList<Float> jumlahBobotGapCFArray, jumlahBobotGapSFArray;

    public TotalAkhir(String idSiswa, ArrayList<Float> gapArray, ArrayList<Float> bobotGapArray, ArrayList<Float> profilIdealArray, ArrayList<Float> profilSiswaArray, ArrayList<Float> totalCFArray, ArrayList<Float> totalSFArray, ArrayList<Float> totalAspekArray, ArrayList<Float> totalNilaiArray, ArrayList<Ranking> rankingArray, ArrayList<Float> jumlahBobotGapCFArray, ArrayList<Float> jumlahBobotGapSFArray) {
        this.idSiswa = idSiswa;
        this.gapArray = gapArray;
        this.bobotGapArray = bobotGapArray;
        this.profilIdealArray = profilIdealArray;
        this.profilSiswaArray = profilSiswaArray;
        this.totalCFArray = totalCFArray;
        this.totalSFArray = totalSFArray;
        this.totalAspekArray = totalAspekArray;
        this.totalNilaiArray = totalNilaiArray;
        this.rankingArray = rankingArray;
        this.jumlahBobotGapCFArray = jumlahBobotGapCFArray;
        this.jumlahBobotGapSFArray = jumlahBobotGapSFArray;
    }

    public float getGap(int i){
        return gapArray.get(i);
    }

    public float getBobotGap(int i){
        return bobotGapArray.get(i);
    }

    public float getProfilIdeal(int i){
        return profilIdealArray.get(i);
    }

    public float getProfilSiswa(int i){
        return profilSiswaArray.get(i);
    }

    public float getTotalCF(int i){
        return totalCFArray.get(i);
    }

    public float getTotalSF(int i){
        return totalSFArray.get(i);
    }

    public float getTotalAspek(int i){
        return totalAspekArray.get(i);
    }

    public float getTotalNilai(int i){
        return totalNilaiArray.get(i);
    }

    public float getJumlahBobotGapCF(int i){
        return jumlahBobotGapCFArray.get(i);
    }
    public float getJumlahBobotGapSF(int i){
        return jumlahBobotGapSFArray.get(i);
    }

    public Float getRanking(int i){
        return rankingArray.get(i).getNilai();
    }

    public String getLintasMinatByRanking(int i){
        return rankingArray.get(i).getLintasMinat();
    }

    public String getIdSiswa() {
        return idSiswa;
    }

    public ArrayList<Float> getGapArray() {
        return gapArray;
    }

    public ArrayList<Float> getJumlahBobotGapCFArray() {
        return jumlahBobotGapCFArray;
    }

    public ArrayList<Float> getJumlahBobotGapSFArray() {
        return jumlahBobotGapSFArray;
    }

    public ArrayList<Float> getBobotGapArray() {
        return bobotGapArray;
    }

    public ArrayList<Float> getProfilIdealArray() {
        return profilIdealArray;
    }

    public ArrayList<Float> getProfilSiswaArray() {
        return profilSiswaArray;
    }

    public ArrayList<Float> getTotalCFArray() {
        return totalCFArray;
    }

    public ArrayList<Float> getTotalSFArray() {
        return totalSFArray;
    }

    public ArrayList<Float> getTotalAspekArray() {
        return totalAspekArray;
    }

    public ArrayList<Float> getTotalNilaiArray() {
        return totalNilaiArray;
    }

    public ArrayList<Ranking> getRankingArray() {
        return rankingArray;
    }

    public String getLintasMinatTertinggi(){
        return rankingArray.get(0).getLintasMinat();
    }

    public float getRankingTertinggi(){
        return rankingArray.get(0).getNilai();
    }

}
