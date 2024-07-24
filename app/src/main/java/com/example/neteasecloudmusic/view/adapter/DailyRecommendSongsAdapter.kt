package com.example.neteasecloudmusic.view.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.neteasecloudmusic.R
import com.example.neteasecloudmusic.model.data.DailyRecommendSongsResponse
import com.example.neteasecloudmusic.model.data.SongResponse
import com.example.neteasecloudmusic.viewmodel.fragmentviewmodel.RecommendViewModel

class DailyRecommendSongsAdapter(private val recommendViewModel: RecommendViewModel,private val songList : List<DailyRecommendSongsResponse.DailySongs>): RecyclerView.Adapter<DailyRecommendSongsAdapter.ViewHolder>(){


    interface GetPosition{
        fun getPosition(position : Int)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val songsName: TextView = itemView.findViewById(R.id.songs_name)
        val artistsName: TextView =itemView.findViewById(R.id.artist_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val view =  LayoutInflater.from(parent.context).inflate(R.layout.item_daily_recommend_songs,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = songList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val dailySongs = songList[position]
        holder.songsName.text = dailySongs.name
        holder.artistsName.text = getArtistName(dailySongs.ar)

        holder.itemView.setOnClickListener {
            val id : Long = dailySongs.al.id
            Log.e("MUSIC",id.toString())
            recommendViewModel.getSongResponse(id)
        }



    }

    private fun getArtistName(artists : List<DailyRecommendSongsResponse.Artists>) : String{

        val listToString : MutableList<String> = mutableListOf("")

        artists.forEach {
            listToString.add(it.name)
        }

        return listToString.toString()

    }

}