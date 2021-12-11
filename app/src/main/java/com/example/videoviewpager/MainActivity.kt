package com.example.videoviewpager

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import kotlin.math.abs


class MainActivity : AppCompatActivity() {

    private lateinit var viewPager2: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewPager2= findViewById(R.id.view_pager)

        val sliderItems: MutableList<SliderItem> = ArrayList()
        sliderItems.add(SliderItem(R.drawable.image_view0, "",false,"Lorem Ipsum", "Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum "))
        sliderItems.add(SliderItem(R.drawable.image_view1, "",false,"Jane Doe", "Jane Doe Jane Doe Jane Doe Jane Doe Jane Doe Jane Doe Jane Doe "))
        sliderItems.add(SliderItem(R.drawable.image_view0, "android.resource://${getPackageName()}/${R.raw.video}",true,"Lorem Ipsum", "Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum "))
        sliderItems.add(SliderItem(R.drawable.image_view1, "",false,"Jane Doe", "Jane Doe Jane Doe Jane Doe Jane Doe Jane Doe Jane Doe Jane Doe "))
        sliderItems.add(SliderItem(R.drawable.image_view0, "",false,"Lorem Ipsum", "Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum "))
        sliderItems.add(SliderItem(R.drawable.image_view1, "",false,"Jane Doe", "Jane Doe Jane Doe Jane Doe Jane Doe Jane Doe Jane Doe Jane Doe "))

        viewPager2.adapter = SliderAdapter(sliderItems ,viewPager2)

        viewPager2.clipToPadding = false //Defines whether the ViewGroup will clip its children
        viewPager2.clipChildren = false
        viewPager2.offscreenPageLimit = 3
        viewPager2.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(MarginPageTransformer(30))
        compositePageTransformer.addTransformer{ page, position ->
            val r = 1 - abs(position)
            page.scaleY = 0.85f + r * 0.25f
        }

        viewPager2.setPageTransformer(compositePageTransformer)
    }
}