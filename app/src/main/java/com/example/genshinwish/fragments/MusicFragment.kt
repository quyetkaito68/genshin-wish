package com.example.genshinwish.fragments

import android.Manifest
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
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


class MusicFragment : Fragment() {
    companion object{
        const val MY_PERMISSION_REQUEST = 1
        fun newInstance(): MusicFragment{
            return MusicFragment()
        }
    }
   private lateinit var binding : FragmentMusicBinding
   var listSongs= ArrayList<SongInfo>()
   var musicAdapter: MySongAdapter?=null

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
        if (ActivityCompat.checkSelfPermission(requireContext(),Manifest.permission.READ_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
            Log.e("quyetkaito","chua duoc")
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),111)
        }else{
            Log.e("quyetkaito","Permission granted")
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
        val song = Song("Genshin Impact Battle Song","Paimon",
            R.drawable.razor,
            R.raw.battle_paimon)
        bundle.putSerializable("object_song",song)
        intent.putExtra("Bundle",bundle)
        requireActivity().startService(intent)
    }

    private fun getMusic(){
        val songUri:Uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        var selection: String = MediaStore.Audio.Media.IS_MUSIC +"!=0"
        var rs = activity?.contentResolver?.query(songUri,null,selection,null,null)
        if (rs!=null && rs.moveToFirst()){
            do {
                var url = rs.getString(rs.getColumnIndex(MediaStore.Audio.Media.DATA))
                Log.e("quyetkaito",url)
                var author = rs.getString(rs.getColumnIndex(MediaStore.Audio.Media.ARTIST))
                Log.e("quyetkaito",author)
                var title = rs.getString(rs.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME))
                listSongs.add(SongInfo(title,author,url))
                Log.e("quyetkaito",listSongs[0].Title.toString())
            } while(rs.moveToNext())
        }
        musicAdapter = MySongAdapter(listSongs)
        for (i in listSongs){
            Log.e("quyetkaito",i.Title.toString())
        }
//        txt_song_name.text = listSongs[0].Title.toString()
//        txt_singer.text = listSongs[0].Author
//
        binding.rvSong.adapter = SongRecyclerViewAdapter(listSongs)
        binding.rvSong.layoutManager= LinearLayoutManager(context)
        binding.rvSong.setHasFixedSize(true)
        //listMusic.adapter = musicAdapter //==> không  chạy
       // musicAdapter!!.notifyDataSetChanged()

    }
//    private fun isPermissionGranted():Boolean{
//        return activity?.let { ContextCompat.checkSelfPermission(it,android.Manifest.permission.READ_EXTERNAL_STORAGE) } == PackageManager.PERMISSION_GRANTED
//    }
//    private fun enableReadStorage(){
//        if (isPermissionGranted()){
//            Log.e("quyetkaito","PERMISSION GRANTED")
//            getMusic()
//        }else{
//            activity?.let {
//                ActivityCompat.requestPermissions(it, arrayOf<String>(android.Manifest.permission.READ_EXTERNAL_STORAGE),
//                    MY_PERMISSION_REQUEST)
//            }
//        }
//    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode== 111){
            if (grantResults.contains(PackageManager.PERMISSION_GRANTED)){
                getMusic()
            }
        }
    }



    inner class MySongAdapter:BaseAdapter {
        var myListSong = ArrayList<SongInfo>()

        constructor(myListSong: ArrayList<SongInfo>) : super() {
            this.myListSong = myListSong
        }

        override fun getCount(): Int {
            return myListSong.size
        }

        override fun getItem(position: Int): Any {
            return  myListSong[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            var myview = layoutInflater.inflate(R.layout.layout_music_component,null)
            var song= myListSong[position]
            myview.textview1.text = song.Title
            myview.textview2.text = song.Author
            return myview
        }

    }
}

