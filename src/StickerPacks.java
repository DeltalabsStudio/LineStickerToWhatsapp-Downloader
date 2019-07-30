

import java.util.List;

public class StickerPacks {
    String android_play_store_link;
    String ios_app_store_link;
    
    // new
    List<sticker_packs> sticker_packs;

    public StickerPacks(String android_play_store_link, String ios_app_store_link, List<sticker_packs> sticker_packs) {
    	this.android_play_store_link = android_play_store_link;
    	this.ios_app_store_link 	 = ios_app_store_link;
    	this.sticker_packs			 = sticker_packs;
    }
    
    public static class sticker_packs {  
		String identifier;
        String name;
        String publisher;
        String tray_image_file;
        String publisher_email;
        String publisher_website;
        String privacy_policy_website;
        String license_agreement_website;
        
        // new
        List<stickers> stickers;

		sticker_packs(String identifier, String name, String publisher, String tray_image_file,
				String publisher_email, String publisher_website, String privacy_policy_website,
				String license_agreement_website, List<StickerPacks.stickers> stickers) {
			this.identifier				   = identifier;
			this.name 					   = name;
			this.publisher 				   = publisher;
			this.tray_image_file 		   = tray_image_file;
			this.publisher_email 		   = publisher_email;
			this.publisher_website 		   = publisher_website;
			this.privacy_policy_website    = privacy_policy_website;
			this.license_agreement_website = license_agreement_website;
			this.stickers = stickers;
		}
    }
    
    public static class stickers {
    	String image_file;

		stickers(String image_file) {

			this.image_file = image_file;
		}
    }
}


