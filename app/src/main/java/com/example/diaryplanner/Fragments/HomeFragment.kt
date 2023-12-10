package com.example.diaryplanner.Fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.diaryplanner.databinding.FragmentHomeBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.shrikanthravi.collapsiblecalendarview.widget.CollapsibleCalendar
import com.yechaoa.materialdesign.fragment.NoteFragment
import com.yechaoa.materialdesign.fragment.ScheduleFragment
import java.util.Calendar


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var collapsibleCalendar: CollapsibleCalendar
    private lateinit var databaseRefernece: DatabaseReference
    private var defaultTabIndex = 0
    private val CALENDAR_KEY = "Calendar"
    private val TAG = "debugTag"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        collapsibleCalendar = binding.calendarView
        databaseRefernece = FirebaseDatabase.getInstance().getReference(CALENDAR_KEY)

        setCalendarListener()
//        setSwipeCalendar()

        setDayOfWeek()
        setSelectedDayButtons()

        initViewPager()
        initTabLayout()

    }

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }

    // Обработка нажатий календаря
    private fun setCalendarListener() {
        collapsibleCalendar.setCalendarListener(object : CollapsibleCalendar.CalendarListener {
            override fun onDaySelect() {
                val day = collapsibleCalendar.selectedDay
                val dateSelected = "${day?.year}${(day?.month)?.plus(1)}${day?.day}"
                setSelectedDayOfWeek()
                Log.v(TAG, dateSelected)
                Log.v(TAG, "day: ${day.toString()}")
            }

            override fun onItemClick(view: View) {}
            override fun onClickListener() {}
            override fun onDataUpdate() {}
            override fun onDayChanged() {}
            override fun onMonthChange() {}
            override fun onWeekChange(i: Int) {}
        })
    }

    // Обработка свайпов календаря
//    @SuppressLint("ClickableViewAccessibility")
//    private fun setSwipeCalendar() {
//        var relativeLayout = binding.nestedScrollView
//        relativeLayout.setOnTouchListener(object: OnSwipeTouchListener(requireActivity()){
//            override fun onSwipeRight() {}
//            override fun onSwipeLeft() {}
//            override fun onSwipeTop() {
//                if(collapsibleCalendar.expanded){
//                    collapsibleCalendar.collapse(400)
//                }
//            }
//            override fun onSwipeBottom() {
//                if(!collapsibleCalendar.expanded){
//                    collapsibleCalendar.expand(400)
//                }
//            }
//        })
//    }

    // Установка дня недели при запуске приложения
    private fun setDayOfWeek() {
        val calendar = Calendar.getInstance()
        val dayOfWeek = when (calendar.get(Calendar.DAY_OF_WEEK)) {
            Calendar.SUNDAY -> "Воскресенье"
            Calendar.MONDAY -> "Понедельник"
            Calendar.TUESDAY -> "Вторник"
            Calendar.WEDNESDAY -> "Среда"
            Calendar.THURSDAY -> "Четверг"
            Calendar.FRIDAY -> "Пятница"
            Calendar.SATURDAY -> "Суббота"
            else -> "Неизвестно"
        }
        binding.textDayOfWeek.text = dayOfWeek
    }

    // Установка дня недели при выборе
    private fun setSelectedDayOfWeek() {
        collapsibleCalendar.selectedDay?.let { selectedDate ->
            val calendar = Calendar.getInstance().apply {
                set(Calendar.YEAR, selectedDate.year)
                set(Calendar.MONTH, selectedDate.month)
                set(Calendar.DAY_OF_MONTH, selectedDate.day)
            }
            val dayOfWeek = when (calendar.get(Calendar.DAY_OF_WEEK)) {
                Calendar.SUNDAY -> "Воскресенье"
                Calendar.MONDAY -> "Понедельник"
                Calendar.TUESDAY -> "Вторник"
                Calendar.WEDNESDAY -> "Среда"
                Calendar.THURSDAY -> "Четверг"
                Calendar.FRIDAY -> "Пятница"
                Calendar.SATURDAY -> "Суббота"
                else -> "Неизвестно"
            }
            binding.textDayOfWeek.text = dayOfWeek
        }
    }

    // Обработка переключения дней в календаре с помощью кнопок
    private fun setSelectedDayButtons() {
        binding.dayBackButton.setOnClickListener {
            collapsibleCalendar.prevDay()
        }
        binding.dayNextButton.setOnClickListener {
            collapsibleCalendar.nextDay()
        }
    }


    // Инициализация TabsLayout и отображение фрагментов
    private fun initViewPager() {
        binding.mainViewPager.adapter = SimpleFragmentPagerAdapter(requireActivity())
        binding.mainViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
            override fun onPageSelected(position: Int) {}
            override fun onPageScrollStateChanged(state: Int) {}
        })
    }

    // инициализация всех добавленных табов (не изменять)
    private fun initTabLayout() {
        // Находим, где будем отображать тексты табов
        val tabLayout = binding.mainTabLayout
        // Находим, где будем менять фрагменты на выбранный в табе
        val viewPager = binding.mainViewPager
        // Перечисляем все нужные табы
        val tabTitles = arrayOf("Распорядок", "Заметка")

        // viewPager меняет отображаемый фрагмент при выборе нужного таба с помощью SimpleFragmentPagerAdapter
        viewPager.adapter = SimpleFragmentPagerAdapter(requireActivity())
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()
        // Установка начально выбранного таба
        tabLayout.getTabAt(defaultTabIndex)?.select()
    }

    // Переключение между фрагментами из табов
    private inner class SimpleFragmentPagerAdapter(fragmentActivity: FragmentActivity) :
        FragmentStateAdapter(fragmentActivity) {
        // Перечисление всех фрагментов (столько же, сколько и табов)
        private val fragment = arrayOf(ScheduleFragment(), NoteFragment())
        override fun getItemCount(): Int {
            return fragment.size
        }
        override fun createFragment(position: Int): Fragment {
            return fragment[position]
        }
    }

}
