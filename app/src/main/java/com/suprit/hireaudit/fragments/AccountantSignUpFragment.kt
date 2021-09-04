package com.suprit.hireaudit.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.suprit.hireaudit.LoginActivity
import com.suprit.hireaudit.R
import com.suprit.hireaudit.entities.Accountant
import com.suprit.hireaudit.repositoryapi.AccountantRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception


class AccountantSignUpFragment : Fragment() {
    private lateinit var accountantFormLayout : LinearLayout
    private lateinit var etFName : EditText
    private lateinit var etLName : EditText
    private lateinit var etEmail : EditText
    private lateinit var imgAccountant : ImageView
    private lateinit var radGender : RadioGroup
    private lateinit var radMale : RadioButton
    private lateinit var radFemale : RadioButton
    private lateinit var radOthers : RadioButton
    private lateinit var etPassword : EditText
    private lateinit var etPassword2 : EditText
    private lateinit var btnSignUp : Button
    private lateinit var tvLogin : TextView

    private lateinit var etExperience : EditText
    private lateinit var etMobNo : EditText
    private lateinit var etPrice : EditText
    private var gender = "Male"


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_accountant_sign_up, container, false)

        accountantFormLayout = view.findViewById(R.id.accountantFormLayout)
        etFName = accountantFormLayout.findViewById(R.id.etFName)
        etLName = accountantFormLayout.findViewById(R.id.etLName)
        etEmail = accountantFormLayout.findViewById(R.id.etEmail)

        radGender = accountantFormLayout.findViewById(R.id.radGender)
        radMale = accountantFormLayout.findViewById(R.id.radMale)
        radFemale = accountantFormLayout.findViewById(R.id.radFemale)
        radOthers = accountantFormLayout.findViewById(R.id.radOthers)
        etPassword = accountantFormLayout.findViewById(R.id.etPassword)
        etPassword2 = accountantFormLayout.findViewById(R.id.etPassword2)
        btnSignUp = accountantFormLayout.findViewById(R.id.btnSignUp)
        tvLogin = accountantFormLayout.findViewById(R.id.tvLogin)
        imgAccountant = view.findViewById(R.id.imgAccountant)
        etMobNo = accountantFormLayout.findViewById(R.id.etMobNo)
        etPrice = accountantFormLayout.findViewById(R.id.etPrice)
        etExperience = accountantFormLayout.findViewById(R.id.etExperience)

        saveState(_savedInstanceState = savedInstanceState)

        btnSignUp.setOnClickListener{
         register()
        }


//        imgAccountant.setOnClickListener{
//            loadPopUpMenu()
//        }

        radGender.setOnCheckedChangeListener{ radioGroup: RadioGroup, i: Int ->
            when {
                radMale.isChecked -> {
                    gender = "Male"
                }
                radFemale.isChecked -> {
                    gender = "Female"
                }
                radOthers.isChecked -> {
                    gender = "Others"
                }
            }
        }

        return view
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putString("etFName", etFName.text.toString())
        outState.putString("etLName", etLName.text.toString())
        outState.putString("etEmail", etEmail.text.toString())
        outState.putString("etMobNo", etMobNo.text.toString())
        outState.putString("etPrice", etPrice.text.toString())
        outState.putString("etPassword", etPassword.text.toString())
        outState.putString("etPassword2", etPassword2.text.toString())
    }


    private fun saveState(_savedInstanceState : Bundle?){
        etFName.setText(_savedInstanceState?.getString("etFName"))
        etFName.setText(_savedInstanceState?.getString("etLName"))
        etFName.setText(_savedInstanceState?.getString("etEmail"))
        etFName.setText(_savedInstanceState?.getString("etUsername"))
        etFName.setText(_savedInstanceState?.getString("etPassword"))
        etFName.setText(_savedInstanceState?.getString("etPassword2"))
        etMobNo.setText(_savedInstanceState?.getString("etMobNo"))
        etPrice.setText(_savedInstanceState?.getString("etPrice"))
    }

    private fun emptyFields(){
        etFName.setText("")
        etLName.setText("")
        etEmail.setText("")

        etPassword.setText("")
        etPassword2.setText("")
    }



