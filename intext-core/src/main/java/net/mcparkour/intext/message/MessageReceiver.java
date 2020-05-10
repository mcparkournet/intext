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
import net.kyori.text.Component;

public interface MessageReceiver {

	void receiveTranslatedComponent(String translationId, Object... arguments);

	void receiveTranslatedColorized(String translationId, Object... arguments);

	void receiveTranslated(String translationId, Object... arguments);

	void receiveComponent(Component component);

	void receiveSerializedComponent(String serializedComponent);

	default void receivePlainColorized(Iterable<String> messages) {
		for (String message : messages) {
			receivePlainColorized(message);
		}
	}

	default void receivePlainColorized(String... messages) {
		for (String message : messages) {
			receivePlainColorized(message);
		}
	}

	void receivePlainColorized(String message);

	default void receivePlain(Iterable<String> messages) {
		for (String message : messages) {
			receivePlain(message);
		}
	}

	default void receivePlain(String... messages) {
		for (String message : messages) {
			receivePlain(message);
		}
	}

	void receivePlain(String message);

	Locale getLanguage();
}
