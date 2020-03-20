package com.example.reuesview


import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.facebook.FacebookSdk
import org.json.JSONObject

/**
 * A simple [Fragment] subclass.
 */
class Recycler_view : Fragment() {

    private var name : String = ""
    private var PhotoURL : String = ""
    private var mButtonDialog: Button? = null

    //    private var det : String = ""
    fun newInstance(url: String,name: String): Recycler_view {

        val fragment = Recycler_view()
        val bundle = Bundle()
        bundle.putString("name", name)
        bundle.putString("PhotoURL", url)
        fragment.setArguments(bundle)
        return fragment
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val bundle = arguments
        if (bundle != null) {
            name = bundle.getString("name").toString()
            PhotoURL = bundle.getString("PhotoURL").toString()

        }else {

        }

        val view = inflater.inflate(R.layout.fragment_recycler_view, container, false)

        mButtonDialog = view.findViewById(R.id.btn_alert) as Button

        mButtonDialog!!.setOnClickListener(View.OnClickListener {
            val builder: AlertDialog.Builder = AlertDialog.Builder(context)
            builder.setMessage("ต้องการออกจากระบบหรือไม่?")
            builder.setPositiveButton("Yes",
                DialogInterface.OnClickListener { dialog, id ->
                    Toast.makeText(
                        FacebookSdk.getApplicationContext(),
                        "ขอบคุณครับ", Toast.LENGTH_SHORT
                    ).show()
                    val login = login()
                    val fm = fragmentManager
                    val transaction : FragmentTransaction = fm!!.beginTransaction()
                    transaction.replace(R.id.layout, login,"fragment_list_view")
                    transaction.commit()
                })
            builder.setNegativeButton("No",
                DialogInterface.OnClickListener { dialog, which ->
                    //dialog.dismiss();
                })
            builder.show()
        })

        // Inflate the layout for this fragment
        val ivProfilePicture = view.findViewById(R.id.iv_profile) as ImageView
        val tvName = view.findViewById(R.id.name) as TextView
        tvName.text = name.toString()
        Glide.with(activity!!.baseContext)
            .load(PhotoURL)
            .into(ivProfilePicture)


        val jsonString : String = loadJsonFromAsset("bnk48.json", activity!!.baseContext).toString()
        val json = JSONObject(jsonString)
        val jsonArray = json.getJSONArray("bnk48")

        val recyclerView: RecyclerView = view.findViewById(R.id.recyLayout)

        //ตั้งค่า Layout
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(activity!!.baseContext)
        recyclerView.layoutManager = layoutManager

        //ตั้งค่า Adapter
        val adapter = MyRecyclerAdapter(activity!!.baseContext,activity!!.supportFragmentManager,jsonArray)
        recyclerView.adapter = adapter


        return view

    }


    private fun loadJsonFromAsset(filename: String, context: Context): String? {
        val json: String?

        try {
            val inputStream = context.assets.open(filename)
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            json = String(buffer, Charsets.UTF_8)
        } catch (ex: java.io.IOException) {
            ex.printStackTrace()
            return null
        }

        return json
    }



}
