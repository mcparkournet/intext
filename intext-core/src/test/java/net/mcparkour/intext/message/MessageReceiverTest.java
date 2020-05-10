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

import java.nio.file.Path;
import java.util.Locale;
import net.mcparkour.intext.Intext;
import net.mcparkour.intext.Resources;
import net.mcparkour.intext.translation.Translations;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MessageReceiverTest {

	private static final Locale POLISH = new Locale("pl", "PL");

	private MessageReceiverFactory<TestReceiver> receiverFactory;

	@BeforeEach
	public void setUp() {
		Path path = Resources.getResourcePath("translations.yml");
		Path parent = path.getParent();
		Intext intext = new Intext(parent);
		Translations translations = intext.loadTranslations();
		this.receiverFactory = new TestMessageReceiverFactory(translations, TestReceiver::getLanguage);
	}

	@Test
	public void testReceiver() {
		TestReceiver testReceiver = new TestReceiver(Locale.US);
		MessageReceiver receiver = this.receiverFactory.createMessageReceiver(testReceiver);
		receiver.receiveTranslatedColorized("foo");
		receiver.receiveTranslatedColorized("foo");
		Assertions.assertEquals("foo en", testReceiver.getLastMessage());
		Assertions.assertEquals("foo en", testReceiver.getLastMessage());
	}

	@Test
	public void testReceiverWithLanguageChange() {
		TestReceiver testReceiver = new TestReceiver(Locale.US);
		MessageReceiver receiver = this.receiverFactory.createMessageReceiver(testReceiver);
		receiver.receiveTranslated("foo");
		testReceiver.setLanguage(POLISH);
		receiver.receiveTranslated("foo");
		Assertions.assertEquals("foo en", testReceiver.getLastMessage());
		Assertions.assertEquals("foo en", testReceiver.getLastMessage());
	}

	@Test
	public void testReceiverMessageWithArguments() {
		TestReceiver testReceiver = new TestReceiver(Locale.US);
		MessageReceiver receiver = this.receiverFactory.createMessageReceiver(testReceiver);
		receiver.receiveTranslated("foo-args", "foo", "bar");
		receiver.receiveTranslated("foo-args", "bar", "foo");
		Assertions.assertEquals("foo en foo bar", testReceiver.getLastMessage());
		Assertions.assertEquals("foo en bar foo", testReceiver.getLastMessage());
	}

	@Test
	public void testReceiverMessageWithArgumentsSecondLanguage() {
		TestReceiver testReceiver = new TestReceiver(POLISH);
		MessageReceiver receiver = this.receiverFactory.createMessageReceiver(testReceiver);
		receiver.receiveTranslated("foo-args", "foo", "bar");
		receiver.receiveTranslated("foo-args", "bar", "foo");
		Assertions.assertEquals("foo pl foo bar", testReceiver.getLastMessage());
		Assertions.assertEquals("foo pl bar foo", testReceiver.getLastMessage());
	}
}
