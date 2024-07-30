package com.example.neteasecloudmusic.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.compose.material3.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.neteasecloudmusic.R
import com.example.neteasecloudmusic.databinding.FragmentDrawerBinding
import com.example.neteasecloudmusic.databinding.ItemDialogLoginBinding
import com.example.neteasecloudmusic.model.data.Account
import com.example.neteasecloudmusic.model.data.LoginResponse
import com.example.neteasecloudmusic.model.sharedPreference.SharedPreferencesHelper
import com.example.neteasecloudmusic.viewmodel.fragmentviewmodel.DrawerViewModel
import com.squareup.picasso.Picasso

class DrawerFragment : Fragment() {
    private val TAG = "DrawerFragment"

    private var _binding: FragmentDrawerBinding? = null
    private val drawerViewModel by lazy { ViewModelProvider(this)[DrawerViewModel::class.java] }

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
            showLoginDialog()
        }

        logoutBtn.setOnClickListener {
            drawerViewModel.getLogoutResponse()
        }

        if (SharedPreferencesHelper.getLoginStatus()==true){
            loginUI()
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        drawerViewModel.logoutResponseLiveData.observe(this@DrawerFragment.viewLifecycleOwner){
            val logoutResponse = it.getOrNull()
            if (logoutResponse!=null){
                logoutUI()
                SharedPreferencesHelper.logout()
            }
        }

        drawerViewModel.loginResponseLiveData.observe(this@DrawerFragment.viewLifecycleOwner) {
            val loginResponse = it.getOrNull()
            if (loginResponse!=null){
                if (loginResponse.code==200){
                    firstLoginUI(loginResponse)
                    SharedPreferencesHelper.login(Account(loginResponse.profile.nickname,loginResponse.profile.avatarUrl,loginResponse.cookie))
                }
            }else{
                Log.e(TAG,"loginResponse is null")
            }
        }



    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun firstLoginUI(loginResponse: LoginResponse){
        drawerText.text = "欢迎回来"
        userNickname.text = loginResponse.profile.nickname
        userAvatar.visibility = View.VISIBLE
        Picasso.get().load(loginResponse.profile.avatarUrl).into(userAvatar)
        cellphoneBtn.visibility = View.GONE
        emailBtn.visibility = View.GONE
        qrcodeBtn.visibility = View.GONE
        logoutBtn.visibility = View.VISIBLE
    }

    private fun loginUI(){
        drawerText.text = "欢迎回来"
        userNickname.text = SharedPreferencesHelper.getAccountNickName()
        userAvatar.visibility = View.VISIBLE
        Picasso.get().load(SharedPreferencesHelper.getAvatarImageUrl()).into(userAvatar)
        cellphoneBtn.visibility = View.GONE
        emailBtn.visibility = View.GONE
        qrcodeBtn.visibility = View.GONE
        logoutBtn.visibility = View.VISIBLE
    }

    private fun logoutUI(){
        drawerText.text = "请登录"
        userNickname.visibility = View.GONE
        userAvatar.visibility = View.GONE
        cellphoneBtn.visibility = View.VISIBLE
        emailBtn.visibility = View.VISIBLE
        qrcodeBtn.visibility = View.VISIBLE
        logoutBtn.visibility = View.GONE
    }

    private fun showLoginDialog(){
        val binding : ItemDialogLoginBinding = ItemDialogLoginBinding.inflate(layoutInflater)
        this@DrawerFragment.context?.let {
            AlertDialog.Builder(it).apply {
                setTitle("请输入账号密码")
                setView(binding.root)

                setPositiveButton("确认"){ _, _ ->
                    drawerViewModel.getLoginResponse(binding.accountEditText.text.toString(),
                        binding.passwordEdit.text.toString()
                    )
                }

                setCancelable(true)
                setNegativeButton("取消"){
                        dialog, _ -> dialog.cancel()
                }

                val dialog : AlertDialog =  this.create()
                if (!dialog.isShowing){
                    dialog.show()
                }
            }
        }
    }
}