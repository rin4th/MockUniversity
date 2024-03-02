 package com.example.mockuniversity

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import com.bumptech.glide.Glide
import com.example.mockuniversity.model.Course
import com.example.mockuniversity.model.CourseStatus
import com.google.firebase.firestore.FirebaseFirestore


 class CourseFragment : Fragment() {
    private lateinit var courseId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        courseId = arguments?.getString("courseId").orEmpty()
        Log.d("CourseFragment", "courseId: $courseId")
        return inflater.inflate(R.layout.fragment_course, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val db = FirebaseFirestore.getInstance()
        val coursesRef = db.collection("courses").document(courseId)

        coursesRef.get().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val documents = task.result
                    val course = Course(
                        documents!!.id,
                        documents["name"] as String,
                        (documents["question"] as Long).toInt(),
                        (documents["duration"] as Long).toInt(),
                        (documents["reward"] as Long).toInt(),
                        documents["icon"] as String,
                        documents["banner"] as String,
                        CourseStatus.valueOf(documents["status"] as String),
                        documents["description"] as String
                    )

                    val totalQuestion = getString(R.string.total_question, course.question.toString())
                    val totalQuestionDetail = getString(R.string.total_question_detail, course.question.toString())
                    val hour = course.duration / 60
                    val minute = course.duration % 60
                    val totalDuration = getString(R.string.total_time, hour.toString(), minute.toString())
                    val totalReward = getString(R.string.total_reward, course.reward.toString())

                    val courseName: TextView = view.findViewById(R.id.tv_titleCourseTop)
                    val courseNameBot: TextView = view.findViewById(R.id.tv_titleCourseBottom)
                    val question: TextView = view.findViewById(R.id.tv_totalQuestion)
                    val detailQuestion: TextView = view.findViewById(R.id.tv_detailQuestion)
                    val duration: TextView = view.findViewById(R.id.tv_totalDuration)
                    val reward: TextView = view.findViewById(R.id.tv_totalReward)
                    val description: TextView = view.findViewById(R.id.tv_descriptionCourse)
                    val banner: ImageView = view.findViewById(R.id.iv_bannerCourse)
                    val status: AppCompatButton = view.findViewById(R.id.btn_statusDetailCourse)

                    courseName.text = course.name
                    courseNameBot.text = course.name
                    question.text = totalQuestion
                    detailQuestion.text = totalQuestionDetail
                    duration.text = totalDuration
                    reward.text = totalReward
                    description.text = course.description
                    status.text = course.status.toString()
                    Glide.with(requireContext())
                        .load(course.banner)
                        .into(banner)
                }
        }
    }
}

