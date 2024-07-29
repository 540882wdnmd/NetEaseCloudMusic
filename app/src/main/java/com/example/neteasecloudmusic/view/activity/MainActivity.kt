package com.example.neteasecloudmusic.view.activity

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.MenuItem
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
import com.example.neteasecloudmusic.view.adapter.DailyRecommendSongsAdapter
import com.example.neteasecloudmusic.view.fragment.MusicPlayerBottomSheetDialogFragment
import com.example.neteasecloudmusic.view.fragment.RecommendFragment
import com.example.neteasecloudmusic.viewmodel.activityviewmodel.MainViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.squareup.picasso.Picasso

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var simpleMusicPlayerView : ConstraintLayout

    private val mainViewModel by lazy { ViewModelProvider(this).get(MainViewModel::class.java) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding  = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)//绑定布局

        drawerLayout = binding.drawerLayoutMain
        val toolbarMain :Toolbar = binding.appBarMain.toolbarMain
        setSupportActionBar(toolbarMain)//设置toolbar

        //导航按钮
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        actionBarDrawerToggle = ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close)
        drawerLayout.addDrawerListener(actionBarDrawerToggle)

        //设置底部导航按钮
        val bottomNavView : BottomNavigationView = binding.appBarMain.contentMain.bottomNavView
        val navController : NavController = findNavController(R.id.nav_host_fragment_activity_main)
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


        simpleMusicPlayerView = binding.appBarMain.contentMain.viewSimpleMusicPlayer.root
        simpleMusicPlayerView.setOnClickListener {
            MusicPlayerBottomSheetDialogFragment().show(supportFragmentManager,"MusicPlayerBottomSheetDialogFragment")
        }

        mainViewModel.songPicLiveData.observe(this, Observer {
            val picUrl = it.getOrNull()
            if (picUrl != null) {
                Log.e("MAIN ACTIVITY",picUrl)
                Picasso.get().load(picUrl).into(binding.appBarMain.contentMain.viewSimpleMusicPlayer.songPic)
            }

        })

        mainViewModel.songNameLiveData.observe(this, Observer {
            val songPlayingName = it.getOrNull()
            if (songPlayingName != null){
                Log.e("MAIN ACTIVITY",songPlayingName)
                binding.appBarMain.contentMain.viewSimpleMusicPlayer.songPlayingName.text = songPlayingName
            }
        })

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


    fun giveImageView(getImageView: RecommendFragment.GetImageView){
        getImageView.getImageView(binding.appBarMain.contentMain.viewSimpleMusicPlayer.songPic)
    }

}


