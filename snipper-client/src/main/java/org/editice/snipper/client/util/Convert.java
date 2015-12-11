package org.editice.snipper.client.util;

/**
 * @author tinglang(editice@gmail.com) on 15/12/11.
 */
public class Convert {

    public static int asInt(Object o) {
        if (o == null) {
            return 0;
        }
        String tmp = String.valueOf(o);
        return Integer.parseInt(tmp);
    }

    public static int asInt(Object o, int defaultValue) {
        if (o == null) {
            return defaultValue;
        }
        String tmp = String.valueOf(o);
        return Integer.parseInt(tmp);
    }
}
