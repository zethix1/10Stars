package fr.sio.a10stars.Reservation;

import android.text.InputFilter;
import android.text.Spanned;
import android.widget.EditText;

public class MaxLengthFilter implements InputFilter {
    private int mMaxLength;

    public MaxLengthFilter(int maxLength) {
        mMaxLength = maxLength;
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        int keep = mMaxLength - (dest.length() - (dend - dstart));
        if (keep <= 0) {
            return "";
        } else if (keep >= end - start) {
            return null; // Keep original
        } else {
            keep += start;
            if (Character.isHighSurrogate(source.charAt(keep - 1))) {
                --keep;
                if (keep == start) {
                    return "";
                }
            }
            return source.subSequence(start, keep);
        }
    }
}

