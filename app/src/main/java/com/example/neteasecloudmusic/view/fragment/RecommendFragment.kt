package com.example.neteasecloudmusic.view.fragment

import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.neteasecloudmusic.databinding.ActivityMainBinding
import com.example.neteasecloudmusic.databinding.FragmentRecommendBinding
import com.example.neteasecloudmusic.databinding.ViewSimpleMusicPlayerBinding
import com.example.neteasecloudmusic.model.sharedPreference.SharedPreferencesHelper
import com.example.neteasecloudmusic.view.activity.MainActivity
import com.example.neteasecloudmusic.view.adapter.DailyRecommendSongsAdapter
import com.example.neteasecloudmusic.viewmodel.activityviewmodel.MainViewModel
import com.example.neteasecloudmusic.viewmodel.fragmentviewmodel.MusicPlayerBottomSheetDialogViewModel
import com.example.neteasecloudmusic.viewmodel.fragmentviewmodel.RecommendViewModel
import com.squareup.picasso.Picasso
import java.io.IOException

class RecommendFragment : Fragment() {

    private var _binding: FragmentRecommendBinding? = null
    private val binding get() = _binding!!
    private val recommendViewModel by lazy { ViewModelProvider(this)[RecommendViewModel::class.java] }
    private val musicPlayerBottomSheetDialogViewModel by lazy { ViewModelProvider(this)[MusicPlayerBottomSheetDialogViewModel::class.java] }
    private lateinit var adapter: DailyRecommendSongsAdapter

    fun interface ItemClickListener{
        fun onItemClick(id : Long)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentRecommendBinding.inflate(inflater, container, false)
        val root: View = binding.root

        adapter =DailyRecommendSongsAdapter(recommendViewModel.songList) {
            recommendViewModel.getSongResponse(it)
        }
        val recyclerView: RecyclerView = binding.dailyRecommendSongsRecyclerView

        val layoutManager = LinearLayoutManager(this.context)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = layoutManager
        recyclerView.addItemDecoration(DividerItemDecoration(context,DividerItemDecoration.VERTICAL))

        recommendViewModel.dailyRecommendSongLiveData.observe(viewLifecycleOwner) {
            val dailySongs = it.getOrNull()
            if (dailySongs != null) {
                recommendViewModel.songList.clear()
                recommendViewModel.songList.addAll(dailySongs)
                adapter.notifyDataSetChanged()
            } else {
                recommendViewModel.songList.clear()
                adapter.notifyDataSetChanged()
                Toast.makeText(activity, "未能查询到日推歌单", Toast.LENGTH_SHORT).show()
                it.exceptionOrNull()?.printStackTrace()
            }
        }


        recommendViewModel.songLiveData.observe(viewLifecycleOwner){
            val songResponse = it.getOrNull()
            if (songResponse!= null){
                musicPlayerBottomSheetDialogViewModel.playMusic(songResponse[0].url)
            }else{
                it.exceptionOrNull()?.printStackTrace()
            }
        }
        return root
    }

    override fun onResume() {
        super.onResume()
        SharedPreferencesHelper.getCookie()
            ?.let { recommendViewModel.getDailyRecommendSongResponse(it) }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}