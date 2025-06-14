package com.abdulgafur.demirci.cryptocurrencies.view

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.abdulgafur.demirci.cryptocurrencies.R
import com.abdulgafur.demirci.cryptocurrencies.adapter.RecyclerAdapter
import com.abdulgafur.demirci.cryptocurrencies.databinding.ActivityMainBinding
import com.abdulgafur.demirci.cryptocurrencies.model.Currency
import com.abdulgafur.demirci.cryptocurrencies.service.ICryptoAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(), RecyclerAdapter.Listener {

    private val baseUrl = "https://raw.githubusercontent.com/"
    private var cryptos: ArrayList<Currency>? = null
    private var adapter: RecyclerAdapter? = null

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
//        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // RecyclerView
        val layoutManager : RecyclerView.LayoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager

        loadData()
    }

    private fun loadData() {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(ICryptoAPI::class.java)
        val call = service.getData()

        call.enqueue(object : Callback<List<Currency>> {
            override fun onResponse(
                call: Call<List<Currency>>,
                response: Response<List<Currency>>
            ) {
                if(response.isSuccessful) {
                    response.body()?.let { it ->
                        cryptos = ArrayList(it)

                        cryptos?.let {
                            adapter = RecyclerAdapter(it, this@MainActivity)
                            binding.recyclerView.adapter = adapter
                        }

                        for (crypto : Currency in cryptos!!) {
                            println(crypto.currency)
                            println(crypto.price)
                        }
                    }
                }
            }

            override fun onFailure(call: Call<List<Currency>>, t: Throwable) {
                t.printStackTrace()
            }

        })
    }

    override fun onItemClick(currency: Currency) {
        Toast.makeText(this,currency.currency,Toast.LENGTH_SHORT).show()
    }
}