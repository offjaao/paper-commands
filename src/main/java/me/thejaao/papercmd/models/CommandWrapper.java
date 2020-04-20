package me.thejaao.papercmd.models;

public class CommandWrapper {

    private String raw;

    public CommandWrapper(String raw) {
        this.raw = raw;
    }

    public String format(String name) {
		return raw.replace("@player", name);
    }


}
