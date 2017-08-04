package www.xqy.cn.library

import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

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
    private val contentView by lazy {
        activity.findViewById(android.R.id.content) as ViewGroup

    }
//    private lateinit var mGlSurfaceView: GLSurfaceView
//    private var mIsEnable: Boolean = false
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

//    private val supportsEs: Boolean by lazy {
//        val configurationInfo = App.instance.activityManager.deviceConfigurationInfo
//        configurationInfo.reqGlEsVersion >= 0x20000
//    }

//    fun enableStarBackgroud(isEnable: Boolean): SlidingMenuBuilder = apply {
//        this.mIsEnable = isEnable
//    }

    fun bindMenuView(layoutId: Int) = apply {
        this.mMenuLayoutId = layoutId
    }
    fun scaleFaction(mScaleFaction :Float): SlidingMenuBuilder = apply {
        slidingMenuLayout.mScaleFaction = mScaleFaction
    }
    fun translateFaction(mTranslateFaction :Float){
        slidingMenuLayout.mTranslateFaction = mTranslateFaction
    }
    fun setMenuBackgroud(): SlidingMenuBuilder = apply{

    }
    fun setDragListener(mDragListener: DragListener) : SlidingMenuBuilder = apply{
        slidingMenuLayout.mDragListener = mDragListener
    }
//    fun setStarPause() {
//        mGlSurfaceView.onPause()
//    }
//
//    fun setStarResume() {
//        mGlSurfaceView.onResume()
//    }

    fun init() {
//        if (mIsEnable && supportsEs) {
//            mGlSurfaceView = GLSurfaceView(activity)
//            mGlSurfaceView.setEGLContextClientVersion(2)
//            // Set the renderer to our demo renderer, defined below.
//            val mRenderer = ParticleSystemRenderer(mGlSurfaceView)
//            mGlSurfaceView.setRenderer(mRenderer)
//            mGlSurfaceView.renderMode = GLSurfaceView.RENDERMODE_CONTINUOUSLY
//            contentView.addView(mGlSurfaceView)
//
//        }
        initSlidingMenu()

    }

    private fun initSlidingMenu() {


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

                if (slidingMenuLayout.currentDragState == SlidingMenuLayout.DragState.isOpened) slidingMenuLayout.closeMenu()
                else if (slidingMenuLayout.currentDragState == SlidingMenuLayout.DragState.isClosed) slidingMenuLayout.openMenu()
            }

        }
    }
}