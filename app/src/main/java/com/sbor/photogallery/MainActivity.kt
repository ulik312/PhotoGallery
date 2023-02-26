package com.sbor.photogallery

import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.sbor.photogallery.adapter.ImageAdapter
import com.sbor.photogallery.databinding.ActivityMainBinding
import com.sbor.photogallery.model.Gallery

class MainActivity : AppCompatActivity(), ImageAdapter.Listener {
    private lateinit var binding: ActivityMainBinding
    var images = ArrayList<Gallery>()
    var selectedImages = ArrayList<Gallery>()
    private lateinit var adapter: ImageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adapter = ImageAdapter(this)
        setAdapter()
        initClickers()

        val permissionStatus = ContextCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE)
        if (permissionStatus == PackageManager.PERMISSION_GRANTED) {
            images = getImagesFromGallery()
            adapter.setItem(images)
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(WRITE_EXTERNAL_STORAGE), 234)
        }
    }

    private fun setAdapter() {
        binding.recyclerView.adapter = adapter
    }
    private fun initClickers(){
        with(binding){
            btnNext.setOnClickListener {
                val intent = Intent(this@MainActivity, SecondActivity::class.java)
                intent.putExtra("result", selectedImages)
                startActivity(intent)

            }
        }
    }

    private fun getImagesFromGallery(): ArrayList<Gallery> {
        val cursor: Cursor
        val listOfAllImages: ArrayList<Gallery> = ArrayList()
        var absolutePathOfImage: String
        val uri: Uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val projection =
            arrayOf(MediaStore.MediaColumns.DATA, MediaStore.Images.Media.BUCKET_DISPLAY_NAME)
        val orderBy: String = MediaStore.Images.Media.DATE_TAKEN
        cursor = contentResolver.query(uri, projection, null, null, "$orderBy DESC")!!
        val columnIndexData: Int = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)
        while (cursor.moveToNext()) {
            absolutePathOfImage = cursor.getString(columnIndexData)
            listOfAllImages.add(Gallery(absolutePathOfImage, false))
        }

        return listOfAllImages
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 234 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            images = getImagesFromGallery()
            adapter.setItem(images)

        } else {
            finish()
        }
    }

    override fun onClick(gallery: Gallery, position: Int) {
        images[position].isSelected = !gallery.isSelected
        adapter.notifyDataSetChanged()
        selectedImages = images.filter { it.isSelected } as ArrayList<Gallery>
        binding.textSelected.text = "Выбрано ${selectedImages.size} фотографий"
    }

}
