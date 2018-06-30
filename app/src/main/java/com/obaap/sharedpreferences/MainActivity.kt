package com.obaap.sharedpreferences

import android.content.ContentValues
import android.content.Context
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import android.widget.Toolbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var dBase = openOrCreateDatabase("mydb",Context.MODE_PRIVATE,null)
        dBase.execSQL("create table if not exists employee(id number primary key,name varchar(50),desig varchar(50),dept varchar(50))")
        insert.setOnClickListener {
            if(id.text.toString().length>0 && name.text.toString().length>0 && desig.text.toString().length>0 && dept.text.toString().length>0) {
                var cv = ContentValues()
                cv.put("id", id.text.toString().toInt())
                cv.put("name", name.text.toString())
                cv.put("desig", desig.text.toString())
                cv.put("dept", dept.text.toString())
                var status = dBase.insert("employee", null, cv)
                if (status > 0) {
                    Toast.makeText(this@MainActivity, "Insert successfully...", Toast.LENGTH_SHORT).show()
                    id.setText("")
                    name.setText("")
                    desig.setText("")
                    dept.setText("")
                } else {
                    Toast.makeText(this@MainActivity, "Error in Insert...", Toast.LENGTH_SHORT).show()
                }
            }
        }
        read.setOnClickListener {
            var list= mutableListOf<String>()
            var c = dBase.query("employee",null,null,null,null,null,null)
            while (c.moveToNext()){
                list.add("${c.getInt(0)}\t${c.getString(1)}\n${c.getString(2)}\t${c.getString(3)}")
            }
            lview.adapter = ArrayAdapter<String>(this@MainActivity,android.R.layout.simple_list_item_single_choice,list)
        }
        update.setOnClickListener {
            var cv = ContentValues()
            cv.put("name",name.text.toString())
            cv.put("desig",desig.text.toString())
            var status =dBase.update("employee",cv,"id=?", arrayOf(id.text.toString()))
            if (status>0){
                Toast.makeText(this@MainActivity,"Updated successfully...",Toast.LENGTH_SHORT).show()
                id.setText("")
                name.setText("")
                desig.setText("")
                dept.setText("")
            }else{
                Toast.makeText(this@MainActivity,"Error in update",Toast.LENGTH_SHORT).show()
            }
        }
        delete.setOnClickListener {
            var status = dBase.delete("employee","id=?", arrayOf(id.text.toString()))
            if (status>0){
                Toast.makeText(this@MainActivity,"Deleted Successfully...",Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this@MainActivity,"Delete operation failed...",Toast.LENGTH_SHORT).show()
            }

        }
    }

}
