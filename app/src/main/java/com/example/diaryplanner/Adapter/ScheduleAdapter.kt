package com.example.diaryplanner.Adapter

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.diaryplanner.R
import com.example.diaryplanner.ScheduleItem

class ScheduleAdapter(private val dataList: MutableList<ScheduleItem>) : RecyclerView.Adapter<ScheduleAdapter.StatisticViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleAdapter.StatisticViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.schedule_item, parent, false)
        return ScheduleAdapter.StatisticViewHolder(view)
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: ScheduleAdapter.StatisticViewHolder, position: Int) {
        val item = dataList[position]

        holder.startTimeTextView.text = item.startTime
        holder.endTimeTextView.text = item.endTime

        // Установка динамического цвета икноки
        val backgroundColor = item.color
        // Определение, является ли фон темным или светлым
        val isBackgroundDark = (Color.red(backgroundColor) + Color.green(backgroundColor) + Color.blue(backgroundColor)) / 3 < 128
        val originalDrawable = ContextCompat.getDrawable(holder.itemView.context, item.icon)
        val wrappedDrawable = DrawableCompat.wrap(originalDrawable!!.mutate())
        // Применяем цвет в зависимости от того, является ли фон темным или светлым
        val iconColor = if (isBackgroundDark) Color.WHITE else R.color.darkGray
        DrawableCompat.setTint(wrappedDrawable, iconColor)
        holder.iconSchedule.setImageDrawable(wrappedDrawable)
        // Установка текста напоминания
        holder.contentTextView.text = item.text
        // Установка длительности напоминания
        holder.durationTextView.text = item.duration
        // Установка цвета заднего фона напоминания
        ViewCompat.setBackgroundTintList(holder.ovalBackground, ColorStateList.valueOf(item.color))
        if (item.isCompleteTask) {
            val checkDrawable = ContextCompat.getDrawable(holder.itemView.context, R.drawable.ic_main_check)
            holder.isCompleteButton.setImageDrawable(checkDrawable)
        } else {
            val circleDrawable = ContextCompat.getDrawable(holder.itemView.context, R.drawable.ic_main_complete_circle)
            holder.isCompleteButton.setImageDrawable(circleDrawable)
        }
        holder.isCompleteButton.setOnClickListener {
            // Изменение значения isCompleteTask
            item.isCompleteTask = !item.isCompleteTask

            // Изменение фона кнопки в зависимости от значения isCompleteTask
            if (item.isCompleteTask) {
                val checkDrawable = ContextCompat.getDrawable(holder.itemView.context, R.drawable.ic_main_check)
                holder.isCompleteButton.setImageDrawable(checkDrawable)
            } else {
                val circleDrawable = ContextCompat.getDrawable(holder.itemView.context, R.drawable.ic_main_complete_circle)
                holder.isCompleteButton.setImageDrawable(circleDrawable)
            }

            // Дополнительно, вы можете уведомить адаптер о том, что данные изменились
            notifyDataSetChanged()
        }
        if (item.isCompleteTask) {
            // Зачеркивание текста
            holder.contentTextView.paintFlags = holder.contentTextView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

            // Установка полупрозрачности
            holder.contentTextView.alpha = 0.5f
            holder.durationTextView.alpha = 0.5f
        } else {
            // Отмена зачеркивания текста
            holder.contentTextView.paintFlags = holder.contentTextView.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()

            // Отмена полупрозрачности
            holder.contentTextView.alpha = 1.0f
            holder.durationTextView.alpha = 1.0f
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    class StatisticViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val startTimeTextView: TextView = itemView.findViewById(R.id.start_time)
        val endTimeTextView: TextView = itemView.findViewById(R.id.end_time)
        val ovalBackground: LinearLayout = itemView.findViewById(R.id.schedule_oval_background)
        val iconSchedule: ImageView = itemView.findViewById(R.id.schedule_icon)
        val contentTextView: TextView = itemView.findViewById(R.id.schedule_text)
        val durationTextView: TextView = itemView.findViewById(R.id.schedule_duration)
        val isCompleteButton: ImageButton = itemView.findViewById(R.id.complete_schedule_button)
    }

}