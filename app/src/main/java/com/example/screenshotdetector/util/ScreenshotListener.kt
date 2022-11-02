package com.example.screenshotdetector.util

import java.io.File

interface ScreenshotListener {

    fun screenShotTaken( file: File?)
}