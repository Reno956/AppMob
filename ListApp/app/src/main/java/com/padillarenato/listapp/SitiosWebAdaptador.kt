package com.padillarenato.listapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class SitiosWebAdaptador(val arreglosSitiosWeb:Array<String>): BaseAdapter() {
    override fun getCount(): Int {
        return arreglosSitiosWeb.size
    }

    override fun getItem(position: Int): Any {
        return arreglosSitiosWeb[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    class ViewHolder(view: View){
        val textView = view.findViewById<TextView>(R.id.textViewP)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.simple_list_item_p,parent,false)
        val viewHolder = ViewHolder(view)
        viewHolder.textView.text = arreglosSitiosWeb[position]
        return view
    }
}