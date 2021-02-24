
package com.gpixel.Retrofit;


import com.gpixel.javabeans.Juego;
import com.gpixel.javabeans.Prueba;
import com.gpixel.javabeans.Prueba2;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by PilarMarGom on 04/01/2018.
 */

public interface APIRestService {
    public static final String BASE_URL ="https://www.giantbomb.com/api/";

    public static final String Key="bd2514fa50bc7f31b80992f4dd257af11aa48f96";
    public static final String format="json";
    public static final String field_list="name,deck,original_release_date,platforms,image,id";
    public static final String filter="platforms:35|18|94|20|36|117|129|145|146|157";
    public static final String filterps4="platforms:145|146|157|117";
    public static final String filtro="name:";
    public static final String filtroplat="platforms:";
    public static final String field_list_generp="name,deck,original_release_date,platforms,image,id,genres";
    public static final String offset="311";
    public static final String order="original_release_date:desc";



    @GET("games/")
    Call<ArrayList<Juego>> obtenerCds(@Query("api_key") String Key, @Query("format") String format, @Query("field_list") String name, @Query("filter") String filtro);

    @GET("games/")
    Call<Prueba>obtenerPrueba2();

    @GET("game")
    Call<Juego> obtenerJuego(@Query("api_key") String Key, @Query("format") String format, @Query("field_list") String nombre);

    @GET("games/")
    Call<Prueba> obtenerPrueba(@Query("api_key") String Key, @Query("format") String format,
                               @Query("field_list") String nombre, @Query("filter") String filtro
            ,@Query("sort")String order);

    @GET("games/")
    Call<Prueba>filtrarNombre(@Query("api_key") String key, @Query("format") String format
            , @Query("field_list") String nombre, @Query("filter") String filtroNomb
            ,@Query("sort")String order);

    @GET("game/{guid}/")
    Call<Prueba2>GeneroJuego(@Path("guid") String id, @Query("api_key")String key
            ,@Query("format") String format, @Query("field_list") String nombre);


}