package com.example.neteasecloudmusic.view.fragment

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.example.neteasecloudmusic.R
import com.example.neteasecloudmusic.databinding.DialogFragmentMusicPlayerBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class MusicPlayerBottomSheetDialogFragment : BottomSheetDialogFragment() {

    private lateinit var dialog: Dialog

    private lateinit var binding : DialogFragmentMusicPlayerBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        dialog = super.onCreateDialog(savedInstanceState)
        binding = DialogFragmentMusicPlayerBinding.inflate(layoutInflater)
        dialog.setContentView(binding.root)
        dialog.findViewById<View>(R.id.dialog_fragment_music_player).layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
        initView(binding.root)

        return dialog
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

    private fun initView(rootView : View){
    }
}