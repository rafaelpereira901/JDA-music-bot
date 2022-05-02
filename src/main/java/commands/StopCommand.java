package commands;

import command.CommandContext;
import command.ICommand;
import lava_player.GuildMusicManager;
import lava_player.PlayerManager;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;

public class StopCommand implements ICommand {

	@Override
	public void handle(CommandContext ctx) {
		final TextChannel channel = ctx.getChannel();
		final Member self = ctx.getSelfMember();
		final GuildVoiceState selfVoiceState = self.getVoiceState();
		
		if (!selfVoiceState.inVoiceChannel()) {
			channel.sendMessage("I need to be on a voice channel!").queue();
			return;
		}
		
		final Member member = ctx.getMember();
		final GuildVoiceState memberVoiceState = member.getVoiceState();

		if(!memberVoiceState.inVoiceChannel()) {
			channel.sendMessage("You are not in a channel!").queue();
			return;
		}
		
		if(!memberVoiceState.getChannel().equals(selfVoiceState.getChannel())){
			channel.sendMessage("We need to be on the same channel!").queue();
			return;
		} 
		
		final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(ctx.getGuild());
		musicManager.scheduler.player.stopTrack();
		musicManager.scheduler.queue.clear();
		channel.sendMessage("The party is over! :rage:").queue();
		

		
	}

	@Override
	public String getName() {
		return "stop";
	}

}
