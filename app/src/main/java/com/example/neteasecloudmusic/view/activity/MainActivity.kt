package com.example.neteasecloudmusic.view.activity

import android.content.Context
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.util.AttributeSet
import android.util.Log
import android.view.KeyEvent
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet.Layout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.neteasecloudmusic.R
import com.example.neteasecloudmusic.databinding.ActivityMainBinding
import com.example.neteasecloudmusic.databinding.ViewSimpleMusicPlayerBinding
import com.example.neteasecloudmusic.model.data.DailyRecommendSongsResponse
import com.example.neteasecloudmusic.view.adapter.DailyRecommendSongsAdapter
import com.example.neteasecloudmusic.view.fragment.MusicPlayerBottomSheetDialogFragment
import com.example.neteasecloudmusic.view.fragment.RecommendFragment
import com.example.neteasecloudmusic.viewmodel.activityviewmodel.MainViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.squareup.picasso.Picasso
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private val TAG : String = "MainActivity"

    private lateinit var binding : ActivityMainBinding
    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var musicPlayingButton: ImageButton
    private lateinit var nextMusicButton : ImageButton
    private lateinit var simpleMusicPlayerView : ViewSimpleMusicPlayerBinding
    private lateinit var toolbarMain: Toolbar
    private lateinit var bottomNavView : BottomNavigationView
    private lateinit var navController : NavController

    private val  mainViewModel by lazy { ViewModelProvider(this)[MainViewModel::class.java] }

    interface Callback{
        fun onResult(dailySongs: DailyRecommendSongsResponse.DailySongs)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding  = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)//绑定布局

        drawerLayout = binding.drawerLayoutMain
        toolbarMain = binding.appBarMain.toolbarMain
        setSupportActionBar(toolbarMain)//设置toolbar

        //导航按钮
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        actionBarDrawerToggle = ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close)
        drawerLayout.addDrawerListener(actionBarDrawerToggle)

        //设置底部导航按钮
        bottomNavView = binding.appBarMain.contentMain.bottomNavView
        navController = findNavController(R.id.nav_host_fragment_activity_main)
        val appBarConfigurationBottom = AppBarConfiguration(
            setOf(
                R.id.navigation_recommend,
                R.id.navigation_discover,
                R.id.navigation_roam,
                R.id.navigation_dynamic,
                R.id.navigation_mine
            ),drawerLayout
        )
        setupActionBarWithNavController(navController,appBarConfigurationBottom)
        bottomNavView.setupWithNavController(navController)

        //简易播放器部分
        initSimpleMusicPlayerView()
    }


    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        actionBarDrawerToggle.syncState()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId==android.R.id.home){
            drawerLayout.openDrawer(GravityCompat.START)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    //重写这个方法可以改变按下返回键后执行的操作，因为drawer相当于一个fragment，如果不重写这个方法，那么按下返回键将会直接退出活动
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode==KeyEvent.KEYCODE_BACK){
            if (drawerLayout.isDrawerOpen(GravityCompat.START)){
                drawerLayout.closeDrawers()
                return false
            }
        }
        return super.onKeyDown(keyCode, event)
    }
    private fun initSimpleMusicPlayerView(){
        simpleMusicPlayerView = binding.appBarMain.contentMain.viewSimpleMusicPlayer
        simpleMusicPlayerView.root.setOnClickListener {
            MusicPlayerBottomSheetDialogFragment().show(supportFragmentManager,"MusicPlayerBottomSheetDialogFragment")
        }

        musicPlayingButton = simpleMusicPlayerView.startPauseButton
        nextMusicButton = simpleMusicPlayerView.nextMusicButton

        mainViewModel.songPicLiveData.observe(this) {
            Log.e(TAG, it)
            Picasso.get().load(it).into(simpleMusicPlayerView.songPic)
        }
        mainViewModel.songNameLiveData.observe(this) {
            Log.e(TAG, it)
            simpleMusicPlayerView.songPlayingName.text = it
        }

        musicPlayingButton.setOnClickListener {

        }
        nextMusicButton.setOnClickListener {

        }
    }


}


