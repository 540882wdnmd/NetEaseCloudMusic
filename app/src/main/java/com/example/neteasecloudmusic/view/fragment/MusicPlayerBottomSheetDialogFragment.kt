package com.example.neteasecloudmusic.view.fragment

import android.app.Dialog
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.lifecycle.ViewModelProvider
import com.example.neteasecloudmusic.R
import com.example.neteasecloudmusic.databinding.DialogFragmentMusicPlayerBinding
import com.example.neteasecloudmusic.viewmodel.fragmentviewmodel.MusicPlayerBottomSheetDialogViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.io.IOException

class MusicPlayerBottomSheetDialogFragment : BottomSheetDialogFragment() {

    private lateinit var dialog: Dialog
    private lateinit var binding : DialogFragmentMusicPlayerBinding
    private lateinit var songPic : ImageView
    private lateinit var progressBar: ProgressBar
    private lateinit var lastButton: ImageButton
    private lateinit var playButton: ImageButton
    private lateinit var nextButton: ImageButton

    private val viewModel by lazy { ViewModelProvider(this@MusicPlayerBottomSheetDialogFragment)[MusicPlayerBottomSheetDialogViewModel::class.java] }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        dialog = super.onCreateDialog(savedInstanceState)

        binding = DialogFragmentMusicPlayerBinding.inflate(layoutInflater)
        dialog.setContentView(binding.root)
        dialog.findViewById<View>(R.id.dialog_fragment_music_player).layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
        initView()
        return dialog
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModelObserve()
    }

    override fun onStart() {
        super.onStart()
        //拿到系统的 bottom_sheet
        val view: FrameLayout = dialog.findViewById(com.google.android.material.R.id.design_bottom_sheet)
        //设置view高度
        view.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
        //获取behavior
        val behavior = BottomSheetBehavior.from(view)
        //设置弹出高度
        behavior.peekHeight = 3000
        //设置展开状态
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    private fun initView(){
        songPic = binding.musicPlayingImage
        progressBar = binding.musicPlayingProgressBar
        lastButton = binding.musicPlayingLast
        playButton = binding.musicPlayingStartStop
        nextButton = binding.musicPlayingNext
    }

    private fun viewModelObserve(){
        val mediaPlayer = MediaPlayer()
        viewModel.musicUrlLiveData.observe(this){
            try {
                mediaPlayer.setDataSource(it)
                mediaPlayer.setOnPreparedListener {
                    mediaPlayer.start()
                }
                mediaPlayer.prepareAsync()
            }catch (e : IOException){
                Log.e("Main Activity",e.message,e)
            }
        }
    }

}