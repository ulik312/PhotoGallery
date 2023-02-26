package com.sbor.photogallery

import android.R
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sbor.photogallery.adapter.SelectedAdapter
import com.sbor.photogallery.databinding.ActivitySecondBinding
import com.sbor.photogallery.model.Gallery

class SecondActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySecondBinding
    private var adapter: SelectedAdapter = SelectedAdapter()

    var imgselect = ArrayList<Gallery>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        setAdapter()
        adapter.setImages(imgselect)
    }

    private fun setAdapter() {
        binding.recyclerView2.adapter = adapter
    }

    private fun initView() {
        imgselect =  intent.getSerializableExtra("result") as ArrayList<Gallery>

    }
}