//    private fun register(){
//        var gender = "Male"
//
//        radGender.setOnCheckedChangeListener { _: RadioGroup, _: Int ->
//            if (radMale.isChecked){
//                gender = "Male"
//            }
//
//            if (radFemale.isChecked){
//                gender = "Female"
//            }
//
//            if (radOthers.isChecked){
//                gender = "Others"
//            }
//        }
//
//
//        if(etPassword.text.toString() != etPassword2.text.toString())
//        {
//            etPassword.setText("")
//            etPassword2.setText("")
//            etPassword.requestFocus()
//            Toast.makeText(context, "Please enter the same passwords on both fields", Toast.LENGTH_SHORT).show()
//            return
//        }
//        else{
//            val accountant = Accountants(etFName.text.toString(),
//                    etLName.text.toString(), etEmail.text.toString(),
//                    etUsername.text.toString(), gender, etPassword.text.toString())
//
//            CoroutineScope(Dispatchers.IO).launch {
//                context?.let { UserDB.getInstance(it).getAccountantDao().registerAccountant(accountant) }
//            }
//
//            emptyFields()
//
//            Toast.makeText(context, "Successfully registered", Toast.LENGTH_SHORT).show()
//
//            startActivity(Intent(context, LoginActivity::class.java))
//
//        }
//    }

    private fun register(){
        var accountant = Accountant(null, accountantFName =  etFName.text.toString(), accountantLName = etLName.text.toString(),
                accountantEmailAddress = etEmail.text.toString(), gender = gender,
                accountantExperience = etExperience.text.toString().toInt(), accountantMob = etMobNo.text.toString().toInt(),
                pricePerDay = etPrice.text.toString().toDouble())


        CoroutineScope(Dispatchers.IO).launch{
            try{
                val accountantRepository = AccountantRepository()

                val response = accountantRepository.registerAccountant(accountant)

                if(response.success == true){
                    withContext(Main){
                        Toast.makeText(context, response.message, Toast.LENGTH_SHORT).show()

                        emptyFields()

                        startActivity(Intent(context, LoginActivity::class.java))

                    }
                }
            }
            catch (ex : Exception){
                withContext(Main) {
                    Toast.makeText(
                            context,
                            "The email address has already been registered in the system",
                            Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

//    private fun uploadImage() {
//        val accountant = Accountant(null, accountantFName =  etFName.text.toString(), accountantLName = etLName.text.toString(),
//                accountantEmailAddress = etEmail.text.toString(), gender = gender,
//               accountantExperience = etExperience.text.toString().toInt(), accountantMob = etMobNo.text.toString(),
//               pricePerDay = etPrice.text.toString().toDouble())
//        val file = File(imageUrl)
//        val reqFile =
//                RequestBody.create(MediaType.parse("multipart/form-data"), file)
//        val body =
//                MultipartBody.Part.createFormData("file", file.name, reqFile)
//        CoroutineScope(Dispatchers.IO).launch {
//            try {
//                val accountantRepository = AccountantRepository()
//                val response = accountantRepository.uploadImage(accountant, body)
//                if (response.success == true) {
//                    withContext(Main) {
//                        Toast.makeText(context, "Uploaded", Toast.LENGTH_SHORT)
//                                .show()
//                    }
//                }
//            } catch (ex: Exception) {
//                withContext(Main) {
//                    Log.d("Mero Error ", ex.localizedMessage)
//                    Toast.makeText(
//                            context,
//                            ex.localizedMessage,
//                            Toast.LENGTH_SHORT
//                    ).show()
//                }
//            }
//        }
//    }
//
//    private fun loadPopUpMenu() {
//        val popupMenu = PopupMenu(context, imgAccountant)
//        popupMenu.menuInflater.inflate(R.menu.imagemenu, popupMenu.menu)
//        popupMenu.setOnMenuItemClickListener { item ->
//            when (item.itemId) {
//                R.id.menuCamera ->
//                    openCamera()
//                R.id.menuGallery ->
//                    openGallery()
//            }
//            true
//        }
//        popupMenu.show()
//    }
//
//    private var REQUEST_GALLERY_CODE = 0
//    private var REQUEST_CAMERA_CODE = 1
//    private var imageUrl: String? = null
//
//    private fun openGallery() {
//        val intent = Intent(Intent.ACTION_PICK)
//        intent.type = "image/*"
//        startActivityForResult(intent, REQUEST_GALLERY_CODE)
//    }
//    private fun openCamera() {
//        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//        startActivityForResult(cameraIntent, REQUEST_CAMERA_CODE)
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (resultCode == Activity.RESULT_OK) {
//            if (requestCode == REQUEST_GALLERY_CODE && data != null) {
//                val selectedImage = data.data
//                val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
//                val contentResolver = context?.contentResolver
//                val cursor =
//                        contentResolver?.query(selectedImage!!, filePathColumn, null, null, null)
//                cursor!!.moveToFirst()
//                val columnIndex = cursor.getColumnIndex(filePathColumn[0])
//                imageUrl = cursor.getString(columnIndex)
//                imgAccountant.setImageBitmap(BitmapFactory.decodeFile(imageUrl))
//                cursor.close()
//            } else if (requestCode == REQUEST_CAMERA_CODE && data != null) {
//                val imageBitmap = data.extras?.get("data") as Bitmap
//                val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
//                val file = bitmapToFile(imageBitmap, "$timeStamp.jpg")
//                imageUrl = file!!.absolutePath
//                imgAccountant.setImageBitmap(BitmapFactory.decodeFile(imageUrl))
//            }
//        }
//    }
//
//    private fun bitmapToFile(
//            bitmap: Bitmap,
//            fileNameToSave: String
//    ): File? {
//        var file: File? = null
//        return try {
//            file = File(
//                    context?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
//                            .toString() + File.separator + fileNameToSave
//            )
//            file?.createNewFile()
//            //Convert bitmap to byte array
//            val bos = ByteArrayOutputStream()
//            bitmap.compress(Bitmap.CompressFormat.PNG, 0, bos) // YOU can also save it in JPEG
//            val bitMapData = bos.toByteArray()
//            //write the bytes in file
//            val fos = FileOutputStream(file)
//            fos.write(bitMapData)
//            fos.flush()
//            fos.close()
//            file
//        } catch (e: java.lang.Exception) {
//            e.printStackTrace()
//            file // it will return null
//        }
//    }

}