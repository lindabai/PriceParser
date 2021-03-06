
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class TaoBaoPriceParser extends PriceParser{

	private String urlString;
	
	
   public TaoBaoPriceParser(String url){
	   super(url, "view_price");
	   
   }
    
    

	public ArrayList<Integer> getAllIndex(String file, String str){
        ArrayList<Integer> allIndex = new ArrayList<Integer>();
        int i = file.indexOf(str) + 1;
        String temp;
        temp = file;
        
        while(i != -1){
        	allIndex.add(i);
        	temp = temp.substring(i+ str.length());
        	i = temp.indexOf(str);
        }
        
        return allIndex;
	}

		
    
    public String doGet() {
        URL localURL;
		try {
			localURL = new URL(urlString);
			URLConnection connection = localURL.openConnection();
	        HttpURLConnection httpURLConnection = (HttpURLConnection)connection;
	        
	        httpURLConnection.setRequestProperty("Accept-Charset", "utf-8");
	        httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
	        
	        InputStream inputStream = null;
	        InputStreamReader inputStreamReader = null;
	        BufferedReader reader = null;
	        StringBuffer resultBuffer = new StringBuffer();
	        String tempLine = null;
	        
	        if (httpURLConnection.getResponseCode() >= 300) {
	            throw new Exception("HTTP Request is not success, Response code is " + httpURLConnection.getResponseCode());
	        }
	        
	        try {
	            inputStream = httpURLConnection.getInputStream();
	            inputStreamReader = new InputStreamReader(inputStream);
	            reader = new BufferedReader(inputStreamReader);
	            
	            while ((tempLine = reader.readLine()) != null) {
	                resultBuffer.append(tempLine);
	            }
	            
	        } finally {
	            
	            if (reader != null) {
	                reader.close();
	            }
	            
	            if (inputStreamReader != null) {
	                inputStreamReader.close();
	            }
	            
	            if (inputStream != null) {
	                inputStream.close();
	            }
	            
	        }
	        
	        return resultBuffer.toString();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e){
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
        
    }
    
    public ArrayList<Double> getAllPrice(String file, ArrayList<Integer> index){
    	ArrayList<Double> prices = new ArrayList<Double>();
    	int sum = 0;
        for(int i = 0; i < index.size(); i++){
        	if(i != 0)
        		sum += "view_price".length();
        		sum += index.get(i);
        		prices.add(Double.parseDouble(file.substring((sum+13), file.indexOf("\"",sum+14))));
        }
		return prices;
    }
 
    
}