package com.example.movo

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class recycleradapter(
    var context: Context,
    var list: ArrayList<movieModel>,
) :
    RecyclerView.Adapter<recycleradapter.vewholder>() {
    class vewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            var image=itemView.findViewById<ImageView>(R.id.movie_poster)
            var title=itemView.findViewById<TextView>(R.id.movie_title)
            var plot=itemView.findViewById<TextView>(R.id.movie_plot)
            var tile=itemView.findViewById<LinearLayout>(R.id.tile)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): vewholder {
        return vewholder(LayoutInflater.from(context).inflate(R.layout.item,parent,false))
    }

    override fun onBindViewHolder(holder: vewholder, position: Int) {
        val movieModel=list[position]
        Picasso.get().load(movieModel.poster).into(holder.image)
        holder.title.text=movieModel.title
        holder.plot.text=movieModel.plot
        holder.tile.setOnClickListener{
            var intent=Intent(context,DetailsScreen::class.java)
            intent.putExtra("data",movieModel)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.applicationContext.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
       return list.size
    }
}