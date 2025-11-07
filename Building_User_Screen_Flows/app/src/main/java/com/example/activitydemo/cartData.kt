package com.example.activitydemo


object CartData {
    val items = mutableListOf<String>()

    fun addItem(product: String) {
        items.add(product)
    }

    fun clear() {
        items.clear()
    }
}