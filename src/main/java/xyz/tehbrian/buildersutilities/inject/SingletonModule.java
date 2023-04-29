package xyz.tehbrian.buildersutilities.inject;

import com.google.inject.AbstractModule;
import dev.tehbrian.restrictionhelper.spigot.SpigotRestrictionHelper;
import xyz.tehbrian.buildersutilities.ability.AbilityMenuProvider;
import xyz.tehbrian.buildersutilities.armorcolor.ArmorColorMenuProvider;
import xyz.tehbrian.buildersutilities.banner.PlayerSessions;
import xyz.tehbrian.buildersutilities.banner.menu.BaseMenuProvider;
import xyz.tehbrian.buildersutilities.banner.menu.ColorMenuProvider;
import xyz.tehbrian.buildersutilities.banner.menu.DoneMenuProvider;
import xyz.tehbrian.buildersutilities.banner.menu.PatternMenuProvider;
import xyz.tehbrian.buildersutilities.config.ConfigConfig;
import xyz.tehbrian.buildersutilities.config.LangConfig;
import xyz.tehbrian.buildersutilities.config.SpecialConfig;
import xyz.tehbrian.buildersutilities.user.UserService;

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
