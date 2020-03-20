package com.example.reuesview

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val fragment_RecyclerView = Recycler_view()
//        val manager = supportFragmentManager
//        val transaction = manager.beginTransaction()
//        transaction.replace(R.id.layout, fragment_RecyclerView,"fragment_RecyclerView")
//        transaction.addToBackStack("fragment_list_view")
//        transaction.commit()

        debugHashKey()

        //  val fragment_list_view = ListView()
        val fragment_login = login()
        val manager = supportFragmentManager;
        val transaction = manager.beginTransaction();
        transaction.replace(R.id.layout,fragment_login,"fragment_login")
        transaction.addToBackStack("fragment_login");
        transaction.commit();




    }

    private fun debugHashKey() {
        try {
            val info = packageManager.getPackageInfo(
                "com.example.reuesview",
                PackageManager.GET_SIGNATURES
            )
            for (signature in info.signatures) {
                val md: MessageDigest = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                Log.d("KeyHash:", Base64.getEncoder().encodeToString(md.digest()))
            }
        } catch (e: PackageManager.NameNotFoundException) {
        } catch (e: NoSuchAlgorithmException) {
        }
    }


//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//
//
//
//    }
}
