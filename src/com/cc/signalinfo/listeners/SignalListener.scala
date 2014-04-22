/*
 *
 * Copyright (c) 2013 Wes Lanning, http://codingcreation.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 * http://www.opensource.org/licenses/mit-license.php
 * /
 */
package com.cc.signalinfo.listeners

import android.telephony.PhoneStateListener
import android.telephony.SignalStrength
import android.util.Log
import com.cc.signalinfo.util.SignalArrayWrapper

/**
 * Private helper class to listener for network signal changes.
 */
object SignalListener
{

    /**
     * Notifies activities and fragments of signal changes.
     */
    trait UpdateSignal
    {
        /**
         * Set the data to return to the caller here
         *
         * @param signalStrength - data to return
         */
        def setData(signalStrength: SignalArrayWrapper)
    }

}

class SignalListener(listener: SignalListener.UpdateSignal) extends PhoneStateListener
{
    private final val TAG     : String                      = getClass.getSimpleName
    private var signalWrapper: SignalArrayWrapper = null

    /**
     * Get the Signal strength from the provider, each time there is an update
     *
     * @param signalStrength - has all the useful signal stuff in it.
     */
    override def onSignalStrengthsChanged(signalStrength: SignalStrength)
    {
        super.onSignalStrengthsChanged(signalStrength)
        if (signalStrength != null) {
            Log.d(TAG, "getting sig strength")
            Log.d(TAG, signalStrength.toString)

            if (signalWrapper == null) {
                signalWrapper = new SignalArrayWrapper(signalStrength.toString)
            }
            // TODO: make filterSignals only accessible to this class
            signalWrapper.filterSignals(signalStrength.toString)
            listener.setData(signalWrapper)
        }
    }


}

