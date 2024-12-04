package com.guanhaolin.pearch.api.model

val emptyResponseBody = """{
    "total": 0,
    "totalHits": 0,
    "hits": []
}""".trimIndent()

val imageResponseBody = """{
    "id": 3994154,
    "pageURL": "https://pixabay.com/photos/clouds-weather-cloudscape-dramatic-3994154/",
    "type": "photo",
    "tags": "clouds, weather, sky background",
    "previewURL": "https://cdn.pixabay.com/photo/2019/02/13/11/17/clouds-3994154_150.jpg",
    "previewWidth": 92,
    "previewHeight": 150,
    "webformatURL": "https://pixabay.com/get/g2198cffb213b1466c6d2311524799c0494fc5fac0c0b2da19a0a25280ea6a27f060f80c1633619b94ac9e3d9f200c24beba8197ea4b85b17529d471d41c3e5fe_640.jpg",
    "webformatWidth": 392,
    "webformatHeight": 640,
    "largeImageURL": "https://pixabay.com/get/g33f1032e6d905708c9e9490d7773ad5fac052782f5dc8a9f11b00c7a99451576426fc74323c46ee14e8a6ad7a9391cfbbdc75c45d5a802aea37e2448280f604f_1280.jpg",
    "imageWidth": 1961,
    "imageHeight": 3200,
    "imageSize": 1024888,
    "views": 119109,
    "downloads": 96314,
    "collections": 3412,
    "likes": 228,
    "comments": 28,
    "user_id": 356019,
    "user": "sandid",
    "userImageURL": "https://cdn.pixabay.com/user/2015/02/23/11-47-07-191_250x250.jpg"
}""".trimIndent()

val videoResponseBody = """{
    "id": 125,
    "pageURL": "https://pixabay.com/videos/id-125/",
    "type": "film",
    "tags": "flowers, yellow, blossom",
    "duration": 12,
    "videos": {
        "large": {
            "url": "https://cdn.pixabay.com/video/2015/08/08/125-135736646_large.mp4",
            "width": 1920,
            "height": 1080,
            "size": 6615235,
            "thumbnail": "https://cdn.pixabay.com/video/2015/08/08/125-135736646_large.jpg"
        },
        "medium": {
            "url": "https://cdn.pixabay.com/video/2015/08/08/125-135736646_medium.mp4",
            "width": 1280,
            "height": 720,
            "size": 3562083,
            "thumbnail": "https://cdn.pixabay.com/video/2015/08/08/125-135736646_medium.jpg"
        },
        "small": {
            "url": "https://cdn.pixabay.com/video/2015/08/08/125-135736646_small.mp4",
            "width": 640,
            "height": 360,
            "size": 1030736,
            "thumbnail": "https://cdn.pixabay.com/video/2015/08/08/125-135736646_small.jpg"
        },
        "tiny": {
            "url": "https://cdn.pixabay.com/video/2015/08/08/125-135736646_tiny.mp4",
            "width": 480,
            "height": 270,
            "size": 426799,
            "thumbnail": "https://cdn.pixabay.com/video/2015/08/08/125-135736646_tiny.jpg"
        }
    },
    "views": 4462,
    "downloads": 1464,
    "likes": 18,
    "comments": 0,
    "user_id": 1281706,
    "user": "Coverr-Free-Footage",
    "userImageURL": "https://cdn.pixabay.com/user/2015/10/16/09-28-45-303_250x250.png"
}""".trimIndent()

val responseBodyForSearchImage = """{
    "total": 1,
    "totalHits": 1,
    "hits": [$imageResponseBody]
}""".trimIndent()

val responseBodyForSearchVideo = """{
    "total": 1,
    "totalHits": 1,
    "hits": [$videoResponseBody]
}""".trimIndent()