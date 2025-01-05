//
//package com.edu.auri.backend.dailylogs
//
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.TextView
//import androidx.recyclerview.widget.RecyclerView
//import com.edu.auri.R
//
//class DailyLogsAdapter(private var logs: List<DailyLog>) : RecyclerView.Adapter<DailyLogsAdapter.LogViewHolder>() {
//
//    inner class LogViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val tvDate: TextView = itemView.findViewById(R.id.tvDate)
//        val tvMood: TextView = itemView.findViewById(R.id.tvMood)
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LogViewHolder {
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_daily_log, parent, false)
//        return LogViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: LogViewHolder, position: Int) {
//        val log = logs[position]
//        holder.tvDate.text = "Date: ${log.docId}"
//        holder.tvMood.text = "Mood: ${log.mood}"
//        // You can show more info here if you like
//    }
//
//    override fun getItemCount(): Int = logs.size
//
//    fun updateLogs(newLogs: List<DailyLog>) {
//        logs = newLogs
//        notifyDataSetChanged()
//    }
//}
