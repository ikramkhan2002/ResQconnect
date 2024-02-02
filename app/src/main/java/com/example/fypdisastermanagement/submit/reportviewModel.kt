package com.example.fypdisastermanagement.submit

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.InputStream

class ReportViewModel : ViewModel() {

    private val storage = Firebase.storage
    private val storageReference = storage.reference.child("report_header_images")

    fun uploadReportHeaderImage(context: Context, imageUri: Uri, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        val inputStream: InputStream = context.contentResolver.openInputStream(imageUri)
            ?: return onFailure("Failed to open image file")

        val imageRef = storageReference.child("${System.currentTimeMillis()}_${imageUri.lastPathSegment}")
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val uploadTask = imageRef.putStream(inputStream)
                uploadTask.continueWithTask { task ->
                    if (!task.isSuccessful) {
                        throw task.exception ?: Exception("Image upload failed")
                    }
                    imageRef.downloadUrl
                }.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val downloadUri = task.result
                        // You can now save this download URL in your Firestore database.
                        onSuccess()
                    } else {
                        onFailure("Image upload failed")
                    }
                }
            } catch (e: Exception) {
                onFailure(e.message ?: "Image upload failed")
            }
        }
    }
}
