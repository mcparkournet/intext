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
import net.mcparkour.intext.translation.Translations;
import net.mcparkour.unifig.Configuration;
import net.mcparkour.unifig.ConfigurationFactory;
import net.mcparkour.unifig.SnakeyamlConfigurationFactory;

public class Intext {

	protected static final Locale DEFAULT_LOCALE = Locale.US;

	private Configuration<Translations> configuration;
	private Translations translations;

	public Intext() {
		this.configuration = createConfiguration();
		this.translations = this.configuration.read();
	}

	private static Configuration<Translations> createConfiguration() {
		ConfigurationFactory configurationFactory = new SnakeyamlConfigurationFactory();
		Translations defaultTranslations = new Translations(DEFAULT_LOCALE);
		return configurationFactory.createConfiguration(Translations.class, defaultTranslations);
	}

	public void loadTranslations() {
		this.translations = this.configuration.read();
	}

	public void saveTranslations() {
		this.configuration.write(this.translations);
	}

	protected Translations getTranslations() {
		return this.translations;
	}
}
