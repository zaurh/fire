package com.example.fire.data


data class UserData(
    var userId: String? = null,
    var username: String? = null,
    var email: String? = null,
    var password: String? = null,
    val image: String? = null,
    val bio: String? = null
) {
    fun toMap() = mapOf(
        "userId" to userId,
        "username" to username,
        "email" to email,
        "password" to password,
        "image" to image,
        "bio" to bio
    )
}
