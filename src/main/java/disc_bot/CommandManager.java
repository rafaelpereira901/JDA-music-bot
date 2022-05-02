package disc_bot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import command.CommandContext;
import command.ICommand;
import commands.JoinCommand;
import commands.LeaveCommand;
import commands.NowPlayingCommand;
import commands.PingCommand;
import commands.PlayCommand;
import commands.QueueCommand;
import commands.RepeatCommand;
import commands.SkipCommand;
import commands.StopCommand;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;



public class CommandManager {
	private final List<ICommand> commands = new ArrayList<>();
	
	public CommandManager() {
		addCommand(new PingCommand());
		
		addCommand(new JoinCommand());
		addCommand(new PlayCommand());
		addCommand(new StopCommand());
		addCommand(new SkipCommand());
		addCommand(new NowPlayingCommand());
		addCommand(new QueueCommand());
		addCommand(new LeaveCommand());
		addCommand(new RepeatCommand());
	}
	
	private void addCommand(  ICommand cmd) {
		boolean nameFound = this.commands.stream().anyMatch((it) -> it.getName().equalsIgnoreCase(cmd.getName()));
		
		if(nameFound) {
			 throw new IllegalArgumentException("A command with this name is already present");
		}
		
		commands.add(cmd);
	}
	
	private ICommand getCommand(String search) {
		String searcbLower = search.toLowerCase();
		for(ICommand cmd : this.commands) {
			if (cmd.getName().equals(searcbLower) || cmd.getAliases().contains(searcbLower)) {
				return cmd;
			}
		}
		return null; 
	}
	
	void handle(GuildMessageReceivedEvent event) {
        String[] split = event.getMessage().getContentRaw()
                .replaceFirst("(?i)" + Pattern.quote(Config.get("PREFIX")), "")
                .split("\\s+");

        String invoke = split[0].toLowerCase();
        ICommand cmd = this.getCommand(invoke);

        if (cmd != null) {
            event.getChannel().sendTyping().queue();
            List<String> args = Arrays.asList(split).subList(1, split.length);

            CommandContext ctx = new CommandContext(event, args);

            cmd.handle(ctx);
        }
    }
	
}
