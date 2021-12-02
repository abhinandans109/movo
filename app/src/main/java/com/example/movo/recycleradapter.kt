package com.example.movo

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView

class recycleradapter(
    var context: Context,
    var list: ArrayList<String>,
    var movies: MutableMap<String, ArrayList<String>>?
) :
    RecyclerView.Adapter<recycleradapter.vewholder>() {
    class vewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            var moviewname=itemView.findViewById<Button>(R.id.moviename)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): vewholder {
        return vewholder(LayoutInflater.from(context).inflate(R.layout.item,parent,false))
    }

    override fun onBindViewHolder(holder: vewholder, position: Int) {
        if(list[position].contains("%"))
        holder.moviewname.text=list[position].split("%")[1]
        else
            holder.moviewname.text=list[position]
        holder.moviewname.setOnClickListener{
            if(!list[position].contains("%")){
                var intent= Intent(context,createlist::class.java)
                intent.putExtra("listfromadapter", movies!![list[position]])
                intent.putExtra("playlistname", list[position])
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(intent)
            }else{
                var intent= Intent(context,search::class.java)
                intent.putExtra("moviename", list[position].split("%")[1])
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(intent)
            }
        }

    }

    override fun getItemCount(): Int {
       return list.size
    }
}