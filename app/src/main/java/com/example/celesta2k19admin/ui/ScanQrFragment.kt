package com.example.celesta2k19admin.ui


import android.Manifest
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.navigateUp
import com.example.celesta2k19admin.R
import com.google.zxing.Result
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import me.dm7.barcodescanner.zxing.ZXingScannerView

class ScanQrFragment : Fragment(), ZXingScannerView.ResultHandler {

    private lateinit var mScannerView: ZXingScannerView
    private var sourceFragment: String? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        if (arguments?.getString("fragment_source") != null) {
            sourceFragment = arguments?.getString("fragment_source").toString()
        }
        Dexter.withActivity(activity)
            .withPermission(Manifest.permission.CAMERA)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                    Toast.makeText(
                        context,
                        "Camera Permission Granted",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onPermissionRationaleShouldBeShown(
                    permission: PermissionRequest?,
                    token: PermissionToken?
                ) {
                    Toast.makeText(context, "Give Camera Permission", Toast.LENGTH_SHORT)
                        .show()
                    token?.continuePermissionRequest()
                }

                override fun onPermissionDenied(response: PermissionDeniedResponse?) {
                    Toast.makeText(
                        context,
                        "Camera Permission Denied",
                        Toast.LENGTH_LONG
                    ).show()
                    if (sourceFragment == "accommodation")
                        findNavController().navigate(R.id.nav_accommodation, null)
                    else if (sourceFragment == "checkin-checkout")
                        findNavController().navigate(R.id.nav_checkin_checkout_user, null)
                }
            })
            .check()

        mScannerView = ZXingScannerView(activity)
        return mScannerView
    }

    override fun onResume() {
        super.onResume()
        mScannerView.setResultHandler(this)
        mScannerView.startCamera()
    }

    override fun onPause() {
        super.onPause()
        mScannerView.stopCamera()
    }

    override fun handleResult(result: Result?) {
        Log.e("qr: ", result!!.text)
        val str = result!!.text.split("/")
        val bundle = bundleOf("celestaid" to str[0])
        Log.e("str: ", str[0])
        if (sourceFragment == "accommodation")
            findNavController().navigate(R.id.nav_accommodation, bundle)
        else if (sourceFragment == "checkin-checkout")
            findNavController().navigate(R.id.nav_checkin_checkout_user, bundle)
    }
}

