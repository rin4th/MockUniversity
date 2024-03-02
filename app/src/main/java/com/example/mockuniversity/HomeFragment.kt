package com.example.mockuniversity

import android.content.res.TypedArray
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mockuniversity.adapter.CourseAdapter
import com.example.mockuniversity.model.Course
import com.example.mockuniversity.model.CourseStatus
import com.google.firebase.firestore.FirebaseFirestore


class HomeFragment : Fragment() {

    private lateinit var rv: RecyclerView

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
        //        prepareCourses()
    }

    private fun prepareResource() {
//        dataName = resources.getStringArray(R.array.data_name)
//        dataQuestion = resources.getIntArray(R.array.data_question).toTypedArray()
//        dataTime = resources.getIntArray(R.array.data_time).toTypedArray()
//        dataReward = resources.getIntArray(R.array.data_reward).toTypedArray()
//        dataIcon = resources.obtainTypedArray(R.array.data_icon)
//        dataBanner = resources.obtainTypedArray(R.array.data_banner)

        val db = FirebaseFirestore.getInstance()
        val coursesRef = db.collection("courses")

        coursesRef.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                courses.clear()
                for (document in task.result!!) {
                    val courseData = document.data!!
                    val id = document.id
                    val name = courseData["name"] as String
                    val question = (courseData["question"] as Long).toInt()
                    val time = (courseData["duration"] as Long).toInt()
                    val reward = (courseData["reward"] as Long).toInt()
                    val icon = courseData["icon"] as String
                    val banner = courseData["banner"] as String
                    val status = CourseStatus.valueOf(courseData["status"] as String)
                    val description = courseData["description"] as String

                    // prepareCourses
                    val course = Course(
                        id,
                        name,
                        question,
                        time,
                        reward,
                        icon,
                        banner,
                        status,
                        description
                    )
                    courses.add(course)
                }
                prepareRecyclerView()
                prepareAdapter()
            }
        }
    }

    private fun prepareRecyclerView() {
        rv.layoutManager = LinearLayoutManager(context)
        rv.setHasFixedSize(true)
    }

//    private fun prepareCourses() {
//        if (courses.isNotEmpty()) courses.clear()
//        for (position in dataName.indices) {
//            val course = Course(
//                dataName[position],
//                dataQuestion[position],
//                dataTime[position],
//                dataReward[position],
//                dataIcon[position],
//                dataBanner[position])
//            )
//            courses.add(course)
//        }
//    }

    private fun prepareAdapter() {
        if (::courseAdapter.isInitialized) {
            courseAdapter.updateCourses(courses)
        } else {
            courseAdapter = CourseAdapter(courses) { course ->

                Toast.makeText(requireContext(), "Clicked on ${course.name}", Toast.LENGTH_SHORT)
                    .show()
//                val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(course)
//                view?.findNavController()?.navigate(action)
            }
        }
        courseAdapter.setOnItemClickCallback(object : CourseAdapter.OnItemClickListener {
            override fun onItemClick(courseId: String) {
                navigateToDetail(courseId)
            }
        })
        rv.adapter = courseAdapter
    }

    private fun navigateToDetail(courseId: String) {
        val action = HomeFragmentDirections.actionItHomeToCourseFragment2(courseId)
        Log.d("HomeFragment", "navigateToDetail: $courseId")
        findNavController().navigate(action)
    }

}