package com.example.plombier

import android.Manifest
import android.annotation.TargetApi
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract
import android.telephony.SmsManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

import kotlinx.android.synthetic.main.dashboard_content.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
class Dashboard : AppCompatActivity(){
    lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dashboard)
        toolbar = findViewById(R.id.dashboard_toolbar)
        setSupportActionBar(toolbar)

        rv_dashboard.text
        spam_msg.text
        btn_msg.setOnClickListener{
            for (i in getContacts()) {

                Log.d("getContacts", "the number is:" + i)
                Toast.makeText(this, R.string.msg, Toast.LENGTH_SHORT).show();
                var sms = SmsManager.getDefault()
                sms.sendTextMessage(i, null, spam_msg.text.toString(), null, null)

            }
        }

        val PERMISSION_ALL = 1
        val PERMISSIONS = arrayOf(
            Manifest.permission.READ_CONTACTS,

            Manifest.permission.SEND_SMS

        )

        if (ActivityCompat.checkSelfPermission(
                this,
                PERMISSIONS.toString()
            ) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL)
        }





    }
    fun getContacts(): ArrayList<String> {
        val contactList = ArrayList<String>()

        val cursor = contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            null,
            null,
            null
        )
        if (cursor != null && cursor.moveToFirst()) {
            do {
                val number =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                contactList.add(number)


            } while (cursor.moveToNext())
            cursor.close()

        }
        return contactList
    }
}