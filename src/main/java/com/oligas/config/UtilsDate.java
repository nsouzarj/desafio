package com.oligas.config;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Traz diferenças de dias entre 2 datas
 */
public class UtilsDate {
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public long getDiasCorridos (Date dataInicial, Date dataFinal) {
        long diasCorridos = -1;
        try {
            Date dateStart = dataInicial;
            Date dateEnd = dataFinal;
            diasCorridos = Math.round((dateEnd.getTime() - dateStart.getTime()) / (double) 86400000);
        } catch (Exception e) {
            //handle the exception according to your own situation
        }
        return diasCorridos;

    }
}