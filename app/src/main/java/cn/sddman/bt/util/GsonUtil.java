package cn.sddman.bt.util;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import cn.sddman.bt.mvp.e.MagnetRule;


/**
 * author  dengyuhan
 * created 2018/3/7 14:00
 */
public class GsonUtil {
    private static Gson gson = new Gson();

    public static Map<String, MagnetRule> getMagnetRule(Context context, String path) {
         Map<String, MagnetRule> magnetRuleMap = new LinkedHashMap<String, MagnetRule>();
            List<MagnetRule> rules = getRule(context,path);
            for (MagnetRule rule : rules) {
                magnetRuleMap.put(rule.getSite(), rule);
            }
        return magnetRuleMap;
    }
    public static Map<String, MagnetRule> getMagnetRule(List<MagnetRule> rules) {
        Map<String, MagnetRule> magnetRuleMap = new LinkedHashMap<String, MagnetRule>();
        for (MagnetRule rule : rules) {
            magnetRuleMap.put(rule.getSite(), rule);
        }
        return magnetRuleMap;
    }
    public static  List<MagnetRule> getRule(Context context,String path){
        InputStream inputStream = null;
        try {
            inputStream = context.getAssets().open(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<MagnetRule> rules = GsonUtil.fromJson(inputStream, new TypeToken<List<MagnetRule>>() {
        });
        return rules;
    }
    public static  List<MagnetRule> getRule(String jsonText){
        List<MagnetRule> rules = GsonUtil.fromJson(jsonText, new TypeToken<List<MagnetRule>>() {
        });
        return rules;
    }

    public static <T> T fromJson(InputStream inputStream, TypeToken<T> token) {
        try {
            return gson.fromJson(new InputStreamReader(inputStream,"UTF-8"), token.getType());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static <T> T fromJson(String jsonText, TypeToken<T> token) {
        try {
            return gson.fromJson(jsonText, token.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
