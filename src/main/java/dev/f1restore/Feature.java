package dev.f1restore;
public enum Feature {
    HAND("Hand / held item"),
    HOTBAR("Hotbar / items"),
    CROSSHAIR("Crosshair"),
    STATUS("Survival bars"),
    XP("XP bar / level"),
    JUMP("Horse jump bar"),
    CHAT("Chat"),
    SCOREBOARD("Scoreboard sidebar"),
    TAB("Tab player list"),
    ITEM_NAME("Selected item name"),
    NOTICES("Action bar / music"),
    TITLES("Titles / subtitles"),
    BOSS("Boss bar"),
    DEBUG("F3 / debug"),
    NAMES("Player name tags"),
    OUTLINE("Block outline"),
    PUMPKIN("Pumpkin overlay"),
    VIGNETTE("Vignette"),
    NAUSEA("Portal overlay"),
    ADDON_HUDS("Evergreen / OneConfig widgets");
    public final String label;
    Feature(String label) { this.label = label; }
}
