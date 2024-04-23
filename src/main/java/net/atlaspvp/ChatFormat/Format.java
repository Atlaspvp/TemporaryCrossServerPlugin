package net.atlaspvp.ChatFormat;

import net.atlaspvp.crossservertest.Configuration;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentBuilder;
import net.kyori.adventure.text.ComponentLike;
import net.kyori.adventure.text.TextComponent;

import java.util.ArrayList;
import java.util.List;

public class Format {

    private static final String plMessage = "{message}";
    private static final String plPlayer = "{player}";
    private static final String plSep = "{seperator}";

    private static Component join(Component player, Component message) {
        return player.append(message);
    }
}
