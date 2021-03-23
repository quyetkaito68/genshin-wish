package com.example.genshinwish.dialog


import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatDialogFragment
import com.example.genshinwish.R

class SettingDialog: AppCompatDialogFragment() {

    private lateinit var switch: Switch
    private lateinit var textHideShow: TextView

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val view = LayoutInflater.from(activity).inflate(R.layout.dialog_setting,null)
        switch = view.findViewById(R.id.switch_animation)
        textHideShow = view.findViewById(R.id.tv_hideShow)
        builder.setView(view)
            .setTitle("Cài đặt")
            .setPositiveButton("hehe",
                DialogInterface.OnClickListener { dialog, id ->

                })
            .setNegativeButton("ehe",
                DialogInterface.OnClickListener { dialog, id ->
                    getDialog()?.cancel()
                })

        switch.setOnCheckedChangeListener { compoundButton, onSwitch ->
            if (onSwitch){
                switch.setBackgroundColor(Color.GREEN)
            }else{
                switch.setBackgroundColor(Color.RED)
            }
        }
        return builder.create()
    }
}