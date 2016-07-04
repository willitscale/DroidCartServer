package uk.co.n3tw0rk.droidcart.utils.common;

public class StringSupport {
    public static String attributeFromAccessor(String method) {
        method = method.replace("get", "").replace("set", "");
        char[] methodName = method.toCharArray();
        methodName[0] = Character.toLowerCase(methodName[0]);
        return new String(methodName);
    }
}
