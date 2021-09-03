package xyz.tehbrian.buildersutilities.user;

import dev.tehbrian.tehlib.paper.user.PaperUserService;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.UUID;

public final class UserService extends PaperUserService<User> {

    @Override
    public @NonNull User getUser(@NonNull final UUID uuid) {
        return this.userMap.computeIfAbsent(uuid, User::new);
    }

}
