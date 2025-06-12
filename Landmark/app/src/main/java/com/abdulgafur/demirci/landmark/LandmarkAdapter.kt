package com.abdulgafur.demirci.landmark

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abdulgafur.demirci.landmark.databinding.RecyclerRowBinding

class LandmarkAdapter(val landmarks: ArrayList<Landmark>) : RecyclerView.Adapter<LandmarkAdapter.LandmarkHolder>() {

    class  LandmarkHolder(val binding : RecyclerRowBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    // xml'i code ile bağlıyoruz.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LandmarkHolder {
        val binding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LandmarkHolder(binding)
    }

    // Kaç tane item olacağını belirtiyoruz.
    override fun getItemCount(): Int {
        return landmarks.size
    }

    // Göstereceği item'a değerlerini atıyoruz.
    override fun onBindViewHolder(holder: LandmarkHolder, position: Int) {
        holder.binding.recyclerTextView.text = landmarks.get(position).name

        holder.itemView.setOnClickListener{
            val intent = Intent(holder.itemView.context, Details::class.java)
            intent.putExtra("landmark", landmarks.get(position))
            holder.itemView.context.startActivity(intent)
        }


    }

}