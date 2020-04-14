/*
 * MIT License
 *
 * Copyright (c) 2020 MCParkour
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package net.mcparkour.intext.message;

import java.util.Locale;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.player.PlayerSettings;
import net.mcparkour.intext.translation.Translations;

public class VelocityMessageReceiverFactory implements MessageReceiverFactory<CommandSource> {

	public static final LanguageProvider<Player> PLAYER_LOCALE_PROVIDER = VelocityMessageReceiverFactory::getPlayerLocale;

	private static Locale getPlayerLocale(Player player) {
		PlayerSettings settings = player.getPlayerSettings();
		return settings.getLocale();
	}

	@Override
	public MessageReceiver createMessageReceiver(Translations translations, CommandSource receiver) {
		if (receiver instanceof Player) {
			Player player = (Player) receiver;
			return createMessageReceiver(translations, player);
		}
		Locale defaultLanguage = translations.getDefaultLanguage();
		return createMessageReceiver(translations, receiver, defaultLanguage);
	}

	public MessageReceiver createMessageReceiver(Translations translations, Player receiver) {
		return createMessageReceiver(translations, receiver, PLAYER_LOCALE_PROVIDER);
	}

	@Override
	public <U extends CommandSource> MessageReceiver createMessageReceiver(Translations translations, U receiver, LanguageProvider<U> provider) {
		Locale language = provider.provide(receiver);
		return createMessageReceiver(translations, receiver, language);
	}

	@Override
	public MessageReceiver createMessageReceiver(Translations translations, CommandSource receiver, Locale language) {
		return new VelocityMessageReceiver(translations, language, receiver);
	}
}
