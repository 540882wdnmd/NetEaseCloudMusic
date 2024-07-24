package com.example.neteasecloudmusic.view.activity

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.neteasecloudmusic.R
import com.example.neteasecloudmusic.viewmodel.fragmentviewmodel.DrawerViewModel

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val drawerViewModel = ViewModelProvider(this).get(DrawerViewModel::class.java)

        val button = findViewById<Button>(R.id.confirm_btn)
        val cellphoneEdit = findViewById<EditText>(R.id.edit_cellphone)
        val passwordEdit = findViewById<EditText>(R.id.edit_password)

        button.setOnClickListener {
            drawerViewModel.getLoginResponse("15976404976","20050602l")
        }

    }
}