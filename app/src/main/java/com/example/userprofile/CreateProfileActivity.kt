package com.example.userprofile

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_create_profile.*

const val GALLERY_REQUEST_CODE = 100

class CreateProfileActivity : AppCompatActivity() {

    private var profileImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_profile)
        initViews()
    }

    private fun initViews() {
        btnOpenGallery.setOnClickListener() { onGalleryClick() }
        btnConfirm.setOnClickListener() { onConfirmClick() }
    }

    private fun onGalleryClick() {
        //Create Intent with action as ACTION_PICK
        val galleryIntent = Intent(Intent.ACTION_PICK)

        // Sets the type as image/*. This ensures only components of type image are selected
        galleryIntent.type = "image/*"

        //Start the activity using the gallery intent
        startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE)
    }

    //When clicking the confirm button
    private fun onConfirmClick() {
        if (checkIfNoFieldsBlank()) {
            val profile = Profile(
                etFirstName.text.toString(),
                etLastName.text.toString(),
                etDescription.text.toString(),
                profileImageUri
            )

            val profileActivityIntent = Intent(this, ProfileActivity::class.java)
            profileActivityIntent.putExtra(PROFILE_EXTRA, profile)
            startActivity(profileActivityIntent)
        } else {
            Toast.makeText(this, "All fields must be filled in!", Toast.LENGTH_SHORT).show()
        }
    }

    //To make sure the fields are not blank
    private fun checkIfNoFieldsBlank(): Boolean {
        val allFieldsFilledIn = etFirstName.text.toString().isNotBlank() &&
                etLastName.text.toString().isNotBlank() &&
                etDescription.text.toString().isNotBlank()

        return allFieldsFilledIn
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK)
            when (requestCode) {
                GALLERY_REQUEST_CODE -> {
                    profileImageUri = data?.data
                    ivProfilePicture.setImageURI(profileImageUri)
                }
            }
    }
}

