package com.example.genshinwish

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.genshinwish.models.SongInfo

class SongRecyclerViewAdapter(private val listSong: ArrayList<SongInfo>):
    RecyclerView.Adapter<SongRecyclerViewAdapter.MyViewHolder>(){
    class MyViewHolder(view: View): RecyclerView.ViewHolder(view){
        val textview1: TextView = view.findViewById(R.id.textview1)
        val textview2: TextView = view.findViewById(R.id.textview2)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_music_component,parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentSong = listSong[position]
        holder.textview1.text  = currentSong.Title
        holder.textview2.text = currentSong.Author
    }

    override fun getItemCount(): Int =  listSong.size


}