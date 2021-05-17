package com.example.genshinwish.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.genshinwish.R
import com.example.genshinwish.databinding.FragmentMusicBinding
import com.example.genshinwish.models.Song
import com.example.genshinwish.notification.Mp3Service
import kotlinx.android.synthetic.main.fragment_music.*

class MusicFragment : Fragment() {
    companion object{
        fun newInstance(): MusicFragment{
            return MusicFragment()
        }
    }
   private lateinit var binding : FragmentMusicBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentMusicBinding.inflate(inflater)
        binding.btnStartService.setOnClickListener {
            clickStartService()
            btn_start_service.isEnabled = false
        }
        binding.btnStopService.setOnClickListener {
            clickStopService()
            btn_start_service.isEnabled = true
        }
        return binding.root
    }

    private fun clickStopService() {
        val intent = Intent(activity, Mp3Service::class.java)
            requireActivity().stopService(intent)
    }

    private fun clickStartService() {
        val intent = Intent(activity, Mp3Service::class.java)
        val bundle = Bundle()
        val song = Song("Genshin Impact Battle Song","Paimon",
            R.drawable.razor,
            R.raw.battle_paimon)
        bundle.putSerializable("object_song",song)
        intent.putExtra("Bundle",bundle)
        requireActivity().startService(intent)
    }
}