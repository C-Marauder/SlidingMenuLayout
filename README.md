# SlidingMenuLayout

kotlin 侧滑菜单

**compile 'com.xqy.slidingmenu:slidingmenulayout:1.0.1'** 

2. 使用简单,无需在xml中写布局

```kotlin
 SlidingMenuBuilder(this)
                .bindMenuView(R.layout.drawer_menu) //绑定菜单
                .bindToolbar(mToolbar,resId)//如果用到toolbar,可以绑定toolbar,第二个参数是icon
		.scaleFaction(0.7f)//缩放比例,非必须设置,有默认值
                .translateFaction(2/3f)//移动比例,非必须设置,有默认值
		.setMenuBackgroud(colorResId)//设置菜单背景颜色
		.setDragListener(object :DragListener{//拖拽监听
                
                    override fun isOpened() { //打开状态                 			
                      	  	Toast.makeText(this@MainActivity,"isOpened",Toast.LENGTH_SHORT).show()
                    }

                    override fun isClosed() {//关闭状态
                        Toast.makeText(this@MainActivity,"isClosed",Toast.LENGTH_SHORT).show()
                    }

                    override fun onDrag(progress: Float) {//拖拽中
                        Toast.makeText(this@MainActivity,"onDrag",Toast.LENGTH_SHORT).show()
                    }

                })
                .init()//初始化
```
