/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package elaborate.tag_analysis.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import elaborate.tag_analysis.LinkData;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author lendle
 */
public class LinkDataLoader {
    public static List<LinkData> load(String filePath) throws Exception{
        BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "utf-8"));
        String json = input.readLine();
        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<LinkData>>() {
        }.getType();
        List<LinkData> list = new ArrayList<LinkData>();
        list = gson.fromJson(json, listType);
        input.close();
        return list;
    }
}
