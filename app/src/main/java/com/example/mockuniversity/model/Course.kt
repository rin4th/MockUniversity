package com.example.mockuniversity.model



// Apply the converter to the `status` property in the Course data class
data class Course(
    val id: String,
    val name: String,
    val question: Int,
    val duration: Int,
    val reward: Int,
    val icon: String,
    val banner: String,
    val status: CourseStatus,
    val description: String
) {
    fun getCourseHashMap(): HashMap<String, Any> {
        // No need to manually convert `status` to a string here
        return hashMapOf(
            "id" to id,
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