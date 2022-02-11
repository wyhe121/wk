package com.example.projectdemo.ext


import android.content.Context
import android.graphics.Color
import android.text.Html
import android.text.Spanned
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.BaseAdapter
import androidx.appcompat.widget.Toolbar
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.chad.library.adapter.base.BaseQuickAdapter
import com.example.projectdemo.App
import com.example.projectdemo.Ktx.Companion.app
import com.example.projectdemo.R
import com.example.projectdemo.appContext
import com.example.projectdemo.data.model.bean.AriticleResponse
import com.example.projectdemo.data.model.bean.BaseResponse
import com.example.projectdemo.data.model.bean.ListDataUiState
import com.example.projectdemo.network.AppException
import com.example.projectdemo.network.ExceptionHandle
import com.example.projectdemo.ui.adapter.HomeAdapter
import com.example.projectdemo.ui.fragment.*
import com.example.projectdemo.util.SettingUtil
import com.example.projectdemo.viewmodel.BaseViewModel2
import com.example.projectdemo.weight.recyclerView.DefineLoadMoreView
import com.example.projectdemo.weight.viewpager.ScaleTransitionPagerTitleView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.yanzhenjie.recyclerview.SwipeRecyclerView
import kotlinx.android.synthetic.main.include_toolbar.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import net.lucode.hackware.magicindicator.MagicIndicator
import net.lucode.hackware.magicindicator.buildins.UIUtil
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator
import java.lang.reflect.ParameterizedType
import java.util.*

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

fun ViewPager2.init(
    fragment: Fragment,
    fragmentArray:MutableList<Fragment>,
    isUserInputEnabled:Boolean =true
)
{
  this.isUserInputEnabled = isUserInputEnabled
    adapter= object :FragmentStateAdapter(fragment)
    {
        override fun createFragment(position: Int)=fragmentArray[position]
        override fun getItemCount()=fragmentArray.size
    }
}
fun<VM>getVmClazz(obj: Any):VM{
    return (obj.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as VM
}

fun ViewPager2.initMain(fragment: Fragment)
{
    isUserInputEnabled=false

    adapter= object :FragmentStateAdapter(fragment)
    {
        override fun createFragment(position: Int): Fragment {
            when(position)
            {
                0-> return HomeFragment()
                1-> return ProjectFragment()
                2-> return SquareFragment()
                3-> return PublicNumberFragment()
                4-> return MeFragment()
                else->return HomeFragment()
            }
        }
        override fun getItemCount()=5
    }
}
fun MagicIndicator.bindViewPager(
    viewPager:ViewPager2,
    titleArray:MutableList<String>,
    action:(indext:Int) -> Unit = {})
{
    val commonNavigator = CommonNavigator(context)
    commonNavigator.adapter=object : CommonNavigatorAdapter() {
        override fun getCount()=titleArray.size

        override fun getTitleView(context: Context?, index: Int): IPagerTitleView {
          return  ScaleTransitionPagerTitleView(context!!).apply{
              //设置文本
              text = titleArray[index]
              //字体大小
              textSize = 17f
              //未选中颜色
              normalColor = Color.WHITE
              //选中颜色
              selectedColor = Color.WHITE
              //点击事件
              setOnClickListener {
                  viewPager.currentItem = index
                  action.invoke(index)
              }
          }
        }

        override fun getIndicator(context: Context?): IPagerIndicator {
            return LinePagerIndicator(context).apply {
                mode = LinePagerIndicator.MODE_EXACTLY
                //线条的宽高度
                lineHeight = UIUtil.dip2px(context, 3.0).toFloat()
                lineWidth = UIUtil.dip2px(context, 30.0).toFloat()
                //线条的圆角
                roundRadius = UIUtil.dip2px(context, 6.0).toFloat()
                startInterpolator = AccelerateInterpolator()
                endInterpolator = DecelerateInterpolator(2.0f)
                //线条的颜色
                setColors(Color.WHITE)
            }

        }
    }
    this.navigator = commonNavigator

    viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            this@bindViewPager.onPageSelected(position)
            action.invoke(position)
        }

        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
            super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            this@bindViewPager.onPageScrolled(position, positionOffset, positionOffsetPixels)
        }

        override fun onPageScrollStateChanged(state: Int) {
            super.onPageScrollStateChanged(state)
            this@bindViewPager.onPageScrollStateChanged(state)
        }
    })
}
fun BottomNavigationView.init(block:(Int)->Unit)
{
    setOnNavigationItemSelectedListener {
        block.invoke(it.itemId)
        true
    }
}
fun Toolbar.init(
    titleStr:String,
    resId:Int
):Toolbar
{
    title=titleStr
    inflateMenu(resId)
   setBackgroundColor(resources.getColor(R.color.colorPrimary))
    return this
}
fun Toolbar.initClose(
    titleStr:String,
    resId:Int,
    backImg:Int =R.drawable.ic_back,
    onBack:() ->Unit = {}
):Toolbar
{
    title=titleStr
    inflateMenu(resId)
    setNavigationIcon(backImg)
    setNavigationOnClickListener { onBack.invoke() }
    setBackgroundColor(resources.getColor(R.color.colorPrimary))
    return this
}
fun SwipeRecyclerView.init(
    recycleradapter:RecyclerView.Adapter<*>,
    layoutManager: RecyclerView.LayoutManager
):SwipeRecyclerView{

    this.layoutManager=layoutManager
    setHasFixedSize(true)
    isNestedScrollingEnabled = true
    adapter=recycleradapter
    return this
    //this.setLoadMoreView(footerView)
}
fun SwipeRecyclerView.initFooter(loadmoreListener: SwipeRecyclerView.LoadMoreListener): DefineLoadMoreView{
    val footerView = DefineLoadMoreView(context)
    footerView.setmLoadMoreListener(SwipeRecyclerView.LoadMoreListener {
        footerView.onLoading()
        loadmoreListener.onLoadMore()
    })
    this.run {
        addFooterView(footerView)
        setLoadMoreView(footerView)
        setLoadMoreListener(loadmoreListener)
    }

//    footerView.setmLoadMoreListener(SwipeRecyclerView.LoadMoreListener {
//        footerView.onLoading()
//        mLoadMoreListener.onLoadMore()
//    })

    //this.setLoadMoreView(footerView)
    return footerView
}
//设置适配器的列表动画
fun BaseQuickAdapter<*, *>.setAdapterAnimation(mode: Int) {
    //等于0，关闭列表动画 否则开启
    if (mode == 0) {
        this.animationEnable = false
    } else {
        this.animationEnable = true
        this.setAnimationWithDefault(BaseQuickAdapter.AnimationType.values()[mode - 1])
    }
}
fun loadListData2(
    data: ListDataUiState<AriticleResponse>,
    baseQuickAdapter: HomeAdapter,
    // loadService: LoadService<*>,
    recyclerView: SwipeRecyclerView,
    swipeRefreshLayout: SwipeRefreshLayout
)
{
    swipeRefreshLayout.isRefreshing = false
    recyclerView.loadMoreFinish(data.isEmpty, data.hasMore)
    if (data.isSuccess) {
        //成功
        when {
            //第一页并没有数据 显示空布局界面
            data.isFirstEmpty -> {
                // loadService.showEmpty()
            }
            //是第一页
            data.isRefresh -> {
                baseQuickAdapter.setList(data.listData)
                //  loadService.showSuccess()
            }
            //不是第一页
            else -> {
                baseQuickAdapter.setList(data.listData)
                // loadService.showSuccess()
            }
        }
    } else {
        //失败
        if (data.isRefresh) {
            //如果是第一页，则显示错误界面，并提示错误信息
            // loadService.showError(data.errMessage)
        } else {
            recyclerView.loadMoreError(0, data.errMessage)
        }
    }
}
fun loadListData(
    adapter: HomeAdapter,
    dataList:ListDataUiState<AriticleResponse>,
    recyclerView: SwipeRecyclerView
)
{
    recyclerView.loadMoreFinish(false, true)
    adapter.setList(dataList.listData)
}

