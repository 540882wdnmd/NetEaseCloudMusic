package com.example.neteasecloudmusic.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.ViewPropertyAnimatorListener
import androidx.core.view.WindowInsetsCompat
import com.example.neteasecloudmusic.R

class WelcomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)


        //设置图片动画
        ViewCompat.animate(findViewById(R.id.startPage)).apply {
            //缩放，变成1.0倍
            scaleX(1.0f)
            scaleY(1.0f)
            //动画时常1秒
            duration = 1000
            //动画监听
            setListener(object : ViewPropertyAnimatorListener {
                override fun onAnimationEnd(view: View) { //动画结束
                    //进入主界面，并结束掉该页面
                    startActivity(Intent(this@WelcomeActivity, MainActivity::class.java))
                    finish()
                }

                override fun onAnimationCancel(view: View) {
                }

                override fun onAnimationStart(view: View) {
                }
            })
        }

    }
}