# SlidingMenuLayout
kotlin 侧滑菜单
1.使用简单,无需在xml中写布局

    SlidingMenuBuilder(this)
                .bindMenuView(R.layout.drawer_menu)
                .bindToolbar(mToolbar)
                .setDragListener(object :DragListener{
                    override fun isOpened() {
                        Toast.makeText(this@MainActivity,"isOpened",Toast.LENGTH_SHORT).show()
                    }

                    override fun isClosed() {
                        Toast.makeText(this@MainActivity,"isClosed",Toast.LENGTH_SHORT).show()
                    }

                    override fun onDrag(progress: Float) {
                        //Toast.makeText(this@MainActivity,"onDrag",Toast.LENGTH_SHORT).show()
                    }

                })
                .init()
