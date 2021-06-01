package com.example.genshinwish

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.genshinwish.models.Song
import com.example.genshinwish.models.SongInfo


class SongRecyclerViewAdapter(private val listSong: ArrayList<Song>, private val listener: OnItemClickListener):
    RecyclerView.Adapter<SongRecyclerViewAdapter.MyViewHolder>(){

    interface OnItemClickListener{
        fun onItemClick(position: Int){
        }
    }
    inner class MyViewHolder(view: View): RecyclerView.ViewHolder(view), View.OnClickListener {
        init {
            view.setOnClickListener(this)
        }
        val textview1: TextView = view.findViewById(R.id.textview1)
        val textview2: TextView = view.findViewById(R.id.textview2)
        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position!=RecyclerView.NO_POSITION){
                listener.onItemClick(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_music_component,
            parent,
            false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentSong = listSong[position]
        holder.textview1.text  = currentSong.title
        holder.textview2.text = currentSong.singer
    }

    override fun getItemCount(): Int =  listSong.size


}