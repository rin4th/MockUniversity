package com.example.mockuniversity

import android.content.res.TypedArray
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mockuniversity.adapter.CourseAdapter
import com.example.mockuniversity.model.Course



class HomeFragment : Fragment() {

    private lateinit var rv : RecyclerView

    private lateinit var dataName: Array<String>
    private lateinit var dataQuestion: Array<Int>
    private lateinit var dataTime: Array<Int>
    private lateinit var dataReward: Array<Int>
    private lateinit var dataIcon: TypedArray
    private lateinit var dataBanner: TypedArray

    private val courses = arrayListOf<Course>()

    private lateinit var courseAdapter: CourseAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rv = view.findViewById(R.id.rv_course)

        prepareResource()
        prepareRecyclerView()
        prepareCourses()
        prepareAdapter()
    }

    private fun prepareResource() {
        dataName = resources.getStringArray(R.array.data_name)
        dataQuestion = resources.getIntArray(R.array.data_question).toTypedArray()
        dataTime = resources.getIntArray(R.array.data_time).toTypedArray()
        dataReward = resources.getIntArray(R.array.data_reward).toTypedArray()
        dataIcon = resources.obtainTypedArray(R.array.data_icon)
        dataBanner = resources.obtainTypedArray(R.array.data_banner)
    }

    private fun prepareRecyclerView() {
        rv.layoutManager = LinearLayoutManager(context)
        rv.setHasFixedSize(true)
    }

    private fun prepareCourses() {
        if (courses.isNotEmpty()) courses.clear()
        for (position in dataName.indices) {
            val course = Course(
                dataName[position],
                dataQuestion[position],
                dataTime[position],
                dataReward[position],
                dataIcon.getResourceId(position, -1),
                dataBanner.getResourceId(position, -1)
            )
            courses.add(course)
        }
    }

    private fun prepareAdapter() {
        if(::courseAdapter.isInitialized){
            courseAdapter.updateCourses(courses)
        } else {
            courseAdapter = CourseAdapter(courses)
        }

        rv.adapter = courseAdapter
    }

}