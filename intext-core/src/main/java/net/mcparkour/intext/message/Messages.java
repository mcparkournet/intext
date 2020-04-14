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

import net.kyori.text.TextComponent;
import net.kyori.text.format.TextColor;

public final class Messages {

	private Messages() {
		throw new UnsupportedOperationException("Cannot create an instance of this class");
	}

	public static TextComponent createMissingTranslationMessage(String translationId) {
		return TextComponent.builder()
			.append(TextComponent.builder()
				.content("Missing translation for the identifier ")
				.color(TextColor.RED)
				.build())
			.append(TextComponent.builder()
				.content(translationId)
				.color(TextColor.GRAY)
				.build())
			.append(TextComponent.builder()
				.content(".")
				.color(TextColor.RED)
				.build())
			.build();
	}

	public static TextComponent createMissingTranslatedTextMessage(String languageName) {
		return TextComponent.builder()
			.append(TextComponent.builder()
				.content("Missing translated text for the ")
				.color(TextColor.RED)
				.build())
			.append(TextComponent.builder()
				.content(languageName)
				.color(TextColor.GRAY)
				.build())
			.append(TextComponent.builder()
				.content(" language.")
				.color(TextColor.RED)
				.build())
			.build();
	}

	public static TextComponent createMissingTranslatedTextMessage(String languageName, String defaultLanguageName) {
		return TextComponent.builder()
			.append(TextComponent.builder()
				.content("Missing translated text for the ")
				.color(TextColor.RED)
				.build())
			.append(TextComponent.builder()
				.content(languageName)
				.color(TextColor.GRAY)
				.build())
			.append(TextComponent.builder()
				.content(" and ")
				.color(TextColor.RED)
				.build())
			.append(TextComponent.builder()
				.content(defaultLanguageName)
				.color(TextColor.GRAY)
				.build())
			.append(TextComponent.builder()
				.content(" languages.")
				.color(TextColor.RED)
				.build())
			.build();
	}
}
