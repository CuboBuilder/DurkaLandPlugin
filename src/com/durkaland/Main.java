package com.durkaland;

import arc.util.*;
import mindustry.game.EventType.*;
import mindustry.gen.*;
import mindustry.mod.*;
import mindustry.net.Administration.*;

public class Main extends Plugin {

    // called when game initializes
    @Override
    public void init() {
        // listen for player join events
        Events.on(PlayerJoin.class, event -> {
            // Send a welcome message to the player
            Call.sendMessage("[lightgray]Welcome, [accent]" + event.player.name + "[lightgray]! Type /team <teamid> to change your team.");
        });
    }

    // register server-side commands
    @Override
    public void registerServerCommands(CommandHandler handler) {
        // Register the /team command for server-side usage
        handler.register("team", "<teamid>", "Change your team.", (args, player) -> {
            try {
                // Parse the team ID argument
                int teamID = Integer.parseInt(args[0]);

                // Ensure the team ID is within the valid range
                if (teamID < 0 || teamID >= Team.all.length) {
                    player.sendMessage("[scarlet]Invalid team ID! Please use a number between 0 and " + (Team.all.length - 1));
                    return;
                }

                // Set the player's team
                player.team(Team.all[teamID]);
                player.sendMessage("[green]You have successfully switched to team: " + Team.all[teamID].name);
            } catch (NumberFormatException e) {
                // Handle invalid input (non-numeric or out of range)
                player.sendMessage("[scarlet]Invalid team ID! Please enter a valid number.");
            }
        });
    }

    // register client-side commands (for in-game player usage)
    @Override
    public void registerClientCommands(CommandHandler handler) {
        // Register a simple say command that sends a message to the chat
        handler.<Player>register("say", "<text...>", "Say something in chat.", (args, player) -> {
            player.sendMessage(args[0]);
        });
    }
}
