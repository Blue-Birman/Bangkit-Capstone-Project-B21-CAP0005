package com.overheat.capstoneproject.ui.photo

import android.Manifest
import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import com.overheat.capstoneproject.databinding.FragmentPhotoBinding
import org.koin.android.viewmodel.ext.android.viewModel
import java.io.ByteArrayOutputStream

class PhotoFragment : Fragment() {

    private val photoViewModel: PhotoViewModel by viewModel()

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
            Toast.makeText(requireContext(), "Permission allowed", Toast.LENGTH_SHORT).show()
        } else {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.CAMERA),
                REQUEST_CODE
            )
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
        photoViewModel.getResultFromImage(imageString)
        photoViewModel.result.observe(viewLifecycleOwner, { result ->
            if (result != null) {
                // show result
                showPopUp(bitmap, result.cancerProba) // pass bitmap and cancer proba from api
            }
        })
    }

    private fun showPopUp(bitmap: Bitmap, cancerProba: Double?) {
        binding.popupResult.ivResult.setImageBitmap(bitmap)

        val cancerPercentage = cancerProba?.times(100)
        binding.popupResult.tvResult.text = cancerPercentage.toString()

        var warningText = ""
        if (cancerProba != null) {
            when {
                cancerProba > 0.7 -> {
                    warningText = "You should check your condition immediately."
                }
                cancerProba > 0.4 -> {
                    warningText = "Not sure, but it's better if you check your condition"
                }
                else -> {
                    warningText = "Congrats, you're okay"
                }
            }

            binding.popupResult.tvWarning.text = warningText
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
        var imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT)
        imageString = imageString.replace("\n","")

        return imageString.replace("\\", "")
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
