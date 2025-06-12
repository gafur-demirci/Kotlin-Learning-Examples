package com.abdulgafur.demirci.landmark

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.abdulgafur.demirci.landmark.databinding.ActivityDetailsBinding

class Details : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
//        setContentView(R.layout.activity_details)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }

        val intent = intent
        //: TODO Serializable cast to Landmark
        val selectedLandmark = intent.getSerializableExtra("landmark") as Landmark
        binding.name.text = selectedLandmark.name
        binding.country.text = selectedLandmark.country
        binding.imageView.setImageResource(selectedLandmark.image)
    }
}