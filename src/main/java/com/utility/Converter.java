package com.utility;

import com.entities.CeramicheEntity;
import com.entities.DecoroCoppetteEntity;
import com.entities.DecoroVassoiEntity;
import com.entities.MerceEntity;
import com.google.gson.Gson;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
public class Converter {
    ///DATE CONVERSION
    LocalDate localDate;
    LocalDateTime localDateTime;
    String localDateFormatter;

    ///Google Json
    Gson gson;

    public Converter(){
        this.localDateFormatter = "dd/MM/yyyy";
        this.localDate = LocalDate.now();
        this.localDateTime = LocalDateTime.now();
        this.gson = new Gson();
    }

    public Converter (Gson gson){
        this.localDateFormatter = "dd/MM/yyyy";
        this.localDate = LocalDate.now();
        this.localDateTime = LocalDateTime.now();
        this.gson = gson;
    }

    public Converter(LocalDate localDate){
        this.localDate = localDate;
        this.localDateFormatter = "dd/MM/yyyy";
        this.gson = new Gson();
    }

    public Converter(LocalDateTime localDateTime){
        this.localDateTime = localDateTime;
        this.localDateFormatter = "dd/MM/yyyy";
        this.gson = new Gson();
    }

    public Converter(LocalDate localDate, String localDateFormatter){
        this.localDate = localDate;
        this.localDateFormatter = localDateFormatter;
        this.gson = new Gson();
    }

    public Converter(LocalDate localDate, Gson gson){
        this.localDate = localDate;
        this.localDateFormatter = "dd/MM/yyyy";
        this.gson = gson;
    }

    public Converter(LocalDateTime localDateTime, Gson gson){
        this.localDateTime = localDateTime;
        this.localDateFormatter = "dd/MM/yyyy";
        this.gson = gson;
    }

    public Converter(LocalDate localDate, String localDateFormatter, Gson gson){
        this.localDate = localDate;
        this.localDateFormatter = localDateFormatter;
        this.gson = gson;
    }

    public Converter(LocalDateTime localDateTime, String localDateFormatter, Gson gson){
        this.localDateTime = localDateTime;
        this.localDateFormatter = localDateFormatter;
        this.gson = gson;
    }

    public String localDateConverterToString(){
        return localDate.format(DateTimeFormatter.ofPattern(localDateFormatter));
    }

    public String localDateTimeConverterToString(){
        return localDateTime.format(DateTimeFormatter.ofPattern(localDateFormatter));
    }

    public Timestamp localDateConverterToTimestamp(){
        return Timestamp.valueOf(localDate.format(DateTimeFormatter.ofPattern(localDateFormatter)));
    }

    public String ceramicheEntityGsonToString(CeramicheEntity entity){
        return this.gson.toJson(entity);
    }

    public String decoroCoppetteEntityGsonToString(DecoroCoppetteEntity entity){
        return this.gson.toJson(entity);
    }

    public String decoroVassoiEntityGsonToString(DecoroVassoiEntity entity){
        return this.gson.toJson(entity);
    }

    public String merceEntityGsonToString(MerceEntity entity){
        return this.gson.toJson(entity);
    }
}
