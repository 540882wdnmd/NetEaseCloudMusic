package com.example.neteasecloudmusic

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.ViewPropertyAnimatorListener
import com.example.neteasecloudmusic.model.sharedPreference.SharedPreferencesHelper
import com.example.neteasecloudmusic.view.activity.MainActivity

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