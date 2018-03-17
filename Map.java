import java.net.*;
import java.io.*;
import org.json.*;
import javax.swing.*;

public class Map {
	Map(){} 
        /*获取该地点经纬度*/
	static String get_Lngtdd_Latttring address) throws Exception{
		URL url = new URL("http://restapi.amap.com/v3/geocode/geo?ad");
		BufferedReader a = new BufferedReader(new InputStreamReader(url.openStream(),"UTF-8"));
		String result="";
		result = a.readLine();
		//System.out.println(result);
		String s="";
		try{
			JSONObject json1 = new JSONObject(result);
			String s1 = json1.getString("geocodes");
			JSONArray json2 = new JSONArray(s1);
			s = json2.getJSONObject(0).getString("location");
		}		
		catch(Exception e){System.out.println(e);}
		return s;
	}
        /*获取路径中地点经纬度*/
	public static void get_Line_ZuoBiad(String address) throws Exception{
		URL url = new URL("http://restapi.amap.com/v3/geocode/geo?address="+URLEncoder.encode(address,"UTF-8")+"&output=json&key=e55a843992773d15743660029ccf2f9a");
		BufferedReader a = new BufferedReader(new InputStreamReader(url.openStream(),"UTF-8"));
		String result="";
		result = a.readLine();
		//System.out.println(result);
		String s="";
		try{
			JSONObject json1 = new JSONObject(result);
			String s1 = json1.getString("geocodes");
			JSONArray json2 = new JSONArray(s1);
			s = json2.getJSONObject(0).getString("location");
		}		
		catch(Exception e){System.out.println(e);}
		return s;
	}
        /*获取路径中地点经纬度*/
	public static void get_Line_ZuoBiao(String orgn,String dstntn) throws Exception{
		URL a = new URL("http://restapi.amap.com/v3/direction/driving?origin="+orgn+"&destination="+dstntn+"&extensions=base&output=json&key=e55a843992773d15743660029ccf2f9a");                  
        BufferedReader b = new BufferedReader(new InputStreamReader(a.openStream(),"UTF-8"));
        BufferedWriter c = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("路径坐标.txt")));
        String p = b.readLine();
        try{
            JSONObject json = new JSONObject(p);
            String q1 = json.getString("route");
            JSONObject json2 = new JSONObject(q1);
            String q2 = json2.getString("paths");
            JSONArray json3 = new JSONArray(q2);
            //String q = json3.getJSONObject(0).getString("distance");
            //c.write(q+"\r\n");
            String q3 = json3.getJSONObject(0).getString("steps");
            JSONArray json4 = new JSONArray(q3);
            for(int i=0;i < json4.length();i++){
                 JSONObject d = json4.getJSONObject(i);
                 String polyline = d.getString("polyline");
                 int index = polyline.indexOf(";");
                 c.write(polyline.substring(0,index)+"\r\n"); 
            }
        }                  
        catch(Exception e){System.out.println(e);}
        //System.out.println(p);       
        b.close(); 
        c.close();
	}
        /*获取路径信息*/
	public static void get_Line_Message(String orgn,String dstntn)throws Exception{
		URL a = new URL("http://restapi.amap.com/v3/direction/driving?origin="+orgn+"&destination="+dstntn+"&extensions=base&output=json&key=e55a843992773d15743660029ccf2f9a");                  
        BufferedReader b = new BufferedReader(new InputStreamReader(a.openStream(),"UTF-8"));
        BufferedWriter c = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("路径信息.txt")));
        String p = b.readLine();
        try{
            JSONObject json = new JSONObject(p);
            String q1 = json.getString("route");
            JSONObject json2 = new JSONObject(q1);
            String q2 = json2.getString("paths");
            JSONArray json3 = new JSONArray(q2);
            String q3 = json3.getJSONObject(0).getString("steps");
            JSONArray json4 = new JSONArray(q3);
            for(int i=0;i < json4.length();i++){
                 JSONObject d = json4.getJSONObject(i);
                 String polyline = d.getString("instruction");   
                 String distance = d.getString("distance");
                 c.write(i+" "+polyline+"("+distance+")\r\n");
            }
        }                  
        catch(Exception e){System.out.println(e);}
        //System.out.println(p);       
        b.close(); 
        c.close();
	}
}
