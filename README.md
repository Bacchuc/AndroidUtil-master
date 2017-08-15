# AndroidUtil
android项目中的工具类，可以直接下载使用，有自己的总结与封装，也有别人封装的，MainActivity中有具体的使用方法，方便项目的快速开发，会随着能力的提升不断的更新增加，以下是目前的工具类：

1.动画工具类AnimatorUtil
2.活动管理工具类ActivityCollectorUtil
3.用于背景模糊Blur、BlurBehind、OnBlurCompleteListener
4.通过选择相册或者拍照上传单张图片工具类SelectImageUtil
5.PopupWindowSelectUtil
6.Toast工具类ToastUtil
7.AppCenterUtil
8.RetrofitUtil

How to use

Step 1. Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}

Step 2. Add the dependency

	dependencies {
	        compile 'com.github.Bacchuc:AndroidUtil:1.0'
	}


Step 3.直接类名调用方法就可以了，具体需要哪些工具类可以自己瞅瞅~
