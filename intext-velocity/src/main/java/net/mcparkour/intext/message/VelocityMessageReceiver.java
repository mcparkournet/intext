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
import com.velocitypowered.api.command.CommandSource;
import net.kyori.text.Component;
import net.kyori.text.TextComponent;
import net.kyori.text.serializer.gson.GsonComponentSerializer;
import net.kyori.text.serializer.legacy.LegacyComponentSerializer;
import net.mcparkour.intext.translation.Translations;

public class VelocityMessageReceiver extends AbstractMessageReceiver {

	private static final LegacyComponentSerializer LEGACY_COMPONENT_SERIALIZER = LegacyComponentSerializer.legacy();

	private CommandSource receiver;

	public VelocityMessageReceiver(Translations translations, Locale language, CommandSource receiver) {
		super(translations, language);
		this.receiver = receiver;
	}

	@Override
	public void receiveComponent(Component component) {
		this.receiver.sendMessage(component);
	}

	@Override
	public void receiveSerializedComponent(String serializedComponent) {
		Component component = GsonComponentSerializer.INSTANCE.deserialize(serializedComponent);
		receiveComponent(component);
	}

	@Override
	public void receivePlainColorized(String message) {
		TextComponent component = LEGACY_COMPONENT_SERIALIZER.deserialize(message, '&');
		receiveComponent(component);
	}

	@Override
	public void receivePlain(String message) {
		TextComponent component = LEGACY_COMPONENT_SERIALIZER.deserialize(message);
		receiveComponent(component);
	}

	public CommandSource getReceiver() {
		return this.receiver;
	}
}
