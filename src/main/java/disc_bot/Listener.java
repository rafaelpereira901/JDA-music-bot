package disc_bot;

import javax.annotation.Nonnull;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Listener extends ListenerAdapter{
	
	private final static String prefix = Config.get("PREFIX");
	 private final CommandManager manager = new CommandManager();
 
	@Override
	public void onReady(@Nonnull ReadyEvent event) {
		System.out.println("to pronto");
	}
	
	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		
		User user = event.getAuthor();
		
		if (user.isBot() || event.isWebhookMessage()) {
			return;
		}
		
		String raw = event.getMessage().getContentRaw();
		
		if(raw.equalsIgnoreCase(prefix + "shutdown") && user.getId().equals(Config.get("OWNER_ID"))) {
			event.getJDA().shutdown();
			return;
		}
		
		if(raw.equalsIgnoreCase(prefix + "jefer") && user.getId().equals(Config.get("OWNER_ID"))) {
			System.out.println("jeferson!");
			return;
		}
		
		if(raw.startsWith(prefix)) {
			manager.handle(event);			
		}
		
		
		
	}
	

	
	
	

}
