package com.guanhaolin.pearch.model

class Count(count: Int) {
    var count: String? = null

    init {
        if (count < 1000) {
            this.count = count.toString()
        } else {
            this.count = String.format("%dK", count / 1000)
        }
    }
}
