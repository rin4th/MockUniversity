package com.example.mockuniversity.model



// Apply the converter to the `status` property in the Course data class
data class Course(
    var name: String,
    var question: Int,
    var duration: Int,
    var reward: Int,
    var icon: String,
    var banner: String,
    var status: CourseStatus,
    var description: String
) {
    fun getCourseHashMap(): HashMap<String, Any> {
        // No need to manually convert `status` to a string here
        return hashMapOf(
            "name" to name,
            "question" to question,
            "duration" to duration,
            "reward" to reward,
            "icon" to icon,
            "banner" to banner,
            "status" to status.toString(),
            "description" to description
        )
    }
}