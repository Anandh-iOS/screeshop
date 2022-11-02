package com.example.screenshotdetector

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.screenshotdetector.adapter.ListAdapter
import com.example.screenshotdetector.model.Product
import com.example.screenshotdetector.util.ScreenshotDetector
import com.example.screenshotdetector.util.ScreenshotListener
import com.example.screenshotdetector.util.load
import com.example.screenshotdetector.viewmodel.ProductViewModel
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File

private const val READ_EXTERNAL_STORAGE_REQUEST = 0x1045
private lateinit var screenshotDetector: ScreenshotDetector

class MainActivity : AppCompatActivity(), ScreenshotListener, Observer<List<Product?>?> {

    private lateinit var viewModel: ProductViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        screenshotDetector = ScreenshotDetector(baseContext, this)
        initViewModel()
    }

    override fun onStart() {
        super.onStart()
        detectScreenshots()
    }

    override fun onStop() {
        super.onStop()
        screenshotDetector.stop()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            READ_EXTERNAL_STORAGE_REQUEST -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    detectScreenshots()
                }
                return
            }
        }
    }

    private fun haveStoragePermission() =
        ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED

    private fun requestPermission() {
        if (!haveStoragePermission()) {
            val permissions = arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
            ActivityCompat.requestPermissions(this, permissions, READ_EXTERNAL_STORAGE_REQUEST)
        }
    }

    private fun detectScreenshots() {
        if (haveStoragePermission()) {
            screenshotDetector.start()
        } else {
            requestPermission()
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(
            this, ViewModelProvider.AndroidViewModelFactory(application)
        ).get(ProductViewModel::class.java)

        viewModel.mediaList.observe(this, this)
    }

    override fun screenShotTaken(file: File?) {
        if (file != null) {
            iv_screen_shot.load(file)
            viewModel.getProducts(file)
progress_bar.visibility = View.VISIBLE
        }
    }

    override fun onChanged(productList: List<Product?>?) {
        if (!productList.isNullOrEmpty()) {
            setAdapter(productList)
        }
    }

    private fun setAdapter(productList: List<Product?>) {
        progress_bar.visibility = View.GONE
        val listAdapter = ListAdapter(this, productList)
        rv_products.adapter = listAdapter
    }
}