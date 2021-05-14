package com.example.genshinwish.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.genshinwish.databinding.FragmentMusicBinding


class MusicFragment : Fragment() {
   private lateinit var binding : FragmentMusicBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentMusicBinding.inflate(inflater)
        return binding.root
    }
}