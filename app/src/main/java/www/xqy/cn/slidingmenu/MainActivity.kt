package www.xqy.cn.slidingmenu

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.widget.Toast
import www.xqy.cn.library.DragListener
import www.xqy.cn.library.SlidingMenuBuilder

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val mToolbar :Toolbar = findViewById(R.id.toolbar) as Toolbar
        SlidingMenuBuilder(this)
                .bindMenuView(R.layout.menu_view)
                .bindToolbar(mToolbar,R.drawable.nav)
                .setDragListener(object : DragListener {
                    override fun isOpened() {
                        Toast.makeText(this@MainActivity,"isOpened", Toast.LENGTH_SHORT).show()
                    }

                    override fun isClosed() {
                        Toast.makeText(this@MainActivity,"isClosed", Toast.LENGTH_SHORT).show()
                    }

                    override fun onDrag(progress: Float) {
                        //Toast.makeText(this@MainActivity,"onDrag",Toast.LENGTH_SHORT).show()
                    }

                })
                .init()
    }
}
