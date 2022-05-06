package xyz.tehbrian.buildersutilities.inject;

import com.google.inject.AbstractModule;
import xyz.tehbrian.buildersutilities.ability.AbilityMenuProvider;
import xyz.tehbrian.buildersutilities.armorcolor.ArmorColorMenuProvider;
import xyz.tehbrian.buildersutilities.banner.provider.BannerBaseMenuProvider;
import xyz.tehbrian.buildersutilities.banner.provider.BannerColorMenuProvider;
import xyz.tehbrian.buildersutilities.banner.provider.BannerPatternMenuProvider;
import xyz.tehbrian.buildersutilities.config.ConfigConfig;
import xyz.tehbrian.buildersutilities.config.LangConfig;
import xyz.tehbrian.buildersutilities.config.SpecialConfig;
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

        this.bind(ArmorColorMenuProvider.class).asEagerSingleton();
        this.bind(AbilityMenuProvider.class).asEagerSingleton();
        this.bind(BannerBaseMenuProvider.class).asEagerSingleton();
        this.bind(BannerColorMenuProvider.class).asEagerSingleton();
        this.bind(BannerPatternMenuProvider.class).asEagerSingleton();
    }

}
