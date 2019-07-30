import static java.lang.System.out;

import java.io.IOException;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;

public class ImageProcessing {
	static Statics statics = new Statics();

	public ImageProcessing() {
		// TODO Auto-generated constructor stub
	}
	
	public void pngToWebpConverter(String mainFolder, String stickerName, String part, int height, int width, String mode) throws ExecuteException, IOException {
        String folderPath = mainFolder + "\\" + stickerName + "\\" + part;
        String h = Integer.toString(height);
        String w = Integer.toString(width);
        String command = null;

        if(mode.equals("sticker")) {
        	command = "cd " + folderPath + " && convert *.png -resize " + h + "x" + w + "! %d.webp";
        }else if(mode.equals("logo")) {
        	command = "cd " + folderPath + " && convert tray_sticker.png -resize " + h + "x" + w + "! tray_sticker.webp";
        }
        
		CommandLine cmdLine = new CommandLine("cmd.exe");
    	cmdLine.addArgument("/c");
        cmdLine.addArgument(command,false);
        
        out.println(statics.dateTime() + "Converting PNG to WEBP..");
        out.println(statics.dateTime() + stickerName + ", Part: " + part);
        
        DefaultExecutor executor = new DefaultExecutor();
        executor.execute(cmdLine);
        
		out.println(statics.dateTime() + "Finished convert!");
    }
	
	public void deletePng(String mainFolder, String stickerName, String part, String mode) throws ExecuteException, IOException {
    	String folderPath = mainFolder + "\\" + stickerName + "\\" + part;
    	String command = null;
    	
    	if(mode.equals("sticker")) {
    		command = "cd " + folderPath + " && del /s /q /f *.png > NUL";
    	}else if(mode.equals("logo")) {
    		command = "cd " + folderPath + " && del /s /q /f tray_sticker.png > NUL";
    	}
    	
    	CommandLine cmdLine = new CommandLine("cmd.exe");
    	cmdLine.addArgument("/c");
        cmdLine.addArgument(command,false);
        
        out.println(statics.dateTime() + "Deleting old files..");
        out.println(statics.dateTime() + stickerName + ", Part: " + part);
        
        DefaultExecutor executor = new DefaultExecutor();
        executor.execute(cmdLine);
        
		out.println(statics.dateTime() + "Finished clean up!");
    }

}
