package org.example.Interface;

import net.dv8tion.jda.api.JDA;

public interface I_Plugin {
    void onEnable(JDA jda);
    void onDisable();
}
