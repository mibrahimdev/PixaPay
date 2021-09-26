package io.github.mohamedisoliman.pixapay.ui.search

val previewImage = ImageUiModel(
    userName = "Ibrahim",
    url = "https://images.unsplash.com/photo-1469854523086-cc02fe5d8800?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1308&q=80",
    tags = listOf(
        "nature",
        "landscape",
        "outing",
        "nature",
        "landscape",
        "outing",
        "nature",
        "landscape",
        "outing"
    ).map { "#$it" }
)

data class ImageUiModel(
    val userName: String,
    val url: String,
    val tags: List<String>,
)
