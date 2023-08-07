package com.example.csc306_newsapp.models

class NewsModel {
    var modelTitle: String? = null
    private var modelImage: Int = 0


    fun getTitle():String {
        return modelTitle.toString()
    }

    fun setTitle(title: String) {
        this.modelTitle = title
    }

    fun getImage():Int {
        return modelImage
    }

    fun setImage(image_drawable: Int) {
        this.modelImage = image_drawable
    }
}