package com.ludoven.base

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.viewbinding.ViewBinding
import com.dylanc.viewbinding.base.ViewBindingUtil
import com.ludoven.base.action.BundleAction
import com.ludoven.base.action.ClickAction
import com.ludoven.base.action.HandlerAction
import com.ludoven.base.action.KeyboardAction
import com.ludoven.base.mvp.BaseContract
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.util.*


/**
 *    author : Android 轮子哥
 *    github : https://github.com/getActivity/AndroidProject-Kotlin
 *    time   : 2018/10/18
 *    desc   : Fragment 技术基类
 */
abstract class BaseFragment<A : BaseActivity<*,*>,VB : ViewBinding, P : BaseContract.IPresenter> : Fragment(),
    HandlerAction, ClickAction, BundleAction, KeyboardAction, BaseContract.IView, Observer {

    /** Activity 对象 */
    private var activity: A? = null

    /** 根布局 */
    private var rootView: View? = null

    /** 当前是否加载过 */
    private var loading: Boolean = false


    val TAG : String by lazy(LazyThreadSafetyMode.NONE){
        this.javaClass.simpleName
    }
    open var mPresenter: P? = null

    private var _binding: VB? = null

    val binding: VB get() = _binding!!

    private var mCompositeDisposable: CompositeDisposable? = null

    open var mFragmentVisible = false

    open fun createPresenter(): P?{
        return null
    }


    @Suppress("UNCHECKED_CAST")
    override fun onAttach(context: Context) {
        super.onAttach(context)
        // 获得全局的 Activity
        activity = requireActivity() as A
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = ViewBindingUtil.inflateWithGeneric(this, inflater,container,false)
        loading = false
        rootView = _binding!!.root
        focusSearchEnable(false)
        initView()
        return rootView
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter = createPresenter()
        if (mPresenter != null) {
            mPresenter?.attachView(this)
        }
    }

    override fun onResume() {
        super.onResume()
        if (!loading) {
            loading = true
            initData()
            onFragmentResume(true)
            return
        }

        if (this.activity?.lifecycle?.currentState == Lifecycle.State.STARTED) {
            onActivityResume()
        } else {
            onFragmentResume(false)
        }
    }

    /**
     * Fragment 可见回调
     *
     * @param first                 是否首次调用
     */
    protected open fun onFragmentResume(first: Boolean) {
        mFragmentVisible = true
    }

    /**
     * Activity 可见回调
     */
    protected open fun onActivityResume() {}

    override fun onStop() {
        super.onStop()
        mFragmentVisible = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        rootView = null
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mPresenter != null) {
            mPresenter?.cancelAll()
            mPresenter?.detachView()
        }
        loading = false
        removeAllDisposable()
        removeCallbacks()
    }

    override fun onDetach() {
        super.onDetach()
        activity = null
    }


    /**
     * 这个 Fragment 是否已经加载过了
     */
    open fun isLoading(): Boolean {
        return loading
    }

    override fun getView(): View? {
        return rootView
    }

    /**
     * 获取绑定的 Activity，防止出现 getActivity 为空
     */
    open fun getAttachActivity(): A? {
        return activity
    }

    /**
     * 获取 Application 对象
     */
    open fun getApplication(): Application? {
        activity?.let { return it.application }
        return null
    }


    /**
     * 初始化控件
     */
    protected abstract fun initView()

    /**
     * 初始化数据
     */
    protected abstract fun initData()

    /**
     * 根据资源 id 获取一个 View 对象
     */
    override fun <V : View?> findViewById(@IdRes id: Int): V? {
        return rootView?.findViewById(id)
    }

    override fun getBundle(): Bundle? {
        return arguments
    }

    /**
     * 跳转 Activity 简化版
     */
    open fun startActivity(clazz: Class<out Activity>) {
        startActivity(Intent(context, clazz))
    }

    /**
     * startActivityForResult 方法优化
     */
    open fun startActivityForResult(clazz: Class<out Activity>, callback: BaseActivity.OnActivityCallback?) {
        activity?.startActivityForResult(clazz, callback)
    }

    open fun startActivityForResult(intent: Intent, callback: BaseActivity.OnActivityCallback?) {
        activity?.startActivityForResult(intent, null, callback)
    }

    open fun startActivityForResult(intent: Intent, options: Bundle?, callback: BaseActivity.OnActivityCallback?) {
        activity?.startActivityForResult(intent, options, callback)
    }

    /**
     * 销毁当前 Fragment 所在的 Activity
     */
    open fun finish() {
        this.activity?.let {
            if (it.isFinishing || it.isDestroyed) {
                return
            }
            it.finish()
        }
    }


    /**
     * Fragment 按键事件派发
     */
    open fun dispatchKeyEvent(event: KeyEvent?): Boolean {
        val fragments: MutableList<Fragment?> = childFragmentManager.fragments
        for (fragment: Fragment? in fragments) {
            // 这个子 Fragment 必须是 BaseFragment 的子类，并且处于可见状态
            if (fragment !is BaseFragment<*,*,*> || fragment.lifecycle.currentState != Lifecycle.State.RESUMED) {
                continue
            }
            // 将按键事件派发给子 Fragment 进行处理
            if (fragment.dispatchKeyEvent(event)) {
                // 如果子 Fragment 拦截了这个事件，那么就不交给父 Fragment 处理
                return true
            }
        }
        return when (event?.action) {
            KeyEvent.ACTION_DOWN -> onKeyDown(event.keyCode, event)
            KeyEvent.ACTION_UP -> onKeyUp(event.keyCode, event)
            else -> false
        }
    }


    /**
     * 按键按下事件回调
     */
    open fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        // 默认不拦截按键事件
        return false
    }

    /**
     * 按键抬起事件回调
     */
    open fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        // 默认不拦截按键事件
        return false
    }

    override fun getContext(): Context? {
        return activity
    }

    override fun showError(flag: Int, errorCode: Int, errorMsg: String?) {

    }

    override fun showResult(flag: Int, bean: BaseBean<String>) {

    }

    open fun addDisposable(disposable: Disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = CompositeDisposable()
            mCompositeDisposable!!.add(disposable)
        }
    }

    open fun removeDisposable(disposable: Disposable) {
        if (mCompositeDisposable != null) mCompositeDisposable!!.remove(disposable)
    }

    open fun removeAllDisposable() {
        if (mCompositeDisposable != null) mCompositeDisposable!!.clear()
    }


    open fun focusSearchEnable(enable: Boolean) {
        if (_binding == null){
            return
        }

        (_binding?.root as ViewGroup).descendantFocusability =
            if (enable) ViewGroup.FOCUS_AFTER_DESCENDANTS else ViewGroup.FOCUS_BLOCK_DESCENDANTS
    }


    open fun focusSearchEnable(view: ViewGroup,enable: Boolean) {
        (view).descendantFocusability =
            if (enable) ViewGroup.FOCUS_AFTER_DESCENDANTS else ViewGroup.FOCUS_BLOCK_DESCENDANTS
    }

    open fun enableFocusableAndRequestFirstFocused():Boolean{
        if (_binding == null){
            return false
        }
        focusSearchEnable(true)
        return false
    }


    open fun requestTabFocused():Boolean{
        return false
    }

    override fun update(p0: Observable?, p1: Any?) {

    }

    fun showToast(msg:String){
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
    }

    fun showToast(msg:Int){
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
    }
}