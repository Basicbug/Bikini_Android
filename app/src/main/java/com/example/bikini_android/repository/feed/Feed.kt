package com.example.bikini_android.repository.feed

/**
 * @author bsgreentea
 */
@Suppress("unused")
data class Feed(
    var feedNumOfUser: Int = 0,
    var userId: String = "sampleUserId",
    var content: String = "sampleContent",
    var imageUri: String = "sampleImageUri",
    var profileImageUri: String = "sampleProfile",
    var longitude: String = "xx.xxxx",
    var latitude: String = "yy.yyyy"
)