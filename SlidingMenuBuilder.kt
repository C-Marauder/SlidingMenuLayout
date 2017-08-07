package www.crionline.cn.library.widget.slide

import android.opengl.GLSurfaceView
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.jetbrains.anko.backgroundResource

/**
 * Created by XieQiongYu on 2017/8/2.
 * @Date 2017/8/2
 * @Author xieqiongyu
 * @Email 17775621462@163.com
 * ^_^ 今天敲一行代码，明天敲一行代码，到了后天你就会有两行代码。
 */
class SlidingMenuBuilder(val activity: AppCompatActivity) {
    private val slidingMenuLayout by lazy {
        SlidingMenuLayout(activity)
    }
    private val contentView: ViewGroup by lazy {
        activity.findViewById<ViewGroup>(android.R.id.content)
    }

    private var mMenuLayoutId: Int = 0
    private var oldRootView: View
    private var mToolbar: Toolbar? = null
    private var mNavIcon: Int = 0
    init {
        oldRootView = contentView.getChildAt(0)
        contentView.removeAllViews()

    }

    fun bindToolbar(mToolbar: Toolbar, navIcon: Int = 0): SlidingMenuBuilder = apply {
        this.mToolbar = mToolbar
        this.mNavIcon = navIcon

    }

    fun bindMenuView(layoutId: Int) = apply {
        this.mMenuLayoutId = layoutId
    }
    fun scaleFaction(mMenuScaleFaction :Float,mContentScaleFaction :Float):SlidingMenuBuilder = apply {
        slidingMenuLayout.mMenuScaleFaction = mMenuScaleFaction
        slidingMenuLayout.mContentScaleFaction = mContentScaleFaction

    }
    fun translateFaction(mTranslateFaction :Float) = apply{
        slidingMenuLayout.mTranslateFaction = mTranslateFaction
    }
    private var mColorResId:Int = 0
    fun setMenuBackground(colorResId:Int):SlidingMenuBuilder = apply{
        this.mColorResId = colorResId
    }
    fun setDragListener(mDragListener: DragListener) :SlidingMenuBuilder = apply{
        slidingMenuLayout.mDragListener = mDragListener
    }


    fun init() {

        initSlidingMenu()

    }

    private fun initSlidingMenu() {

        if (mColorResId != 0){
            contentView.backgroundResource = mColorResId
        }
        if (mMenuLayoutId != 0) {
            val menuView = LayoutInflater.from(activity).inflate(mMenuLayoutId, slidingMenuLayout, false)
            slidingMenuLayout.addView(menuView)
            slidingMenuLayout.mMenuView = menuView
            val mSlidingFrameLayout = SlidingFrameLayout(activity)
            mSlidingFrameLayout.addView(oldRootView)
            mSlidingFrameLayout.bindSlidingMenuLayout(slidingMenuLayout)
            slidingMenuLayout.addView( mSlidingFrameLayout)
            slidingMenuLayout.mContentView = mSlidingFrameLayout
            contentView.addView(slidingMenuLayout)
        }

        if (mToolbar != null) {
            if (mNavIcon != 0) mToolbar!!.setNavigationIcon(mNavIcon)

            mToolbar!!.setNavigationOnClickListener {
                if (slidingMenuLayout.currentDragState == DragState.isOpened) {
                    slidingMenuLayout.closeMenu()
                } else if(slidingMenuLayout.currentDragState == DragState.isClosed){
                    slidingMenuLayout.openMenu()
                }

            }

        }
    }
}