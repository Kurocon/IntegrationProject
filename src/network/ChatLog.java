package network;

import java.util.Collection;
import java.util.HashMap;

public class ChatLog implements Log {
	HashMap<Long, ChatLogElement> log = new HashMap<Long, ChatLogElement>();

	@Override
	public void addElement(LogElement le) {
		if (le instanceof ChatLogElement) {
			ChatLogElement cle = (ChatLogElement) le;
			log.put(cle.getIndex(), cle);
		}
	}

	@Override
	public LogElement getElement(long t) {
		return this.log.get(t);
	}

	@Override
	public LogElement[] getAllElements() {
		Collection<ChatLogElement> values = log.values();
		LogElement[] array = values.toArray(new ChatLogElement[values.size()]);
		return array;
	}

}
