package com.yechaoa.materialdesign.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.diaryplanner.Adapter.ScheduleAdapter
import com.example.diaryplanner.AddDialogFragment
import com.example.diaryplanner.R
import com.example.diaryplanner.ScheduleItem
import com.example.diaryplanner.databinding.FragmentScheduleBinding

class ScheduleFragment : Fragment() {
    private lateinit var binding: FragmentScheduleBinding
    private var dataList: MutableList<ScheduleItem> = mutableListOf()
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ScheduleAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentScheduleBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAddButton()
        val startTime = "19:00"
        val endTime: String? = "20:05"
        val duration = calculateDuration(startTime, endTime)
        val test = ScheduleItem(
            startTime,
            endTime,
            ContextCompat.getColor(requireContext(), R.color.green),
            R.drawable.ic_main_hashtag,
            "Какое-то очень длинное напоминание",
            duration,
            false
        )
        val test1 = ScheduleItem(
            startTime,
            endTime,
            ContextCompat.getColor(requireContext(), R.color.blue),
            R.drawable.ic_main_hashtag,
            "Какое-то очень длинное напоминание на 3 строчки",
            duration,
            true
        )
        val test2 = ScheduleItem(
            startTime,
            endTime,
            ContextCompat.getColor(requireContext(), R.color.pink),
            R.drawable.ic_main_hashtag,
            "Какое-то напоминание",
            duration,
            false
        )
        dataList.add(test)
        dataList.add(test1)
        dataList.add(test2)

        setRecycler()

    }

    companion object {
        @JvmStatic
        fun newInstance() = ScheduleFragment()
    }

    private fun setAddButton() {
        binding.fabAdd.setOnClickListener {
            AddDialogFragment(R.layout.fragment_add).show(childFragmentManager, "add fragment")
        }
    }

    fun calculateDuration(startTime: String, endTime: String?): String {
        if (endTime == null) {
            return "бессрочно"
        }

        val startParts = startTime.split(":")
        val endParts = endTime.split(":")

        val startHours = startParts[0].toInt()
        val startMinutes = startParts[1].toInt()

        val endHours = endParts[0].toInt()
        val endMinutes = endParts[1].toInt()

        val durationMinutes = (endHours * 60 + endMinutes) - (startHours * 60 + startMinutes)

        val durationHours = durationMinutes / 60
        val remainingMinutes = durationMinutes % 60

        return when {
            durationHours > 0 && remainingMinutes > 0 -> "$durationHours ч. $remainingMinutes мин."
            durationHours > 0 -> "$durationHours ч."
            remainingMinutes > 0 -> "$remainingMinutes мин."
            else -> "0 мин."
        }
    }

    private fun setRecycler() {
        recyclerView = binding.recycleSchedule
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        adapter = ScheduleAdapter(dataList)
        recyclerView.adapter = adapter

        if (dataList.isEmpty()) {
            binding.scheduleBlank.visibility = View.VISIBLE
        } else {
            binding.scheduleBlank.visibility = View.GONE
        }
    }


}