package com.hugo.authentication.data.mapper

import com.hugo.authentication.data.dto.ProfileDto
import com.hugo.datasource.local.entity.User.GoogleSignInResult

fun ProfileDto.toGoogleSignInResult(idToken: String, email: String): GoogleSignInResult {
    return GoogleSignInResult(
        idToken = idToken,
        userId = this.id,
        displayName = this.displayName,
        email = email,
        profilePictureUrl = this.avatarUrl,
        nonce = null
    )
}