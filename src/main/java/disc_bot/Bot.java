package disc_bot;

import javax.security.auth.login.LoginException;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.utils.Compression;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

public class Bot extends ListenerAdapter {
	
	private Bot() throws LoginException {
		
		JDABuilder builder = JDABuilder.createDefault(Config.get("TOKEN"));
	    builder.disableCache(CacheFlag.MEMBER_OVERRIDES);
	    builder.setBulkDeleteSplittingEnabled(false);
	    builder.setCompression(Compression.NONE);
	    builder.setActivity(Activity.listening("!play <link>"));
		builder.addEventListeners(new Listener());		
		builder.build();    
	}
	
	

	public static void main(String[] args) throws LoginException {
		
	    new Bot();   
	    
	}
	


}
