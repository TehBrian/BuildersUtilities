package dev.tehbrian.buildersutilities.inject;

import com.google.inject.AbstractModule;
import dev.tehbrian.buildersutilities.ability.AbilityMenuProvider;
import dev.tehbrian.buildersutilities.armorcolor.ArmorColorMenuProvider;
import dev.tehbrian.buildersutilities.banner.PlayerSessions;
import dev.tehbrian.buildersutilities.banner.menu.BaseMenuProvider;
import dev.tehbrian.buildersutilities.banner.menu.ColorMenuProvider;
import dev.tehbrian.buildersutilities.banner.menu.DoneMenuProvider;
import dev.tehbrian.buildersutilities.banner.menu.PatternMenuProvider;
import dev.tehbrian.buildersutilities.config.ConfigConfig;
import dev.tehbrian.buildersutilities.config.LangConfig;
import dev.tehbrian.buildersutilities.config.SpecialConfig;
import dev.tehbrian.buildersutilities.user.UserService;
import dev.tehbrian.restrictionhelper.spigot.SpigotRestrictionHelper;

public final class SingletonModule extends AbstractModule {

  @Override
  protected void configure() {
    this.bind(UserService.class).asEagerSingleton();
    this.bind(SpigotRestrictionHelper.class).asEagerSingleton();

    this.bind(ConfigConfig.class).asEagerSingleton();
    this.bind(LangConfig.class).asEagerSingleton();
    this.bind(SpecialConfig.class).asEagerSingleton();

    this.bind(ArmorColorMenuProvider.class).asEagerSingleton();
    this.bind(AbilityMenuProvider.class).asEagerSingleton();

    this.bind(BaseMenuProvider.class).asEagerSingleton();
    this.bind(ColorMenuProvider.class).asEagerSingleton();
    this.bind(PatternMenuProvider.class).asEagerSingleton();
    this.bind(DoneMenuProvider.class).asEagerSingleton();
    this.bind(PlayerSessions.class).asEagerSingleton();
  }

}
