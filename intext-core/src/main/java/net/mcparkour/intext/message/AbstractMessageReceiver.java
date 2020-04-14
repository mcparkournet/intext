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
import net.kyori.text.TextComponent;
import net.mcparkour.intext.Intext;
import net.mcparkour.intext.translation.TranslatedText;
import net.mcparkour.intext.translation.Translation;
import net.mcparkour.intext.translation.Translations;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractMessageReceiver implements MessageReceiver {

	private Translations translations;
	private Locale language;

	public AbstractMessageReceiver(Translations translations, Locale language) {
		this.translations = translations;
		this.language = language;
	}

	@Override
	public void receiveTranslated(String translationId, Object... arguments) {
		String message = getMessage(translationId, arguments);
		if (message == null) {
			return;
		}
		receivePlain(message);
	}

	@Override
	public void receiveTranslatedColorized(String translationId, Object... arguments) {
		String message = getMessage(translationId, arguments);
		if (message == null) {
			return;
		}
		receivePlainColorized(message);
	}

	@Override
	public void receiveTranslatedComponent(String translationId, Object... arguments) {
		String message = getMessage(translationId, arguments);
		if (message == null) {
			return;
		}
		receiveSerializedComponent(message);
	}

	@Nullable
	private String getMessage(String translationId, Object... arguments) {
		Translation translation = this.translations.getTranslation(translationId);
		if (translation == null) {
			Intext.LOGGER.warn("Missing translation for the identifier {}.", translationId);
			TextComponent message = Messages.createMissingTranslationMessage(translationId);
			receiveComponent(message);
			return null;
		}
		TranslatedText translatedText = getTranslatedText(translation);
		if (translatedText == null) {
			String languageName = this.language.getDisplayLanguage();
			Locale defaultLanguage = this.translations.getDefaultLanguage();
			String defaultLanguageName = defaultLanguage.getDisplayLanguage();
			Intext.LOGGER.warn("Missing translated text for the {} and {} languages.", languageName, defaultLanguageName);
			TextComponent message = Messages.createMissingTranslatedTextMessage(languageName, defaultLanguageName);
			receiveComponent(message);
			return null;
		}
		return translatedText.format(arguments);
	}

	@Nullable
	private TranslatedText getTranslatedText(Translation translation) {
		TranslatedText translatedText = translation.getTranslatedText(this.language);
		if (translatedText == null) {
			Locale defaultLocale = this.translations.getDefaultLanguage();
			return translation.getTranslatedText(defaultLocale);
		}
		return translatedText;
	}

	@Override
	public Translations getTranslations() {
		return this.translations;
	}

	@Override
	public Locale getLanguage() {
		return this.language;
	}
}
