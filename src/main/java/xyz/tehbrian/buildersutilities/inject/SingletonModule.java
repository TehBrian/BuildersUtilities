package xyz.tehbrian.buildersutilities.inject;

import com.google.inject.AbstractModule;
import xyz.tehbrian.buildersutilities.armorcolor.ArmorColorInventoryProvider;
import xyz.tehbrian.buildersutilities.banner.provider.BannerBaseInventoryProvider;
import xyz.tehbrian.buildersutilities.banner.provider.BannerColorInventoryProvider;
import xyz.tehbrian.buildersutilities.banner.provider.BannerPatternInventoryProvider;
import xyz.tehbrian.buildersutilities.config.ConfigConfig;
import xyz.tehbrian.buildersutilities.config.LangConfig;
import xyz.tehbrian.buildersutilities.config.SpecialConfig;
import xyz.tehbrian.buildersutilities.ability.AbilityInventoryProvider;
import xyz.tehbrian.buildersutilities.user.UserService;
import xyz.tehbrian.restrictionhelper.spigot.SpigotRestrictionHelper;

public class SingletonModule extends AbstractModule {

    @Override
    protected void configure() {
        this.bind(UserService.class).asEagerSingleton();
        this.bind(SpigotRestrictionHelper.class).asEagerSingleton();

        this.bind(ConfigConfig.class).asEagerSingleton();
        this.bind(LangConfig.class).asEagerSingleton();
        this.bind(SpecialConfig.class).asEagerSingleton();

        this.bind(ArmorColorInventoryProvider.class).asEagerSingleton();
        this.bind(AbilityInventoryProvider.class).asEagerSingleton();
        this.bind(BannerBaseInventoryProvider.class).asEagerSingleton();
        this.bind(BannerColorInventoryProvider.class).asEagerSingleton();
        this.bind(BannerPatternInventoryProvider.class).asEagerSingleton();
    }

}
