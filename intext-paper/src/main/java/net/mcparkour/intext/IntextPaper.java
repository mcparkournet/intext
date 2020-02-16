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

package net.mcparkour.intext;

import java.util.Locale;
import net.mcparkour.intext.translation.TranslatedText;
import net.mcparkour.intext.translation.Translation;
import net.mcparkour.intext.translation.Translations;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

public class IntextPaper extends Intext {

	private LocaleProvider<Player> playerLocaleProvider;

	public IntextPaper(LocaleProvider<Player> playerLocaleProvider) {
		this.playerLocaleProvider = playerLocaleProvider;
	}

	public void sendMessage(ConsoleCommandSender receiver, String translationId, Object... arguments) {
		sendMessage(receiver, localized -> DEFAULT_LOCALE, translationId, arguments);
	}

	public void sendMessage(Player receiver, String translationId, Object... arguments) {
		sendMessage(receiver, this.playerLocaleProvider, translationId, arguments);
	}

	private <T extends CommandSender> void sendMessage(T receiver, LocaleProvider<T> localeProvider, String translationId, Object... arguments) {
		Translation translation = getTranslation(translationId);
		if (translation == null) {
			sendMissingTranslationMessage(receiver, translationId);
			return;
		}
		Locale locale = localeProvider.provide(receiver);
		TranslatedText translatedText = getTranslatedText(translation, locale);
		if (translatedText == null) {
			sendMissingTranslatedTextMessage(receiver, locale);
			return;
		}
		String text = translatedText.getText();
		String formattedText = String.format(locale, text, arguments);
		receiver.sendMessage(formattedText);
	}

	private void sendMissingTranslationMessage(CommandSender receiver, String translationId) {
		receiver.sendMessage(ChatColor.RED + "Missing translation for the identifier " + ChatColor.GRAY + translationId + ChatColor.RED + ".");
	}

	private void sendMissingTranslatedTextMessage(CommandSender receiver, Locale locale) {
		receiver.sendMessage(ChatColor.RED + "Missing translated text for the locale " + ChatColor.GRAY + locale + ChatColor.RED + " and for the default locale " + ChatColor.GRAY + getDefaultLocale() + ChatColor.RED + ".");
	}

	@Nullable
	private Translation getTranslation(String id) {
		Translations translations = getTranslations();
		return translations.getTranslation(id);
	}

	@Nullable
	private TranslatedText getTranslatedText(Translation translation, Locale locale) {
		TranslatedText translatedText = translation.getTranslatedText(locale);
		if (translatedText == null) {
			Locale defaultLocale = getDefaultLocale();
			return translation.getTranslatedText(defaultLocale);
		}
		return translatedText;
	}

	private Locale getDefaultLocale() {
		Translations translations = getTranslations();
		return translations.getDefaultLocale();
	}
}
