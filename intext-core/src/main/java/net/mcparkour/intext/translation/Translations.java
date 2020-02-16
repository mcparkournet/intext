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

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import net.mcparkour.unifig.annotation.Configuration;
import org.jetbrains.annotations.Nullable;

@Configuration("translations")
public class Translations {

	private Locale defaultLocale;
	private Map<String, Map<Locale, String>> texts;

	public Translations(Locale defaultLocale) {
		this(defaultLocale, new LinkedHashMap<>(2));
	}

	public Translations(Locale defaultLocale, Map<String, Map<Locale, String>> texts) {
		this.defaultLocale = defaultLocale;
		this.texts = texts;
	}

	public boolean hasTranslation(String translationId) {
		Map<Locale, String> translationsMap = this.texts.get(translationId);
		return translationsMap != null;
	}

	@Nullable
	public Translation getTranslation(String translationId) {
		Map<Locale, String> translationsMap = this.texts.get(translationId);
		if (translationsMap == null) {
			return null;
		}
		return new Translation(translationId, translationsMap);
	}

	public void addTranslation(Translation translation) {
		String translationId = translation.getTranslationId();
		Map<Locale, String> translations = translation.getTranslations();
		this.texts.put(translationId, translations);
	}

	public Locale getDefaultLocale() {
		return this.defaultLocale;
	}

	public void setDefaultLocale(Locale defaultLocale) {
		this.defaultLocale = defaultLocale;
	}

	public Map<String, Map<Locale, String>> getTexts() {
		return this.texts;
	}
}
