package com.ahrorovk.duaforpeople.core.util

sealed class Graph(val route: String) {
    object MainGraph : Graph("MainGraph")
}