package com.abdulgafur.demirci.landmark

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.abdulgafur.demirci.landmark.databinding.ActivityDetailsBinding
import com.abdulgafur.demirci.landmark.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var landmarks: ArrayList<Landmark>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
//        enableEdgeToEdge()
//        setContentView(R.layout.activity_main)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }

        landmarks = ArrayList<Landmark>()
        //: MARK Dummy Data

        val pisa = Landmark("Pisa", "Italy", R.drawable.pisa)
        val eiffelTower = Landmark("Eiffel", "France", R.drawable.eiffel_tower)
        val colosseum = Landmark("Colosseum", "Italy", R.drawable.colosseum)
        val londonBridge = Landmark("London Bridge", "England", R.drawable.london_bridge)

        landmarks.add(pisa)
        landmarks.add(eiffelTower)
        landmarks.add(colosseum)
        landmarks.add(londonBridge)

        binding.recycler.layoutManager = LinearLayoutManager(this)
        val adapter = LandmarkAdapter(landmarks)
        binding.recycler.adapter = adapter
    }
}