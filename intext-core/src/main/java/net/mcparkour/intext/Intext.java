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

import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import net.mcparkour.intext.translation.TranslatedText;
import net.mcparkour.intext.translation.Translation;
import net.mcparkour.intext.translation.Translations;
import net.mcparkour.unifig.Configuration;
import net.mcparkour.unifig.ConfigurationFactory;
import net.mcparkour.unifig.SnakeyamlConfigurationFactory;
import net.mcparkour.unifig.options.Options;
import net.mcparkour.unifig.options.OptionsBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Intext {

	public static final Logger LOGGER = LoggerFactory.getLogger(Intext.class);

	private static final Locale DEFAULT_LANGUAGE = Locale.US;

	private Configuration<IntextConfiguration> configuration;

	public Intext() {
		this(Path.of(""));
	}

	public Intext(Path configurationDirectory) {
		this.configuration = createConfiguration(configurationDirectory);
	}

	private static Configuration<IntextConfiguration> createConfiguration(Path configurationDirectory) {
		ConfigurationFactory configurationFactory = new SnakeyamlConfigurationFactory();
		IntextConfiguration defaultConfiguration = new IntextConfiguration(DEFAULT_LANGUAGE, new LinkedHashMap<>(0));
		Options defaultOptions = configurationFactory.createOptions();
		Options options = new OptionsBuilder()
			.options(defaultOptions)
			.directoryPath(configurationDirectory)
			.build();
		return configurationFactory.createConfiguration(IntextConfiguration.class, defaultConfiguration, options);
	}

	public Translations loadTranslations() {
		IntextConfiguration configuration = this.configuration.read();
		Locale defaultLocale = configuration.getDefaultLanguage();
		Map<String, Map<Locale, String>> configurationTranslations = configuration.getTranslations();
		Map<String, Translation> translations = configurationTranslations.entrySet().stream()
			.collect(Collectors.toUnmodifiableMap(Entry::getKey, Intext::createTranslation));
		return new Translations(defaultLocale, translations);
	}

	private static Translation createTranslation(Entry<String, Map<Locale, String>> entry) {
		String translationId = entry.getKey();
		Map<Locale, String> map = entry.getValue();
		Map<Locale, TranslatedText> translatedTexts = map.entrySet().stream()
			.collect(Collectors.toUnmodifiableMap(Entry::getKey, TranslatedText::new));
		return new Translation(translationId, translatedTexts);
	}

	public void saveTranslations(Translations translations) {
		Locale defaultLocale = translations.getDefaultLanguage();
		Map<String, Translation> translationMap = translations.getTranslations();
		Map<String, Map<Locale, String>> translationsMap = translationMap.entrySet().stream()
			.collect(Collectors.toUnmodifiableMap(Entry::getKey, Intext::createTranslationMap));
		IntextConfiguration configuration = new IntextConfiguration(defaultLocale, translationsMap);
		this.configuration.write(configuration);
	}

	private static Map<Locale, String> createTranslationMap(Entry<String, Translation> translationsEntry) {
		Translation translation = translationsEntry.getValue();
		Map<Locale, TranslatedText> translatedTexts = translation.getTranslatedTexts();
		return translatedTexts.entrySet().stream()
			.collect(Collectors.toUnmodifiableMap(Entry::getKey, entry -> entry.getValue().getText()));
	}
}
