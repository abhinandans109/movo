package com.example.movo

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class rvAdapter(var context: Context,var list:List<movieModel>) : RecyclerView.Adapter<rvAdapter.Myviewholder>() {

    class Myviewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val modelimage:ImageView=itemView.findViewById(R.id.model_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Myviewholder {
        return Myviewholder(LayoutInflater.from(context).inflate(R.layout.model,parent,false))


    }

    override fun onBindViewHolder(holder: Myviewholder, position: Int) {
            Picasso.get().load("https://image.tmdb.org/t/p/w500"+list[position].poster)
                .into(holder.modelimage)
        holder.modelimage.setOnClickListener {
            var intent = Intent(context, movie_viewer::class.java)
            intent.putExtra("id", list[position].id)
            intent.putExtra("title", list[position].title)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
       return list.size
    }
}