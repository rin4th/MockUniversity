package com.example.mockuniversity.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mockuniversity.R
import com.example.mockuniversity.model.Course

class CourseAdapter(private var courses: List<Course>, private val onItemClick: (Course) -> Unit) :
    RecyclerView.Adapter<CourseAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var icon: ImageView = itemView.findViewById(R.id.iv_iconCourse)
        private var name: TextView = itemView.findViewById(R.id.tv_titleCourse)
        private var question: TextView = itemView.findViewById(R.id.tv_taskQuestion)
        private var time: TextView = itemView.findViewById(R.id.tv_timeRemining)
        private var status: TextView = itemView.findViewById(R.id.btn_statusCourse)

        fun bind(course: Course) {
            Glide.with(itemView.context)
                .load(course.icon)
                .into(icon)
            name.text = course.name
            question.text = course.question.toString()
            time.text = course.duration.toString()
            status.text = course.status.toString()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.card_course, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return courses.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(courses[position])
        holder.itemView.setOnClickListener{
            onItemClick(courses[position])
        }
    }

    fun updateCourses(newCourse: List<Course>) {
        val diffCallback = CourseDiffCallback(courses, newCourse)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        courses = newCourse
        diffResult.dispatchUpdatesTo(this)
    }

}

class CourseDiffCallback(
    private val oldCourse: List<Course>,
    private val newCourse: List<Course>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldCourse.size

    override fun getNewListSize(): Int = newCourse.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldCourse[oldItemPosition].name == newCourse[newItemPosition].name
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldHero = oldCourse[oldItemPosition]
        val newHero = newCourse[newItemPosition]
        return oldHero.name == newHero.name && oldHero.question == newHero.question && oldHero.duration == newHero.duration && oldHero.reward == newHero.reward && oldHero.icon == newHero.icon && oldHero.banner == newHero.banner
    }
}