package com.example.videoviewpager

import android.net.Uri
import android.net.Uri.parse
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.VideoView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.makeramen.roundedimageview.RoundedImageView


class SliderAdapter internal constructor(
    sliderItems: MutableList<SliderItem>,
    viewPager: ViewPager2
): RecyclerView.Adapter<SliderAdapter.SliderViewHolder>() {

    private val sliderItems: List<SliderItem>
    private val viewPager: ViewPager2

    init {
        this.sliderItems = sliderItems
        this.viewPager = viewPager
    }

    class SliderViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        private val imageView: RoundedImageView = itemView.findViewById(R.id.image_slide)
        private val headText: TextView = itemView.findViewById(R.id.title_text)
        private val descriptionText: TextView = itemView.findViewById(R.id.title_text_description)
        private val videoView: VideoView = itemView.findViewById(R.id.video_slide)


        fun setViews(sliderItem: SliderItem){
            if(sliderItem.isVideo)
            {

                //specify the location of media file
                val uri: Uri = parse(sliderItem.video)
                //Setting URI, then starting the videoView
                videoView.setVideoURI(uri)
                videoView.requestFocus()
                videoView.start()

                videoView.visibility = View.VISIBLE
                imageView.visibility = View.GONE

                videoView.setOnPreparedListener { mp -> mp.isLooping = true }
            }
            else {

                imageView.visibility = View.VISIBLE
                videoView.visibility = View.GONE
                imageView.setImageResource(sliderItem.image)
            }
            headText.text = sliderItem.heading
            descriptionText.text = sliderItem.description
        }

        fun stopVideo(){
            if(videoView.isPlaying)
                videoView.pause()
        }

        fun playVideo(){
            if(!videoView.isPlaying)
                videoView.start()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderViewHolder {
        return SliderViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.slide_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SliderViewHolder, position: Int) {
        holder.setViews(sliderItems[position])

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if(sliderItems[position].isVideo)
                    holder.playVideo()
                else if(sliderItems[position+1].isVideo || sliderItems[if(position>0) position-1 else position+1].isVideo){
                    holder.stopVideo()
                }
            }
        })

    }

    override fun getItemCount(): Int {
        return sliderItems.size
    }


//    override fun onViewAttachedToWindow(holder: SliderViewHolder) {
//        super.onViewAttachedToWindow(holder)
//        holder.itemView.addOnAttachStateChangeListener( View.OnAttachStateChangeListener{
//
//        })
//    }

//    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
//        super.onAttachedToRecyclerView(recyclerView)
//        val manager = recyclerView.layoutManager
//        if (manager is LinearLayoutManager && itemCount > 0) {
//            val llm = manager
//            recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
//                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
//                    super.onScrollStateChanged(recyclerView, newState)
//                }
//
//                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                    super.onScrolled(recyclerView, dx, dy)
//                    val visiblePosition = llm.findFirstCompletelyVisibleItemPosition()
//                    if (visiblePosition > -1) {
//                        val v = llm.findViewByPosition(visiblePosition)
//                        //do something
//                        v!!.setBackgroundColor(Color.parseColor("#777777"))
//                    }
//                }
//            })
//        }
//    }

    override fun onViewDetachedFromWindow(holder: SliderViewHolder) {
        super.onViewDetachedFromWindow(holder)
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
        
    }
}