package com.swasi.androidsecurity.utils

import android.accounts.Account
import android.accounts.AccountManager
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.ConnectivityManager
import android.os.Build
import android.util.DisplayMetrics
import android.util.Patterns
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ListView
import com.google.android.material.snackbar.Snackbar
import java.io.IOException
import java.net.MalformedURLException
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Sibaprasad Mohanty on 2020-01-13.
 * SPM Limited
 * sp.dobest@gmail.com
 */

object CommonUtils {

    private val UNBOUNDED =
        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)

    fun getBitmap(url: String?): Bitmap? {
        var newurl: URL? = null
        try {
            newurl = URL(url)
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        }
        var miconVal: Bitmap? = null
        try {
            miconVal = BitmapFactory.decodeStream(newurl!!.openConnection().getInputStream())
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return miconVal
    }

    /**
     * Convert pojo class to string string.
     *
     * @param object  the object of the class.
     * @return the string
     */
    fun convertPojoToString(objects: Any): String {
        var pojoString: String = ""
        /* val  mapper ObjectMapper = ObjectMapper()
         try {
             pojoString = mapper.writeValueAsString(object)
         } catch ( e:JsonProcessingException) {
             Log.i("TAG", "convertPojoToString: "+e.getMessage())
         }*/
        return pojoString
    }

    /**
     * Get class from string object.
     *
     * @param value   the jsonString
     * @param mClassz the m classz
     * @return the object
     */
    fun getClassFromString(value: String, mClassz: Any): Object? {
        val requiredClass: Object? = null
        /* val mapper:ObjectMapper =  ObjectMapper()
         try {
             requiredClass = mapper.readValue(value,mClassz);
         } catch (IOException e) {
             e.printStackTrace();
         }*/
        return requiredClass
    }

    fun showOrHideKeyBoard(
        mContext: Context,
        rootLayout: View
    ) {
        val inputMethodManager =
            mContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.toggleSoftInputFromWindow(
            rootLayout.applicationWindowToken,
            InputMethodManager.SHOW_FORCED, 0
        )
    }

    fun showOrHideSoftKeyBoard(editView: View, show: Boolean) {
        val imm = editView.context
            .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (show) {
            imm.showSoftInput(editView, InputMethodManager.SHOW_FORCED)
        } else {
            imm.hideSoftInputFromWindow(editView.windowToken, 0)
        }
        editView.isFocusable = true
    }

    // CHECK EMAIL ID VALIDATION
    fun isValidEmail(target: CharSequence?): Boolean {
        return if (target == null) {
            false
        } else {
            Patterns.EMAIL_ADDRESS.matcher(target).matches()
        }
    }

    // CHECK PASSWORD VALIDATION
    fun isValidPassword(target: String?): Boolean {
        return if (target == null) {
            false
        } else {
            target.length >= 6
        }
    }

    // ================++SET UP TO GET GMAIL =========================
    fun getAccount(accountManager: AccountManager): Account? {
        val accounts = accountManager.getAccountsByType("com.google")
        val account: Account?
        account = if (accounts.size > 0) {
            accounts[0]
        } else {
            null
        }
        return account
    }

    // GET PRIMARY EMAIL ID
    fun getPrimaryEmailId(mContext: Context?): String? {
        val accountManager = AccountManager.get(mContext)
        val account = getAccount(accountManager)
        return account?.name
        /* String primaryEmailid = "";
        Pattern emailPattern = Patterns.EMAIL_ADDRESS;
        AccountManager manager = (AccountManager) mContext.getSystemService(mContext.ACCOUNT_SERVICE);
        Account[] listAccounts = manager.getAccounts();
        if (listAccounts.length != 0) {
            if (emailPattern.matcher(listAccounts[0].name).matches()) {
                primaryEmailid = listAccounts[0].name;
            }
        }
        return primaryEmailid;*/
    }

    /*GETTING VERSION*/
    fun getVersion(context: Context): String {
        val packageManager = context.packageManager
        var versionName = ""
        try {
            val packageInfo =
                packageManager.getPackageInfo(context.packageName, 0)
            versionName = packageInfo.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return versionName
    }

    fun justifyListViewHeightBasedOnChildren(listView: ListView) {
        val adapter = listView.adapter ?: return
        val vg: ViewGroup = listView
        var totalHeight = 0
        for (i in 0 until adapter.count) {
            val listItem = adapter.getView(i, null, vg)
            //            listItem.measure(0, 0);
            listItem.measure(UNBOUNDED, UNBOUNDED)
            totalHeight += listItem.measuredHeight
            //            totalHeight += listItem.getMeasuredHeightAndState();
        }
        val par = listView.layoutParams
        par.height = totalHeight + listView.dividerHeight * (adapter.count - 1)
        listView.layoutParams = par
        listView.requestLayout()
    }

    /*
    Snackbar message to show instance message
    PARAMETERS
    snackbarCallback  : to get callback at the calling place on ACTION BUTTON CLICK ,like UNDO , RETRY
    rootlayout : its the rootlayout of SCREEN WHERE we call this method
    message : The snackbar message
    action_name : undo, retry button name
    isAction : if we want to show action button or not
    Snackbar Time
     */
//SNACKBAR WITH ACTION BUTTON
    fun showSnackBar(
        snackbarCallback: SnackbarCallback,
        rootlayout: View?,
        message: String?,
        action_name: String?,
        snackbarTime: Int
    ) {
        val snackbar = Snackbar
            .make(rootlayout!!, message!!, snackbarTime)
        snackbar.setAction(action_name) { snackbarCallback.onSnackbarActionClick() }
        snackbar.setActionTextColor(Color.parseColor("#ff0000"))
        snackbar.show()
    }

    //SNACKBAR WITHOUT ACTION BUTTON
    fun showSnackBar(
        rootlayout: View?,
        message: String?,
        snackbarTime: Int
    ) { /* Snackbar snackbar = Snackbar
                .make(rootlayout, message , snackbarTime);
        snackbar.show();*/
    }

    fun convertDpToPixel(dp: Float, context: Context): Float {
        val resources = context.resources
        val metrics = resources.displayMetrics
        return dp * (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    }

    /**
     * This method converts device specific pixels to density independent pixels.
     *
     * @param px      A value in px (pixels) unit. Which we need to convert into db
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent dp equivalent to px value
     */
    fun convertPixelsToDp(px: Float, context: Context): Float {
        val resources = context.resources
        val metrics = resources.displayMetrics
        return px / (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    }

    val isLolipop: Boolean
        get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP

    val isMarshmallow: Boolean
        get() = Build.VERSION.SDK_INT >= 23

    val isJellyBean: Boolean
        get() = Build.VERSION.SDK_INT >= 16

    //    =========================SHOWING MESSAGE FOR GEUST USER , FORGOT PASSWORD AND ============
    fun showMessageForUser(mContext: Activity?, message: String?) { /*    AppCompatTextView mTextViewMessageLogin;
    LayoutInflater    inflater = mContext.getLayoutInflater();
    View layout = inflater.inflate(R.layout.include_message_ontop,
            null);
    mTextViewMessageLogin = (AppCompatTextView) layout.findViewById(R.id.mTextViewMessageLogin);
    mTextViewMessageLogin.setText(Html.fromHtml(message));
    Toast toast = new Toast(mContext);
    toast.setGravity(Gravity.FILL_HORIZONTAL|Gravity.TOP, 0, 0);
    toast.setDuration(Toast.LENGTH_LONG);
    toast.setView(layout);
    toast.show();*/
    }

    fun getProductPriceForCreatOrder(OfferPrice: Int, RegularPrice: Int): Double {
        var price = 0.0
        try {
            if (OfferPrice != 0) {
                price = OfferPrice.toDouble()
            } else {
                if (RegularPrice != 0) {
                    price = RegularPrice.toDouble()
                } else {
                }
            }
        } catch (e: NumberFormatException) {
            e.printStackTrace()
        }
        return price
    }

    fun isInternetAvailable(ctx: Context?): Boolean {
        var isConnected = false
        var connectivityManager: ConnectivityManager? = null
        if (ctx != null) {
            connectivityManager =
                ctx.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        }
        if (connectivityManager != null) {
            val networkInfo = connectivityManager.activeNetworkInfo
            if (networkInfo != null && networkInfo.isConnected) {
                isConnected = true
            }
        }
        return isConnected
    }

    val currentTimeInMillis: Long
        get() {
            return System.currentTimeMillis()
        }

    //		String result = df.format(new Date());  27-Dec-2016 09:38 p.m.
    val currentDateAndTime: String
        get() {
            val df = SimpleDateFormat("dd-MMM-yyyy hh:mm a")
            //		String result = df.format(new Date());  27-Dec-2016 09:38 p.m.
            return df.format(Date())
        }


    val currentDate: String
        get() {
            val df = SimpleDateFormat("dd-MMM-yyyy")
            return df.format(Date()).toString()
        }

    //27-Dec-2016 09:38 p.m.
    val currentTime: String
        get() {
            val df =
                SimpleDateFormat("dd-MMM-yyyy hh:mm a") //27-Dec-2016 09:38 p.m.
            return df.format(Date()).substring(11).trim { it <= ' ' }
        }

    fun hideKeyboard(activity: Activity) {
        val imm: InputMethodManager =
            activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        //Find the currently focused view, so we can grab the correct window token from it.
        var view = activity.currentFocus
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

}

interface SnackbarCallback {
    fun onSnackbarActionClick()
}