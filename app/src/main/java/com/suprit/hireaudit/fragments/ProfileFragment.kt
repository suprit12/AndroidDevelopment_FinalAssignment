package com.suprit.hireaudit.fragments

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.bumptech.glide.Glide
import com.suprit.hireaudit.LoginActivity
import com.suprit.hireaudit.R
import com.suprit.hireaudit.api.ServiceBuilder
import com.suprit.hireaudit.repository.UserRepository
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*


/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment() {

    private lateinit var profileLayout : LinearLayout
    private lateinit var profileImg : CircleImageView
    private lateinit var etName : TextView
    private lateinit var etEmail : TextView
    private lateinit var etAddress : TextView
    private lateinit var etOrganization : TextView
    private lateinit var etMobileNo : TextView
    private lateinit var btnLogout : Button

    private var REQUEST_GALLERY_CODE = 0
    private var REQUEST_CAMERA_CODE = 1
    private var imageUrl: String? = null

    val data=ServiceBuilder.data!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        profileLayout=view.findViewById(R.id.profileLayout)
        profileImg=profileLayout.findViewById(R.id.imgProfile)
        etName=profileLayout.findViewById(R.id.etName)
        etEmail=profileLayout.findViewById(R.id.etEmail)
        etAddress=profileLayout.findViewById(R.id.etAddress)
        etOrganization=profileLayout.findViewById(R.id.etOrganization)
        etMobileNo=profileLayout.findViewById(R.id.etMobNo)
        btnLogout = profileLayout.findViewById(R.id.btnLogout)

        profileImg.setOnClickListener {
            loadPopUpMenu()
        }

        btnLogout.setOnClickListener{
            openLogoutDialog()
        }

        viewProfile()


        return view
    }

    private fun viewProfile() {

        val image=ServiceBuilder.loadImagePath()+data[0].userImage!!
        Glide.with(requireContext()).load(image).into(profileImg)
        etName.text=data[0].fName + " "+data[0].lName
        etEmail.text=data[0].emailAddress
        etAddress.text=data[0].address
        etOrganization.text=data[0].organization
        etMobileNo.text=data[0].mobileNo
    }

    private fun uploadImage() {
        val userId=data[0]._id
        val file = File(imageUrl!!)
        val reqFile =
            RequestBody.create(MediaType.parse("multipart/form-data"), file)
        val body =
            MultipartBody.Part.createFormData("file", file.name, reqFile)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val userRepositoory = UserRepository()
                val response = userRepositoory.uploadImage(userId!!, body)
                if (response.success == true) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "Uploaded", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            } catch (ex: Exception) {
                withContext(Dispatchers.Main) {
                    Log.d("Mero Error ", ex.localizedMessage!!)
                    Toast.makeText(
                        context,
                        ex.localizedMessage,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun loadPopUpMenu() {
        val popupMenu = PopupMenu(context, profileImg)
        popupMenu.menuInflater.inflate(R.menu.imagemenu, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.menuCamera ->
                    openCamera()
                R.id.menuGallery ->
                    openGallery()
            }
            true
        }
        popupMenu.show()
    }




    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_GALLERY_CODE)
    }
    private fun openCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, REQUEST_CAMERA_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_GALLERY_CODE && data != null) {
                val selectedImage = data.data
                val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
                val contentResolver = requireContext().contentResolver
                val cursor =
                    contentResolver.query(selectedImage!!, filePathColumn, null, null, null)
                cursor!!.moveToFirst()
                val columnIndex = cursor.getColumnIndex(filePathColumn[0])
                imageUrl = cursor.getString(columnIndex)
                profileImg.setImageBitmap(BitmapFactory.decodeFile(imageUrl))
                cursor.close()
            } else if (requestCode == REQUEST_CAMERA_CODE && data != null) {
                val imageBitmap = data.extras?.get("data") as Bitmap
                val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
                val file = bitmapToFile(imageBitmap, "$timeStamp.jpg")
                imageUrl = file!!.absolutePath
                profileImg.setImageBitmap(BitmapFactory.decodeFile(imageUrl))
                uploadImage()
            }
        }
    }

    private fun bitmapToFile(
        bitmap: Bitmap,
        fileNameToSave: String
    ): File? {
        var file: File? = null
        return try {
            file = File(
                requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                    .toString() + File.separator + fileNameToSave
            )
            file.createNewFile()
            //Convert bitmap to byte array
            val bos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, bos) // YOU can also save it in JPEG
            val bitMapData = bos.toByteArray()
            //write the bytes in file
            val fos = FileOutputStream(file)
            fos.write(bitMapData)
            fos.flush()
            fos.close()
            file
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            file // it will return null
        }
    }

    private fun openLogoutDialog(){
        val builder = AlertDialog.Builder(context)

        builder.setTitle("Logout?")

        builder.setMessage("Are you sure?")

        builder.setIcon(android.R.drawable.editbox_background_normal )

        builder.setPositiveButton("Logout"){ dialogInterface: DialogInterface, i: Int ->
            logout()
        }

        builder.setNegativeButton("Cancel"){ dialogInterface: DialogInterface, i: Int ->

        }

        val alertDialog : AlertDialog = builder.create()
        alertDialog.setCancelable(true)
        alertDialog.show()
    }

    private fun logout(){
        startActivity(Intent(context, LoginActivity::class.java))

        val preferences = requireContext().getSharedPreferences("UserData", Context.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.clear()
        editor.apply()
    }
}