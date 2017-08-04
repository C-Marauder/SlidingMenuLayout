package www.xqy.cn.library

import android.view.View

/**
 * Created by XieQiongYu on 2017/7/31.
 * @Date 2017/7/31
 * @Author xieqiongyu
 * @Email 17775621462@163.com
 * ^_^ 今天敲一行代码，明天敲一行代码，到了后天你就会有两行代码。
 */
interface DragListener {
    fun onDrag( progress:Float)
    fun isOpened()
    fun isClosed()
}



interface SlideMenuHelper{
    var mMenuView:View
    var mContentView:View
    var mScaleFaction :Float
    var mTranslateFaction:Float


}