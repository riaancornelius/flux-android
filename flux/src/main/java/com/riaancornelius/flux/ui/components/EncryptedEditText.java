package com.riaancornelius.flux.ui.components;

import android.content.Context;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * User: riaan.cornelius
 */
public class EncryptedEditText extends EditText {

    public EncryptedEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setText(CharSequence text, boolean doDecrypt)
    {
        if (doDecrypt) {
            text = rot13Decode(text);
        }
        super.setText(text);
    }

    public Editable getText(boolean doEncrypt)
    {
        Editable e = super.getText();
        if (doEncrypt) {
            e = rot13Encode(e);
        }
        return e;
    }

    public static Editable rot13Encode(CharSequence input) {
        StringBuilder output = new StringBuilder();
        for (int i=0; i< input.length(); i++) {
            char c = input.charAt(i);
            if ((c < 32) || (c > 126)) {
                //outside our printable range so leave in tact
                output.append(c);
            } else {
                //shift
                c+=13;
                if (c > 126) {
                    //wrap
                    c-=((126 - 32) + 1);
                }
                output.append(c);
            }
        }
        return new SpannableStringBuilder(output);
    }

    public static CharSequence rot13Decode(CharSequence input) {
        StringBuilder output = new StringBuilder();
        for (int i=0; i< input.length(); i++) {
            char c = input.charAt(i);
            if ((c < 32) || (c > 126)) {
                //outside our printable range so leave in tact
                output.append(c);
            } else {
                //shift
                c-=13;
                if (c < 32) {
                    //wrap
                    c+=((126 - 32) + 1);
                }
                output.append(c);
            }
        }
        return output.toString();
    }
}
