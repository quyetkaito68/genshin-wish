package com.example.genshinwish.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.genshinwish.R
import com.example.genshinwish.databinding.FragmentHomeBinding
import com.example.genshinwish.databinding.FragmentPublicBinding


class PublicFragment : Fragment() {
    private lateinit var binding: FragmentPublicBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentPublicBinding.inflate(inflater)
        return binding.root
    }

}