package com.example.neteasecloudmusic.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.example.neteasecloudmusic.R
import com.example.neteasecloudmusic.databinding.FragmentRecommendBinding
import com.example.neteasecloudmusic.databinding.FragmentRoamBinding
import com.example.neteasecloudmusic.viewmodel.RecommendViewModel
import com.example.neteasecloudmusic.viewmodel.RoamViewModel

class RoamFragment : Fragment() {

    private var _binding: FragmentRoamBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val roamViewModel =
            ViewModelProvider(this).get(RoamViewModel::class.java)

        _binding = FragmentRoamBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textRoam
        roamViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}