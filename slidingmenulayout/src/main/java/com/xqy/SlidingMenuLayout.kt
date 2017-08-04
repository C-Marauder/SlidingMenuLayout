package www.xqy.cn.library

import android.animation.FloatEvaluator
import android.content.Context
import android.support.v4.view.ViewCompat
import android.support.v4.widget.ViewDragHelper
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout

/**
 * Created by XieQiongYu on 2017/7/29.
 * @Date 2017/7/29
 * @Author xieqiongyu
 * @Email 17775621462@163.com
 * ^_^ 今天敲一行代码，明天敲一行代码，到了后天你就会有两行代码。
 */
internal class SlidingMenuLayout : FrameLayout, SlideMenuHelper {
    var mDragListener: DragListener? = null

    override var mTranslateFaction: Float = 2 / 3f //默认位移比例

    override var mScaleFaction: Float = 0.7f //默认缩放比例

    override lateinit var mMenuView: View //菜单

    override lateinit var mContentView: View // 内容视图
    private val mRange: Int by lazy {
        //可拖动范围
        (measuredWidth * mTranslateFaction).toInt()
    }
    private lateinit var mViewDragHelper: ViewDragHelper
    private var mLeft: Int = 0
    private lateinit var floatEvaluator: FloatEvaluator
    var currentDragState = DragState.isClosed
    enum class DragState{
        isOpened,
        isClosed,
        isDragging

    }

    /**
     * @Author :xqy
     * @Date :create at 2017/7/31 下午2:53
     * @function : 打开菜单
     *
     **/
    fun openMenu() {

        if (mViewDragHelper.smoothSlideViewTo(mContentView, mRange, mContentView.top)) {
            ViewCompat.postInvalidateOnAnimation(this)

        }

    }

    /**
     * @Author :xqy
     * @Date :create at 2017/7/31 下午2:53
     * @function : 关闭菜单
     *
     **/
    fun closeMenu() {

        if ( mViewDragHelper.smoothSlideViewTo(mContentView, 0, 0)) {
            ViewCompat.postInvalidateOnAnimation(this)
        }


    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }


    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    constructor(context: Context?) : super(context) {
        init()
    }


    private fun init() {
        mViewDragHelper = ViewDragHelper.create(this, 0.7f, ViewDragCallBack())
        mViewDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT)
        floatEvaluator = FloatEvaluator()


    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = getDefaultSize(suggestedMinimumWidth, widthMeasureSpec)//得到viewgroup的宽高
        val height = getDefaultSize(suggestedMinimumHeight, heightMeasureSpec)
        val lp = mMenuView.layoutParams
        lp.width = mRange + ((width - mRange) * (1 - mScaleFaction)).toInt()
        mMenuView.layoutParams = lp
        measureChild(mMenuView, width, height)
    }


    private inner class ViewDragCallBack : ViewDragHelper.Callback() {
        override fun tryCaptureView(child: View?, pointerId: Int): Boolean {
            return child == mContentView
        }

        override fun clampViewPositionHorizontal(child: View?, left: Int, dx: Int): Int {
            mLeft = left
            if (child == mContentView) {
                if (left <= 0) {//内容视图不能往左滑动
                    mLeft = 0
                } else if (left >= mRange) { //内容视图不能滑过屏幕三分之二的区域
                    mLeft = mRange
                }
                //返回子控件拖动后左侧的位置
            }

            return mLeft
        }

        override fun clampViewPositionVertical(child: View?, top: Int, dy: Int): Int {
            return 0
        }

        override fun onViewPositionChanged(changedView: View?, left: Int, top: Int, dx: Int, dy: Int) {
            super.onViewPositionChanged(changedView, left, top, dx, dy)
            val dragProgress = Math.abs(changedView!!.left * 1f / mRange)

            if (dragProgress == 0.0f) {
                currentDragState = DragState.isClosed

            } else if (dragProgress == 1.0f) {
                currentDragState = DragState.isOpened
            }else{
                currentDragState = DragState.isDragging
            }
            if (mDragListener != null) {

                when(currentDragState){
                    DragState.isOpened -> mDragListener!!.isOpened()
                    DragState.isClosed -> mDragListener!!.isClosed()
                    DragState.isDragging -> mDragListener!!.onDrag(dragProgress)
                }

            }
            contentViewAnim(dragProgress)
            menuViewAnim(dragProgress)

        }

        /**
         * @Author :xqy
         * @Date :create at 2017/8/1 上午8:52
         * @function : 边界拖动
         *
         **/
        override fun onEdgeDragStarted(edgeFlags: Int, pointerId: Int) {
            mViewDragHelper.captureChildView(mContentView, pointerId)
        }

        private var edgeTouched: Boolean = false
        override fun onEdgeTouched(edgeFlags: Int, pointerId: Int) {
            edgeTouched = true
        }

        /**
         * @Author :xqy
         * @Date :create at 2017/7/31 上午10:11
         * @function : 菜单动画
         *
         **/
        private fun menuViewAnim(fraction: Float) {

            mMenuView.translationX = floatEvaluator.evaluate(fraction, -mMenuView.width, 0)
            mMenuView.scaleX = floatEvaluator.evaluate(fraction, 0, 1f)
            mMenuView.scaleY = floatEvaluator.evaluate(fraction, 0, 1f)
            mMenuView.alpha = fraction
        }

        /**
         * @Author :xqy
         * @Date :create at 2017/7/31 上午10:13
         * @function :内容视图动画
         *
         **/
        private fun contentViewAnim(fraction: Float) {

            mContentView.scaleY = floatEvaluator.evaluate(fraction, 1f, mScaleFaction)
            mContentView.scaleX = floatEvaluator.evaluate(fraction, 1f, mScaleFaction)

        }

        override fun onViewReleased(releasedChild: View?, xvel: Float, yvel: Float) {
            super.onViewReleased(releasedChild, xvel, yvel)
            if (releasedChild == mContentView) {
                if (mLeft <= mRange / 2) {

                    closeMenu()
                } else {
                    openMenu()
                }

                if (xvel < -200 && currentDragState == DragState.isOpened) {

                    closeMenu()
                }

            }
        }

        override fun getViewHorizontalDragRange(child: View?): Int {
            return if (child === mContentView) mRange else 0
        }

    }

    override fun computeScroll() {
        super.computeScroll()
        if (mViewDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this)

        }
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return mViewDragHelper.shouldInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        mViewDragHelper.processTouchEvent(event)
        return true
    }

}