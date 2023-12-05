package pl.alexbul.bt_def

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_list)
        initRcView()
    }


    private fun initRcView(){
        val uy = mutableListOf<Int>(12,3,4,67,9)
        val rcView = findViewById<RecyclerView>(R.id.rcViewPaired)
        rcView.layoutManager = LinearLayoutManager(this)
        val adapter = ItemAdapter()
        rcView.adapter = adapter



      //  val tem = findViewById<TextView>(R.id.tvEmptySearch)
      //  tem.text = createDeviceList().size.toString()
        adapter.submitList(createDeviceList())

        adapter.submitList(createDeviceList())
    }

    private fun createDeviceList(): List<ListItem>{
        val list = ArrayList<ListItem>()

        for (i in 0 until 5){
            list.add(
                ListItem(
                    "Device $i",
                    "34:56:89:56"
                )
            )
        }
        return list
    }
}
