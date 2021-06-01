package com.example.genshinwish.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.genshinwish.R
import com.example.genshinwish.SongRecyclerViewAdapter
import com.example.genshinwish.databinding.FragmentMusicBinding
import com.example.genshinwish.models.Song
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
    var listSongs = ArrayList<Song>()
    var mediaPlayer: MediaPlayer? = null
    var urltmp: String = ""
    var durationtmp: Int = 0
    var isPlaying: Boolean = false

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
            btn_start_service.setBackgroundResource(R.drawable.ic_play_arrow)
        }
        //request Permission start
        if (ActivityCompat.checkSelfPermission(requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
        ) {
            Log.e("quyetkaito", "chua duoc")
            requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 111)
        } else {
            Log.e("quyetkaito", "Permission granted")
            getMusic()
        }
        //request Permission end


        return binding.root
    }

    private fun createTimeLabel(time: Int): String {
        var timeLabel = ""
        var min = time / 1000 / 60
        var sec = time / 1000 % 60
        timeLabel = "$min:"
        if (sec < 10) timeLabel += "0"
        timeLabel += sec
        return timeLabel
    }

    private fun clickStopService() {
        val intent = Intent(activity, Mp3Service::class.java)
        requireActivity().stopService(intent)
        mediaPlayer?.release()
    }

    private fun clickStartService() {
        // set default music start
        if (urltmp.equals("")) {
            urltmp = listSongs[listSongs.size - 1].url
            binding.txtSongName.text = listSongs[listSongs.size - 1].title
            binding.txtSinger.text = listSongs[listSongs.size - 1].singer
            startMusic(listSongs[listSongs.size - 1])
        }
        // set default music end

        val intent = Intent(activity, Mp3Service::class.java)
        val bundle = Bundle()
//        val song = Song("Genshin Impact Battle Song","Paimon",
//            R.drawable.razor,
//            R.raw.battle_paimon)
        val song2 = Song(binding.txtSongName.text.toString(),
            binding.txtSinger.text.toString(),
            R.drawable.razor,
            R.raw.battle_paimon,
            urltmp, durationtmp)


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
                // Log.e("quyetkaito", url)
                var author = rs.getString(rs.getColumnIndex(MediaStore.Audio.Media.ARTIST))
                //Log.e("quyetkaito", author)
                var title = rs.getString(rs.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME))
                // Log.e("quyetkaito", title)
                var duration = rs.getInt(rs.getColumnIndex(MediaStore.Audio.Media.DURATION))
                listSongs.add(Song(title, author, R.drawable.razor, null, url, duration))
            } while (rs.moveToNext())
        }
        //kiem tra ket qua thu duoc trong list
        for (i in listSongs) {
            Log.e("quyetkaito", i.title)
        }
        //setup RecyclerView Adapter
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
        //xử lý các thứ khác liên quan đến item ở đây :)
        val clickedItem = listSongs[position]
        //set current song display start
        binding.txtSongName.text = clickedItem.title
        binding.txtSinger.text = clickedItem.singer
        urltmp = clickedItem.url
        durationtmp = clickedItem.duration
        binding.tvTimeEnd.text = createTimeLabel(durationtmp)
        if (mediaPlayer?.isPlaying == true) {
            mediaPlayer?.release()
            startMusic(clickedItem)
        } else {
            startMusic(clickedItem)
        }
        clickStartService()
        binding.btnStartService.setBackgroundResource(R.drawable.ic_pause)
        //set current song display end

        //seekbar process
        if (mediaPlayer?.isPlaying == true) {
            binding.seekbarProgress.max = durationtmp
            binding.seekbarProgress.setOnSeekBarChangeListener(
                object : SeekBar.OnSeekBarChangeListener {
                    override fun onProgressChanged(
                        seekBar: SeekBar?,
                        progress: Int,
                        fromUser: Boolean,
                    ) {
                        if (fromUser) {
                            mediaPlayer?.seekTo(progress)
                        }
                    }

                    override fun onStartTrackingTouch(seekBar: SeekBar?) {

                    }

                    override fun onStopTrackingTouch(seekBar: SeekBar?) {

                    }

                }
            )

            Thread(Runnable {
                while (mediaPlayer != null) {
                    try {
                        var msg = Message()
                        msg.what = mediaPlayer?.currentPosition!!
                        handler.sendMessage(msg)
                        Thread.sleep(1000)
                    } catch (e: InterruptedException) {
                    }
                }
            }).start()

        }//seekbar progress end
    }

    @SuppressLint("HandlerLeak")
    var handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            var currentPosition = msg.what
            //update progress
            binding.seekbarProgress.progress = currentPosition
            //update labels
            var elapsedTime = createTimeLabel(currentPosition)
            binding.tvTimeStart.text = elapsedTime

            var remainingTime = createTimeLabel(durationtmp - currentPosition)
            binding.tvTimeEnd.text = remainingTime
        }
    }

    private fun startMusic(song: Song) {
        // mediaPlayer = MediaPlayer.create(applicationContext, song.resource)
        mediaPlayer = MediaPlayer()
        try {
            mediaPlayer?.isLooping = true
            mediaPlayer?.setDataSource(song.url) //play audio in storage demo
            mediaPlayer?.prepare()
            mediaPlayer?.start()
        } catch (e: Exception) {
        }
        isPlaying = true

    }

}

