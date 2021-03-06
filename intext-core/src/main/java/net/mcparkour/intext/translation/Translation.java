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

public class Translation {

	private String translationId;
	private Map<Locale, TranslatedText> translatedTexts;

	public Translation(String translationId, Map<Locale, TranslatedText> translatedTexts) {
		this.translationId = translationId;
		this.translatedTexts = translatedTexts;
	}

	@Nullable
	public String getFormattedTranslatedText(Locale language, Object... arguments) {
		TranslatedText text = getTranslatedText(language);
		if (text == null) {
			return null;
		}
		return text.format(arguments);
	}

	public boolean hasTranslatedText(Locale language) {
		return this.translatedTexts.containsKey(language);
	}

	public Optional<TranslatedText> getTranslatedTextOptional(Locale language) {
		TranslatedText translatedText = getTranslatedText(language);
		return Optional.ofNullable(translatedText);
	}

	@Nullable
	public TranslatedText getTranslatedText(Locale language) {
		return this.translatedTexts.get(language);
	}

	public String getTranslationId() {
		return this.translationId;
	}

	public Map<Locale, TranslatedText> getTranslatedTexts() {
		return Map.copyOf(this.translatedTexts);
	}
}
