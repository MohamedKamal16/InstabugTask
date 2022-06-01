package com.example.myapplication.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R

class EditTextAdapter(
    private var context: Context,
    private var keys: MutableList<String>,
    private var value: MutableList<String>/*,var recyclerViewClick: RecyclerViewClick*/
) : RecyclerView.Adapter<EditTextAdapter.EditTextViewHolder>() {


    class EditTextViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var keyText: EditText = itemView.findViewById(R.id.et_key)
        var valueText: EditText = itemView.findViewById(R.id.et_value)
        var btnAdd: ImageView = itemView.findViewById(R.id.add_et)
        var btnRemove: ImageView = itemView.findViewById(R.id.remove)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EditTextViewHolder {
        return EditTextViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_edit_text, parent, false)
        )
    }


    override fun onBindViewHolder(holder: EditTextViewHolder, position: Int) {
        holder.btnAdd.setOnClickListener {
            if (holder.keyText.text.isEmpty()) {
                Toast.makeText(context, "Please Add key ", Toast.LENGTH_SHORT).show()
            } else {
                keys.add(holder.keyText.text.toString())
            }
            if (holder.valueText.text.isEmpty()) {
                Toast.makeText(context, "Please Add Value ", Toast.LENGTH_SHORT).show()
            } else {
                value.add(holder.valueText.text.toString())
            }
            notifyDataSetChanged()
        }

        holder.btnRemove.setOnClickListener {
            if (keys.size > 1) {
                keys.removeAt(position)
                value.removeAt(position)
                notifyItemRemoved(position)
            }
            Log.w("TRY", "size ${keys.size} and ${value.size}")
        }


    }

    fun updateEditText() {
        keys.clear()
        value.clear()
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return keys.size
    }
}