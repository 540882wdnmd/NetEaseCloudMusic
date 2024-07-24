package com.example.neteasecloudmusic.view.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import com.example.neteasecloudmusic.databinding.FragmentDrawerBinding
import com.example.neteasecloudmusic.model.data.Account
import com.example.neteasecloudmusic.model.data.User
import com.example.neteasecloudmusic.model.repository.Repository
import com.example.neteasecloudmusic.model.sharedPreference.SharedPreferencesHelper
import com.example.neteasecloudmusic.view.activity.LoginActivity
import com.example.neteasecloudmusic.viewmodel.fragmentviewmodel.DrawerViewModel
import com.squareup.picasso.Picasso

class DrawerFragment : Fragment() {
    private val TAG = "DrawerFragment"

    private var _binding: FragmentDrawerBinding? = null
    private val drawerViewModel by lazy { ViewModelProvider(this).get(DrawerViewModel::class.java) }

    private lateinit var drawerText: TextView
    private lateinit var userNickname : TextView
    private lateinit var userAvatar : ImageView
    private lateinit var cellphoneBtn : Button
    private lateinit var emailBtn : Button
    private lateinit var qrcodeBtn : Button
    private lateinit var logoutBtn : Button

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDrawerBinding.inflate(inflater, container, false)
        val root: View = binding.root

        drawerText = binding.drawerText
        userNickname = binding.userNicknameText
        userAvatar = binding.userAvatarImageView
        cellphoneBtn = binding.cellphoneLoginBtn
        emailBtn = binding.emailLoginBtn
        qrcodeBtn = binding.qrcodeLoginBtn
        logoutBtn = binding.logoutButton

        cellphoneBtn.setOnClickListener {
            drawerViewModel.getLoginResponse("15976404976","20050602l")
        }

        logoutBtn.setOnClickListener {
            drawerViewModel.getLogoutResponse()
        }

        if(SharedPreferencesHelper.getLoginStatus() == true){
            drawerText.text = "欢迎回来"
            userNickname.text = SharedPreferencesHelper.getAccountNickName()
            userAvatar.visibility = View.VISIBLE
            Picasso.get().load(SharedPreferencesHelper.getAvatarImageUrl()).into(userAvatar)
            cellphoneBtn.visibility = View.GONE
            emailBtn.visibility = View.GONE
            qrcodeBtn.visibility = View.GONE
            logoutBtn.visibility = View.VISIBLE
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        drawerViewModel.logoutResponseLiveData.observe(this@DrawerFragment.viewLifecycleOwner){
            val logoutResponse = it.getOrNull()
            if (logoutResponse!=null){
                drawerText.text = "请登录"
                userNickname.visibility = View.GONE
                userAvatar.visibility = View.GONE
                cellphoneBtn.visibility = View.VISIBLE
                emailBtn.visibility = View.VISIBLE
                qrcodeBtn.visibility = View.VISIBLE
                logoutBtn.visibility = View.GONE
                SharedPreferencesHelper.logout()
            }
        }

        drawerViewModel.loginResponseLiveData.observe(this@DrawerFragment.viewLifecycleOwner) {
            val loginResponse = it.getOrNull()
            if (loginResponse!=null){
                if (loginResponse.code==200){
                    drawerText.text = "欢迎回来"
                    userNickname.text = loginResponse.profile.nickname
                    userAvatar.visibility = View.VISIBLE
                    Picasso.get().load(loginResponse.profile.avatarUrl).into(userAvatar)
                    cellphoneBtn.visibility = View.GONE
                    emailBtn.visibility = View.GONE
                    qrcodeBtn.visibility = View.GONE
                    logoutBtn.visibility = View.VISIBLE

                    SharedPreferencesHelper.login(Account(loginResponse.profile.nickname,loginResponse.profile.avatarUrl,loginResponse.cookie))
                }
            }else{
                Log.e(TAG,"loginResponse is null")
            }
        }



    }

    override fun onStart() {
        super.onStart()

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}