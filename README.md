
# 模仿 AppleMusic 编写
实现方式 分成三种 :
第一种: ScollerActivity   用到的是MetalDesign 思想 但是难以实现产品需求 交互实现不流畅

第二种: MusicMainActivity  用到 BottomSheetBehavior 实现列表的书单的方式 (个人更倾向于这一种 待添加侧滑删除)
    遇到的问题 BottomSheetBehavior 和 ListView 有事件冲突 (暂时没有想到解决的办法)


第三种 : 基本的实现方式 页面布局为 一个侧滑删除的slideAndDragListView (借鉴与别人的代码)
    遇到的问题 :
                (1) 添加可滑动的头部 上面的事件 被父View给分发处理了
                deal 修改源码的得判断 onInterceptTouchEvent

                (2)不能提供一个简单的listview
                deal  提供 简单的设置 setNormalListView 正常的listview
                    setCanSlideAndDrag  (boolean canDrag, boolean canSilde)  可以上下拖动 可以左右滑动
