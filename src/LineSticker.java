import static java.lang.System.out;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

/**
 * 28 July 2019 10:06 PM
 */

/**
 * 
 * LINE Sticker Downloader to Whatsapp Sticker
 * 
 * @author Snow Bases (https://github.com/SnowBases)
 *
 */
public class LineSticker {
	final static String mainFolder = "C:\\WhatsappSticker";
	final static String platform = "iphone";
	
	static String[] packageId = new String[] {
		"7770679",  
		"7294158",
		"7998180"
	};
	
	static Statics statics = new Statics();
	static ImageProcessing imageProcessing = new ImageProcessing();
	
	static int identifier = 0;
	
	public LineSticker() {
		// Silence is golden
	}

	/**
	 * @param args
	 * @throws IOException 
	 * @throws JsonSyntaxException 
	 * @throws JsonIOException 
	 */
	public static void main(String[] args) throws JsonIOException, JsonSyntaxException, IOException {
		// TODO Auto-generated method stub
		
		for(int i = 0; i <= (packageId.length-1); i++) {
		    // May be an array, may be an object. 
		    JsonObject rootObject = getMetadata(packageId[i]).getAsJsonObject();
			
		    // Save stickers to folder
		    firedRequest(rootObject);
		}
	}
	
	public static void firedRequest(JsonObject rootObject) throws IOException {
		for(int i = 0; i <= rootObject.getAsJsonArray("stickers").size()-1 ; i++) {
	    	if(i < (rootObject.getAsJsonArray("stickers").size()/2)) {
	    		// 1 IDs
	    		if( i == 0 ) {
	    			out.println('\n' + rootObject.get("title").getAsJsonObject().get("en").getAsString() + " (Part 1): " + '\n');
	    			identifier++;
	    		}
	    	} else if((i+1) > (rootObject.getAsJsonArray("stickers").size()/2) && i < rootObject.getAsJsonArray("stickers").size()) {
	    		// Part 2 IDs
	    		if( i == (rootObject.getAsJsonArray("stickers").size()/2) ) {
	    			out.println('\n' + rootObject.get("title").getAsJsonObject().get("en").getAsString() + " (Part 2): " + '\n');
	    			identifier++;
	    		}
	    	}
	    	
    		// Ensure have folder path
    		createStickerFolder(
    			mainFolder, 
    			rootObject.get("title").getAsJsonObject().get("en").getAsString(), 
    			String.valueOf(identifier)
    		);
    		
    		// Save stickers
    		saveImage(
				mainFolder, 
				rootObject.get("packageId").getAsString(),
				rootObject.getAsJsonArray("stickers").get(i).getAsJsonObject().get("id").getAsString(), 
				rootObject.getAsJsonArray("stickers").get(i).getAsJsonObject().get("id").getAsString(), 
				rootObject.get("title").getAsJsonObject().get("en").getAsString(), 
				String.valueOf(identifier), 
				"sticker", 
				platform,
				".png" // Change this if format has been changed
    		);
    		
    		// Print info
    		out.println(statics.dateTime() + "Saved " + rootObject.getAsJsonArray("stickers").get(i).getAsJsonObject().get("id").getAsString() + ".png" + " - " + (i+1));
	    }
		

		System.out.println(identifier);

		for(int i = (identifier-2); i < identifier; i++) {
			imageProcessing.pngToWebpConverter(
				mainFolder, 
				rootObject.get("title").getAsJsonObject().get("en").getAsString(), 
				String.valueOf(i+1), 
				512, 
				512, 
				"sticker"
			);
			
			imageProcessing.deletePng(
				mainFolder, 
				rootObject.get("title").getAsJsonObject().get("en").getAsString(), 
				String.valueOf(i+1), 
				"sticker"
			);
		}
		
		out.println("Finished!");
	}
	
	public static JsonElement getMetadata(String currentStickerId) throws IOException {
		String sURL = "http://dl.stickershop.line.naver.jp/products/0/0/1/" + currentStickerId + "/iphone/productInfo.meta";

	    // Connect to the URL using java's native library
	    URL url = new URL(sURL);
	    URLConnection request = url.openConnection();
	    request.connect();

	    // Convert to a JSON object to print data
	    JsonParser jsonParser = new JsonParser();
	    JsonElement root = jsonParser.parse(new InputStreamReader((InputStream) request.getContent()));
	    return root;
	}
	
	public static void saveImage(String savePath, String packageId, String stickerId, String stickerSaveName, String currentStickerName, String part, String mode, String platform, String imageFormat) throws IOException {
		String imageUrl = null;
		String destinationFile = null;
		if(mode.equals("sticker")) {
			imageUrl = "http://dl.stickershop.line.naver.jp/products/0/0/1/" + packageId + "/" + platform + "/stickers/" + stickerId + "@2x.png";
			destinationFile = savePath + "\\" + currentStickerName + "\\" + part + "\\" + stickerSaveName + imageFormat;
		} else if(mode.equals("logo")) {
			imageUrl = "http://dl.stickershop.line.naver.jp/products/0/0/1/" + packageId+ "/" + platform + "/main@2x.png";
			destinationFile = savePath + "\\" + currentStickerName + "\\" + part + "\\" + stickerSaveName + imageFormat;
		}
		openStreamAndSave(imageUrl, destinationFile);
	}
	
	public static void openStreamAndSave(String imageUrl, String destinationFile) throws IOException {
		URL url = new URL(imageUrl);
		InputStream is = url.openStream();
		OutputStream os = new FileOutputStream(destinationFile);

		byte[] b = new byte[2048];
		int length;

		while ((length = is.read(b)) != -1) {
			os.write(b, 0, length);
		}
		is.close();
		os.close();
	}
	
	public static void createStickerFolder(String savePath, String stickerFolderName, String part) throws IOException {
		File files = new File(savePath + "\\" + stickerFolderName + "\\" + part); 
		if (!files.exists()) {
		    if (files.mkdirs()) {
		        out.println(statics.dateTime() + "Folder '" + stickerFolderName + "\\" + part + "' are created!");
		        out.println(statics.dateTime() + "Data will be saved inside '" + savePath + "\\" +  stickerFolderName + "\\" + part);
		    }
		}
	}
}
