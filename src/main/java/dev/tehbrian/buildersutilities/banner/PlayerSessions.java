package dev.tehbrian.buildersutilities.banner;

import com.google.inject.Inject;
import dev.tehbrian.buildersutilities.banner.menu.BaseMenuProvider;
import dev.tehbrian.buildersutilities.banner.menu.ColorMenuProvider;
import dev.tehbrian.buildersutilities.banner.menu.DoneMenuProvider;
import dev.tehbrian.buildersutilities.banner.menu.PatternMenuProvider;
import dev.tehbrian.buildersutilities.config.LangConfig;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public final class PlayerSessions {

	private final BaseMenuProvider baseProvider;
	private final ColorMenuProvider colorProvider;
	private final PatternMenuProvider patternProvider;
	private final DoneMenuProvider doneProvider;
	private final LangConfig langConfig;
	private final Map<Player, Session> sessions = new HashMap<>();

	@Inject
	public PlayerSessions(
			final BaseMenuProvider baseProvider,
			final ColorMenuProvider colorProvider,
			final PatternMenuProvider patternProvider,
			final DoneMenuProvider doneProvider,
			final LangConfig langConfig
	) {
		this.baseProvider = baseProvider;
		this.colorProvider = colorProvider;
		this.patternProvider = patternProvider;
		this.doneProvider = doneProvider;
		this.langConfig = langConfig;
	}

	public Session get(final Player player) {
		return this.sessions.computeIfAbsent(player, (p) -> this.newSession());
	}

	public void wipe(final Player player) {
		this.sessions.put(player, this.newSession());
	}

	private Session newSession() {
		return new Session(this.baseProvider, this.colorProvider, this.patternProvider, this.doneProvider, this.langConfig);
	}

}
