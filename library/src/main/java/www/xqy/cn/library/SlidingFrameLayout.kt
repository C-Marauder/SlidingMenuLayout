package www.xqy.cn.library

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.FrameLayout

/**
 * Created by XieQiongYu on 2017/8/2.
 * @Date 2017/8/2
 * @Author xieqiongyu
 * @Email 17775621462@163.com
 * ^_^ 今天敲一行代码，明天敲一行代码，到了后天你就会有两行代码。
 */
internal class SlidingFrameLayout : FrameLayout {
    private  var mSlidingMenuLayout: SlidingMenuLayout ?= null
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    constructor(context: Context?) : super(context)
    fun bindSlidingMenuLayout(mSlidingMenuLayout: SlidingMenuLayout){
        this.mSlidingMenuLayout = mSlidingMenuLayout
    }
    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {

        if (mSlidingMenuLayout !=null&& mSlidingMenuLayout!!.currentDragState == SlidingMenuLayout.DragState.isOpened) {
            return true
        }
        return super.onInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        if (mSlidingMenuLayout !=null&& mSlidingMenuLayout!!.currentDragState == SlidingMenuLayout.DragState.isOpened) {
            when (event!!.action) {
                MotionEvent.ACTION_DOWN -> {

                    performClick()
                    return true
                }

                MotionEvent.ACTION_UP -> {

                    mSlidingMenuLayout!!.closeMenu()
                    return true
                }

            }
        }


        return super.onTouchEvent(event)
    }

    override fun performClick(): Boolean {
        return super.performClick()
    }
}