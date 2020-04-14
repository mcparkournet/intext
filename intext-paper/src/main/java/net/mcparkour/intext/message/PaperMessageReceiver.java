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
import net.kyori.text.serializer.gson.GsonComponentSerializer;
import net.mcparkour.intext.translation.Translations;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class PaperMessageReceiver extends AbstractMessageReceiver {

	private CommandSender receiver;

	public PaperMessageReceiver(Translations translations, Locale language, CommandSender receiver) {
		super(translations, language);
		this.receiver = receiver;
	}

	@Override
	public void receiveComponent(Component component) {
		String serialized = GsonComponentSerializer.INSTANCE.serialize(component);
		receiveSerializedComponent(serialized);
	}

	@Override
	public void receiveSerializedComponent(String serializedComponent) {
		BaseComponent[] baseComponents = ComponentSerializer.parse(serializedComponent);
		receiveComponent(baseComponents);
	}

	public void receiveComponent(BaseComponent[] components) {
		this.receiver.sendMessage(components);
	}

	@Override
	public void receivePlainColorized(String message) {
		String colorized = ChatColor.translateAlternateColorCodes('&', message);
		receivePlain(colorized);
	}

	@Override
	public void receivePlain(String message) {
		this.receiver.sendMessage(message);
	}

	public CommandSender getReceiver() {
		return this.receiver;
	}
}
