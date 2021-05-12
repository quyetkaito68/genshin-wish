package com.example.genshinwish

import android.R
import android.media.Image
import android.view.LayoutInflater
import android.view.LayoutInflater.from
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.genshinwish.models.mImage


class BannerAdapter: RecyclerView.Adapter<BannerAdapter.ItemViewHolder>() {
    var data = listOf<mImage>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        TODO("Not yet implemented")
    }



    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = data[position]
        holder.textView.text = item.imageUrl.toString()
    }

    override fun getItemCount() = data.size

    class ItemViewHolder(val textView: TextView, val image: Image): RecyclerView.ViewHolder(textView){
    }



}