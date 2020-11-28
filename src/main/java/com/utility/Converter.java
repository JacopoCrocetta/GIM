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

/**
 *
 * @author Jacopo Crocetta
 */
@Getter
@Setter
public class Converter {
    ///DATE CONVERSION
    LocalDate localDate;
    LocalDateTime localDateTime;
    String localDateFormatter;

    ///Google Json
    Gson gson;

    /*
    TODO Finire di creare i javadoc
    TODO Mettere il converter da String a Entity
    */

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

    /**
     *
     * <p>
     *     Method that converts a variable
     *     of type LocalDate to a string
     * </p>
     * @return the LocalDate converted in string
     */
    public String localDateConverterToString(){
        return localDate.format(DateTimeFormatter.ofPattern(localDateFormatter));
    }

    /**
     *
     * <p>
     *     Method that converts a variable
     *     of type LocalDateTime to a string
     * </p>
     * @return the LocalDateTime converted in string
     */
    public String localDateTimeConverterToString(){
        return localDateTime.format(DateTimeFormatter.ofPattern(localDateFormatter));
    }

    /**
     *
     * <p>
     *     Method that converts a variable
     *     of type LocalDate to a timestamp
     * </p>
     * @return the LocalDate converted in string
     */
    public Timestamp localDateConverterToTimestamp(){
        return Timestamp.valueOf(localDate.format(DateTimeFormatter.ofPattern(localDateFormatter)));
    }

    /**
     *
     * <p>
     *     Method that converts a variable
     *     of type LocalDate to a timestamp
     * </p>
     * @return the LocalDateTime converted in Timestamp
     */
    public Timestamp localDateTimeConverterToTimestamp(){
        return Timestamp.valueOf(localDateTime.format(DateTimeFormatter.ofPattern(localDateFormatter)));
    }

    /**
     *
     * <p>
     *     Method that converts a variable
     *     of type CeramicheEntity to a String
     * </p>
     * @param entity the CeramicheEntity we aro going to convert
     * @return the CeramicheEntity converted in String in Json format
     */
    public String ceramicheEntityGsonToString(CeramicheEntity entity){
        return this.gson.toJson(entity);
    }

    /**
     *
     * <p>
     *     Method that converts a variable
     *     of type DecoroCoppetteEntity to a String
     * </p>
     * @param entity the CeramicheEntity we aro going to convert
     * @return the DecoroCoppetteEntity converted in String in Json format
     */
    public String decoroCoppetteEntityGsonToString(DecoroCoppetteEntity entity){
        return this.gson.toJson(entity);
    }

    /**
     *
     * <p>
     *     Method that converts a variable
     *     of type DecoroVassoiEntity to a String
     * </p>
     * @param entity the DecoroVassoiEntity we aro going to convert
     * @return the DecoroVassoiEntity converted in String in Json format
     */
    public String decoroVassoiEntityGsonToString(DecoroVassoiEntity entity){
        return this.gson.toJson(entity);
    }

    /**
     *
     * <p>
     *     Method that converts a variable
     *     of type CeramicheEntity to a String
     * </p>
     * @param entity the CeramicheEntity we aro going to convert
     * @return the CeramicheEntity converted in String in Json format
     */
    public String merceEntityGsonToString(MerceEntity entity){
        return this.gson.toJson(entity);
    }
}
