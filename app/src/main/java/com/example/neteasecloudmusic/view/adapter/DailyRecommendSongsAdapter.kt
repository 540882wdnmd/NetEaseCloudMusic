package com.example.neteasecloudmusic.view.adapter


import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.neteasecloudmusic.R
import com.example.neteasecloudmusic.model.data.DailyRecommendSongsResponse.DailySongs
import com.example.neteasecloudmusic.model.data.DailyRecommendSongsResponse.Artists
import com.example.neteasecloudmusic.view.fragment.RecommendFragment
import kotlin.properties.Delegates

class DailyRecommendSongsAdapter(private val songList : List<DailySongs>,val itemClickListener: RecommendFragment.ItemClickListener): RecyclerView.Adapter<DailyRecommendSongsAdapter.ViewHolder>(){



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
        val dailySong = songList[position]
        holder.songsName.text = dailySong.name
        holder.artistsName.text = getArtistName(dailySong.ar)

        holder.itemView.setOnClickListener {
            itemClickListener.onItemClick(dailySong.id)
        }
    }

    private fun getArtistName(artists : List<Artists>) : String{
        val listToString : MutableList<String> = mutableListOf("")
        artists.forEach {
            listToString.add(it.name)
        }
        return listToString.toString()
    }

}