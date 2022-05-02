package commands;

import command.CommandContext;
import command.ICommand;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.managers.AudioManager;

public class JoinCommand implements ICommand {

	@Override
	public void handle(CommandContext ctx) {
		
		final TextChannel channel = ctx.getChannel();
		final Member self = ctx.getSelfMember();
		final GuildVoiceState selfVoiceState = self.getVoiceState();
		
		if(selfVoiceState.inVoiceChannel()) {
			channel.sendMessage("I'm already on a channel!").queue();
			return;			
		}
		
		final Member member = ctx.getMember();
		final GuildVoiceState memberVoiceState = member.getVoiceState();
		
		if(!memberVoiceState.inVoiceChannel()) {
			channel.sendMessage("You are not in a channel!").queue();
			return;
		}
		
		final AudioManager audioManager = ctx.getGuild().getAudioManager();
		final VoiceChannel memberChannel = memberVoiceState.getChannel();
		
		audioManager.openAudioConnection(memberChannel);
		channel.sendMessageFormat("Entering voice channel `\uD83D\uDD0A %s`", memberChannel.getName()).queue();

	}

	@Override
	public String getName() {
		return "join";
	}

}
