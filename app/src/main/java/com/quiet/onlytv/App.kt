package com.quiet.onlytv

import android.app.Application
import android.content.Context
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.multidex.MultiDex
import com.bumptech.glide.Glide
import com.bumptech.glide.util.Util
import com.ludoven.base.utils.ActivityManager
import timber.log.Timber


class App : Application(), DefaultLifecycleObserver {

    companion object{
        var instance: App? = null


        fun exitApp() {
            ActivityManager.getInstance().finishAllActivities()
        }
    }

    override fun onCreate() {
        super<Application>.onCreate()
        instance = this
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)

        ActivityManager.getInstance().init(this)

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
//        LogUtil.i("进入后台")
        exitApp()
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
//        LogUtil.w("onTrimMemory ,level:$level")
        trimMemory(this, level)
    }


    override fun onLowMemory() {
        super.onLowMemory()
//        LogUtil.w("onLowMemory")
        clearMemory(this)
    }


    /**
     * 内存释放 必须在主线程
     * @param context
     * @param level
     */
    private fun trimMemory(context: Context?, level: Int) {
        if (Util.isOnMainThread()) {
            Glide.get(context!!).trimMemory(level)
        }
    }

    /**
     * 清除内存 必须在主线程
     * @param context
     */
    private  fun clearMemory(context: Context?) {
        if (Util.isOnMainThread()) {
            Glide.get(context!!).onLowMemory()
        }
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}