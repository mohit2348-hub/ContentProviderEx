package com.example.contentproviderex

import android.annotation.SuppressLint
import android.app.Instrumentation.ActivityResult
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import com.example.contentproviderex.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val list: MutableLiveData<MutableSet<String>> = MutableLiveData()

    val activityresult =
        registerForActivityResult(ActivityResultContracts.RequestPermission())
        {
            if (it) {
                list?.postValue(getContactList())

            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        list?.postValue(getContactList())
        list?.observe(this)
        {
            it?.let {
                binding.recyclerView.adapter = MyAdapter(it.toList())

            }
        }

    }

    @SuppressLint("Range")
    private fun getContactList(): MutableSet<String> {
        val set = mutableSetOf<String>()
        sdkIntAboveOreo {
            isPermissionGranted(this, android.Manifest.permission.READ_CONTACTS)
            {
                if (it) {
                    val contentResolver = applicationContext.contentResolver
                    val cursor =
                        contentResolver.query(
                            ContactsContract.Contacts.CONTENT_URI,
                            null,
                            null,
                            null
                        )
                    if (cursor?.moveToFirst() == true) {
                        do {
                            val name =
                                cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                            set.add(name)
                            Log.d("AASSa", "" + name)

                        } while (cursor.moveToNext())
                    }

                } else {
                    activityresult.launch(android.Manifest.permission.READ_CONTACTS)
                    Log.d("AASSa", "" + list)

                }

            }


        }
        return set
    }

    inline fun sdkIntAboveOreo(call: () -> Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            call.invoke()
        }


    }

    inline fun isPermissionGranted(context: Context, permission: String, call: (Boolean) -> Unit) {
        if (ContextCompat.checkSelfPermission(
                context,
                permission,
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            call.invoke(true)
        } else {
            call.invoke(false)
        }

    }


}