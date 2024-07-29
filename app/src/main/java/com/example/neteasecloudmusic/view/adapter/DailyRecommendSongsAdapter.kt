package com.example.neteasecloudmusic.view.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.neteasecloudmusic.NetEaseCloudMusicApplication
import com.example.neteasecloudmusic.R
import com.example.neteasecloudmusic.databinding.ViewSimpleMusicPlayerBinding
import com.example.neteasecloudmusic.model.data.DailyRecommendSongsResponse
import com.example.neteasecloudmusic.model.data.SongResponse
import com.example.neteasecloudmusic.view.fragment.RecommendFragment
import com.example.neteasecloudmusic.viewmodel.activityviewmodel.MainViewModel
import com.example.neteasecloudmusic.viewmodel.fragmentviewmodel.RecommendViewModel
import com.squareup.picasso.Picasso

class DailyRecommendSongsAdapter(private val recommendViewModel: RecommendViewModel, private val mainViewModel: MainViewModel,private val songList : List<DailyRecommendSongsResponse.DailySongs>): RecyclerView.Adapter<DailyRecommendSongsAdapter.ViewHolder>(){


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
            val id : Long = dailySong.id
            Log.e("MUSIC",id.toString())
            recommendViewModel.getSongResponse(id)
            Log.e("MUSIC",dailySong.al.picUrl)
            mainViewModel.getSong(dailySong)
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