package com.overheat.capstoneproject.ui.home

import android.os.Bundle
import android.view.*
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.overheat.capstoneproject.R
import com.overheat.capstoneproject.databinding.FragmentHomeBinding
import com.synnapps.carouselview.ImageListener

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fillHomeFragment()
        prepareRecyclerView()
        prepareCarouselView()
    }

    private fun fillHomeFragment() {
        with(binding) {
            tvWelcomeUser.text = context?.resources?.getString(R.string.welcome_guest, "Guest")
            tvWelcomeText.text = "lorem ipsum dolor sit amet"
        }
    }

    private fun prepareRecyclerView() {
        val adapter = HomeListFaqAdapter()

        with(binding.rvFaqs) {
            this.layoutManager = LinearLayoutManager(context)
            this.adapter = adapter
        }
    }

    private fun prepareCarouselView() {
        val sampleImages = arrayOf(
            "https://raw.githubusercontent.com/sayyam/carouselview/master/sample/src/main/res/drawable/image_3.jpg",
            "https://raw.githubusercontent.com/sayyam/carouselview/master/sample/src/main/res/drawable/image_1.jpg",
            "https://raw.githubusercontent.com/sayyam/carouselview/master/sample/src/main/res/drawable/image_2.jpg"
        )

        with(binding.carouselView) {
            // Set image listener must be called before page count
            // If not, it will cause error -> View must set ImageListener or ViewListener
            setImageListener { position, imageView ->
                Glide.with(this@HomeFragment)
                    .load(sampleImages[position])
                    .into(imageView)
            }
            pageCount = sampleImages.size
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.notifications_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
}