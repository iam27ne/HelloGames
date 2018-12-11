package fr.epita.hellogames
import android.content.Intent
import android.media.Image
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class MainActivity : AppCompatActivity() {

    var game_list = arrayListOf<Game>()

    val base_URL = "https://androidlessonsapi.herokuapp.com/api/"
    val json_converter = GsonConverterFactory.create(GsonBuilder().create())
    val retrofit = Retrofit.Builder().baseUrl(base_URL).addConverterFactory(json_converter).build()
    val service: WebService = retrofit.create(WebService::class.java)

    val callback = object : Callback<List<Game>> {

        override fun onResponse(call: Call<List<Game>>?, response: Response<List<Game>>?) {

            if (response != null) {

                if (response.code() == 200) {

                    val value = response.body()

                    if (value != null) {

                        game_list.addAll(value)

                        Log.d("TAG", "WebService success : " + game_list.size)

                        val rand_list = ArrayList<Int>()
                        for (i in 0..4) {
                            val rand_int = (0..game_list.size - 1).shuffled().first()
                            if (!rand_list.contains(rand_int)) rand_list.add(rand_int)
                        }

                        imageButton1.setImageResource(getImage(game_list[rand_list.get(0)].id!!))
                        imageButton1.setOnClickListener{
                            val explicitIntent = Intent(this@MainActivity, SecondActivity::class.java)
                            explicitIntent.putExtra("game_selector", game_list[rand_list.get(0)].id)
                            startActivity(explicitIntent)
                        }

                        imageButton2.setImageResource(getImage(game_list[rand_list.get(1)].id!!))
                        imageButton2.setOnClickListener{
                            val explicitIntent = Intent(this@MainActivity, SecondActivity::class.java)
                            explicitIntent.putExtra("game_selector", game_list[rand_list.get(1)].id)
                            startActivity(explicitIntent)
                        }

                        imageButton3.setImageResource(getImage(game_list[rand_list.get(2)].id!!))
                        imageButton3.setOnClickListener{
                            val explicitIntent = Intent(this@MainActivity, SecondActivity::class.java)
                            explicitIntent.putExtra("game_selector", game_list[rand_list.get(2)].id)
                            startActivity(explicitIntent)
                        }

                        imageButton4.setImageResource(getImage(game_list[rand_list.get(3)].id!!))
                        imageButton4.setOnClickListener{
                            val explicitIntent = Intent(this@MainActivity, SecondActivity::class.java)
                            explicitIntent.putExtra("game_selector", game_list[rand_list.get(3)].id)
                            startActivity(explicitIntent)
                        }

                    }
                }
            }
        }

        override fun onFailure(call: Call<List<Game>>?, t: Throwable?) {
            Log.d("TAG", "WebService call failed")
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        service.listGames().enqueue(callback)
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
