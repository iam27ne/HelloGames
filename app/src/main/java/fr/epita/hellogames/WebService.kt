package fr.epita.hellogames

import retrofit2.http.GET
import retrofit2.http.Query

interface WebService {

    @GET("game/list")
    fun listGames(): retrofit2.Call<List<Game>>

    @GET("game/details")
    fun DescGame(@Query("game_id") id: Int): retrofit2.Call<Game>
}