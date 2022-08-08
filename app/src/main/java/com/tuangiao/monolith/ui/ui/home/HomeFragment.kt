package com.tuangiao.monolith.ui.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.tuangiao.monolith.common.DataResult
import com.tuangiao.monolith.databinding.FragmentHomeBinding
import com.tuangiao.monolith.presentation.mvvm.LegoThemeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val legoThemeViewModel : LegoThemeViewModel by viewModels()

    // This property is only valid between onCreateView and
    // onDestroyView.
//    private val binding get() = _binding!!
    private lateinit var legoThemeAdapter: LegoThemeAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        legoThemeAdapter = LegoThemeAdapter();
        binding.rvPosts.adapter = legoThemeAdapter
        legoThemeViewModel.legoThemes.observe(viewLifecycleOwner) {
            when (it) {
                is DataResult.Loading -> binding.loadingPb.visibility = View.VISIBLE
                is DataResult.Error -> {
                    binding.loadingPb.visibility = View.GONE
                    Toast.makeText(context, it.exception.message, Toast.LENGTH_SHORT).show()
                }
                is DataResult.Success -> {
                    binding.loadingPb.visibility = View.GONE
                    legoThemeAdapter.submitList(it.data)
                }
            }
        }
        return root
    }

//    override fun onDestroyView() {
//        super.onDestroyView()
//        binding = null
//    }
}