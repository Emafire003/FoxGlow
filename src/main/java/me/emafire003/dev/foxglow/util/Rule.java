package me.emafire003.dev.foxglow.util;

import net.minecraft.world.level.GameRules;

//Code based off https://gitlab.com/HorribleNerd/moregamerules and slightly modified by me, Emafire003
public class Rule {

    private final String name;
    private final GameRules.Category category;
    private GameRules.Key rule;

    public Rule(String name, GameRules.Category category) {

        this.name = name;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public GameRules.Category getCategory() {
        return category;
    }

    public GameRules.Key getRule() {
        return rule;
    }

    public void setRule(GameRules.Key ruleKey) {
        this.rule = ruleKey;
    }

    public static class BooleanRule extends Rule {

        private final boolean defaultValue;

        public BooleanRule(String name, GameRules.Category category, boolean defaultValue) {
            super(name, category);
            this.defaultValue = defaultValue;
        }

        @Override
        public GameRules.Key<GameRules.BooleanValue> getRule() {
            return (GameRules.Key<GameRules.BooleanValue>) super.getRule();
        }

        public boolean getDefaultValue() {
            return defaultValue;
        }

    }

    public static class IntegerRule extends Rule {

        private final int defaultValue;

        public IntegerRule(String name, GameRules.Category category, int defaultValue) {
            super(name, category);
            this.defaultValue = defaultValue;
        }

        @Override
        public GameRules.Key<GameRules.IntegerValue> getRule() {
            return (GameRules.Key<GameRules.IntegerValue>) super.getRule();
        }

        public int getDefaultValue() {
            return defaultValue;
        }

    }

}
