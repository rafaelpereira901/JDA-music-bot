package commands;

import org.apache.commons.validator.routines.UrlValidator;

import command.CommandContext;
import command.ICommand;
import lava_player.PlayerManager;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

public class PlayCommand implements ICommand {

	@Override
	public void handle(CommandContext ctx) {
		final TextChannel channel = ctx.getChannel();
		
		if(ctx.getArgs().isEmpty()) {
			channel.sendMessage("The command is `!play <link>`").queue();
			return;
		}
		
		String link = String.join(" ", ctx.getArgs());
		final Member self = ctx.getSelfMember();
		final GuildVoiceState selfVoiceState = self.getVoiceState();
		
		
		if (!selfVoiceState.inVoiceChannel()) {
			JoinCommand jc = new JoinCommand();
			jc.handle(ctx);			
		}
		
		final Member member = ctx.getMember();
		final GuildVoiceState memberVoiceState = member.getVoiceState();

		if(!memberVoiceState.inVoiceChannel()) {
			channel.sendMessage("You are not in a channel!").queue();
			return;
		}

		UrlValidator urlValidator = new UrlValidator();
		if(!urlValidator.isValid(link)) {
			link = "ytsearch:" + link; 
		} 
		
		PlayerManager.getInstance().loadAndPlay(channel, link);
		
	}

	@Override
	public String getName() {
		return "play";
	}


}
