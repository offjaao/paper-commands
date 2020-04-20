package me.thejaao.papercmd.managers;

import me.thejaao.papercmd.impl.Manager;
import me.thejaao.papercmd.models.PaperActivation;

public class PaperManager extends Manager<PaperActivation> {

    public PaperActivation getById(int identifier) {
        return get(paperActivation -> paperActivation.getIdentifier() == identifier).orElse(null);
    }

}
