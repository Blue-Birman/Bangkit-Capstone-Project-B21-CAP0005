package com.overheat.capstoneproject.ui.photo

import android.Manifest
import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
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
import java.io.ByteArrayOutputStream

class PhotoFragment : Fragment() {



    private var _binding: FragmentPhotoBinding? = null
    private val binding get() = _binding!!

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
        binding.btnSend.isEnabled = false

        binding.btnCapture.setOnClickListener {
            openCamera()
            binding.btnSend.isEnabled = true
        }

        binding.btnSend.setOnClickListener {
            getResult()
        }

        binding.popupResult.btnClose.setOnClickListener {
            closePopUp()
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

    private fun getResult() {
        val imageString = getImage()

        // resize image
        val bitmap =
            Bitmap.createScaledBitmap((binding.ivPhoto.drawable).toBitmap(), 150, 150, true)

        // send the image string to api
        val result =

        // show result
        showPopUp(bitmap, 0.7) // pass bitmap and cancer proba from api
    }

    private fun showPopUp(bitmap: Bitmap, cancerProba: Double) {
        binding.popupResult.ivResult.setImageBitmap(bitmap)
        binding.popupResult.tvResult.text = cancerProba.toString()

        if (cancerProba > 0.5) {
            binding.popupResult.tvWarning.text = "You should check your condition to doctor."
        } else {
            binding.popupResult.tvWarning.text = "..."
        }

        binding.btnCapture.isEnabled = false
        binding.btnSend.isEnabled = false
        binding.popupResult.root.bringToFront()
        binding.popupResult.root.visibility = View.VISIBLE
    }

    private fun closePopUp() {
        binding.btnCapture.isEnabled = true
        binding.popupResult.root.visibility = View.GONE
    }

    private fun getImage(): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        val bitmap =
            Bitmap.createScaledBitmap((binding.ivPhoto.drawable).toBitmap(), 150, 150, true)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)

        val imageBytes = byteArrayOutputStream.toByteArray()

        return Base64.encodeToString(imageBytes, Base64.DEFAULT)
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val REQUEST_CODE = 200
    }
}
