package com.example.genshinwish

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.genshinwish.models.Photos



class BannerAdapter(private val listphotos: ArrayList<Photos>): RecyclerView.Adapter<BannerAdapter.BannerViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): BannerAdapter.BannerViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.banner_info_view,parent,false)
        return  BannerViewHolder(v)
    }

    override fun onBindViewHolder(holder: BannerAdapter.BannerViewHolder, position: Int) {
        val currentPhoto =  listphotos[position]
        holder.imageView.setImageResource(currentPhoto.imageResource)
    }

    override fun getItemCount() = listphotos.size

    class BannerViewHolder(v : View): RecyclerView.ViewHolder(v){
        val imageView: ImageView = v.findViewById(R.id.rv_img_banner)

    }
}