package com.example.genshinwish

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.genshinwish.models.User

class UserAdapter(context: Context?, resource: Int, data: List<User>?)
    : ArrayAdapter<User>(context!!, resource, data!!) {

    lateinit var data: List<User>
    var resource: Int = 0

    init {
        this.data = data!!
        this.resource = resource
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        //return super.getView(position, convertView, parent)
        val view: View?
        val vh: ViewHolder

        if (convertView == null) {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(this.resource, parent, false)
            vh = ViewHolder(view)
            view.tag = vh
        } else {
            view = convertView
            vh = view.tag as ViewHolder
        }

        vh.email.text = "Email: " + data[position].email
        vh.name.text = "Name: " + data[position].name

        return view!!
    }

    private class ViewHolder(view: View?) {
        val email : TextView
        val name: TextView
        init {
            email = view?.findViewById(R.id.edt_email) as TextView
            name = view?.findViewById<TextView>(R.id.edt_name)
        }
    }
}