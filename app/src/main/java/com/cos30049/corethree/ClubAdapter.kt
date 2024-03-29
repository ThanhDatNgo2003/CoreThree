package com.cos30049.corethree

// ClubAdapter.kt

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class ClubAdapter(private var clubs: List<Club>) :
    RecyclerView.Adapter<ClubAdapter.ClubViewHolder>() {

    class ClubViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val doorIconImageView: ImageView = itemView.findViewById(R.id.doorIconImageView)
        val dateTimeTextView: TextView = itemView.findViewById(R.id.dateTimeTextView)
        val additionalInfoTextView: TextView = itemView.findViewById(R.id.additionalInfoTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClubViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_club, parent, false)
        return ClubViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ClubViewHolder, position: Int) {
        val currentClub = clubs[position]
        holder.titleTextView.text = currentClub.title
        // Set door icon based on whether it's open or not
        if (currentClub.isDoorOpen) {
            holder.doorIconImageView.visibility = View.GONE
        } else {
            holder.doorIconImageView.visibility = View.VISIBLE
        }
        holder.dateTimeTextView.text = currentClub.dateTime
        holder.additionalInfoTextView.text = currentClub.additionalInfo
    }

    override fun getItemCount(): Int {
        return clubs.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newList: List<Club>) {
        val diffResult = DiffUtil.calculateDiff(ClubDiffCallback(clubs, newList))
        clubs = newList
        diffResult.dispatchUpdatesTo(this)
    }
}
