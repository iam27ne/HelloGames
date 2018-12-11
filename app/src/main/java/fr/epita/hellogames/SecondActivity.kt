package fr.epita.hellogames

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SecondActivity : AppCompatActivity() {

    var game = Game()
    val base_URL = "https://androidlessonsapi.herokuapp.com/api/"
    val json_converter = GsonConverterFactory.create(GsonBuilder().create())
    val retrofit = Retrofit.Builder().baseUrl(base_URL).addConverterFactory(json_converter).build()
    val service: WebService = retrofit.create(WebService::class.java)

    val callback = object : retrofit2.Callback<Game> {

        override fun onFailure(call: Call<Game>?, t: Throwable?) {
            Log.d("TAG", "WebService call failed")
        }

        override fun onResponse(call: Call<Game>?, response: Response<Game>?) {
            if (response != null) {
                Log.d("TAG", response.toString())
                if (response.code() == 200) {
                    val value = response.body()
                    if (value != null) {
                        game = value
                        findViewById<ImageView>(R.id.imageView).setImageResource(getImage(game.id!!))
                        findViewById<TextView>(R.id.name).text = "Name: " + game.name
                        findViewById<TextView>(R.id.type).text = "Type: " + game.type
                        findViewById<TextView>(R.id.players).text = "Nb Player: " + game.nmbPlayers.toString()
                        findViewById<TextView>(R.id.year).text = "Year: " + game.year.toString()
                        findViewById<TextView>(R.id.desc).text = "Description: " + game.description_en
                    }
                }
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val game_selector = intent.getIntExtra("game_selector", -1)

        service.DescGame(game_selector).enqueue(callback)
    }

    fun getImage(id: Int): Int {
        var game_selector = 0
        when(id) {
            1 -> game_selector = R.drawable.tictactoe
            2 -> game_selector = R.drawable.hangman
            3 -> game_selector = R.drawable.sudoku
            4 -> game_selector = R.drawable.battleship
            5 -> game_selector = R.drawable.mineswepper
            6 -> game_selector = R.drawable.gameoflife
            7 -> game_selector = R.drawable.memory
            8 -> game_selector = R.drawable.simon
            9 -> game_selector = R.drawable.slidingpuzzle
            10 -> game_selector = R.drawable.mastermind
        }
        return game_selector
    }
}