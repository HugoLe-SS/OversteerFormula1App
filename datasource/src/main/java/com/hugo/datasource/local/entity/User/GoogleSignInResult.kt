package com.hugo.datasource.local.entity.User

data class GoogleSignInResult(
    val idToken: String,
    val userId: String,
    val displayName: String?,
    val email: String?,
    val profilePictureUrl: String?,
    val nonce: String? = null
)
