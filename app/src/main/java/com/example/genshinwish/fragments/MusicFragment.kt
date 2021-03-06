package com.example.genshinwish.fragments

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.genshinwish.R
import com.example.genshinwish.SongRecyclerViewAdapter
import com.example.genshinwish.databinding.FragmentMusicBinding
import com.example.genshinwish.models.Song
import com.example.genshinwish.models.SongInfo
import com.example.genshinwish.notification.Mp3Service
import kotlinx.android.synthetic.main.fragment_music.*
import kotlinx.android.synthetic.main.layout_music_component.view.*
import java.lang.Exception


class MusicFragment : Fragment(), SongRecyclerViewAdapter.OnItemClickListener {
    companion object {
        const val MY_PERMISSION_REQUEST = 1
        fun newInstance(): MusicFragment {
            return MusicFragment()
        }
    }

    private lateinit var binding: FragmentMusicBinding
    var listSongs = ArrayList<SongInfo>()
    var musicAdapter: MySongAdapter? = null
    var mp: MediaPlayer? = null
    lateinit var urltmp: String

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
        if (ActivityCompat.checkSelfPermission(requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
        ) {
            Log.e("quyetkaito", "chua duoc")
            requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 111)
        } else {
            Log.e("quyetkaito", "Permission granted")
            getMusic()
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
//        val song = Song("Genshin Impact Battle Song","Paimon",
//            R.drawable.razor,
//            R.raw.battle_paimon)
        val song2 = Song(binding.txtSongName.text.toString(),
            binding.txtSinger.text.toString(),
            R.drawable.razor,
            R.raw.battle_paimon,
            urltmp)
        bundle.putSerializable("object_song", song2)
        intent.putExtra("Bundle", bundle)
        requireActivity().startService(intent)
    }

    private fun getMusic() {
        val songUri: Uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        var selection: String = MediaStore.Audio.Media.IS_MUSIC + "!=0"
        var rs = activity?.contentResolver?.query(songUri, null, selection, null, null)
        if (rs != null && rs.moveToFirst()) {
            do {
                var url = rs.getString(rs.getColumnIndex(MediaStore.Audio.Media.DATA))
                Log.e("quyetkaito", url)
                var author = rs.getString(rs.getColumnIndex(MediaStore.Audio.Media.ARTIST))
                Log.e("quyetkaito", author)
                var title = rs.getString(rs.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME))
                Log.e("quyetkaito", title)
                listSongs.add(SongInfo(title, author, url))
            } while (rs.moveToNext())
        }
        //kiem tra ket qua thu duoc trong list
        for (i in listSongs) {
            Log.e("quyetkaito", i.Title.toString())
        }
        //
        binding.rvSong.adapter = SongRecyclerViewAdapter(listSongs, this)
        binding.rvSong.layoutManager = LinearLayoutManager(context)
        binding.rvSong.setHasFixedSize(true)

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 111 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.e("quyetkaito", "First Action when permission granted")
            getMusic()
        }
    }

    override fun onItemClick(position: Int) {
        //Toast.makeText(context,"asdfad",Toast.LENGTH_SHORT).show()
        //x??? l?? c??c th??? kh??c li??n quan ?????n item ??? ????y :)
        val clickedItem = listSongs[position]
        binding.txtSongName.text = clickedItem.Title
        binding.txtSinger.text = clickedItem.Author
        urltmp = clickedItem.SongURL.toString()

    }

    inner class MySongAdapter : BaseAdapter {
        var myListSong = ArrayList<SongInfo>()

        constructor(myListSong: ArrayList<SongInfo>) : super() {
            this.myListSong = myListSong
        }

        override fun getCount(): Int {
            return myListSong.size
        }

        override fun getItem(position: Int): Any {
            return myListSong[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            var myview = layoutInflater.inflate(R.layout.layout_music_component, null)
            var song = myListSong[position]
            myview.textview1.text = song.Title
            myview.textview2.text = song.Author
            return myview
        }

    }
}

