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

package net.mcparkour.intext.translation;

import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import org.jetbrains.annotations.Nullable;

public class Translations {

	private Locale defaultLanguage;
	private Map<String, Translation> translations;

	public Translations(Locale defaultLanguage, Map<String, Translation> translations) {
		this.defaultLanguage = defaultLanguage;
		this.translations = translations;
	}

	@Nullable
	public String getFormattedTranslation(String translationId, Locale language, Object... arguments) {
		Translation translation = getTranslation(translationId);
		if (translation == null) {
			return null;
		}
		String text = translation.getFormattedTranslatedText(language, arguments);
		if (text == null) {
			text = translation.getFormattedTranslatedText(this.defaultLanguage, arguments);
		}
		return text;
	}

	@Nullable
	public TranslatedText getTranslation(String translationId, Locale language) {
		Translation translation = getTranslation(translationId);
		if (translation == null) {
			return null;
		}
		TranslatedText text = translation.getTranslatedText(language);
		if (text == null) {
			text = translation.getTranslatedText(this.defaultLanguage);
		}
		return text;
	}

	public boolean hasTranslation(String translationId) {
		return this.translations.containsKey(translationId);
	}

	public Optional<Translation> getTranslationOptional(String translationId) {
		Translation translation = getTranslation(translationId);
		return Optional.ofNullable(translation);
	}

	@Nullable
	public Translation getTranslation(String translationId) {
		return this.translations.get(translationId);
	}

	public Locale getDefaultLanguage() {
		return this.defaultLanguage;
	}

	public Map<String, Translation> getTranslations() {
		return Map.copyOf(this.translations);
	}
}