fun String.toHtml(flag: Int = Html.FROM_HTML_MODE_LEGACY): Spanned {
    return if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
        Html.fromHtml(this, flag)
    } else {
        Html.fromHtml(this)
    }
}
//绑定SwipeRecyclerView
fun SwipeRecyclerView.init2(
    layoutManger: RecyclerView.LayoutManager,
    bindAdapter: RecyclerView.Adapter<*>,
    isScroll: Boolean = true
): SwipeRecyclerView {
    layoutManager = layoutManger
    setHasFixedSize(true)
    adapter = bindAdapter
    isNestedScrollingEnabled = isScroll
    return this
}
fun SwipeRecyclerView.initFooter2(loadmoreListener: SwipeRecyclerView.LoadMoreListener): DefineLoadMoreView {
    val footerView = DefineLoadMoreView( App.mApplication)
    //给尾部设置颜色
    footerView.setLoadViewColor(SettingUtil.getOneColorStateList(App.mApplication))
    //设置尾部点击回调
    footerView.setmLoadMoreListener(SwipeRecyclerView.LoadMoreListener {
        footerView.onLoading()
        loadmoreListener.onLoadMore()
    })
    this.run {
        //添加加载更多尾部
        addFooterView(footerView)
        setLoadMoreView(footerView)
        //设置加载更多回调
        setLoadMoreListener(loadmoreListener)
    }
    return footerView
}
fun<T> BaseViewModel2.request(
    block:suspend ()-> BaseResponse<T>,
    success:(T)->Unit,
    error:(AppException)->Unit={},
    isShowDialog:Boolean=false,
    loadingMessage:String="请求网络中..."
): Job {
    return viewModelScope.launch {
        runCatching {
            if (isShowDialog)loadingChange.showDialog.postValue(loadingMessage)
            block()
        }.onSuccess{
            loadingChange.dismissDialog.postValue(false)
            runCatching {
                executeResponse(it){t->success(t)}
            }
        }.onFailure {
            it.message?.loge()
            error(ExceptionHandle.handleException(it))
        }
    }
}

suspend fun <T> executeResponse(
    response: BaseResponse<T>,
    success:suspend CoroutineScope.(T) -> Unit
){
    coroutineScope {
        when
        {
            response.isSuccess()->{
                success(response.getResponseData())
            }
            else->{
                throw AppException(
                    response.getResponseCode(),
                    response.getResponseMsg(),
                    response.getResponseMsg()
                )
            }
        }
    }
}