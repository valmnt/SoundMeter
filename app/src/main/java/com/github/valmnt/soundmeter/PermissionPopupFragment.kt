package com.github.valmnt.soundmeter

import android.Manifest
import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.fragment.app.DialogFragment
import com.github.valmnt.soundmeter.SoundMeterActivity.Companion.REQUEST_CODE_PERMISSION

class PermissionPopupFragment: DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
                .setTitle(R.string.permission_popup_title)
                .setPositiveButton(R.string.permission_popup_ok) { _, _ ->
                    ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_CODE_PERMISSION)
                }
                .setNegativeButton(R.string.permission_popup_ko) { _, _ ->

                }
                .show()
    }
}