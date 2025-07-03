package com.hugo.authentication.domain.model

data class GoogleSignInResult(
    val idToken: String,
    val userId: String,
    val displayName: String?,
    val email: String?,
    val profilePictureUrl: String?,
    val nonce: String? = null
)
