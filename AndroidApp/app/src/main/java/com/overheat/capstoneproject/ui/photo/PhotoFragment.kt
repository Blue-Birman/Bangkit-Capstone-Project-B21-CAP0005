package com.overheat.capstoneproject.ui.photo

import android.Manifest
import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.view.*
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import com.overheat.capstoneproject.R
import com.overheat.capstoneproject.databinding.FragmentPhotoBinding
import com.overheat.capstoneproject.ui.result.ResultActivity
import java.io.ByteArrayOutputStream
import java.util.*

class PhotoFragment : Fragment() {

    private var _binding: FragmentPhotoBinding? = null
    private val binding get() = _binding!!

    val REQUEST_CODE = 200

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPhotoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkPermission()

        binding.btnCapture.setOnClickListener {
            openCamera()
        }

        binding.btnSend.setOnClickListener {
            sendToApi()
        }
    }

    private fun checkPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                requireActivity(),
                Manifest.permission.CAMERA
            )
        ) {
            Toast.makeText(requireContext(), "Permission allowed", Toast.LENGTH_SHORT).show();
        } else {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.CAMERA),
                REQUEST_CODE
            );
        }
    }

    private fun openCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, REQUEST_CODE)
    }

    private fun sendToApi() {
        val imageString = getImage()
        val intent = Intent(context, ResultActivity::class.java).apply {
            putExtra(ResultActivity.EXTRA_IMAGE, (binding.ivPhoto.drawable.toBitmap()))
            putExtra(ResultActivity.EXTRA_STRING, imageString)
        }
        startActivity(intent)
    }

    private fun getImage() : String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        val bitmap = (binding.ivPhoto.drawable).toBitmap()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)

        val imageBytes = byteArrayOutputStream.toByteArray()
        val imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT)

        return imageString
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode != RESULT_CANCELED) {
            when (requestCode) {
                200 -> if (resultCode == RESULT_OK && data != null) {
                    val selectedImage = data.extras?.get("data") as Bitmap
                    binding.ivPhoto.setImageBitmap(selectedImage)
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.notifications_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}